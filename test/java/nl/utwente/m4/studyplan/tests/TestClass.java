package nl.utwente.m4.studyplan.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nl.utwente.m4.studyplan.dao.ListOfMentors;
import nl.utwente.m4.studyplan.dao.ListOfStudents;
import nl.utwente.m4.studyplan.dao.ListOfStudyPlans;
import nl.utwente.m4.studyplan.model.Course;
import nl.utwente.m4.studyplan.model.Student;
import nl.utwente.m4.studyplan.model.StudyPlan;
import nl.utwente.m4.studyplan.resources.CourseResource;
import nl.utwente.m4.studyplan.resources.MentorsResource;
import nl.utwente.m4.studyplan.resources.StudentsResource;
import nl.utwente.m4.studyplan.resources.loginResource;

public class TestClass {
		 	
			//test to check if courses are present in the database
		    @Test
		    public void testcourses() throws SQLException {
		    	CourseResource c=new CourseResource();
		    	int num=c.getAll().size();
		    	assertTrue(num>0);
		    }
		    
		    //test to access course by a courseId
		    @Test
		    public void checkCourseById() throws SQLException {
		    	CourseResource cr=new CourseResource();
		    	Course c=new Course();
		    	c=cr.getTodo("192199978");
		    	String name=c.getType();
		    	assertEquals("GRADUATION",name);
		    }
		    
		    //test to check for mentors in a database
		    @Test
		    public void checkmentors() throws SQLException, IOException {
		    	MentorsResource m=new MentorsResource();
		    	int num=m.getallmentors().size();
		    	assertTrue(num>0);
		    }
		    
		    //test to check studyplans for a student in the database
		    @Test
		    public void checkstudyplans() throws SQLException {
		    	List<StudyPlan> sp=new ArrayList<>();
		    	sp=ListOfStudyPlans.instance.getAllStudyPlans("s2382598");
		    	assertTrue(sp.size()>0);
		    }
		    
		    //test to see if there are students enrolled for a studyplan
		    @Test
		    public void checkstudents() throws SQLException, IOException {
		    	StudentsResource s=new StudentsResource(); 
		    	int num=s.getallstudents().size();
		    	assertTrue(num>0);
		    	
		    }
		    
		    
		        
  }


