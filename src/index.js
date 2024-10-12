import Express from "express";
import cors from "cors";
import mongoose from "mongoose";
import attendanceRouter from "./routes/v1/attendance.js";
import scoutsRouter from "./routes/v1/scouts.js";

const PORT = process.env.PORT || 8000;
const HOST = process.env.HOST || "127.0.0.1";
const app = Express();

// Middlewares
app.use(cors());
app.use(Express.json());
app.use(Express.urlencoded({ extended: true }));

mongoose
  .connect("mongodb://localhost:27017/scouts")
  .then((_) => console.log("connected to DB"));

// Routes
app.use("/api/v1", attendanceRouter);
app.use("/api/v1", scoutsRouter);

app.listen(PORT, HOST, () => {
  console.log(`Listening on ${HOST}:${PORT}`);
});
