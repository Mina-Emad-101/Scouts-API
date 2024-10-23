import Express from "express";
import attendance from "../../views/attendance.js";
import getAttendance from "../../views/get-attendance.js";
import attendanceSheets from "../../views/attendance-sheets.js";
import createScout from "../../views/create-scout.js";
import scouts from "../../views/scouts.js";
import Attendance from "../../models/v1/attendance.js";

const router = Express.Router();

router.get("/attendance", async (req, res) => {
  return res.send(attendanceSheets());
});

router.get("/attendance/:id", async (req, res) => {
  const id = req.params.id;
  return res.send(getAttendance(id));
});

router.get("/sign-attendance", (req, res) => {
  return res.send(attendance());
});

router.get("/create-scout", (req, res) => {
  return res.send(createScout());
});

router.get("/scouts", (req, res) => {
  return res.send(scouts());
});

export default router;
