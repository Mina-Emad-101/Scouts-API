export const createSchema = {
  name: {
    notEmpty: { errorMessage: "Name Cannot Be Empty" },
  },
  level: {
    notEmpty: { errorMessage: "Level Cannot Be Empty" },
    isNumber: { errorMessage: "Level Has To Be A Number" },
    isInt: {
      errorMessage: "Level Has To Be Between 1 and 12",
      options: [{ min: 1, max: 12 }],
    },
  },
  sector: {
    notEmpty: { errorMessage: "Sector Cannot Be Empty" },
    isNumber: { errorMessage: "Sector Has To Be A Number" },
    isIn: {
      errorMessage: "Sector Has To Be In ( 1, 2, 3 )",
      options: [[1, 2, 3]],
    },
  },
};
