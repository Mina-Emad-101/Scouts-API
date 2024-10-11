import Express from "express";
import Attendance from "../../models/v1/attendance.js";
import { getDate } from "../../utils.js";

const router = Express.Router();

router.get("/attendance/today", async (req, res) => {
	const { day, month, year } = getDate();

	const attendance = await Attendance.findOne({
		day: day,
		month: month,
		year: year,
	});

	if (!attendance) return res.sendStatus(404);

	return res.json(attendance);
});

router.get("/attendance", async (req, res) => {
	const { day, month, year } = req.query;
	const filter = {};

	if (day) filter.day = day;
	if (month) filter.month = month;
	if (year) filter.year = year;

	const attendances = await Attendance.find(filter);

	if (!attendances) return res.sendStatus(404);

	return res.json(attendances);
});

router.post("/attendance", async (req, res) => {
	const { scout_id } = req.body;

	const { day, month, year } = getDate();

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

	if (attendance.scouts_ids.includes(scout_id)) return res.sendStatus(403);

	attendance.scouts_ids.push(scout_id);

	await attendance.save().then(
		(_) => res.sendStatus(200),
		(err) => res.status(500).json({ error: err }),
	);
});

export default router;
