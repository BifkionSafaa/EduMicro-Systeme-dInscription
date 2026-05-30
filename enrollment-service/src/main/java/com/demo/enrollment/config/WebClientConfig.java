package com.demo.enrollment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration WebClient (nécessite spring-boot-starter-webflux dans pom.xml).
 * WebClient = client HTTP pour appeler les autres microservices (StudentClient, CourseClient).
 */
@Configuration
public class WebClientConfig {

    /** Bean injecté dans StudentClient et CourseClient pour les appels GET inter-services */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}