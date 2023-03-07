package com.xanthus.controller;

import brave.sampler.Sampler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/three")
public class ThreeController {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/message")
    public String message(@RequestHeader("three-request") String header){
        System.out.println(header);
        return "Hello this is three-service";
    }

    @GetMapping("/service")
    public String twoService(){
        LOG.info("Inside three-service");
        List<ServiceInstance> instances=discoveryClient.getInstances("four-service");
        ServiceInstance serviceInstance=instances.get(0);
        String baseUrl=serviceInstance.getUri().toString();
        baseUrl=baseUrl+"/four/service";
        try {
            Thread.sleep(2000);
        } catch (Exception ex) {

        }
        ResponseEntity<String> response=null;
        try {
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
        }catch (Exception ex){
            LOG.error(ex.toString());
        }
        LOG.info("The response received by three-service is " + response.getBody());
        return response.getBody();
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        String message = "Testing my healh check function";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Bean RestTemplate restTemplate(){
        return new RestTemplate();
    }
    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
