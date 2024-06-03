# Springboot Microservices
This is an application that shows the application of the microservices concept, this application is designed to manage employees, departments and tasks across various services. Utilize centralized configuration, service discovery, tracing logging and api gateway.

## Microservices Environment

1. **Department Service** - Manages departments.
2. **Employee Service** - Manages employees and interacts with the Department and Project services.
3. **Project Service** - Manages tasks and associates them with employees.
4. **Config Server** - Centralized configuration management.
5. **Service Registry** - Service discovery and registration.
6. **API Gateway** - Acts as an entry point for all client requests.
7. **Zipkin** - Distributed tracing for monitoring (run from docker).

## Running the Application

1. **Start Config Server**:
   - Run the Config Server application to provide configuration to other services.

2. **Start Service Registry**:
   - Run the Service Registry application to enable service discovery.

3. **Start API Gateway**:
   - Run the API Gateway application to handle routing.

4. **Start Microservices**:
   - Run Department, Employee, and Project services to handle respective operations.


