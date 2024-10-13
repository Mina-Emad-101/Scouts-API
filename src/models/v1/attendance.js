import { Schema, model } from "mongoose";

const schema = new Schema({
  sector: {
    type: Number,
    required: true,
  },
  scouts_ids: {
    type: [Number],
    default: [],
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

const Attendance = model("Attendance", schema);
export default Attendance;
