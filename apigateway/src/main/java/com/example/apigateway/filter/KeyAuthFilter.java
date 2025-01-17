package com.example.apigateway.filter;

import com.example.apigateway.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
public class KeyAuthFilter extends AbstractGatewayFilterFactory<Object> {
    @Autowired
    private AuthService authService;

    // Danh sách API không yêu cầu xác thực
    private static final List<String> WHITELISTED_APIS = List.of(
            "/api/v1/auth/.*"
    );

    @Override
    public GatewayFilter apply(Object config) {
        System.out.println("00000");
        return (exchange, chain) -> {
            System.out.println("1121111");
            String requestPath = exchange.getRequest().getURI().getPath();
            System.out.println("Request Path: " + requestPath); // Log đầy đủ path

            // Kiểm tra các API trong danh sách whitelist
            if (WHITELISTED_APIS.stream().anyMatch(requestPath::matches)) {
                System.out.println("Khong can xac thuc login");
                return chain.filter(exchange); // Cho phép tiếp tục mà không xác thực
            }

            // Kiểm tra accessToken từ Header
            String accessToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (accessToken == null || !accessToken.startsWith("Bearer ")) {
                throw new ResponseStatusException(UNAUTHORIZED, "Missing or invalid access token");
            }

            accessToken = accessToken.substring(7); // Bỏ "Bearer " để lấy token thật sự
            System.out.println("Access token in api gateway: " + accessToken);

            // Xác thực token bằng AuthService
            return authService.validateToken(accessToken)
                    .then(chain.filter(exchange)) // Nếu token hợp lệ, tiếp tục xử lý
                    .onErrorResume(e -> {
                        // Nếu token không hợp lệ, trả về lỗi 401
                        return Mono.error(new ResponseStatusException(UNAUTHORIZED, "Invalid access token"));
                    });
        };
    }
}