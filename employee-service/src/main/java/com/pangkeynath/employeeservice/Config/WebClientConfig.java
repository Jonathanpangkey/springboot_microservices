package com.pangkeynath.employeeservice.Config;

import com.pangkeynath.employeeservice.Client.DepartmentClient;
import com.pangkeynath.employeeservice.Client.TaskClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WebClientConfig {
    @Autowired
    private LoadBalancedExchangeFilterFunction filterFunction;

    @Bean
    public DepartmentClient departmentClient() {
        return buildFactory("http://department-service").createClient(DepartmentClient.class);
    }

    @Bean
    public TaskClient taskClient() {
        return buildFactory("http://project-service").createClient(TaskClient.class);
    }

    private HttpServiceProxyFactory buildFactory(String baseUrl) {
        final WebClient webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .filter(filterFunction)
                .build();
        return HttpServiceProxyFactory.builderFor(WebClientAdapter.create(webClient)).build();
    }

}
