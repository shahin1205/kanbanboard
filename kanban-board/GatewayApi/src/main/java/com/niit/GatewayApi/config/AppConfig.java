package com.niit.GatewayApi.config;


import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes()
                .route(p -> p.path("/api/auth/**")
                        .uri("lb://UserAuthentication"))
                .route(p -> p.path("/api/user/**")
                        .uri("lb://UserRegistrationService"))
                .route(p -> p.path("/api/spaces/**")
                        .uri("lb://UserRegistrationService"))
                .route(p -> p.path("/api/projects/**")
                        .uri("lb://UserRegistrationService"))
                .route(p -> p.path("/api/tasks/**")
                        .uri("lb://UserRegistrationService"))
                .route(p -> p.path("/api/statuses/**")
                        .uri("lb://UserRegistrationService"))
                .build();
    }
}
