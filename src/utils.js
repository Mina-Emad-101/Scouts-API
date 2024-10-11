import Scout from "./models/v1/scouts.js";

export const getDate = () => {
	const date = new Date();
	const day = date.getUTCDate();
	const month = date.getUTCMonth() + 1;
	const year = date.getUTCFullYear();

	return {
		day: day,
		month: month,
		year: year,
	};
};

export const mapIDs = async (attendance) => {
	attendance.scouts = await Promise.all(
		attendance.scouts_ids.map(async (scout_id) => {
			const scout = await Scout.findOne({ scout_id: scout_id });
			return {
				scout_id: scout.scout_id,
				name: scout.name,
			};
		}),
	);
	attendance.scouts_ids = undefined;
};
