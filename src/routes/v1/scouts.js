import Express from "express";
import Scout from "../../models/v1/scouts.js";
import { checkSchema, validationResult } from "express-validator";
import { createSchema } from "../../validators/scouts.js";

const router = Express.Router();

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

export default router;
