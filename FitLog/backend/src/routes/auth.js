const express = require("express");
const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const User = require("../models/User");

const router = express.Router();

/**
 * Password encryption strategy (see also app/.../data/security/PasswordHasher.kt):
 *
 *  1. The Android app hashes the user's raw password with SHA-256 + a
 *     client pepper *before* it ever leaves the device, over an HTTPS
 *     connection.
 *  2. This server treats that value as "the password" and hashes it AGAIN
 *     with bcrypt (which adds a random per-user salt and a slow, tunable
 *     work factor) before storing it. Only this final bcrypt hash is
 *     persisted — never a reversible or plain-text form of the password.
 *
 * This means neither a database leak nor a network capture (even without
 * TLS) exposes the user's actual typed password.
 */

function issueToken(user) {
  return jwt.sign({ userId: user._id.toString() }, process.env.JWT_SECRET, { expiresIn: "30d" });
}

function toUserDto(user) {
  return {
    id: user._id.toString(),
    name: user.name,
    email: user.email,
    preferredLanguage: user.preferredLanguage,
    notificationsEnabled: user.notificationsEnabled,
  };
}

// POST /auth/register
router.post("/register", async (req, res) => {
  try {
    const { name, email, passwordHash } = req.body;
    if (!name || !email || !passwordHash) {
      return res.status(400).json({ error: "name, email and passwordHash are required" });
    }

    const existing = await User.findOne({ email: email.toLowerCase() });
    if (existing) {
      return res.status(409).json({ error: "An account with this email already exists" });
    }

    const storedHash = await bcrypt.hash(passwordHash, 10);
    const user = await User.create({ name, email: email.toLowerCase(), passwordHash: storedHash });

    console.log(`[auth] registered new user ${user._id} (${user.email})`);
    res.status(201).json({ token: issueToken(user), user: toUserDto(user) });
  } catch (err) {
    console.error("[auth] register failed:", err.message);
    res.status(500).json({ error: "Registration failed" });
  }
});

// POST /auth/login
router.post("/login", async (req, res) => {
  try {
    const { email, passwordHash } = req.body;
    if (!email || !passwordHash) {
      return res.status(400).json({ error: "email and passwordHash are required" });
    }

    const user = await User.findOne({ email: email.toLowerCase() });
    if (!user) {
      return res.status(401).json({ error: "Invalid email or password" });
    }

    const matches = await bcrypt.compare(passwordHash, user.passwordHash);
    if (!matches) {
      return res.status(401).json({ error: "Invalid email or password" });
    }

    console.log(`[auth] user ${user._id} logged in`);
    res.json({ token: issueToken(user), user: toUserDto(user) });
  } catch (err) {
    console.error("[auth] login failed:", err.message);
    res.status(500).json({ error: "Login failed" });
  }
});

// POST /auth/verify — POE phase: verifies a Firebase SSO ID token (Google
// Sign-In) instead of a password, using the Firebase Admin SDK, then
// creates/finds the matching User exactly as register/login do above.
// Left as a stub here so the intended shape is clear.
router.post("/verify", async (req, res) => {
  res.status(501).json({
    error: "SSO verification is implemented in the final POE build (see Part 1 design doc, Section 2.2).",
  });
});

module.exports = router;
