const mongoose = require("mongoose");

const exerciseSchema = new mongoose.Schema({
  _id: { type: String, required: true },
  name: { type: String, required: true },
  category: { type: String, required: true },
});

module.exports = mongoose.model("Exercise", exerciseSchema);
