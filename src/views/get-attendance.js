import header from "./components/header.js";

export default (id) => `
<html lang="en">

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Attendance</title>
	<link rel="stylesheet" href="/css/style.css" />
	<script src="/scripts.js"></script>
</head>

<body>
	${header({})}
	<h2 id="title" class="tr"></h2>
	<table id="table">
		<tr>
			<th>م</th>
			<th>الاسم</th>
		</tr>
	</table>
</body>
<script>
	getAttendance("${id}");
</script>

</html>
`;
