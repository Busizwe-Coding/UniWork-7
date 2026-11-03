const express = require("express");
const User = require("../models/User");
const { sendToUser, isReady } = require("../push");
const { requireAuth } = require("../middleware/auth");

const router = express.Router();
router.use(requireAuth);

// POST /devices/register - stores this device's FCM token so we know where to deliver push notifications.
router.post("/register", async (req, res) => {
  const { fcmToken } = req.body;
  if (!fcmToken) return res.status(400).json({ error: "fcmToken is required" });
  await User.findByIdAndUpdate(req.userId, { $addToSet: { fcmTokens: fcmToken } });
  console.log(`[devices] registered FCM token for user ${req.userId}`);
  res.status(201).json({ registered: true });
});

// POST /devices/unregister - called on log out so the next person on the phone doesn't get this user's pushes.
router.post("/unregister", async (req, res) => {
  const { fcmToken } = req.body;
  if (fcmToken) await User.findByIdAndUpdate(req.userId, { $pull: { fcmTokens: fcmToken } });
  res.json({ unregistered: true });
});

// POST /devices/test - sends a test push to the caller's devices (handy for the demo video).
router.post("/test", async (req, res) => {
  if (!isReady()) return res.status(503).json({ error: "Push is not configured on the server" });
  const user = await User.findById(req.userId);
  const delivered = await sendToUser(user, "FitLog test notification 🔔", "Real-time push is working.", { type: "test" });
  res.json({ delivered });
});

module.exports = router;
