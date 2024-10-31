import Scout from "./models/v1/scouts.js";

export const BASE_URL = process.env.NODE_ENV
  ? "https://scouts-api.onrender.com"
  : "http://127.0.0.1:8000";

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

export const resourcifyAttendance = async (attendance) => {
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

export const resourcifyScout = async (scout) => {
  scout = {
    id: scout.id,
    scout_id: scout.scout_id,
    name: scout.name,
    sector: scout.sector,
    level: scout.level,
  };
};
