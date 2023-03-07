package com.xanthus.controllers.Employee;

import com.xanthus.model.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="employee-zuul-service")
public interface RemoteCallService {
    @RequestMapping(method= RequestMethod.GET, value="/producer/employee")
    public Employee getData();
}
