import Express from "express";
import cors from "cors";
import mongoose from "mongoose";
import path from "path";
import frontRouter from "./routes/v1/front.js";
import attendanceRouter from "./routes/v1/attendance.js";
import scoutsRouter from "./routes/v1/scouts.js";

const PORT = process.env.PORT || 8000;
const HOST = process.env.HOST || "127.0.0.1";
const NODE_ENV = process.env.NODE_ENV || "development";
const app = Express();

// Middlewares
app.use(cors());
app.use(Express.json());
app.use(Express.urlencoded({ extended: true }));
app.use(Express.static(path.join(import.meta.dirname, "public")));

mongoose
	.connect(
		NODE_ENV === "development"
			? "mongodb://localhost:27017/scouts"
			: `mongodb+srv://mina:${process.env.DBPASSWORD}@cluster0.yjr3f.mongodb.net/scouts?retryWrites=true&w=majority&appName=Cluster0`,
	)
	.then(() => console.log("Connected to MongoDB"));

// Routes
app.use(frontRouter);
app.use("/api/v1", attendanceRouter);
app.use("/api/v1", scoutsRouter);

app.listen(PORT, HOST, () => {
	console.log(`Listening on ${HOST}:${PORT}`);
});
