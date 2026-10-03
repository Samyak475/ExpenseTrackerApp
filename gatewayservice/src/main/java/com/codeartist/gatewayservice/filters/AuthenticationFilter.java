package com.codeartist.gatewayservice.filters;

import com.codeartist.gatewayservice.config.ReqValidator;
import com.codeartist.gatewayservice.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

@Component
public class AuthenticationFilter  implements GlobalFilter {
    @Autowired
    ReqValidator reqValidator;
    @Autowired
    JwtUtils jwtUtils;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        Predicate<ServerHttpRequest> isSecured = reqValidator.isSecured;

        if(isSecured.test(request)){
            String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            ServerHttpResponse response = exchange.getResponse();

            if(header ==null){
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
               Boolean isValid= jwtUtils.validateToken(token);
               if(Boolean.TRUE.equals(isValid)){
                   Integer userId = jwtUtils.getUserIdFrmToken(token);
                   ServerHttpRequest mutedReq=  exchange.getRequest().mutate().
                            header("X-User-Id",String.valueOf(userId)).build();
                   ServerWebExchange serverWebExchange =exchange.mutate().request(mutedReq).build();
                 return chain.filter(serverWebExchange);
               }
               else{
                   response.setStatusCode(HttpStatus.UNAUTHORIZED);
                   return response.setComplete();
               }
            }else{
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        }
        return chain.filter(exchange);
    }
}
