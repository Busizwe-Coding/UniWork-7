// Run once after connecting to a fresh database: `node seed.js`
require("dotenv").config();
const mongoose = require("mongoose");
const Exercise = require("./src/models/Exercise");

const exercises = [
  { _id: "ex-bench", name: "Bench Press", category: "Chest" },
  { _id: "ex-incline", name: "Incline Dumbbell Press", category: "Chest" },
  { _id: "ex-fly", name: "Cable Fly", category: "Chest" },
  { _id: "ex-ohp", name: "Overhead Press", category: "Arms" },
  { _id: "ex-pushup", name: "Push-Up", category: "Chest" },
  { _id: "ex-deadlift", name: "Deadlift", category: "Back" },
  { _id: "ex-row", name: "Barbell Row", category: "Back" },
  { _id: "ex-pullup", name: "Pull-Up", category: "Back" },
  { _id: "ex-squat", name: "Back Squat", category: "Legs" },
  { _id: "ex-legpress", name: "Leg Press", category: "Legs" },
  { _id: "ex-curl", name: "Bicep Curl", category: "Arms" },
  { _id: "ex-tricep", name: "Tricep Pushdown", category: "Arms" },
];

mongoose
  .connect(process.env.MONGODB_URI)
  .then(async () => {
    for (const ex of exercises) {
      await Exercise.findByIdAndUpdate(ex._id, ex, { upsert: true });
    }
    console.log(`Seeded ${exercises.length} exercises.`);
    process.exit(0);
  })
  .catch((err) => {
    console.error("Seed failed:", err.message);
    process.exit(1);
  });
