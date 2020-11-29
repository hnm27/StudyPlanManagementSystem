package nl.utwente.m4.studyplan.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Form {
	
	private List<String> courseIds;
	private String studentcomment;
	private Boolean firsttime;
	private Boolean internship;
	private int ecs;
	private String studentcomment1;
	

	public Form() {
		// TODO Auto-gated constructor stub
	}


	public List<String> getCourseIds() {
		return courseIds;
	}


	public void setCourseIds(List<String> courseIds) {
		this.courseIds = courseIds;
	}


	public String getStudentcomment() {
		return studentcomment;
	}


	public void setStudentcomment(String studentcomment) {
		this.studentcomment = studentcomment;
	}


	public Boolean getFirsttime() {
		return firsttime;
	}


	public void setFirsttime(Boolean firsttime) {
		this.firsttime = firsttime;
	}


	public Boolean getInternship() {
		return internship;
	}


	public void setInternship(Boolean internship) {
		this.internship = internship;
	}


	public int getEcs() {
		return ecs;
	}


	public void setEcs(int ecs) {
		this.ecs = ecs;
	}


	public String getStudentcomment1() {
		return studentcomment1;
	}


	public void setStudentcomment1(String studentcomment1) {
		this.studentcomment1 = studentcomment1;
	}

}
