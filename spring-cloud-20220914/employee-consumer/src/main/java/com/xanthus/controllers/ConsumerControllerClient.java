package com.xanthus.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ConsumerControllerClient {
//    @Autowired
//    private DiscoveryClient discoveryClient;
      @Autowired
      private LoadBalancerClient loadBalancer;
    public void getEmployee() throws RestClientException, IOException {
//        List<ServiceInstance> instances=discoveryClient.getInstances("employee-producer");
//        ServiceInstance serviceInstance=instances.get(0);
        ServiceInstance serviceInstance=loadBalancer.choose("employee-producer");
        String baseUrl=serviceInstance.getUri().toString();
        System.out.println(baseUrl);
        baseUrl=baseUrl+"/employee";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response=null;
        try{
            response=restTemplate.exchange(baseUrl,
                    HttpMethod.GET, getHeaders(),String.class);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
        System.out.println(response.getBody());
    }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
