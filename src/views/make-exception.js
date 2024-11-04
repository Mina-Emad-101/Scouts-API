export default () => `
<html lang="en">

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Exception</title>
	<link rel="stylesheet" href="/css/style.css" />
	<script src="/scripts.js"></script>
</head>

<body>
	<h3>ID:</h3>
	<input type="number" name="scout_id" id="scout_id" />
	<h3>Reason:</h3>
	<input type="text" name="reason" id="reason" />
	<h3>Date:</h3>
	<input type="date" name="date" id="date" />
	<button type="submit" onclick="makeException()">Submit</button>
</body>

</html>
`;
