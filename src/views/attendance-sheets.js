import header from "./components/header.js";

export default () => `
<!doctype html>
<html lang="ar">

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Attendance Sheets</title>
	<link href="/css/style.css" rel="stylesheet" />
	<script src="/scripts.js"></script>
</head>

<body dir="rtl">
	${header({ attendance: true })}
	<h2 class="tr">Attendance Sheets</h2>
	<table id="table">
		<tr>
			<th>الكشف</th>
			<th>التاريخ</th>
			<th>القطاع</th>
		</tr>
	</table>
</body>

<script>
	getAttendances();
</script>

</html>
`;
