package nl.utwente.m4.studyplan.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import org.apache.commons.codec.binary.Hex;

import nl.utwente.m4.studyplan.dao.ListOfStudents;
import nl.utwente.m4.studyplan.model.Student;

@Path("/Student")
public class StudentsResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	//Creates a new student profile
	
	@Path("createprofile")
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	public void createNewStudent(@FormParam("lastName")String lastname,@FormParam("firstName")String firstname,@FormParam("student_number")String snum,@FormParam("email_address")String email,@FormParam("programme")String programme,@FormParam("password")String password,@Context HttpServletResponse servletResponse) throws IOException, SQLException {
		
		byte[] salt =createSalt();
		InputStream saltinput = new ByteArrayInputStream(salt);
		for(byte b: salt){
	           System.out.print(b);
	       }
		System.out.println();
		String hashpassword= getPasswordHash(password,salt);
		System.out.println(hashpassword);
		
		Savepoint savepoint1=null;
		Connection con=null;
		String squery="INSERT INTO student(sid,programme)" + 
				"VALUES(?,?)";
		String pquery="INSERT INTO person(pid,first_name,last_name,email,password,salt)" + 
				"VALUES(?,?,?,?,?,?)";
		String username="dab_dsgnprj_11";
		String password1="jK2is7Hr/6okpzQd";
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch(ClassNotFoundException cnfe ) {
			System.err.println("Error loading driver: "+ cnfe);
		}
		String host = "bronto.ewi.utwente.nl";
		String dbName= "dab_dsgnprj_11";
		String url= "jdbc:postgresql://" + host + ":5432/" + dbName;
		
		try {
			con =
			(Connection) DriverManager.getConnection(url, username, password1);
			PreparedStatement 
			ps =con.prepareStatement(pquery);
			con.setAutoCommit(false); //set autocommit to false
			savepoint1 = con.setSavepoint("Student Profile Update"); //set savepoint for rollback
			ps.setString(1, snum);
			ps.setString(2, firstname);
			ps.setString(3, lastname);
			ps.setString(4, email);
			ps.setString(5, hashpassword);
			ps.setBinaryStream(6, saltinput);
			int rowAffected1 = ps.executeUpdate();
            System.out.println(String.format("Person class : Row affected %d", rowAffected1));
            ps =con.prepareStatement(squery);
			ps.setString(1, snum);
			ps.setString(2, programme);
			int rowAffected = ps.executeUpdate();
			con.commit(); // commit the updates
			System.out.println(String.format("Student class : Row affected %d", rowAffected));
            servletResponse.addHeader("id",snum);
            servletResponse.sendRedirect("/onlineStore/courses/"+programme+".html");
		}
		catch(SQLException sqle) {
			
			System.err.println("Error connecting: " + sqle);
			con.rollback(savepoint1);  //if there is an error, you rollback to the savepoint
			servletResponse.sendError(404,"THIS STUDENT NUMBER ALREADY EXISTS");
		}
		finally {
			con.setAutoCommit(true); //set autocommit to true
			con.close(); //close connection
		}
	}
	
	//Returns a list of all Students
	
	@Path("allstudents")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Student> getallstudents() throws SQLException, IOException{
		List<Student> students=new ArrayList<>(ListOfStudents.instance.getall());
		return students;
	}
	
	//Returns the total number of students
	
	@Path("count")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public int numOfStudents() throws SQLException, IOException {
		List<Student> students=new ArrayList<>(ListOfStudents.instance.getall());
		return students.size();
	}
	
	@Path("{studentid}")
	@Produces(MediaType.APPLICATION_JSON)
	public StudentResource getStudent(@PathParam("studentid")String id) {
		return new StudentResource(uriInfo,request,id);
	}
	
	//generate salted hash
	
	public static String getPasswordHash(String password,byte[] salt) {
		String encode="";
		try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(salt);
            byte[] hash = md.digest(password.getBytes());
           
            
            encode = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException ex) {
        	ex.printStackTrace(); 
        }
        
		return encode;
	}
	
	public static byte[] createSalt() {
		byte[] bytes = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(bytes);
		return bytes;
	}
	
	
	
	
	
	
	
}
