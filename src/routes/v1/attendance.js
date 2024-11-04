import Express from "express";
import Attendance from "../../models/v1/attendance.js";
import { getDate, resourcifyAttendance } from "../../utils.js";
import Scout from "../../models/v1/scouts.js";
import { checkSchema, validationResult } from "express-validator";
import { createSchema } from "../../validators/exceptions.js";
import Exception from "../../models/v1/exception.js";

const router = Express.Router();

router.get("/attendance/today", async (req, res) => {
  const { day, month, year } = getDate();

  const attendances = await Attendance.find({
    day: day,
    month: month,
    year: year,
  });

  if (attendances.length === 0) return res.sendStatus(404);

  const attendancesJSON = attendances.map((attendance) => attendance.toJSON());
  await Promise.all(
    attendancesJSON.map(async (attendance) => {
      await resourcifyAttendance(attendance);
    }),
  );

  return res.json(attendancesJSON);
});

router.get("/attendance", async (req, res) => {
  const { day, month, year, sector } = req.query;
  const filter = {};

  if (day) filter.day = day;
  if (month) filter.month = month;
  if (year) filter.year = year;
  if (sector) filter.sector = sector;

  const attendances = await Attendance.find(filter);

  if (attendances.length === 0) return res.sendStatus(404);

  const attendancesJSON = attendances.map((attendance) => attendance.toJSON());
  await Promise.all(
    attendancesJSON.map(async (attendance) => {
      await resourcifyAttendance(attendance);
    }),
  );

  return res.json(attendancesJSON);
});

router.get("/attendance/:id", async (req, res) => {
  const id = req.params.id;
  await Attendance.findById(id).then(
    async (attendance) => {
      attendance = attendance.toJSON();
      await resourcifyAttendance(attendance);
      return res.json(attendance);
    },
    (err) => res.sendStatus(404),
  );
});

router.post("/attendance", async (req, res) => {
  const { scout_id } = req.body;

  const scout = await Scout.findOne({ scout_id: scout_id });
  if (!scout) return res.sendStatus(404);

  const { day, month, year } = getDate();

  const attendance =
    (await Attendance.findOne({
      day: day,
      month: month,
      year: year,
      sector: scout.sector,
    })) ??
    new Attendance({
      day: day,
      month: month,
      year: year,
      sector: scout.sector,
    });

  if (attendance.scouts_ids.includes(scout_id)) return res.sendStatus(403);

  attendance.scouts_ids.push(scout_id);

  await attendance.save().then(
    (_) => res.sendStatus(200),
    (err) => res.status(500).json({ error: err }),
  );
});

router.post("/exception", checkSchema(createSchema), async (req, res) => {
  const result = validationResult(req);
  if (!result.isEmpty())
    return res.status(400).json({ errors: result.array() });

  const { scout_id, reason, day, month, year } = req.body;

  const scout = await Scout.findOne({ scout_id: scout_id });
  if (!scout) return res.sendStatus(404);

  const prevException = await Exception.findOne({ scout_id: scout_id });
  if (prevException) return res.sendStatus(403);

  const exception = new Exception({
    scout_id: scout_id,
    reason: reason,
    day: day,
    month: month,
    year: year,
  });

  await exception.save().then(
    (_) => res.sendStatus(200),
    (_) => res.sendStatus(500),
  );
});

export default router;
