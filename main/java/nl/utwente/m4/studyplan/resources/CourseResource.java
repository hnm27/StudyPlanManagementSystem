package nl.utwente.m4.studyplan.resources;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nl.utwente.m4.studyplan.dao.ListOfCourses;
import nl.utwente.m4.studyplan.model.Course;

@Path("/courses")
public class CourseResource {

	public CourseResource() {

	}
	
	
	//get all courses
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getAll() throws SQLException {
		List<Course> items = new ArrayList<Course>();
		for (Map.Entry<String, Course> es : ListOfCourses.instance.getAllCourses().entrySet())
			items.add(es.getValue());
		return (items);
	}
	
	
	//get the number of courses selected
	
	@Path("count")
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() throws SQLException {
		int count = ListOfCourses.instance.getAllCourses().size();
		return String.valueOf(count);
	}
	
	//get a course by ID

	@GET
	@Path("{courseID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getTodo(@PathParam("courseID") String id) throws SQLException {
		return ListOfCourses.instance.getAllCourses().get(id);
	}

}
