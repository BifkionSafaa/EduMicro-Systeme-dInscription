package com.demo.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.student.entity.Student;
import com.demo.student.service.StudentService;

@RestController
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // GET /api/students - Récupérer tous les étudiants
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }
    
    // GET /api/students/{id} - Récupérer un étudiant par ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    	
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)   //si Optional<Student> contient un étudiant alors renvoyer ResponseEntity.ok(student)
           
                .orElse(ResponseEntity.notFound().build()); // renvoyer 404
    }
    
    // GET /api/students/cnie/{cnie} - Récupérer étudiant par CNIE
    @GetMapping("/cnie/{cnie}")
    public ResponseEntity<Student> getStudentByCnie(@PathVariable String cnie) {
        return studentService.getStudentByCnie(cnie)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // POST /api/students - Créer un nouvel étudiant
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
            Student saved = studentService.createStudent(student);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // DELETE /api/students/{id} - Supprimer un étudiant
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        if (studentService.getStudentById(id).isPresent()) {
            studentService.deleteStudent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //204
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
