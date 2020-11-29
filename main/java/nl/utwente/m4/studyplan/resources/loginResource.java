package nl.utwente.m4.studyplan.resources;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import nl.utwente.m4.studyplan.dao.ListOfMentors;
import nl.utwente.m4.studyplan.dao.ListOfStudents;
import nl.utwente.m4.studyplan.model.Mentor;
import nl.utwente.m4.studyplan.model.Student;

@Path("/login")
public class loginResource {

	public loginResource() {
		// TODO Auto-generated constructor stub
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	public void loginStudentMentor(@FormParam("email")String email,@FormParam("password")String password,@Context HttpServletResponse servletResponse) throws IOException, SQLException {
		  Student s=new Student();
		  Mentor m=new Mentor();
		  m=ListOfMentors.instance.login(email, password);
		  s=ListOfStudents.instance.login(email, password);
		  if(s!=null && m==null) {
			  servletResponse.sendRedirect("/onlineStore/student/student.html"); 
		  } 
		  else if(s==null && m!=null) {
			  servletResponse.sendRedirect("/onlineStore/mentor/mentor.html");  
		  }
		  else {
		  servletResponse.sendError(403, "WRONG EMAIL OR PASSWORD, PLEASE TRY AGAIN!!");  
	  }
	}
	
	@Path("getlogin")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@QueryParam("email")String email,@QueryParam("password")String password) throws SQLException, IOException {
		Student s=new Student();
		  Mentor m=new Mentor();
		  m=ListOfMentors.instance.login(email, password);
		  s=ListOfStudents.instance.login(email, password);
		  if(s!=null && m==null) {
			  String snum= s.getsnum();
			  return "../rest/Student/"+snum;  //url to access Student resource
		  } 
		  else if(s==null && m!=null) {
			  String mnum=m.getId();
			  return "../rest/Mentor/"+mnum;  //url to access Mentor resource
			  
		  }
		  else {
		  return null;
		
	}}

}
