$(document).ready(function() {
  $(".btn").on("click", function() {
	  localStorage.removeItem("pass");
      localStorage.removeItem("user");

      var element = document.getElementById("the_password");
      var input = element.value;

      var element2 = document.getElementById("the_username");
      var input2 = element2.value;

      if (input === "") {
        alert("The password cannot be empty!");
      } else if (input2 === "") {
        alert("The username cannot be empty!");
      } else {
        localStorage.setItem("pass", input);
        localStorage.setItem("user", input2);

        var form = document.getElementById("form_id");
        form.submit();
      }
  });

  $(document).on("keypress", function(e) {
    if (e.which == 13) {
      localStorage.removeItem("pass");
      localStorage.removeItem("user");

      var element = document.getElementById("the_password");
      var input = element.value;

      var element2 = document.getElementById("the_username");
      var input2 = element2.value;

      if (input === "") {
        alert("The password cannot be empty!");
      } else if (input2 === "") {
        alert("The username cannot be empty!");
      } else {
        localStorage.setItem("pass", input);
        localStorage.setItem("user", input2);

        var form = document.getElementById("form_id");
        form.submit();
      }
    }
  });
});

function reveal() {
  if (document.getElementById("eye").className == "fas fa-eye-slash") {
    document.getElementById("the_password").type = "text";
    document.getElementById("eye").className = "fas fa-eye";
  } else if (document.getElementById("eye").className == "fas fa-eye") {
    document.getElementById("the_password").type = "password";
    document.getElementById("eye").className = "fas fa-eye-slash";
  }
}

function forTest() {
  console.log("Does it work?");
}
