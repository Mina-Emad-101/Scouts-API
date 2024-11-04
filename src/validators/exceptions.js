export const createSchema = {
  scout_id: {
    notEmpty: { errorMessage: "Scout ID Cannot Be Empty" },
    isNumber: { errorMessage: "Scout ID Has To Be A Number" },
  },
  reason: {
    notEmpty: { errorMessage: "Reason Cannot Be Empty" },
  },
  day: {
    notEmpty: { errorMessage: "Day Cannot Be Empty" },
    isInt: {
      errorMessage: "Day Has To Be Between 01 and 31",
      options: [{ min: 1, max: 31 }],
    },
  },
  month: {
    notEmpty: { errorMessage: "Month Cannot Be Empty" },
    isInt: {
      errorMessage: "Month Has To Be Between 01 and 12",
      options: [{ min: 1, max: 12 }],
    },
  },
  year: {
    notEmpty: { errorMessage: "Year Cannot Be Empty" },
    isInt: {
      errorMessage: "Year Has To Be Higher Than 2000",
      options: [{ min: 2000 }],
    },
  },
};
