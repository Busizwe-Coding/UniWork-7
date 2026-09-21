const mongoose = require("mongoose");

// Mirrors the "User" entity in the Part 1 design document, Section 6.1.
// passwordHash stores the result of hashing (with bcrypt) the hash the
// Android client already sent — see routes/auth.js for the full
// two-stage hashing explanation.
const userSchema = new mongoose.Schema(
  {
    name: { type: String, required: true, trim: true },
    email: { type: String, required: true, unique: true, lowercase: true, trim: true },
    passwordHash: { type: String, required: true },
    preferredLanguage: { type: String, default: "en" },
    notificationsEnabled: { type: Boolean, default: true },
    fcmTokens: [{ type: String }], // registered devices, for push notifications (POE phase)
  },
  { timestamps: true },
);

module.exports = mongoose.model("User", userSchema);
