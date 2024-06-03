package com.pangkeynath.employeeservice.Client;

import com.pangkeynath.employeeservice.model.Task;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface TaskClient {
    @GetExchange("/tasks/employee/{employeeId}")
    List<Task> findByEmployee(@PathVariable("employeeId") Long employeeId) throws WebClientException;
}

