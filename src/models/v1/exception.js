import { Schema, model } from "mongoose";

const schema = new Schema({
  scout_id: {
    type: Number,
    required: true,
  },
  reason: {
    type: String,
    required: true,
  },
  day: {
    type: Number,
    required: true,
  },
  month: {
    type: Number,
    required: true,
  },
  year: {
    type: Number,
    required: true,
  },
});

const Exception = model("Exception", schema);
export default Exception;
