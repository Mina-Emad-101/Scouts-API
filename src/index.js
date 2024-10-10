import Express from "express";
import cors from "cors";

const PORT = process.env.PORT || 8000;
const HOST = process.env.HOST || "127.0.0.1";
const app = Express();

// Middlewares
app.use(cors());
app.use(Express.json());

// Routes

app.listen(process.env.PORT || 8000, process.env.HOST || "127.0.0.1", () => {
	console.log("Listening");
});
