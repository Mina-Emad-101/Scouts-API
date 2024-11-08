import header from "./components/header.js";

export default () => `
<html lang="en">

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Attendance</title>
	<link rel="stylesheet" href="/css/style.css" />
	<script src="/scripts.js"></script>
</head>

<body>
	${header({ sign_attendance: true })}
	<h3>ID:</h3>
	<input type="number" name="scout_id" id="scout_id" />
	<button type="submit" onclick="signAttendance()">Submit</button>
</body>

</html>
`;
