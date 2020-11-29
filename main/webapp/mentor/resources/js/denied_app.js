//var global;
function data(){
	var URI = localStorage.getItem("URI");
	$.ajax({
	        url: URI + "/denied",
	        type: "GET", //send it through get method
	        success: function(response) {
	          let myOBJ = response;
	          loaded(myOBJ);
	        },
	        error: function(xhr) {
	          //Do Something to handle error
	        }
	});
}

function loaded(item) {
//	  global = item;
	  for (h = 0; h < item.length; h++) {
	    var table = document.getElementById("here");
	    
	    var row = table.insertRow(-1);
	    
	    cell_firstName = row.insertCell(0);
	    cell_lastName = row.insertCell(1);
	    cell1 = row.insertCell(2);
	    cell2 = row.insertCell(3);
	    cell3 = row.insertCell(4);
	    cell4 = row.insertCell(5);
	    cell5 = row.insertCell(6);
	    cell6 = row.insertCell(7);
	    
	    cell_firstName.innerHTML = "<b>" + item[h].sfirstname + "</b>";
	    cell_lastName.innerHTML = "<b>" + item[h].slastname + "</b>";


	    cell1.innerHTML = "<b>" + item[h].studyId + "</b>";
	    
	    if(item[h].firstTime) {
	    	cell2.innerHTML = "<b>Yes</b>";
	    } else {
	    	cell2.innerHTML = "<b>No</b>";
	    }
	    
	    var random = "";
	    
	    var table = document.getElementById("here");
	    
	    for (let j = 0; j < item[h].courses.length; j++) {
	      random = random + "<b>" + item[h].courses[j].description + "</b><br>";
	    }
	    
	    cell3.innerHTML = random;
	    
	    if(item[h].internship){
	    	cell4.innerHTML = "<b>Yes</b>";
	    } else {
	    	cell4.innerHTML = "<b>No</b>";
	    }
	    
	    cell5.innerHTML = "<b>" + item[h].total_credits + " ECs</b>";
	    
	    if(item[h].mentorComments === null){
	    	cell6.innerHTML = "<b>No Comments</b>";
	    } else {
	    	cell6.innerHTML = "<b>" + item[h].mentorComments + "</b>";
	    }
	    
	  }
	}
