package com.example.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class GateWayApplication {

    @Value("${server.address:}")
    private String serverAddress;

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }

    @Bean
    DiscoveryClientRouteDefinitionLocator routesDynamique(ReactiveDiscoveryClient rdc,
                                                          DiscoveryLocatorProperties dlp){
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedOrigin("http://192.168.8.227:8100");
        config.addAllowedOrigin("http://localhost:8100");// Add the origin of your frontend application
        config.addAllowedOrigin("http://192.168.1.105:8100");
        config.addAllowedOrigin("http://192.168.8.108:8100");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

    @Bean
    public String serverAddress() {
        if (serverAddress.isEmpty()) {
            try {
                System.out.println("Ip Address of Machine :"+InetAddress.getLocalHost().getHostAddress());
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {

                return "localhost";
            }
        } else {
            return serverAddress;
        }
    }
}
