function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1);
}

function redir() {
  $.ajax({
    url: "../rest/login/getlogin",
    type: "GET", //send it through get method
    data: {
      email: localStorage.getItem("user"),
      password: localStorage.getItem("pass")
    },
    success: function(response) {
      localStorage.setItem("URI", response);
      $.ajax({
        url: response,
        type: "GET", //send it through get method
        success: function(response) {
          document.getElementById("studyplan").href=`../courses/${response.programme}.html`; 
          var welcome = `Welcome ${capitalizeFirstLetter(response.firstname)}`;
          document.getElementById("welcome").innerHTML = welcome;
          console.log(response);
          localStorage.setItem("studentID", response.snum);
          
        },
        error: function(xhr) {
          //Do Something to handle error
        }
      });
    },
    error: function(xhr) {
      //Do Something to handle error
    }
  });
}

function studyplan(){
	console.log(out);
}