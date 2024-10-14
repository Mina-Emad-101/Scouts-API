import { BASE_URL } from "../utils";

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
	const boxes = document.getElementsByClassName("box");
	const brs = document.body.getElementsByTagName("br");
	if (boxes.length > 0) {
		for (let i = 0; i < boxes.length; i++) {
			boxes[i].remove();
			brs[brs.length - 1].remove();
		}
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
		document.body.appendChild(document.createElement("br"));
		const div = document.createElement("div");
		div.classList.add("box");
		const p = document.createElement("p");
		p.style.margin = "8px";
		div.appendChild(p);
		document.body.appendChild(div);
		div.classList.add("success_box");
		p.innerHTML = "Added Successfully";
	} else if (result.status === 400) {
		result.body.errors.map((error) => {
			document.body.appendChild(document.createElement("br"));
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
		document.body.appendChild(document.createElement("br"));
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
