$(document).ready(function() {
  $(".btns").on("click", function() {
    submitter();
  });
  $(".ret").on("click", function() {
	    location.replace("../");
	  });
});

function submitter() {
  var form = document.getElementById("form_id");
  var studID = document.getElementById("student_number").value;
  localStorage.setItem("studentID", studID);

  var element = document.getElementById("password");
  var input = element.value;

  var element2 = document.getElementById("conf_password");
  var input2 = element2.value;

  if (input2 == input) {
    document.getElementById("testing").innerHTML = "";
    form.submit();
  } else {
    document.getElementById("testing").innerHTML = "Password does not match!";
  }
}
