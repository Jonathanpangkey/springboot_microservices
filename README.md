## Microservices Environment

1. **Config Server Service:** Acts as a centralized configuration management system, serving configuration properties to other microservices.
2. **Service Registry Service (Eureka):** Enables service discovery and load balancing by maintaining a registry of all registered microservices.
3. **API Gateway Service:** The entry point for client requests, routing them to the appropriate microservices based on the request path.
4. **Department Service:** Manages operations related to departments, including adding and retrieving department details.
5. **Employee Service:** Manages operations related to employees, including adding, retrieving, and fetching employees by department.

## Flow and Interaction

1. **Configuration Management:**
   - The Config Server Service loads configuration properties from its local file system (`config/employee-service.yml` and `config/department-service.yml`).
   - Other microservices fetch their configuration properties from the Config Server Service via HTTP.

2. **Service Registration and Discovery:**
   - Upon startup, all microservices register themselves with the Service Registry Service (Eureka).
   - The Service Registry Service maintains a registry of all registered microservices.

3. **API Gateway Routing:**
   - Client requests are directed to the API Gateway Service.
   - The API Gateway Service routes requests to the appropriate microservice based on the request path.

4. **Department Service Operations:**
   - Exposes REST endpoints for CRUD operations related to departments (`/department/**`).
   - The Department Controller handles department-related requests and interacts with the Department Repository for CRUD operations.
   - For fetching employees of a department, the Department Controller communicates with the Employee Service via the `EmployeeClient` REST client.

5. **Employee Service Operations:**
   - Exposes REST endpoints for CRUD operations related to employees (`/employee/**`).
   - The Employee Controller handles employee-related requests and interacts with the Employee Repository for CRUD operations.
   - For fetching employees by department, the Employee Controller queries the Employee Repository.

6. **Communication between Department and Employee Services:**
   - The Department Service communicates with the Employee Service to fetch employees of a department.
   - It utilizes the `EmployeeClient` REST client to make HTTP requests to the `findByDepartment()` endpoint exposed by the Employee Service.
   - The Employee Service responds with the list of employees associated with the requested department.

This microservices architecture promotes modularity, scalability, and resilience. Each microservice is responsible for a specific business domain, and they communicate with each other via well-defined RESTful APIs. The use of service registration and discovery simplifies the management of microservice instances, while centralized configuration management ensures consistency across deployments.
