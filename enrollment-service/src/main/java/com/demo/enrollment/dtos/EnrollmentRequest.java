package com.demo.enrollment.dtos;

public class EnrollmentRequest {
    private String studentCnie;
    private Long courseId;
    
    public EnrollmentRequest() {
    }
    
    public EnrollmentRequest(String studentCnie, Long courseId) {
        this.studentCnie = studentCnie;
        this.courseId = courseId;
    }
    
    public String getStudentCnie() {
        return studentCnie;
    }
    
    public void setStudentCnie(String studentCnie) {
        this.studentCnie = studentCnie;
    }
    
    public Long getCourseId() {
        return courseId;
    }
    
    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}