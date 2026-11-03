const admin = require("firebase-admin");
const User = require("./models/User");

/**
 * Firebase Cloud Messaging sender (real-time push notifications).
 * Needs the env var FIREBASE_SERVICE_ACCOUNT = the full JSON of a Firebase service-account key.
 * If it is missing the server still runs; pushes are simply skipped (and logged).
 */
let ready = false;
try {
  if (process.env.FIREBASE_SERVICE_ACCOUNT) {
    admin.initializeApp({ credential: admin.credential.cert(JSON.parse(process.env.FIREBASE_SERVICE_ACCOUNT)) });
    ready = true;
    console.log("[push] Firebase Admin initialised");
  } else {
    console.warn("[push] FIREBASE_SERVICE_ACCOUNT not set - push notifications disabled");
  }
} catch (err) {
  console.error("[push] Firebase init failed:", err.message);
}

/** Send a notification to every registered device of one user. Returns number of devices reached. */
async function sendToUser(user, title, body, data = {}) {
  if (!ready || !user.fcmTokens || user.fcmTokens.length === 0) return 0;
  const res = await admin.messaging().sendEachForMulticast({
    tokens: user.fcmTokens,
    notification: { title, body },
    data: Object.fromEntries(Object.entries(data).map(([k, v]) => [k, String(v)])),
    android: { priority: "high", notification: { channelId: "fitlog_default" } },
  });
  // Drop tokens Firebase says are dead (app uninstalled, token rotated)
  const dead = [];
  res.responses.forEach((r, i) => {
    const code = r.error && r.error.code;
    if (code === "messaging/registration-token-not-registered" || code === "messaging/invalid-registration-token") dead.push(user.fcmTokens[i]);
  });
  if (dead.length) await User.findByIdAndUpdate(user._id, { $pull: { fcmTokens: { $in: dead } } });
  console.log(`[push] ${res.successCount}/${user.fcmTokens.length} delivered to ${user._id}: "${title}"`);
  return res.successCount;
}

module.exports = { sendToUser, isReady: () => ready };
