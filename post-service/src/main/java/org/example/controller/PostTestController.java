package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostTestController {
    @GetMapping("/test")
    public ResponseEntity<String> postTestEndpoint() {
        return ResponseEntity.ok("It works!");
    }
}
