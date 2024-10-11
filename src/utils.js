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
