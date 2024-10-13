import { Schema, model } from "mongoose";

const schema = new Schema({
  scout_id: {
    type: Number,
    unique: true,
  },
  name: {
    type: String,
    required: true,
    unique: true,
  },
  sector: {
    type: Number,
    required: true,
  },
  level: {
    type: Number,
    required: true,
  },
});

const Scout = model("Scout", schema);
export default Scout;
