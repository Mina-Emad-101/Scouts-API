async function signAttendance() {
  const result = await fetch(
    "https://scouts-api.onrender.com/api/v1/attendance",
    {
      method: "POST",
      body: JSON.stringify({
        id: document.forms["form1"]["scout_id"].value,
      }),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    },
  ).catch((err) => console.log(err));

  const div = document.createElement("div");
  const p = document.createElement("p");
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
