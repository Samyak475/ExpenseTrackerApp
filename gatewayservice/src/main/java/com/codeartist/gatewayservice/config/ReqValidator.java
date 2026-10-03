package com.codeartist.gatewayservice.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class ReqValidator {
    private final List<String> allowedReq=  List.of(
            "/v1/signup","/v1/login","/v1/refreshToken"
    );

    public Predicate<ServerHttpRequest> isSecured  = request -> allowedReq
            .stream().noneMatch(uri->request.getURI().getPath().equals(uri));
}
