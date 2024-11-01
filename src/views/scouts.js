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
		<label for="level">السنة الدراسية</label>
		<select name="level" id="level" onchange="getScouts()">
			<option value="" selected>All</option>
			<option value="0">قادة</option>
			${(() => {
        let result = "";
        for (let i = 1; i <= 12; i++)
          result += `<option value="${i}">${i}</option>`;
        return result;
      })()}
		</select>
		<table id="table">
			<tr>
				<th>م</th>
				<th>الاسم</th>
				<th>ID</th>
				<th>القطاع</th>
				<th>السنة الدراسية</th>
				<th></th>
			</tr>
		</table>
	</body>

	<script>
		getScouts();
	</script>

</html>
`;
