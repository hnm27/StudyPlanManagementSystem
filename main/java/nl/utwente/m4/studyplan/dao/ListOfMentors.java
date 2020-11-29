package nl.utwente.m4.studyplan.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Hex;

import nl.utwente.m4.studyplan.model.Mentor;
import nl.utwente.m4.studyplan.model.Student;

public enum ListOfMentors {
	instance;
	
	private ListOfMentors() {
		
	}
	
	//get a list of all mentors
	
	public List<Mentor> getall() throws SQLException, IOException{
		List<Mentor> allmentors=new ArrayList<>();
		Connection con=null;
		String query="SELECT * FROM person p,mentor m WHERE m.mid=p.pid";
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
				Mentor m=new Mentor();
				m.SetEmail(resultSet.getString("email"));
				m.SetId(resultSet.getString("pid"));
				m.setLastname(resultSet.getString("first_name"));
				m.SetfirstName(resultSet.getString("last_name"));
				m.SetProgramme(resultSet.getString("programme"));
				m.setPassword(resultSet.getString("password"));
				m.setSalt(inputstreamtobytearray(resultSet.getBinaryStream("salt")));
				
				allmentors.add(m);
		}
		con.close();
		return allmentors;	
	}
	
	//get a mentor by the mentor id
	
	public Mentor getByid(String id) throws SQLException, IOException {
		int c=0;
		Mentor m=new Mentor();
		Connection con=null;
		String query="SELECT * FROM person p,mentor m WHERE m.mid=p.pid AND m.mid = ?";
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
				m.SetEmail(resultSet.getString("email"));
				m.SetId(resultSet.getString("pid"));
				m.setLastname(resultSet.getString("first_name"));
				m.SetfirstName(resultSet.getString("last_name"));
				m.SetProgramme(resultSet.getString("programme"));
				m.setPassword(resultSet.getString("password"));
				m.setSalt(inputstreamtobytearray(resultSet.getBinaryStream("salt")));
		}
		con.close();
		if(c==0) {  //no rows found in databse
			return null;
		}
		return m;
	}
	
	//login for mentor
	
	public Mentor login(String email,String password) throws SQLException, IOException {
		List<Mentor> check = new ArrayList<>(instance.getall());
		for(Mentor s:check) {
			byte salt[]=s.getSalt();
			String hashpassword=getPasswordHash(password,salt);
			if(s.getEmail().contentEquals(email) && s.getPassword().contentEquals(hashpassword)) {
				return s;
			}
		}
		return null;
	}
	
	//add new mentor to database
	
	public void createNewMentor(String lastname,String firstname,String mid,String email,String programme,String password) {
		byte[] salt =createSalt();
		InputStream saltinput = new ByteArrayInputStream(salt);
		for(byte b: salt){
	           System.out.print(b);
	       }
		System.out.println();
		String hashpassword= getPasswordHash(password,salt);
		System.out.println(hashpassword);
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
			System.out.println(String.format("Student class : Row affected %d", rowAffected));
            con.close();
		}
		catch(SQLException sqle) {
			System.err.println("Error connecting: " + sqle);
		}	
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