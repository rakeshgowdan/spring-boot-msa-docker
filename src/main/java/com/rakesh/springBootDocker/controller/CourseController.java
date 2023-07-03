package com.rakesh.springBootDocker.controller;

import java.net.URI;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rakesh.springBootDocker.entities.Course;
import com.rakesh.springBootDocker.service.CourseServiceImple;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CourseController {

	
	
		@Autowired
		  private CourseServiceImple courseManagementService;
		
	@GetMapping("/instructors/{username}/courses")
	public List<Course> getAllCourses(@PathVariable String username, HttpServletRequest request) {

		System.err.println(getIPAddress(request));
		
		System.err.println(getClientInformation(request));

		return courseManagementService.findAll();
	}

	/**
	 * @param request
	 */
	public String getClientInformation(HttpServletRequest request) {
		String browserDetails = request.getHeader("User-Agent");
		
		System.err.println("browserDetails " + browserDetails);

		String userAgent = browserDetails;
		String user = userAgent.toLowerCase();

		String os = "";
		String browser = "";
		System.err.println("User Agent for the request is===>" + browserDetails);
		// =================OS=======================
		if (userAgent.toLowerCase().indexOf("windows") >= 0) {
			os = "Windows";
		} else if (userAgent.toLowerCase().indexOf("mac") >= 0) {
			os = "Mac";
		} else if (userAgent.toLowerCase().indexOf("x11") >= 0) {
			os = "Unix";
		} else if (userAgent.toLowerCase().indexOf("android") >= 0) {
			os = "Android";
		} else if (userAgent.toLowerCase().indexOf("iphone") >= 0) {
			os = "IPhone";
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		// ===============Browser===========================
		if (user.contains("edg")) {
			log.info("edge");
			browser = (userAgent.substring(userAgent.indexOf("Edg")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("msie")) {
			log.info("msie");
			String substring = userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-" + substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			log.info("safari");
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0] + "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			log.info("opera");
			if (user.contains("opera"))
				browser = (userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0] + "-"
						+ (userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
			else if (user.contains("opr"))
				browser = ((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
						.replace("OPR", "Opera");
		} else if (user.contains("chrome")) {
			log.info("chrome");
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("firefox")) {
			log.info("firefox");
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			log.info("rv");
			browser = "IE-" + user.substring(user.indexOf("rv") + 3, user.indexOf(")"));
		}
		else {
			log.info("Browser not defined");
			browser = "UnKnown, More-Info: " + userAgent;
		}
		System.err.println("Operating System======>" + os);
		System.err.println("Browser Name==========>" + browser);
		return os+","+browser;
	}

	/**
	 * @param request
	 */
	public String getIPAddress(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		log.info("IP " + ipAddress);
		return ipAddress;
	}

		@GetMapping("/instructors/{username}/courses/{id}")
		  public Course getCourse(@PathVariable String username, @PathVariable long id) {
		    return courseManagementService.findById(id);
		  }
		@DeleteMapping("/instructors/{username}/courses/{id}")
		  public ResponseEntity<Void> deleteCourse(@PathVariable String username, @PathVariable long id) {

		    Course course = courseManagementService.deleteById(id);

		    if (course != null) {
		      return ResponseEntity.noContent().build();
		    }

		    return ResponseEntity.notFound().build();
		  }
		@PutMapping("/instructors/{username}/courses/{id}")
		  public ResponseEntity<Course> updateCourse(@PathVariable String username, @PathVariable long id,
		      @RequestBody Course course) {

			
		    Course courseUpdated = courseManagementService.save(course);
		    if(courseUpdated==null) {
		    return new ResponseEntity<Course>(course, HttpStatus.OK);
		    }else
		    {
		    	return new ResponseEntity<Course>(course, HttpStatus.NOT_ACCEPTABLE);
		    }
		  }
		
		@PostMapping("/instructors/{username}/courses")
		  public ResponseEntity<Void> createCourse(@PathVariable String username, @RequestBody Course course) {

		    Course createdCourse = courseManagementService.save(course);

		    // Location
		    // Get current resource url
		    /// {id}
		    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdCourse.getId())
		        .toUri();

		    return ResponseEntity.created(uri).build();
		  }

		
	
}
