package com.demo.enrollment.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class CourseClient {
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Value("${course.service.url}")
    private String courseServiceUrl;
    
    public Map<String, Object> getCourseById(Long id) {
        return webClientBuilder
                .build()
                .get()
                .uri(courseServiceUrl + "/courses/{id}", id)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}