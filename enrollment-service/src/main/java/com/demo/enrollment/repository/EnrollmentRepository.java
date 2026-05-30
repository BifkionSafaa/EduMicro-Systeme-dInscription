package com.demo.enrollment.repository;

import com.demo.enrollment.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    
    // Trouver les inscriptions d'un étudiant
    List<Enrollment> findByStudentId(Long studentId);
    
    // Compter les inscriptions pour un cours (pour vérifier max 3)
    long countByCourseId(Long courseId);
    
    // Vérifier si un étudiant est déjà inscrit à un cours
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}