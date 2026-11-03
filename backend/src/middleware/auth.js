const jwt = require("jsonwebtoken");

// Every endpoint except /auth/register and /auth/login requires a valid
// session token in the Authorization header, per the Part 1 design doc,
// Section 4: "All endpoints require the application session token in an
// Authorization header, except /auth/verify itself." (Here, /auth/login
// and /auth/register play that role for the Part 2 prototype, since SSO's
// /auth/verify is a POE-phase addition — see routes/auth.js.)
function requireAuth(req, res, next) {
  const header = req.headers.authorization;
  if (!header || !header.startsWith("Bearer ")) {
    return res.status(401).json({ error: "Missing or malformed Authorization header" });
  }

  const token = header.substring("Bearer ".length);
  try {
    const payload = jwt.verify(token, process.env.JWT_SECRET);
    req.userId = payload.userId;
    next();
  } catch (err) {
    return res.status(401).json({ error: "Invalid or expired session token" });
  }
}

module.exports = { requireAuth };
