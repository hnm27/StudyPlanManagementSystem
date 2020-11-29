package nl.utwente.m4.studyplan.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

import nl.utwente.m4.studyplan.dao.ListOfStudyPlans;

@XmlRootElement
public class Student {
	private String firstname;
	private String lastname;
	private String snum;
	private String email;
	private String programme;
	private String password;
	private byte[] salt;

	public Student() {}
	

	public Student(String firstname,String lastname,String snum,String email,String programme,String password) {
		this.firstname=firstname;
		this.lastname=lastname;
		this.password=password;
		this.snum=snum;
		this.email=email;
		this.programme=programme;
	}
	public void setfirstname(String f) {
		this.firstname=f;
	}
	public void setpassword(String f) {
		this.password=f;
	}
	public void setlastname(String f) {
		this.lastname=f;
	}
	public void setnum(String f) {
		this.snum=f;
	}
	public void setprogramme(String f) {
		this.programme=f;
	}
	public void setemail(String f) {
		this.email=f;
	}
	public String getemail() {
		return this.email;
	}
	public String getpassword() {
		return this.password;
	}
	public String getsnum() {
		return this.snum;
	}
	public String getProgramme() {
		return this.programme;
	}
	public String getfirstname() {
		return this.firstname;
	}
	public String getlastname() {
		return this.lastname;
	}
	//get all study plans of this student
	
	public List<StudyPlan> getAllStudyPlans() throws SQLException {
		List<StudyPlan> allstudyplans = new ArrayList<StudyPlan>(ListOfStudyPlans.instance.getAllStudyPlans(getsnum()));
		return allstudyplans;
	}
	
	public StudyPlan getStudyPlanbyId(String id) throws SQLException {
		List<StudyPlan> allstudyplans = new ArrayList<StudyPlan>(ListOfStudyPlans.instance.getAllStudyPlans(getsnum()));
		for(StudyPlan s:allstudyplans) {
			if(s.getstudyId().contentEquals(id)) {
				return s;
			}
		}
		return null;
		
	}

	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] bs) {
		this.salt = bs;
	}
	
	
	
	
	
	

}
