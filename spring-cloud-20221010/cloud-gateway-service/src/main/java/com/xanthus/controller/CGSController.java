package com.xanthus.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CGSController {
    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        String message = "Testing my healh check function";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @GetMapping("/fallback/message")
    public String message() {
        return "Hello GW Called in Fallback Service";
    }
}
