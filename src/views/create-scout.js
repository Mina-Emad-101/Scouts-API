export default () => `
<!doctype html>
<html lang="ar">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Create Scout</title>
    <link rel="stylesheet" href="/css/style.css" />
    <script src="/scripts.js"></script>
  </head>

  <body dir="rtl">
    <h3>الأسم</h3>
    <input type="text" name="name" id="name" />
    <h3>السنة الدراسية (1 - 12)</h3>
    <input type="number" name="level" id="level" />
    <h3>القطاع (أشبال و زهرات: 1 - كشافة و مرشدات: 2 - متقدم و رائدات: 3)</h3>
    <input type="number" name="sector" id="sector" />
    <button type="submit" onclick="createScout()">Submit</button>
  </body>
</html>
`;
