$(document).ready(function() {
  document.getElementById("internship").addEventListener("click", function() {
    if ($("#internship").prop("checked")) {
      $("#testing").slideDown();
    } else {
      $("#testing").slideUp();
    }
  });
});

var myOBJ = {
  firsttime: null,
  courseIds: [
    "201600070",
    "201200044",
    "201400174",
    "201700080",
    "191612680",
    "192199508",
    "192199978"
  ],
  internship: null,
  ecs: null,
  studentcomment: null,
  studentcomment1: null
};

let mand = 0;
[...document.getElementsByClassName("fourOutOfSix")].forEach(function(item) {
  item.addEventListener("change", function(e) {
    if (e.target.checked) {
      mand++;
      if (mand < 4) {
        document.getElementById("fourneeded").innerHTML = `<br><br>${4 -
          mand} more courses have to be selected`;
      } else {
        document.getElementById("fourneeded").innerHTML = "";
      }
    } else {
      mand--;
      if (mand < 4) {
        document.getElementById("fourneeded").innerHTML = `<br><br>${4 -
          mand} more courses have to be selected`;
      } else {
        document.getElementById("fourneeded").innerHTML = "";
      }
    }
  });
});

var total = 60;
[...document.getElementsByClassName("answers")].forEach(function(item) {
  item.addEventListener("change", function(e) {
    if (e.target.checked) {
      total += parseInt(e.target.value, 10);
    } else {
      total -= parseInt(e.target.value, 10);
    }
    document.getElementById("total").innerHTML = "Your Total is = " + total;
  });
});

[...document.getElementsByClassName("first")].forEach(function(item) {
  item.addEventListener("change", function(e) {
    if (item.checked) {
      if (item.value == "Yes") {
        myOBJ.firsttime = true;
        document.getElementById("errorChoice").innerHTML = "";
      } else {
        myOBJ.firsttime = false;
        document.getElementById("errorChoice").innerHTML = "";
      }
    }
  });
});

function testingg(event) {
  [...document.getElementsByClassName("advanced")].forEach(function(item) {
    for (let i = 0; i < 6; i++) {
      if (item.getElementsByTagName("INPUT")[i].checked) {
        myOBJ.courseIds.push(item.getElementsByTagName("INPUT")[i].id);
      }
    }
  });
  [...document.getElementsByClassName("profiling")].forEach(function(item) {
    for (let i = 0; i < 22; i++) {
      if (item.getElementsByTagName("INPUT")[i].checked) {
        myOBJ.courseIds.push(item.getElementsByTagName("INPUT")[i].id);
      }
    }
  });

  if (document.getElementById("internship").checked) {
    myOBJ.internship = true;
  } else {
    myOBJ.internship = false;
  }
  myOBJ.ecs = total;
  console.log(myOBJ);
  if (myOBJ.firsttime == null) {
    document.getElementById("header").scrollIntoView();
    document.getElementById("errorChoice").innerHTML = "<br>Choose one!";
  } else {
    var xmlhttpPost = new XMLHttpRequest();
    var studID = localStorage.getItem("studentID");
    xmlhttpPost.open("POST", `../rest/Student/${studID}/createstudy`, true);
    xmlhttpPost.setRequestHeader("Content-type", "application/json");
    xmlhttpPost.send(JSON.stringify(myOBJ));
    location.replace("../");
  }
}
