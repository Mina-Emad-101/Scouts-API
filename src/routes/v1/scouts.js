import Express from "express";
import Scout from "../../models/v1/scouts.js";
import { checkSchema, validationResult } from "express-validator";
import { createSchema } from "../../validators/scouts.js";

const router = Express.Router();

router.get("/scouts", async (req, res) => {
  const scouts = await Scout.find();

  return res.json(scouts);
});

router.post("/scouts", checkSchema(createSchema), async (req, res) => {
  const result = validationResult(req);
  if (!result.isEmpty()) return res.status(400).json(result.array());

  const { name, level, sector } = req.body;

  const scout = new Scout({
    scout_id: (await Scout.collection.countDocuments()) + 1,
    name: name,
    level: level,
    sector: sector,
  });

  await scout.save().then(
    (scout) => res.json(scout),
    (err) => res.status(500).json({ error: err }),
  );
});

router.delete("/socuts/:id", async (req, res) => {
  const id = req.params.id;
  const scout = await Scout.findById(id);
  await scout.deleteOne();
  return res.sendStatus(200);
});

export default router;
