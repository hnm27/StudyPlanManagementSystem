package nl.utwente.m4.studyplan.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import nl.utwente.m4.studyplan.dao.ListOfStudents;
import nl.utwente.m4.studyplan.dao.ListOfStudyPlans;
import nl.utwente.m4.studyplan.model.Approval;
import nl.utwente.m4.studyplan.model.Course;
import nl.utwente.m4.studyplan.model.Student;
import nl.utwente.m4.studyplan.model.StudyPlan;

public class StudyPlanResource { 

	@Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  String studyplanid;
	  String studentid;
	  
	  public StudyPlanResource(UriInfo uriInfo, Request request, String studyplanid,String studentid) {
	    this.uriInfo = uriInfo;
	    this.request = request;
	    this.studyplanid = studyplanid;
	    this.studentid=studentid;
	  }
	  
	//Application integration     
	  @GET
	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public StudyPlan getStudent() throws SQLException, IOException {
		  Student s=new Student();
		  s=ListOfStudents.instance.getByid(studentid);
		  StudyPlan sp=new StudyPlan();
		  sp=s.getStudyPlanbyId(studyplanid);
		  if(sp==null) {
			  throw new RuntimeException("Get: StudyPlan with " + studyplanid +"not found");
		  }
		  return sp;
	  }
	  
	  // for the browser
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public StudyPlan getStudentHTML() throws SQLException, IOException {
		  Student s=new Student();
		  s=ListOfStudents.instance.getByid(studentid);
		  StudyPlan sp=new StudyPlan();
		  sp=s.getStudyPlanbyId(studyplanid);
		  if(sp==null) {
			  throw new RuntimeException("Get: StudyPlan with " + studyplanid +"not found");
		  }
		  return sp;
	  }
	  
	  @Path("chosencourses")
	  @GET
	  @Consumes(MediaType.APPLICATION_JSON)
	  public List<Course> getCourses() throws SQLException {
		  List<Course> courses= new ArrayList<>(ListOfStudyPlans.instance.getCoursesforStudyPlan(this.studyplanid, this.studentid));
		  return courses;
		  
	  }
	//for mentor to add a comment and approve study plan
	
	@Path("approve")
    @PUT
	@Consumes(MediaType.APPLICATION_JSON)
		public void approve_comment(Approval a) throws SQLException, IOException {  
			Savepoint savepoint1=null;
			Connection con=null;
			String squery="UPDATE studyplan" + 
					" SET mentorcomment=?,approved=?" + 
					"WHERE studyid=?";
			
			String username="dab_dsgnprj_11";
			String password1="jK2is7Hr/6okpzQd";
			try {
				Class.forName("org.postgresql.Driver");
			}
			catch(ClassNotFoundException cnfe ) {
				System.err.println("Error loading driver: "+ cnfe);
			}
			String host = "bronto.ewi.utwente.nl";
			String dbName= "dab_dsgnprj_11";
			String url= "jdbc:postgresql://" + host + ":5432/" + dbName;
			
			try {
				con =
				(Connection) DriverManager.getConnection(url, username, password1);
				PreparedStatement 
				
				ps =con.prepareStatement(squery);
				con.setAutoCommit(false);
				savepoint1 = con.setSavepoint("StudyPlan Approval Update");
				ps.setString(1, a.getMentorcomments());
				ps.setString(2,a.getApproved());
				ps.setString(3, this.studyplanid);
				int rowAffected1 = ps.executeUpdate();
				con.commit();
	            System.out.println(String.format("StudyPlan class : Row affected %d", rowAffected1));
			}
			catch(SQLException sqle) {
				System.err.println("Error connecting: " + sqle);
				con.rollback(savepoint1);
			}
			finally {
				con.setAutoCommit(true);
				con.close();
			}
	}
		
	  
	  

}
