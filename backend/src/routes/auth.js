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
    notificationFrequency: user.notificationFrequency,
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

    if (!user.passwordHash) {
      return res.status(401).json({ error: "This account uses Google sign-in" });
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

// POST /auth/google - Single sign-on. The app obtains a Google ID token with Credential Manager and
// sends it here. We verify it with Google (signature, expiry and that it was issued for OUR web
// client id), then find or create the matching user and return our own session token.
const { OAuth2Client } = require("google-auth-library");
router.post("/google", async (req, res) => {
  try {
    const { idToken } = req.body;
    if (!idToken) return res.status(400).json({ error: "idToken is required" });
    if (!process.env.GOOGLE_CLIENT_ID) return res.status(503).json({ error: "Google sign-in is not configured on the server" });

    const client = new OAuth2Client(process.env.GOOGLE_CLIENT_ID);
    const ticket = await client.verifyIdToken({ idToken, audience: process.env.GOOGLE_CLIENT_ID });
    const payload = ticket.getPayload();
    if (!payload.email || !payload.email_verified) return res.status(401).json({ error: "Google account email is not verified" });

    const email = payload.email.toLowerCase();
    let user = await User.findOne({ email });
    if (!user) {
      user = await User.create({ name: payload.name || email.split("@")[0], email, passwordHash: "", ssoProvider: "google" });
      console.log(`[auth] created Google user ${user._id} (${email})`);
    } else {
      console.log(`[auth] Google sign-in for existing user ${user._id}`);
    }
    res.json({ token: issueToken(user), user: toUserDto(user) });
  } catch (err) {
    console.error("[auth] google verify failed:", err.message);
    res.status(401).json({ error: "Invalid Google sign-in" });
  }
});

module.exports = router;
