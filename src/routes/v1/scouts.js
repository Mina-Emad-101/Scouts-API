import Express from "express";
import Scout from "../../models/v1/scouts.js";

const router = Express.Router();

router.post("/scouts", async (req, res) => {
	const { name } = req.body;

	const scout = new Scout({
		scout_id: (await Scout.collection.countDocuments()) + 1,
		name: name,
	});

	await scout.save().then(
		(scout) => res.json(scout),
		(err) => res.status(500).json({ error: err }),
	);
});

export default router;
