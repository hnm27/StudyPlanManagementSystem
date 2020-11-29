package nl.utwente.m4.studyplan.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Course {
  
	    private String id;
	    private String type;
		private int credits;
		private String description;
		
		public Course() {}

		public Course(String type,String id,int credits,String description) {
			this.type=type;
			this.id=id;
			this.credits=credits;
			this.description=description;	
		}
		public String getDescription() {
			return this.description;
		}
		public String getId() {
			return this.id;
		}
		public void setDescription(String description) {
			this.description=description;
		}
		public void setID(String id) {
			this.id=id;
		}
		public void setCredit(int credits) {
			this.credits=credits;
		}
		public int getcredit() {
			return this.credits;
		}
		public String getType() {
			return this.type;
		}
		public void setType(String type) {
			this.type=type;
		}
		public String toString() {
			  return "CourseID = " + this.getId() + " Description =  "+ this.getDescription();
		 }
}
