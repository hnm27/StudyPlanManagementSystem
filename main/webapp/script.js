import AppState from "./AppState.js"

const content = new AppState({
	init: "",
	onChange: (state) => {document.getElementById('main-content').innerHTML = state}
})

function route(hash) {
	const hashLinks = {
		"#!/courses":"rest/courses/all",
		"#!/istCourses":"rest/courses/ist",
		"#!/dstCourses":"rest/courses/dst"
	};

	fetch(hashLinks[hash])
		.then(res => res.json())
		.then(res => {
			let result = `
			<table>
			<tr>
			<th>type</th>
			<th>description</th>
			<th>credits</th>
			</tr>`;
			for (let i of res) {
				result += `
				<tr><td>${i.type}</td><td>${i.description}</td><td>${i.credit}</td></tr>`;
			}
			result += "</table>";
			return result;
		})
		.then(state => content.setState(state));
}

window.addEventListener("hashchange", () => route(location.hash));
window.addEventListener("load", () => route(location.hash));