package nl.utwente.m4.studyplan.resources;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import nl.utwente.m4.studyplan.dao.ListOfMentors;
import nl.utwente.m4.studyplan.dao.ListOfStudents;
import nl.utwente.m4.studyplan.model.Mentor;
import nl.utwente.m4.studyplan.model.Student;
import nl.utwente.m4.studyplan.model.StudyPlan;

public class MentorResource {

	  @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  String id;
	  public MentorResource(UriInfo uriInfo, Request request, String id) {
	    this.uriInfo = uriInfo;
	    this.request = request;
	    this.id = id;
	  }
	  
	//Application integration     
//	  @GET
//	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
//	  public Mentor getMentor() throws SQLException {
//		  Mentor s=new Mentor();
//		  s=ListOfMentors.instance.getByid(id);
//		  if(s==null) {
//			  throw new RuntimeException("Get: Mentor with " + id +  " not found");
//		  }
//		  return s;
//	  }
//	  
	  
	  // for the browser
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public Mentor getMentorHTML() throws SQLException, IOException {
		  Mentor s=new Mentor();
		  s=ListOfMentors.instance.getByid(id);
		  if(s==null) {
			  throw new RuntimeException("Get: Mentor with " + id +  " not found");
		  }
		  return s;
	  }
	  
	  public List<StudyPlan> getallstudyplans() throws SQLException, IOException{
		  List<Student> allstudents=new ArrayList<Student>(ListOfStudents.instance.getall());
		  List<StudyPlan> allstudyplans=new ArrayList<>();
		  Mentor m=new Mentor();
		  m=ListOfMentors.instance.getByid(id);
		  for(Student s:allstudents) {
			  if(s.getProgramme().toLowerCase().contentEquals(m.getProgramme().toLowerCase())) {   //check for studyplans of this programme mentor
				  allstudyplans.addAll(s.getAllStudyPlans());
			  }
		  }
		  return allstudyplans;
	  }
	  
	  //get all students for this programme mentor
	  
	  @Path("studyplans")
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<StudyPlan> getMyStudents() throws SQLException, IOException{
		  List<StudyPlan> mystudyplans=new ArrayList<>(getallstudyplans());
		  return mystudyplans;  
	  }
	  
	  //get all approved studyplans
	  
	  @Path("approved")
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<StudyPlan> getApproved() throws SQLException, IOException{
		  List<StudyPlan> all=new ArrayList<>(getallstudyplans());
		  List<StudyPlan> approved=new ArrayList<>();
		  for(StudyPlan s:all) {
			  if(s.getapproved()==null) {
				  continue;
			  }
			  if(s.getapproved().equals("Yes")) {
				  approved.add(s);
			  }
		  }
		  return approved;	  
	  }
	  
	  //get all denied studyplans
	  
	  @Path("denied")
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<StudyPlan> getDenied() throws SQLException, IOException{
		  List<StudyPlan> all=new ArrayList<>(getallstudyplans());
		  List<StudyPlan> denied=new ArrayList<>();
		  for(StudyPlan s:all) {
			  if(s.getapproved()==null) {
				  continue;
			  }
			  if(s.getapproved().equals("No")) {
				  denied.add(s);
			  }
		  }
		  return denied;	  
	  }
	  
	  //get all pending studyplans
	  
	  @Path("pending")
	  @GET
	  @Produces(MediaType.APPLICATION_JSON)
	  public List<StudyPlan> getPending() throws SQLException, IOException{
		  List<StudyPlan> all=new ArrayList<>(getallstudyplans());
		  List<StudyPlan> pending=new ArrayList<>();
		  for(StudyPlan s:all) {
			  if(s.getapproved()==null) {
				  pending.add(s);
			  }
		  }
		  return pending;	  
	  }
	  
	  
	  
	  
	  
	  
	  
	  

}
