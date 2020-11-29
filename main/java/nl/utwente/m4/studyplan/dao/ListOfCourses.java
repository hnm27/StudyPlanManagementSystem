package nl.utwente.m4.studyplan.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.utwente.m4.studyplan.model.Course;
import nl.utwente.m4.studyplan.model.Student;;

public enum ListOfCourses {
	instance;

	

	private ListOfCourses() {}
	
	//get all courses from the database
	
	public Map<String, Course> getAllCourses() throws SQLException {
		
		Map<String,Course> courses = new HashMap<String,Course>();
		Connection con=null;
		String query="SELECT * FROM course";
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
		Statement statement =con.createStatement();
		ResultSet resultSet=statement.executeQuery(query);
		while(resultSet.next()) {
				Course c=new Course();
				c.setCredit(resultSet.getInt("ECs"));
				c.setDescription(resultSet.getString("description"));
				String courseId=resultSet.getString("courseId");
				c.setID(courseId);
				c.setType(resultSet.getString("type"));
				courses.put(courseId,c);
		}
		con.close();
		return courses;	
		
	}
	
	//get course from coursemap
	
	public Course getCourseById(String id) throws SQLException {
		Map<String, Course> coursemap=new HashMap<>(instance.getAllCourses());
		return coursemap.get(id);
		
	}

}