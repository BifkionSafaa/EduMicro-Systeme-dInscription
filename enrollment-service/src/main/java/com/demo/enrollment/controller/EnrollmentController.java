package com.demo.enrollment.controller;

import com.demo.enrollment.dtos.EnrollmentRequest;
import com.demo.enrollment.dtos.EnrollmentResponseDTO;
import com.demo.enrollment.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    
    //inscrire un etudiant 
    @PostMapping
    public ResponseEntity<?> enroll(@RequestBody EnrollmentRequest request) {
        try {
            EnrollmentResponseDTO response = enrollmentService.enrollStudent(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED); //HTTP = 201  Creation
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);//HTTP = 400
        }
    }
    
    //le nombre total d’inscriptions dans le système
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalEnrollments() {
        return ResponseEntity.ok(enrollmentService.getTotalEnrollments());
    }

    @GetMapping("/student/{cnie}")
    public ResponseEntity<List<EnrollmentResponseDTO>> getEnrollments(@PathVariable String cnie) {
        try {
            List<EnrollmentResponseDTO> enrollments = enrollmentService.getStudentEnrollments(cnie);
            return ResponseEntity.ok(enrollments);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();//HTTP =404 NOT FOUND
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEnrollment(@PathVariable Long id) {
        try {
            enrollmentService.deleteEnrollment(id);
            return ResponseEntity.ok("Inscription supprimée");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}