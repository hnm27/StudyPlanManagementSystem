package nl.utwente.m4.studyplan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nl.utwente.m4.studyplan.model.Course;
import nl.utwente.m4.studyplan.model.StudyPlan;

public enum ListOfStudyPlans {
	instance;
	
	private ListOfStudyPlans() {}
	
	//studyplan overview of a student with student_id
	
	public List<StudyPlan> merge(String studentid) throws SQLException{
		List<StudyPlan> allstudyplans=new ArrayList<StudyPlan>();
		Connection con=null;
		String query="SELECT * FROM studyplan s,person p WHERE s.student_id= ? AND s.student_id=p.pid";
		String username="dab_dsgnprj_11";
		String password="jK2is7Hr/6okpzQd";
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException cnfe ) {
			System.err.println("Error loading driver: "+ cnfe);
		}
		String host = "bronto.ewi.utwente.nl";
		String dbName= "dab_dsgnprj_11";
		String url= "jdbc:postgresql://" + host + ":5432/" + dbName;
		
		con =(Connection) DriverManager.getConnection(url, username, password);
		PreparedStatement statement =con.prepareStatement(query);
		statement.setString(1,studentid);
		ResultSet resultSet=statement.executeQuery();
		while(resultSet.next()) {
				StudyPlan s=new StudyPlan();
				s.setstudyID(resultSet.getString("studyid"));
				s.setTotal_credits(resultSet.getInt("total_ecs"));
				s.setInternship(resultSet.getBoolean("internship"));
				s.setApproved(resultSet.getString("approved"));
				s.setMentorComment(resultSet.getString("mentorcomment"));
				s.setstudentID(studentid);
				s.setStudentComment(resultSet.getString("studentcomment"));
				s.setFirstTime(resultSet.getBoolean("firsttime"));
				s.setSfirstname(resultSet.getString("first_name"));
				s.setSlastname(resultSet.getString("last_name"));
				allstudyplans.add(s);
		}
		con.close();
		return allstudyplans;	
	}
	
	//get a list of courses chosen in a studyplan(studyplan_id) by student(student_id)
	
	public List<Course> getCoursesforStudyPlan(String studyid,String studentid) throws SQLException{
		List<Course> allcourses=new ArrayList<Course>();
		Connection con=null;
		String query="SELECT c.courseid,c.type,c.ecs,c.description FROM course c,coursematch m,studyplan s " + 
				"WHERE c.courseid=m.courseid " + 
				"AND m.studyid=s.studyid AND s.studyid=? AND s.student_id=?";
		String username="dab_dsgnprj_11";
		String password="jK2is7Hr/6okpzQd";
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException cnfe ) {
			System.err.println("Error loading driver: "+ cnfe);
		}
		String host = "bronto.ewi.utwente.nl";
		String dbName= "dab_dsgnprj_11";
		String url= "jdbc:postgresql://" + host + ":5432/" + dbName;
		
		con =(Connection) DriverManager.getConnection(url, username, password);
		PreparedStatement statement =con.prepareStatement(query);
		statement.setString(1, studyid);
		statement.setString(2, studentid);
		ResultSet resultSet=statement.executeQuery();
		while(resultSet.next()) {
				Course course=new Course();
				course.setCredit(resultSet.getInt("ecs"));
				course.setID(resultSet.getString("courseid"));
				course.setDescription(resultSet.getString("description"));
				course.setType(resultSet.getString("type"));
				allcourses.add(course);
		}
		con.close();
		return allcourses;
	}
	
	//get all the studyplans of a student
	
	public List<StudyPlan> getAllStudyPlans(String studentid) throws SQLException{
		List<StudyPlan> allstudyplans=new ArrayList<>(this.merge(studentid));
		for(StudyPlan s:allstudyplans) {
			 s.setCourses(this.getCoursesforStudyPlan(s.getstudyId(),studentid));
		}
		return allstudyplans;
	}
	
	
	

}
