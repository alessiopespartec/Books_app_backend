package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/demo")
public class HomeController {

    @PreAuthorize("hasAuthority('SCOPE_email')")
    @GetMapping
    public String hello(){
        return "Hello from Spring Boot and Keycloak";
    }

    @GetMapping("/hello-2")
    public String about(){
        return "Hello from Spring Boot and Keycloak - ADMIN";
    }
}