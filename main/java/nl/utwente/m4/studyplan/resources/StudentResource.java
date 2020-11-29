package nl.utwente.m4.studyplan.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import nl.utwente.m4.studyplan.dao.ListOfStudents;
import nl.utwente.m4.studyplan.model.Form;
import nl.utwente.m4.studyplan.model.Student;
import nl.utwente.m4.studyplan.model.StudyPlan;

public class StudentResource {
	
	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  String id;
	  public StudentResource(UriInfo uriInfo, Request request, String id) {
	    this.uriInfo = uriInfo;
	    this.request = request;
	    this.id = id;
	  }
	  
	  //Application integration     
//	  @GET
//	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//	  public Student getStudent() throws SQLException, IOException {
//		  Student s=new Student();
//		  s=ListOfStudents.instance.getByid(id);
//		  if(s==null) {
//			  throw new RuntimeException("Get: Student with " + id +  " not found");
//		  }
//		  return s;
//	  }
//	  
	  //for the browser
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public Student getStudentHTML() throws SQLException, IOException {
		  Student s=new Student();
		  s=ListOfStudents.instance.getByid(id);
		  if(s==null) {
			  throw new RuntimeException("Get: Student with " + id +  " not found");
		  }
		  return s;
	  }
	  
	  //create a new study plan for this student
	  
	  @Path("createstudy")
	  @POST
	  @Consumes(MediaType.APPLICATION_JSON)
	  public void createNewStudyPlan(Form f) throws SQLException, IOException {
		  
		  Savepoint savepoint1=null;
		  Connection con=null; 
		  int c=0;
		  String studyid=id+Long.toString(System.currentTimeMillis());  //giving unique id to each studyplan
			String squery="INSERT INTO studyplan(total_ecs,internship,firsttime,student_id,studentcomment,studyid,studentcomment1) " + 
					"VALUES(?,?,?,?,?,?,?)";
			String cmquery="INSERT INTO coursematch(courseID,studyId) "+
					"VALUES(?,?)";
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
				savepoint1 = con.setSavepoint("Register for courses of student:"+this.id);
				ps.setInt(1,f.getEcs());
				ps.setBoolean(2, f.getInternship());
				ps.setBoolean(3, f.getFirsttime());
				ps.setString(4, this.id);
				ps.setString(5, f.getStudentcomment());
				ps.setString(6, studyid);
				ps.setString(7, f.getStudentcomment1());
				int rowAffected1 = ps.executeUpdate();
	            System.out.println(String.format("StudyPlan class : Row affected %d", rowAffected1));
	            PreparedStatement ps1=con.prepareStatement(cmquery);
	            for(String cid:f.getCourseIds()) {
	            	ps1.setString(1,cid);
	            	ps1.setString(2, studyid);
	            	ps1.executeUpdate();
	            	c++;
	            }
	            con.commit();
	            System.out.println(c+ " courses added to studyplan");
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
	 
	  //get all study plans for this student
	  
	  @Path("studyplans")
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<StudyPlan> getStudyPlans() throws SQLException, IOException{
		    
			Student s=new Student();
			s=ListOfStudents.instance.getByid(id);
			List<StudyPlan> studyplans=new ArrayList<>(s.getAllStudyPlans());
			return studyplans;
	  }
	  
	  //get a study plan according to ID
	  
	  
	  @Path("{studyplanid}")
	  @Produces(MediaType.APPLICATION_JSON) 
	  public StudyPlanResource getStudyPlan(@PathParam("studyplanid")String studyplanid) {
		  return new StudyPlanResource(uriInfo,request,studyplanid,id);
	  }
	  
	  
	  @DELETE
	  public void delstudent() {
		  ListOfStudents.instance.delStudent(id);
	  }
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  
	  

}
