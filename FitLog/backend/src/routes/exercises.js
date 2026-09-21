const express = require("express");
const Exercise = require("../models/Exercise");
const { requireAuth } = require("../middleware/auth");

const router = express.Router();
router.use(requireAuth);

// GET /exercises — the master list the app caches into Room on first login
// (WorkoutRepository.cacheExercises) so exercise selection works offline
// from then on.
router.get("/", async (req, res) => {
  const exercises = await Exercise.find().sort({ name: 1 });
  res.json(exercises.map((e) => ({ id: e._id, name: e.name, category: e.category })));
});

module.exports = router;
