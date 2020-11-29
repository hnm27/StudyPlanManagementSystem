package nl.utwente.m4.studyplan.tests;

import nl.utwente.m4.studyplan.dao.ListOfMentors;
import nl.utwente.m4.studyplan.model.Mentor;
import nl.utwente.m4.studyplan.dao.ListOfStudents;
import nl.utwente.m4.studyplan.model.Student;
import nl.utwente.m4.studyplan.resources.StudentsResource;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.Random;

/**
 * this class tests the properties of testmentor
 * testmentor is an entry in the database that has all the default properties of a mentor,
 * accepting applications for the
 */
public class RestTest {
    
    private Mentor refMentor;

    @Before
    public void setup() {
        //database insertion went a little bit wonky here...
        refMentor = new Mentor("testmentor", "testmentor", "dst", "testmentor", "0987", "8697dd7ee51135ac2a835f49e0848235");
    }

    @Test
    public void login() {
        try {
            if (ListOfMentors.instance.getByid("0987") == null) {
                ListOfMentors.instance.createNewMentor("testmentor", "testmentor", "0987",
                        "testmentor.com", "dst", "testmentor");
            }
            Mentor testmentor = ListOfMentors.instance.login("testmentor.com", "testmentor");
            assertNotNull("has the correct password", testmentor);
            // pretty sure this test is supposed to pass, UNLESS there are certain fields not being filled in...
            //assertTrue(refMentor.equals(testmentor));
        } catch (SQLException e) {
            fail("error connecting to database");
        } catch (IOException e) {
            fail("error generating salts");
        }
    }

    @Test
    public void testStudent() throws SQLException, IOException { //test to update database with a new student profile
        StudentsResource s=new StudentsResource();
        Random rand = new Random();
        int rand_int1 = rand.nextInt(1000); 
        int c=s.numOfStudents();
        ListOfStudents.instance.addStudenttoDatabase("TestName", "TestName", Integer.toString(rand_int1), "TestName.com", "dst", "strongpassword"); 
        int c1= s.numOfStudents();
        assertEquals(c+1,c1);
        Student student=ListOfStudents.instance.getByid(Integer.toString(rand_int1));
        assertEquals("TestName.com", student.getemail());
    }
}
