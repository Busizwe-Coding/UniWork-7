const mongoose = require("mongoose");

// "User" entity from the Part 1 design document (Section 6.1).
// passwordHash is the bcrypt hash of the hash the Android client sent (see routes/auth.js);
// it is empty for Google (SSO) accounts, which never have a password.
const userSchema = new mongoose.Schema(
  {
    name: { type: String, required: true, trim: true },
    email: { type: String, required: true, unique: true, lowercase: true, trim: true },
    passwordHash: { type: String, default: "" },
    ssoProvider: { type: String, enum: ["email", "google"], default: "email" },
    preferredLanguage: { type: String, default: "en" },
    notificationsEnabled: { type: Boolean, default: true },
    // daily | weekly | monthly | off  (chosen on the app's Push notifications page)
    notificationFrequency: { type: String, enum: ["daily", "weekly", "monthly", "off"], default: "weekly" },
    fcmTokens: [{ type: String }], // registered devices for push notifications
  },
  { timestamps: true },
);

module.exports = mongoose.model("User", userSchema);
