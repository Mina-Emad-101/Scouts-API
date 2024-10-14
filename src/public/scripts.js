const NODE_ENV = process.env.NODE_ENV || "development";

async function signAttendance() {
	const trash = document.getElementsByClassName("box")[0];
	const brs = document.body.getElementsByTagName("br");
	if (trash) {
		trash.remove();
		brs[brs.length - 1].remove();
	}

	const result = await fetch(
		NODE_ENV === "development"
			? "https://127.0.0.1:8000/api/v1/attendance"
			: "https://scouts-api.onrender.com/api/v1/attendance",
		{
			method: "POST",
			body: JSON.stringify({
				scout_id: document.getElementById("scout_id").value,
			}),
			headers: {
				"Content-type": "application/json; charset=UTF-8",
			},
		},
	).catch((err) => console.log(err));

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
