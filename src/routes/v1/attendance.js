import Express from "express";
import Attendance from "../../models/v1/attendance.js";

const router = Express.Router();

router.get("/attendance", async (req, res) => {
	const date = new Date();
	const day = date.getUTCDate();
	const month = date.getUTCMonth() + 1;
	const year = date.getUTCFullYear();

	const attendance = await Attendance.findOne({
		day: day,
		month: month,
		year: year,
	});

	if (!attendance) return res.sendStatus(404);

	return res.json(attendance);
});

router.post("/attendance", async (req, res) => {
	const { scout_id } = req.body;

	const date = new Date();
	const day = date.getUTCDate();
	const month = date.getUTCMonth() + 1;
	const year = date.getUTCFullYear();

	const attendance =
		(await Attendance.findOne({
			day: day,
			month: month,
			year: year,
		})) ??
		new Attendance({
			day: day,
			month: month,
			year: year,
		});

	attendance.scouts_ids.push(scout_id);

	await attendance.save().then(
		(_) => res.sendStatus(200),
		(err) => res.status(500).json({ error: err }),
	);
});

export default router;
