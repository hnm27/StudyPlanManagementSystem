package nl.utwente.m4.studyplan.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StudyPlan {  
	
	private List<Course> courses;
	private String StudentCommment;
	private String MentorComment;
	private Boolean firstTime; //first time filling out a form 
	private String approved=null; // study plan is approved or not
	private String studyid;
	private String studentid;
	private int total_credits;
	private Boolean internship; //number of credits in internship is fixed
	private String sfirstname;
	private String slastname;
	
	public StudyPlan() {}
	
	public String getStudentComments() {
		return this.StudentCommment;
	}
	public String getMentorComments() {
		return this.MentorComment;
	}
	public Boolean getfirstTime() {
		return this.firstTime;
	}
	public String getapproved() {
		return this.approved;
	}
	public void setStudentComment(String StudentComment) {
		this.StudentCommment=StudentComment;
	}
	public void setMentorComment(String MentorComment) {
		this.MentorComment=MentorComment;
	}
	public void setApproved(String approved) {
		this.approved=approved;
	}
	public void setFirstTime(Boolean firstTime) {
		this.firstTime=firstTime;
	}
	public String getstudyId() {
		return this.studyid;
	}
	public void setstudyID(String id) {
		this.studyid=id;
	}
	public String getstudentId() {
		return this.studentid;
	}
	public void setstudentID(String id) {
		this.studentid=id;
	}
	public int getTotal_credits() {
		return total_credits;
	}
	public void setTotal_credits(int total_credits) {
		this.total_credits = total_credits;
	}

	public Boolean getInternship() {
		return internship;
	}

	public void setInternship(Boolean val) {
		this.internship = val;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public String getSfirstname() {
		return sfirstname;
	}

	public void setSfirstname(String sfirstname) {
		this.sfirstname = sfirstname;
	}

	public String getSlastname() {
		return slastname;
	}

	public void setSlastname(String slastname) {
		this.slastname = slastname;
	}
}
