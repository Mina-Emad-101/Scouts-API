import Express from "express";
import attendance from "../../views/attendance.js";
import createScout from "../../views/create-scout.js";

const router = Express.Router();

router.get("/", (req, res) => {
	return res.send(attendance());
});

router.get("/create-scout", (req, res) => {
	return res.send(createScout());
});

export default router;
