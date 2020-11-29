function data(){
	var URI = localStorage.getItem("URI");
	$.ajax({
	        url: URI + "/studyplans",
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
	  
	  for (h = 0; h < item.length; h++) {
	    var table = document.getElementById("here");
	    
	    var row = table.insertRow(-1);
	    
	    cell1 = row.insertCell(0);
	    cell2 = row.insertCell(1);
	    cell3 = row.insertCell(2);
	    cell4 = row.insertCell(3);
	    cell5 = row.insertCell(4);
	    cell6 = row.insertCell(5);
	    cell7 = row.insertCell(6);

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
	    if(item[h].approved == true) {
		    cell7.innerHTML = "<b>" + "Approved" + "</b>";

	    } else if (item[h].approved === null) {
		    cell7.innerHTML = "<b>" + "Pending" + "</b>";
	    } else {
		    cell7.innerHTML = "<b>" + "Denied" + "</b>";

	    }
	    
	  }
	}
