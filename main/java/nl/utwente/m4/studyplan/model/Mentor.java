package nl.utwente.m4.studyplan.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Mentor {
	
	private String firstname;
	private String programme;
	private String emp_id;
	private String email;
	private String password;
	private String lastname;
	private byte[] salt;
 
	public Mentor() {}
	
	public Mentor(String firstname,String lastname,String email,String programme,String emp_id,String password) {
		this.firstname=firstname;
		this.setLastname(lastname);
		this.programme=programme;
		this.emp_id=emp_id;
		this.email=email;
		this.password=password;
	}
	
	public String getfirstName() {
		return this.firstname;
	}
	public String getProgramme() {
		return this.programme;
	}
	public String getId() {
		return this.emp_id;
	}
	public String getEmail() {
		return this.email;
	}
	public void SetfirstName(String firstname) {
		this.firstname=firstname;
	}
	public void SetProgramme(String programme) {
		this.programme=programme;
	}
	public void SetId(String emp_id) {
		this.emp_id=emp_id;
	}
	public void SetEmail(String email) {
		this.email=email;
	}
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public byte[] getSalt() {
		return this.salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	

}
