package com.xanthus.controller;

import brave.sampler.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/four")
public class FourController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/message")
    public String message(@RequestHeader("four-request") String header){
        System.out.println(header);
        return "Hello this is four-service";
    }

    @GetMapping("/service")
    public String fourService(){
        LOG.info("Inside four-service");
        return "Hello World Spring Cloud(four-service)";
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        String message = "Testing my healh check function";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }

}
