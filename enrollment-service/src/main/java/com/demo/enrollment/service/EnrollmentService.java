package com.demo.enrollment.service;

import com.demo.enrollment.clients.StudentClient;
import com.demo.enrollment.clients.CourseClient;
import com.demo.enrollment.dtos.EnrollmentRequest;
import com.demo.enrollment.dtos.EnrollmentResponseDTO;
import com.demo.enrollment.entity.Enrollment;
import com.demo.enrollment.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EnrollmentService {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    
    @Autowired
    private StudentClient studentClient;
    
    @Autowired
    private CourseClient courseClient;
    
    private static final int MAX_STUDENTS_PER_COURSE = 3;
    private static final long CANCELLATION_HOURS_LIMIT = 24;
    
    //inscrire un etudiant
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequest request) {
        // 1. Vérifier que l'étudiant existe dans student-service
        // La variable student est une Map contenant le JSON renvoyé par student-service.
        Map<String, Object> student = studentClient.getStudentByCnie(request.getStudentCnie());
        if (student == null || student.isEmpty()) {
            throw new RuntimeException("Étudiant non trouvé");
        }

        // Récupérer l'id technique de l'étudiant pour le stocker dans la table enrollments
        Long studentId = Long.valueOf(student.get("id").toString());
        
        // 2. Vérifier que le cours existe dans course-service
        Map<String, Object> course = courseClient.getCourseById(request.getCourseId());
        if (course == null || course.isEmpty()) {
            throw new RuntimeException("Cours non trouvé");
        }
        
        Long courseId = Long.valueOf(course.get("id").toString());
        String courseName = (String) course.get("title"); // Sera utilisé dans le DTO de réponse
        
        // 3. Empêcher la double inscription au même cours
        boolean alreadyEnrolled = enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
        if (alreadyEnrolled) {
            throw new RuntimeException("Déjà inscrit à ce cours");
        }
        
        // 4. Vérifier la règle métier : maximum 3 étudiants par cours
        long count = enrollmentRepository.countByCourseId(courseId);
        if (count >= MAX_STUDENTS_PER_COURSE) {
            throw new RuntimeException("Cours complet (max " + MAX_STUDENTS_PER_COURSE + " étudiants)");
        }
        
        // 5. Construire l'entité Enrollment à enregistrer en base
        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setEnrollmentDate(LocalDateTime.now());
        
        Enrollment saved = enrollmentRepository.save(enrollment);
        
        // 6. Construire le DTO de réponse renvoyé au frontend
        EnrollmentResponseDTO response = new EnrollmentResponseDTO();
        response.setEnrollmentId(saved.getId());
        response.setStudentCnie(request.getStudentCnie());
        response.setCourseName(courseName);
        response.setDate(saved.getEnrollmentDate().toString());
        response.setDeletable(true);
        
        return response;
    }
    
    public List<EnrollmentResponseDTO> getStudentEnrollments(String studentCnie) {
        Map<String, Object> student = studentClient.getStudentByCnie(studentCnie);
        if (student == null || student.isEmpty()) {
            throw new RuntimeException("Étudiant non trouvé");
        }
        
        Long studentId = Long.valueOf(student.get("id").toString());

        // Récupérer toutes les inscriptions de cet étudiant dans enrollment_db
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        
        // Parcourir les inscriptions et construire un DTO de réponse pour chacune
        return enrollments.stream().map(enrollment -> {
            Map<String, Object> course = courseClient.getCourseById(enrollment.getCourseId());
            String courseName = course != null ? (String) course.get("title") : "Inconnu";
            
            // Calculer le nombre d'heures écoulées depuis l'inscription
            long hoursSince = ChronoUnit.HOURS.between(enrollment.getEnrollmentDate(), LocalDateTime.now());
            
           
            EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
            dto.setEnrollmentId(enrollment.getId());
            dto.setStudentCnie(studentCnie);
            dto.setCourseName(courseName);
            dto.setDate(enrollment.getEnrollmentDate().toString());
            dto.setDeletable(hoursSince < CANCELLATION_HOURS_LIMIT);
            
            return dto;
        }).collect(Collectors.toList());
    }
    
    public long getTotalEnrollments() {
        return enrollmentRepository.count();
    }

    public boolean deleteEnrollment(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Inscription non trouvée"));
        
        long hoursSince = ChronoUnit.HOURS.between(enrollment.getEnrollmentDate(), LocalDateTime.now());
        
        if (hoursSince < CANCELLATION_HOURS_LIMIT) {
            enrollmentRepository.delete(enrollment);
            return true;
        } else {
            throw new RuntimeException("Suppression impossible après 24h");
        }
    }
}