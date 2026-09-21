const express = require("express");
const Workout = require("../models/Workout");
const { requireAuth } = require("../middleware/auth");

const router = express.Router();
router.use(requireAuth);

function toWorkoutDto(w) {
  return {
    id: w._id,
    userId: w.userId,
    name: w.name,
    dateTime: w.dateTime,
    durationSeconds: w.durationSeconds,
    notes: w.notes,
    sets: w.sets.map((s) => ({
      id: s.id,
      workoutId: w._id,
      exerciseId: s.exerciseId,
      exerciseName: s.exerciseName,
      setNumber: s.setNumber,
      reps: s.reps,
      weightKg: s.weightKg,
    })),
  };
}

// GET /workouts?since=<epoch millis> — used by the app's sync worker to
// pull anything created or changed on another device since the last sync.
router.get("/", async (req, res) => {
  const since = parseInt(req.query.since || "0", 10);
  const workouts = await Workout.find({
    userId: req.userId,
    updatedAt: { $gte: new Date(since) },
  }).sort({ dateTime: -1 });

  res.json(workouts.map(toWorkoutDto));
});

// POST /workouts — creates OR updates a workout by its client-generated id,
// so a retried sync request (e.g. after a timeout) never creates a
// duplicate. This is the "idempotent" behaviour described in the Part 1
// design document, Section 4.
router.post("/", async (req, res) => {
  try {
    const { id, name, dateTime, durationSeconds, notes, sets } = req.body;
    if (!id || !name || !dateTime) {
      return res.status(400).json({ error: "id, name and dateTime are required" });
    }

    const workout = await Workout.findByIdAndUpdate(
      id,
      {
        _id: id,
        userId: req.userId,
        name,
        dateTime,
        durationSeconds: durationSeconds || 0,
        notes: notes || "",
        sets: sets || [],
      },
      { upsert: true, new: true, setDefaultsOnInsert: true },
    );

    console.log(`[workouts] upserted workout ${workout._id} for user ${req.userId} (${workout.sets.length} sets)`);
    res.status(201).json(toWorkoutDto(workout));
  } catch (err) {
    console.error("[workouts] create failed:", err.message);
    res.status(500).json({ error: "Could not save workout" });
  }
});

// PATCH /workouts/:id
router.patch("/:id", async (req, res) => {
  const workout = await Workout.findOneAndUpdate(
    { _id: req.params.id, userId: req.userId },
    { $set: req.body },
    { new: true },
  );
  if (!workout) return res.status(404).json({ error: "Workout not found" });
  res.json(toWorkoutDto(workout));
});

// DELETE /workouts/:id
router.delete("/:id", async (req, res) => {
  const result = await Workout.deleteOne({ _id: req.params.id, userId: req.userId });
  if (result.deletedCount === 0) return res.status(404).json({ error: "Workout not found" });
  res.status(204).send();
});

module.exports = router;
