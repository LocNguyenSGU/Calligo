package com.example.apigateway.config;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.loadbalancer.config.LoadBalancerCacheAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder loadBalancedWebClientBuilder(ReactorLoadBalancerExchangeFilterFunction loadBalancerFunction) {
        return WebClient.builder()
                .filter(loadBalancerFunction); // Thêm chức năng load balancer vào WebClient
    }
}