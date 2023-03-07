package com.xanthus;

import com.xanthus.controllers.ConsumerControllerClient;
import com.xanthus.controllers.ConsumerControllerFeignClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SpringBootHelloWorldApplication {
    public static void main(String[] args) throws RestClientException, IOException {
        ApplicationContext ctx = SpringApplication.run(
                SpringBootHelloWorldApplication.class, args);
        ConsumerControllerFeignClient consumerControllerClient=ctx.getBean(ConsumerControllerFeignClient.class);
        System.out.println(consumerControllerClient);
        consumerControllerClient.getEmployee();
//        for(int i=0;i<200;i++) {
//            consumerControllerClient.getEmployee();
//        }

    }

    @Bean
    public  ConsumerControllerFeignClient  consumerControllerFeignClient()
    {
        return  new ConsumerControllerFeignClient();
    }
    @Bean
    public  ConsumerControllerClient  consumerControllerClient()
    {
        return  new ConsumerControllerClient();
    }
}
