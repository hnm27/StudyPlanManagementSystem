package nl.utwente.m4.studyplan.resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

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

import nl.utwente.m4.studyplan.dao.ListOfMentors;
import nl.utwente.m4.studyplan.model.Mentor;

@Path("/Mentor")
public class MentorsResource {
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	public MentorsResource() {
		// TODO Auto-generated constructor stub
	}
	
	//Returns a list of all mentors
	
		@Path("allmentors")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<Mentor> getallmentors() throws SQLException, IOException{
			List<Mentor> mentors=new ArrayList<>(ListOfMentors.instance.getall());
			return mentors;
		}
		//create new mentor profile - (not used in frontend,only for databse input)
		
		@Path("createprofile")
		@POST
		@Produces(MediaType.TEXT_HTML)
		@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
		public void createNewMentor(@FormParam("lastName")String lastname,@FormParam("firstName")String firstname,@FormParam("emp_number")String mid,@FormParam("email_address")String email,@FormParam("programme")String programme,@FormParam("password")String password,@Context HttpServletResponse servletResponse) throws IOException, SQLException {
			
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
			String squery="INSERT INTO mentor(mid,programme)" + 
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
				con.setAutoCommit(false);
				savepoint1 = con.setSavepoint("Mentor profile Update");
				ps.setString(1, mid);
				ps.setString(2, firstname);
				ps.setString(3, lastname);
				ps.setString(4, email);
				ps.setString(5, hashpassword);
				ps.setBinaryStream(6, saltinput);
				int rowAffected1 = ps.executeUpdate();
	            System.out.println(String.format("Person class : Row affected %d", rowAffected1));
	            ps =con.prepareStatement(squery);
				ps.setString(1, mid);
				ps.setString(2, programme);
				int rowAffected = ps.executeUpdate();
				con.commit();
				System.out.println(String.format("Student class : Row affected %d", rowAffected));
			}
			catch(SQLException sqle) {
				System.err.println("Error connecting: " + sqle);
				con.rollback(savepoint1);
				servletResponse.sendError(404,"ERROR CREATING MENTOR PROFILE");
			}
			finally {
				con.setAutoCommit(true);
				con.close();
			}
		}
		
		
		@Path("{mentorid}")
		@Produces(MediaType.APPLICATION_JSON)
		public MentorResource getMentor(@PathParam("mentorid")String id) {
			return new MentorResource(uriInfo,request,id);
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
