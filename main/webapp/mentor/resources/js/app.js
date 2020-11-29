import AppState from "../../../AppState.js"

const content = new AppState({
	init: "",
	onChange: (state) => {
		document.getElementById("content").innerHTML = state;
	}
})

const mentor = new AppState({
	init: "",
	onChange: (mId) => {
		const setLink = (id) => {
			document.getElementById(id).setAttribute("href", `#!/${mId}/${id}`)
		}
		setLink("registrations");
		setLink("approved");
		setLink("denied");
		setLink("pending")
	}
})

const studyplans = new AppState({
	init: "",
	onChange: (state) => {
		let result = `
		<table>
		<tr>
		<th>Chosen courses</th>
		<th>Comments</th>
		<th>approval status</th>
		</tr>
		`
		for (const entry of state) {
			result += "<tr>"
			// course names
			const courseString = entry.courses.map(el => el.type).join(", ")
			result += `<td>${courseString}</td>`
			// comments
			result += `<td>${entry.studentComment}</td>`
			// approval status
			switch (entry.approved) {
				case true:
					result += "<td>Approved</td>"
				case false:
					result += "<td>Denied</td>"
				default:
					result += "<td>Pending</td>"
			}
			result += "</tr>"
		}
		result += "</table>"
		content.setState(result)
	}
})


function route(url) {
	const urlArray = url.split('/');
	if (urlArray.length <= 1) {
		content.setState("<h1>404: invalid hash</h1>")
		return
	}
	const mentorID = urlArray[1];
	mentor.setState(mentorID);
	if (urlArray.length === 2) {
		fetch(`../rest/Mentor/${mentorID}`)
			.then(res => res.json())
			.then(res => res.name)
			.then(name => {
				content.setState(`
					<h1>Welcome, ${name}!</h1>
					<p> on this page, you can get an overview of submitted study plans</p>
				`)})
		return
	}
	switch (urlArray[2]) {
		case "registrations":
			getStudyPlans()
			break
		case "approved":
			getStudyPlans()
			studyplans.setState(state => state.filter(el => el.approved === true))
			break
		case "pending":
			getStudyPlans()
			studyplans.setState(state => state.filter(el => el.approved === null))
			break
		case "denied":
			getStudyPlans()
			studyplans.setState(state => state.filter(el => el.approved === null))
			break
		default:
			content.setState("<h1>404: invalid hash</h1>")

			
	}
}

function getStudyPlans() {
	fetch("../rest/Student/allstudents")
		.then(res => res.json())
		.then(res => res.map(el => el.allStudyPlans))
		.then(res => res.reduce((a, b) => a.concat(b),  []))
		.then(state => studyplans.setState(state))

}

window.addEventListener("load", () => route(location.hash))
window.addEventListener("hashchange", () => route(location.hash))