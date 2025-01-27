package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserTestController {
    @GetMapping("/test")
    public ResponseEntity<String> userTestEndpoint() {
        return ResponseEntity.ok("It works!");
    }
}