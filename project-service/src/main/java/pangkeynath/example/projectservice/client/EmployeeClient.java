package pangkeynath.example.projectservice.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface EmployeeClient {
    @GetExchange("/employees/exists/{id}")
    public boolean doesEmployeeExist(@PathVariable Long id) throws WebClientException;
}
