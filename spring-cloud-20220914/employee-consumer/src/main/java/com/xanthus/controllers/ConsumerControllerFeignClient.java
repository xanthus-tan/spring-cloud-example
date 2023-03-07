package com.xanthus.controllers;

import com.xanthus.controllers.Employee.RemoteCallService;
import com.xanthus.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestClientException;

import java.io.IOException;
public class ConsumerControllerFeignClient {
    @Autowired
    private RemoteCallService loadBalancer;
    public void getEmployee() throws RestClientException, IOException {

        try {
            Employee emp = loadBalancer.getData();
            System.out.println(emp.getEmpId());
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
