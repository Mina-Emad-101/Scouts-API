import Express from "express";
import signAttendance from "../../views/sign-attendance.js";
import getAttendance from "../../views/get-attendance.js";
import attendanceSheets from "../../views/attendance-sheets.js";
import createScout from "../../views/create-scout.js";
import scouts from "../../views/scouts.js";
import makeException from "../../views/make-exception.js";
import main from "../../views/main.js";

const router = Express.Router();

router.get("/", (req, res) => {
	return res.send(main());
});

router.get("/attendance", (req, res) => {
	return res.send(attendanceSheets());
});

router.get("/attendance/:id", (req, res) => {
	const id = req.params.id;
	return res.send(getAttendance(id));
});

router.get("/sign-attendance", (req, res) => {
	return res.send(signAttendance());
});

router.get("/create-scout", (req, res) => {
	return res.send(createScout());
});

router.get("/scouts", (req, res) => {
	return res.send(scouts());
});

router.get("/make-exception", (req, res) => {
	return res.send(makeException());
});

export default router;
