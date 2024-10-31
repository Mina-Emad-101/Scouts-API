import Express from "express";
import Scout from "../../models/v1/scouts.js";
import { checkSchema, validationResult } from "express-validator";
import { createSchema } from "../../validators/scouts.js";
import { resourcifyScout } from "../../utils.js";

const router = Express.Router();

router.get("/scouts", async (req, res) => {
  const { level, sector } = req.query;
  const filter = {};

  if (level) filter.level = level;
  if (sector) filter.sector = sector;

  const scouts = await Scout.find(filter);

  const result = await Promise.all(
    scouts.map(async (scout) => await resourcifyScout(scout)),
  );

  return res.json(result);
});

router.post("/scouts", checkSchema(createSchema), async (req, res) => {
  const result = validationResult(req);
  if (!result.isEmpty()) return res.status(400).json(result.array());

  const { name, level, sector } = req.body;
  const scouts = await Scout.find();

  const scout = new Scout({
    scout_id: scouts[scouts.length - 1].scout_id + 1,
    name: name,
    level: level,
    sector: sector,
  });

  await scout.save().then(
    (scout) => res.json(scout),
    (err) => res.status(500).json({ error: err }),
  );
});

router.delete("/scouts/:id", async (req, res) => {
  const id = req.params.id;
  const scout = await Scout.findById(id);
  await scout.deleteOne();
  return res.sendStatus(200);
});

export default router;
