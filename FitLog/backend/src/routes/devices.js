const express = require("express");
const User = require("../models/User");
const { requireAuth } = require("../middleware/auth");

const router = express.Router();
router.use(requireAuth);

// POST /devices/register — stores this device's FCM token so the backend
// knows where to deliver push notifications (workout reminders, weekly
// recap). Real-time push is a POE-phase requirement; this endpoint is
// ready now so the client integration in the final build is a small
// addition rather than a new API surface.
router.post("/register", async (req, res) => {
  const { fcmToken } = req.body;
  if (!fcmToken) return res.status(400).json({ error: "fcmToken is required" });

  await User.findByIdAndUpdate(req.userId, { $addToSet: { fcmTokens: fcmToken } });
  console.log(`[devices] registered FCM token for user ${req.userId}`);
  res.status(201).json({ registered: true });
});

module.exports = router;
