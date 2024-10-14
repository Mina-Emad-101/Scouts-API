import Express from "express";
import attendanceTemplate from "../../views/attendance.js";

const router = Express.Router();

router.get("/", (req, res) => {
	return res.send(attendanceTemplate());
});

export default router;
