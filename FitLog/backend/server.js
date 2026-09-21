require("dotenv").config();
const express = require("express");
const cors = require("cors");
const mongoose = require("mongoose");

const authRoutes = require("./src/routes/auth");
const userRoutes = require("./src/routes/users");
const workoutRoutes = require("./src/routes/workouts");
const exerciseRoutes = require("./src/routes/exercises");
const deviceRoutes = require("./src/routes/devices");

const app = express();
app.use(cors());
app.use(express.json());

// Simple request logger — demonstrates the "make use of logging" submission
// requirement on the backend side (the Android app logs via android.util.Log
// / Retrofit's HttpLoggingInterceptor on the client side).
app.use((req, res, next) => {
  console.log(`${new Date().toISOString()} ${req.method} ${req.originalUrl}`);
  next();
});

app.get("/", (req, res) => {
  res.json({ status: "ok", service: "FitLog API", time: new Date().toISOString() });
});

app.use("/auth", authRoutes);
app.use("/users", userRoutes);
app.use("/workouts", workoutRoutes);
app.use("/exercises", exerciseRoutes);
app.use("/devices", deviceRoutes);

// Catch-all error handler so a bad request never crashes the process or
// leaks a stack trace to the client.
app.use((err, req, res, next) => {
  console.error("[unhandled]", err);
  res.status(500).json({ error: "Internal server error" });
});

const PORT = process.env.PORT || 3000;

mongoose
  .connect(process.env.MONGODB_URI)
  .then(() => {
    console.log("[db] connected to MongoDB Atlas");
    app.listen(PORT, () => console.log(`[server] FitLog API listening on port ${PORT}`));
  })
  .catch((err) => {
    console.error("[db] connection failed:", err.message);
    process.exit(1);
  });
