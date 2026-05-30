package com.demo.enrollment.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.Map;

@Component
public class StudentClient {
    
    @Autowired
    private WebClient.Builder webClientBuilder;
    
    @Value("${student.service.url}") // vérifier dans application.properties url de student-service
    private String studentServiceUrl;
    
    public Map<String, Object> getStudentByCnie(String cnie) {
        return webClientBuilder
                .build()
                .get()
                .uri(studentServiceUrl + "/students/cnie/{cnie}", cnie)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}