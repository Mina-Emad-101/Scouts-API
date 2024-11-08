import header from "./components/header.js";

export default () => `
<html lang="en">

<head>
	<meta charset="UTF-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1" />
	<title>Karma Scouts</title>
	<link rel="stylesheet" href="/css/style.css" />
	<script src="/scripts.js"></script>
</head>

<body>
	${header({})}
</body>

</html>
`;
