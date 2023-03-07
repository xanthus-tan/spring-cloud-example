package com.xanthus.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r -> r.path("/employee/**")
                        .filters(f -> f.hystrix(h -> h.setName("Hystrix")
                                .setFallbackUri("forward:/fallback/message")))
                        .uri("lb://one-service")
                        .id("employeeModule"))
                .route(r -> r.path("/consumer/**")
                        .filters(f -> f.addRequestHeader("second-request", "second-request-header")
                                       .addResponseHeader("second-response", "second-response-header"))
                        .uri("lb://two-service")
                        .id("consumerModule"))
                .build();
    }
}
