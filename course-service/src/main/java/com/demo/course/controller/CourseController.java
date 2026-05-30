package com.demo.course.controller;

import com.demo.course.entity.Course;
import com.demo.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    
    @Autowired
    private CourseService courseService;
    
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    //@RequestBody : lit des données dans le corps JSON   pour les transformer   en objet  JAVA 
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course saved = courseService.createCourse(course);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }
}