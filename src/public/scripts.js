const BASE_URL = window.location.origin;
const sectorMap = {
  1: "أشبال و زهرات",
  2: "كشافة و مرشدات",
  3: "متقدم و رائدات",
  4: "جوالة",
};

async function signAttendance() {
  const trash = document.getElementsByClassName("box")[0];
  const brs = document.body.getElementsByTagName("br");
  if (trash) {
    trash.remove();
    brs[brs.length - 1].remove();
  }

  const result = await fetch(`${BASE_URL}/api/v1/attendance`, {
    // const result = await fetch(`http://127.0.0.1:8000/api/v1/attendance`, {
    method: "POST",
    body: JSON.stringify({
      scout_id: document.getElementById("scout_id").value,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  }).catch((err) => console.log(err));

  document.body.appendChild(document.createElement("br"));
  const div = document.createElement("div");
  div.classList.add("box");
  const p = document.createElement("p");
  p.style.margin = "8px";
  div.appendChild(p);
  document.body.appendChild(div);

  if (result.status === 200) {
    div.classList.add("success_box");
    p.innerHTML = "Signed Successfully";
  } else if (result.status === 404) {
    div.classList.add("error_box");
    p.innerHTML = "ID Not Found";
  } else if (result.status === 403) {
    div.classList.add("error_box");
    p.innerHTML = "Already Signed";
  } else {
    div.classList.add("error_box");
    p.innerHTML = "An Error Occured";
  }
}

async function createScout() {
  const trash = document.getElementsByClassName("box");
  for (let i = trash.length - 1; i >= 0; i--) {
    trash[i].remove();
  }

  const result = await fetch(`${BASE_URL}/api/v1/scouts`, {
    // const result = await fetch(`http://127.0.0.1:8000/api/v1/scouts`, {
    method: "POST",
    body: JSON.stringify({
      name: document.getElementById("name").value,
      level: document.getElementById("level").value,
      sector: document.getElementById("sector").value,
    }),
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  }).catch((err) => console.log(err));

  if (result.ok) {
    const br = document.createElement("br");
    br.classList.add("box");
    document.body.appendChild(br);
    const div = document.createElement("div");
    div.classList.add("box");
    const p = document.createElement("p");
    p.style.margin = "8px";
    div.appendChild(p);
    document.body.appendChild(div);
    div.classList.add("success_box");
    p.innerHTML = `${document.getElementById("name").value} Added Successfully`;
  } else if (result.status === 400) {
    const errors = await result.json();
    errors.map((error) => {
      const br = document.createElement("br");
      br.classList.add("box");
      document.body.appendChild(br);
      const div = document.createElement("div");
      div.classList.add("box");
      const p = document.createElement("p");
      p.style.margin = "8px";
      div.appendChild(p);
      document.body.appendChild(div);
      div.classList.add("error_box");
      p.innerHTML = error.msg;
    });
  } else {
    const br = document.createElement("br");
    br.classList.add("box");
    document.body.appendChild(br);
    const div = document.createElement("div");
    div.classList.add("box");
    const p = document.createElement("p");
    p.style.margin = "8px";
    div.appendChild(p);
    document.body.appendChild(div);
    div.classList.add("error_box");
    p.innerHTML = "An Error Occured";
  }
}

async function getScouts() {
  const result = await fetch(`${BASE_URL}/api/v1/scouts`, {
    // const result = await fetch(`http://127.0.0.1:8000/api/v1/scouts`, {
    method: "GET",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  }).catch((err) => console.log(err));

  if (result.ok) {
    const trash = document.getElementsByClassName("trash");
    for (let i = trash.length - 1; i >= 0; i--) {
      trash[i].remove();
    }

    const scouts = await result.json();
    let counter = 1;
    scouts.map((scout) => {
      const tr = document.createElement("tr");
      tr.classList.add("trash");

      let td = document.createElement("td");
      td.innerText = counter++;
      tr.appendChild(td);

      td = document.createElement("td");
      td.innerText = scout.name;
      tr.appendChild(td);

      td = document.createElement("td");
      td.innerText = sectorMap[scout.sector];
      tr.appendChild(td);

      td = document.createElement("td");
      td.innerText = scout.level;
      tr.appendChild(td);

      td = document.createElement("td");
      td.innerHTML = `
				<button onclick="deleteScout('${scout.id}')">Delete</button>
			`;
      tr.appendChild(td);

      const table = document.getElementById("table");
      table.appendChild(tr);
    });
  } else {
    const table = document.getElementById("table");
    const tr = document.createElement("tr");
    tr.classList.append("trash");
    const td = document.createElement("td");
    td.innerText = `Unable to Get Data | Error Code ${result.status}`;
    table.appendChild(tr).appendChild(td);
  }
}

async function deleteScout(id) {
  const result = await fetch(`${BASE_URL}/api/v1/scouts/${id}`, {
    // const result = await fetch(`http://127.0.0.1:8000/api/v1/scouts`, {
    method: "DELETE",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  }).catch((err) => console.log(err));

  if (result.ok) {
    location.reload();
  }
}

async function getAttendances() {
  const result = await fetch(`${BASE_URL}/api/v1/attendance`, {
    // const result = await fetch(`http://127.0.0.1:8000/api/v1/scouts`, {
    method: "GET",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  }).catch((err) => console.log(err));

  if (result.ok) {
    const trash = document.getElementsByClassName("trash");
    for (let i = trash.length - 1; i >= 0; i--) {
      trash[i].remove();
    }

    const attendances = await result.json();
    attendances.map((attendance) => {
      const tr = document.createElement("tr");
      tr.classList.add("trash");

      let td = document.createElement("td");
      td.innerHTML = `<a href="${BASE_URL}/attendance/${attendance._id}">${attendance._id}</a>`;
      tr.appendChild(td);

      td = document.createElement("td");
      td.innerText = `${attendance.day}-${attendance.month}-${attendance.year}`;
      tr.appendChild(td);

      td = document.createElement("td");
      td.innerText = sectorMap[attendance.sector];
      tr.appendChild(td);

      const table = document.getElementById("table");
      table.appendChild(tr);
    });
  } else {
    const table = document.getElementById("table");
    const tr = document.createElement("tr");
    tr.classList.append("trash");
    const td = document.createElement("td");
    td.innerText = `Unable to Get Data | Error Code ${result.status}`;
    table.appendChild(tr).appendChild(td);
  }
}

async function getAttendance(id) {
  const result = await fetch(`${BASE_URL}/api/v1/attendance/${id}`, {
    // const result = await fetch(`http://127.0.0.1:8000/api/v1/scouts`, {
    method: "GET",
    headers: {
      "Content-type": "application/json; charset=UTF-8",
    },
  }).catch((err) => console.log(err));

  if (result.ok) {
    const trash = document.getElementsByClassName("trash");
    for (let i = trash.length - 1; i >= 0; i--) {
      trash[i].remove();
    }

    const attendance = await result.json();

    const title = document.getElementById("title");
    title.innerText = `${attendance.day}-${attendance.month}-${attendance.year}`;

    attendance.scouts.map((scout) => {
      const tr = document.createElement("tr");
      tr.classList.add("trash");

      // let td = document.createElement("td");
      // td.innerHTML = `<a href="${BASE_URL}/attendance/${attendance._id}">${attendance._id}</a>`;
      // tr.appendChild(td);

      td = document.createElement("td");
      td.innerText = scout.scout_id;
      tr.appendChild(td);

      td = document.createElement("td");
      td.innerText = scout.name;
      tr.appendChild(td);

      const table = document.getElementById("table");
      table.appendChild(tr);
    });
  } else {
    const table = document.getElementById("table");
    const tr = document.createElement("tr");
    tr.classList.append("trash");
    const td = document.createElement("td");
    td.innerText = `Unable to Get Data | Error Code ${result.status}`;
    table.appendChild(tr).appendChild(td);
  }
}
