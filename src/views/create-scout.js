export default () => `
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Add Scout</title>
    <link rel="stylesheet" href="css/styles.css" />
    <script src="/scripts.js"></script>
  </head>

  <body>
    <h3>Name:</h3>
    <input type="text" name="name" id="name" />
    <h3>Level:</h3>
    <input type="number" name="level" id="level" />
    <h3>Sector: (أشبال: 1, كشافة و مرشدات: 2, متقدم و رائدات: 3)</h3>
    <input type="number" name="sector" id="sector" />
    <button type="submit" onclick="createScout()">Submit</button>
  </body>
</html>
`;
