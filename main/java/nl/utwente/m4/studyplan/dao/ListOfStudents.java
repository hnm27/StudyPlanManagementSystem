package nl.utwente.m4.studyplan.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.Context;

import org.apache.commons.codec.binary.Hex;

import nl.utwente.m4.studyplan.model.Student;

public enum ListOfStudents {
	instance;
	
	private ListOfStudents() {}
	
	//get a list of all students
	
	public List<Student> getall() throws SQLException, IOException{
		List<Student> allstudents=new ArrayList<Student>();
		Connection con=null;
		String query="SELECT * FROM person p,student s WHERE s.sid=p.pid";
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
				Student s=new Student();
				s.setfirstname(resultSet.getString("first_name"));
				s.setlastname(resultSet.getString("last_name"));
				s.setemail(resultSet.getString("email"));
				s.setpassword(resultSet.getString("password"));
				s.setnum(resultSet.getString("pid"));
				s.setprogramme(resultSet.getString("programme"));
				s.setSalt(inputstreamtobytearray(resultSet.getBinaryStream("salt")));
				allstudents.add(s);
				
		}
		con.close();
		return allstudents;	
	}
	
	//get a student by student id
	
	public Student getByid(String id) throws SQLException, IOException {
		Student s = null;
		int c=0;
		Connection con=null;
		String query="SELECT * FROM person p,student s WHERE s.sid=p.pid AND s.sid= ?";
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
		statement.setString(1, id);
		ResultSet resultSet=statement.executeQuery();
		while(resultSet.next()) {
				++c;
				s=new Student();
				s.setfirstname(resultSet.getString("first_name"));
				s.setlastname(resultSet.getString("last_name"));
				s.setemail(resultSet.getString("email"));
				s.setpassword(resultSet.getString("password"));
				s.setnum(resultSet.getString("pid"));
				s.setprogramme(resultSet.getString("programme"));
				s.setSalt(inputstreamtobytearray(resultSet.getBinaryStream("salt")));
				
		}
		con.close();
		if(c==0) {
			return null;
		}
		else return s;
	}
	
	//login for student using salted hashing 
	
	public Student login(String email,String password) throws SQLException, IOException {
		 
		List<Student> check = new ArrayList<>(getall());
		for(Student s:check) {
			byte salt[]=s.getSalt();
			String hashpassword=getPasswordHash(password,salt);
			if(s.getemail().contentEquals(email) && s.getpassword().contentEquals(hashpassword)) {
				return s;
			}
		}
		return null;
	}
		//remove student from database
		
		public void delStudent(String id) {
			
			Connection con=null;
			String squery="DELETE FROM student WHERE sid=?)";
			String pquery="DELETE FROM person WHERE pid=?";
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
				PreparedStatement statement =con.prepareStatement(squery);
				statement.setString(1, id);
				int rowsa=statement.executeUpdate();
				System.out.println("student rows affected:"+ rowsa);
				statement=con.prepareStatement(pquery);
				statement.setString(1, id);
				int rows1=statement.executeUpdate();
				System.out.println("person rows affected:"+ rows1);
	            con.close();
			}
			catch(SQLException sqle) {
				System.err.println("Error connecting: " + sqle);
			}
		}
		
		//Add a student to database 
		
		public void addStudenttoDatabase(String lastname,String firstname,String snum,String email,String programme,String password) {
			
			byte[] salt =createSalt();
			InputStream saltinput = new ByteArrayInputStream(salt);
			for(byte b: salt){
		           System.out.print(b);
		       }
			System.out.println();
			String hashpassword= getPasswordHash(password,salt);
			System.out.println(hashpassword);
			
			
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
				System.out.println(String.format("Student class : Row affected %d", rowAffected));
	            con.close();
	            
			}
			catch(SQLException sqle) {
				System.err.println("Error connecting: " + sqle);
				
			}	
		}
		
		//generate salted hash
		
			public String getPasswordHash(String password,byte[] salt) {
				String encode="";
				try {
		            MessageDigest md = MessageDigest.getInstance("MD5");
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
			
			public byte[] inputstreamtobytearray(InputStream in) 
			  throws IOException { 
			 
			    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			    int nRead;
			    byte[] data = new byte[1024];
			    while ((nRead = in.read(data, 0, data.length)) != -1) {
			        buffer.write(data, 0, nRead);
			    }
			 
			    buffer.flush();
			    byte[] byteArray = buffer.toByteArray();
			    return byteArray;
			}
}
