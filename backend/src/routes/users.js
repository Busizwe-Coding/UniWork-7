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
    notificationFrequency: user.notificationFrequency,
  };
}

// GET /users/me
router.get("/me", async (req, res) => {
  const user = await User.findById(req.userId);
  if (!user) return res.status(404).json({ error: "User not found" });
  res.json(toUserDto(user));
});

// PATCH /users/me (and POST /users/me/settings, used by the Android client because
// HttpURLConnection has patchy PATCH support) - backs the Settings screen: language,
// notifications on/off, notification frequency (daily/weekly/monthly/off) and name.
async function updateSettings(req, res) {
  const { preferredLanguage, notificationsEnabled, notificationFrequency, name } = req.body;
  const update = {};
  if (preferredLanguage !== undefined) update.preferredLanguage = preferredLanguage;
  if (notificationsEnabled !== undefined) update.notificationsEnabled = notificationsEnabled;
  if (["daily", "weekly", "monthly", "off"].includes(notificationFrequency)) update.notificationFrequency = notificationFrequency;
  if (typeof name === "string" && name.trim()) update.name = name.trim();

  const user = await User.findByIdAndUpdate(req.userId, update, { new: true });
  if (!user) return res.status(404).json({ error: "User not found" });

  console.log(`[users] ${user._id} updated settings:`, update);
  res.json(toUserDto(user));
}
router.patch("/me", updateSettings);
router.post("/me/settings", updateSettings);

// DELETE /users/me - permanent account deletion (required by Google Play for apps with accounts).
// Removes the user and every workout they own.
router.delete("/me", async (req, res) => {
  const Workout = require("../models/Workout");
  const removed = await Workout.deleteMany({ userId: req.userId });
  await User.findByIdAndDelete(req.userId);
  console.log(`[users] deleted account ${req.userId} and ${removed.deletedCount} workout(s)`);
  res.status(204).send();
});

module.exports = router;
