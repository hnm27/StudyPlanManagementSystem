import AppState from "./AppState.js"

const content = new AppState({
	init: "",
	onChange: (state) => {document.getElementById('main-content').innerHTML = state}
})

const student = new AppState({
	init: "",
	onChange: (studentID) => {document.getElementById('navbar').innerHTML += `
	<a href="#!/${studentID}/courses" class="links">Your Courses</a>
	<a href="#!/${studentID}/addCourse" class="links">Add a new course</a>
	`}
})


function route(hash) {
	const hashArray = hash.split('/')
	let studentID;
	if (hashArray.length >= 2) {
		studentID = hashArray[1];
	} else {
		content.setState("<h1>ERROR: NO LOGIN CREDENTIALS GIVEN</h1>");
		return;
	}
	if (student.state === "") {
		student.setState(studentID)
	}
	console.log(studentID)
	if (hashArray.length === 2) {
		fetch(`rest/Student/${studentID}`, {
			headers: {'Accept':'application/json'}
		})
			.then(res => res.json())
			.then(res => `
				<h3>Welcome ${res.firstname} ${res.lastname}!</h3>
				<p>In here you can edit your account settings, or send a new form</p>
				`)
			.then(state => content.setState(state))
	} else {
		switch (hashArray[2]) {
			case "courses":
			fetch(`rest/Student/${studentID}/chosencourses`, {
				headers: {'Accept':'application/json'}
			})
				.then(res => res.json())
				.then(res => {
					if (res.length === 0) {
						return `<h3>You haven't chosen any courses yet!</h3>`;
					} else {
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
						const totalCredits = res.map(i => i.credit).map(x => parseInt(x)).reduce((a, b) => a + b)
						result += `<p><b>Total Credits: <i>${totalCredits}</i></b></p>`;
						return result;
					}
				})
				.then(state => content.setState(state))
			break;
			case "addCourse":
			fetch(`rest/courses/all`)
				.then(res => res.json())
				.then(res => {
					let result = `
					<table>
					<tr>
					<th>type</th>
					<th>description</th>
					<th>credits</th>
					<th>Add course</th>
					<th>Remove course</th>
					</tr>`;
					for (let i of res) {
						result += `
						<tr><td>${i.type}</td><td>${i.description}</td><td>${i.credit}</td>
						<td onclick='addCourse(${JSON.stringify(i)})'>Add to your courses</td>
						<td onclick='removeCourse(${JSON.stringify(i)})''>Remove from your courses</td></tr>`;
					}
					result += "</table>";
					return result;
				})
				.then(state => content.setState(state))
			default:
				content.setState("<h3>Work in progress!</h3>")
		}
	}
}


//i'm pretty sure this is not how the final design should work but im doing it like this anyways
//because im not going to screw with the backend without people telling me i should
function addCourse(courseData) {
	const studentID = student.state;
	fetch(`rest/Student/${studentID}`, {
		method: "PUT",
		headers: {
			'Content-Type':'application/json'
		},
		//TODO: blame the person who thought it was a good idea to accept a LIST for a SINGLE COURSE
		//NAME. THINGS. CONSISTENTLY.
		body: "[" + JSON.stringify(courseData) + "]"
	})
	
}

function removeCourse(courseData) {
	const studentID = student.state;
	fetch(`rest/Student/${studentID}`, {
		method: "DELETE",
		headers: {
			'Content-Type':'application/json'
		},
		body: JSON.stringify(courseData)
	})
}

window.addCourse = addCourse;
window.removeCourse = removeCourse;
window.addEventListener("hashchange", () => route(location.hash));
window.addEventListener("load", () => route(location.hash));