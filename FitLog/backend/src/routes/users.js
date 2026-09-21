const express = require("express");
const User = require("../models/User");
const { requireAuth } = require("../middleware/auth");

const router = express.Router();
router.use(requireAuth);

function toUserDto(user) {
  return {
    id: user._id.toString(),
    name: user.name,
    email: user.email,
    preferredLanguage: user.preferredLanguage,
    notificationsEnabled: user.notificationsEnabled,
  };
}

// GET /users/me
router.get("/me", async (req, res) => {
  const user = await User.findById(req.userId);
  if (!user) return res.status(404).json({ error: "User not found" });
  res.json(toUserDto(user));
});

// PATCH /users/me — backs the app's Settings screen (notifications on/off,
// language). Only the fields provided are updated.
router.patch("/me", async (req, res) => {
  const { preferredLanguage, notificationsEnabled } = req.body;
  const update = {};
  if (preferredLanguage !== undefined) update.preferredLanguage = preferredLanguage;
  if (notificationsEnabled !== undefined) update.notificationsEnabled = notificationsEnabled;

  const user = await User.findByIdAndUpdate(req.userId, update, { new: true });
  if (!user) return res.status(404).json({ error: "User not found" });

  console.log(`[users] ${user._id} updated settings:`, update);
  res.json(toUserDto(user));
});

module.exports = router;
