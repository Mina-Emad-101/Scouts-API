export default () => `
<!doctype html>
<html lang="ar">

	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1" />
		<title>Scouts</title>
		<link href="css/style.css" rel="stylesheet" />
		<script src="/scripts.js"></script>
	</head>

	<body dir="rtl">
		<table id="table">
			<tr>
				<th>م</th>
				<th>الاسم</th>
				<th>القطاع</th>
				<th>السنة الدراسية</th>
			</tr>
		</table>
	</body>

	<script>
		getScouts();
	</script>

</html>
`;
