package com.rakesh.springBootDocker.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rakesh.springBootDocker.entities.Course;

@Service
public class CourseServiceImple {

	private static List<Course> courses = new ArrayList<>();
	  private static long idCounter = 0;

	  static {
	    courses.add(new Course(++idCounter, "rakesh", "Learn Spring Boot "));
	    courses.add(new Course(++idCounter, "rakesh", "Learn Full stack with Spring Boot and React"));
	    courses.add(new Course(++idCounter, "rakesh", "Master Microservices with Spring Boot and Spring Cloud"));
	    courses.add(new Course(++idCounter, "rakesh","Deploy Spring Boot Microservices to Cloud with Docker and Kubernetes"));
	  }

	  public List<Course> findAll() {
	    return courses;
	  }
	  public Course deleteById(long id) {
		    Course course = findById(id);

		    if (course == null)
		      return null;

		    if (courses.remove(course)) {
		      return course;
		    }

		    return null;
		  }
	  public Course findById(long id) {
		    for (Course course : courses) {
		      if (course.getId() == id) {
		        return course;
		      }
		    }

		    return null;
		  }
	  public Course save(Course course) {
		    if (course.getId() == -1 || course.getId() == 0) {
		      course.setId(++idCounter);
		      courses.add(course);
		    } else {
		      deleteById(course.getId());
		      courses.add(course);
		    }
		    return course;
		  }
}
