package nl.utwente.m4.studyplan.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Approval {
	
	private String approved;
	private String mentorcomments;

	public Approval() {
		// TODO Auto-generated constructor stub
	}

	public String getMentorcomments() {
		return mentorcomments;
	}

	public void setMentorcomments(String mentorcomments) {
		this.mentorcomments = mentorcomments;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}

}
