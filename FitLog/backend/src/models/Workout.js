const mongoose = require("mongoose");

// A Workout document embeds its sets directly (Part 1 design doc, Section 4:
// "a document database is a natural fit here because a Workout with its
// nested array of WorkoutSet objects maps cleanly onto a single JSON
// document"). The client-generated id is used as MongoDB's _id so that
// re-sending an already-synced workout updates it instead of duplicating it
// (idempotent sync, see Section 4's closing paragraph).
const workoutSetSchema = new mongoose.Schema(
  {
    id: { type: String, required: true },
    exerciseId: { type: String, required: true },
    exerciseName: { type: String, required: true },
    setNumber: { type: Number, required: true },
    reps: { type: Number, required: true },
    weightKg: { type: Number, required: true },
  },
  { _id: false },
);

const workoutSchema = new mongoose.Schema(
  {
    _id: { type: String, required: true }, // client-generated UUID
    userId: { type: String, required: true, index: true },
    name: { type: String, required: true },
    dateTime: { type: Number, required: true }, // epoch millis
    durationSeconds: { type: Number, default: 0 },
    notes: { type: String, default: "" },
    sets: [workoutSetSchema],
  },
  { timestamps: true },
);

module.exports = mongoose.model("Workout", workoutSchema);
