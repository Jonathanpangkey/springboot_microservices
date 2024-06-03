package com.pangkeynath.employeeservice.Client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface DepartmentClient {
    @GetExchange("departments/exists/{id}")
    Boolean doesDepartmentExist(@PathVariable("id") Long id) throws WebClientException;
}
