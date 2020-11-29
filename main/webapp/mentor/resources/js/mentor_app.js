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
          console.log(response);
//          document.getElementById("studyplan").href=`../courses/${response.programme}.html`; 
          var welcome = `Welcome ${capitalizeFirstLetter(response.firstName)}`;
          document.getElementById("welcome").innerHTML = welcome;
          localStorage.setItem("mentorID", response.id);
          
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