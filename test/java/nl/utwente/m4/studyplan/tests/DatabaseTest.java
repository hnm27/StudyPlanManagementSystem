package nl.utwente.m4.studyplan.tests;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;


public class DatabaseTest {
    private Connection conn;
    private static final String username = "dab_dsgnprj_11";
    private static final String password = "jK2is7Hr/6okpzQd";
    private static final String hostname = "jdbc:postgresql://bronto.ewi.utwente.nl:5432/dab_dsgnprj_11";


    public DatabaseTest() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ResultSet executeQuery(String query)  {
        try {
            this.conn = DriverManager.getConnection(hostname, username, password);
            ResultSet result = this.conn.createStatement().executeQuery(query);
            this.conn.close();
            return result;
        } catch (SQLException e) {
            System.out.println("Could not return anything \uD83D\uDE1E\n" + e.getMessage());
            return null;
        }
    }

    @Test
    public void testConnection() {
        // test if the database is online
        assertNotNull(executeQuery("SELECT * FROM course"));
    }

    @Test
    public void testTables() {
        //names of tables in our database, according to the schema
        String[] tables = {
                "course",
                "coursematch",
                "mentor",
                "person",
                "student",
                "studyplan"
        };
        //test for all tables if there is at least some data in them
        for (String tablename : tables) {
            ResultSet result = executeQuery("SELECT * FROM " + tablename);
            assertNotNull(result);
            try {
                // i think it's fine if there is at least ONE entry in each table
                assertTrue("table should not be empty",result.next());
            } catch (SQLException e) {
                fail("Error while going through ResultSet");
            }
        }
    }

}
