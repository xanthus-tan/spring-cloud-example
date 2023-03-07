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
@RequestMapping("/one")
public class OneController {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String header){
        System.out.println(header);
        return "Hello This is one-service";
    }
    @GetMapping("/service")
    public String oneService(){
        LOG.info("Inside one-service");
        List<ServiceInstance> instances=discoveryClient.getInstances("two-service");
        ServiceInstance serviceInstance = null;
        try{
            serviceInstance=instances.get(0);
        }catch (IndexOutOfBoundsException ex){
            LOG.error("two-service can not connect");
            return "call two-service error(one-service)";
        }
        String baseUrl=serviceInstance.getUri().toString();
        baseUrl=baseUrl+"/two/service";
        ResponseEntity<String> response=null;
        try {
            response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
        }catch (Exception ex){
            LOG.error(ex.toString());
        }
        LOG.info("The response received by one-service is " + response.getBody());
        return response.getBody();
    }
    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck() {
        String message = "Testing my healh check function";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }
}
