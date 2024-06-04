# Springboot Microservices
This is an project that shows the application of the microservices concept, this application is designed to manage employees, departments and tasks across various services. Utilize centralized configuration, service discovery, tracing logging and api gateway.
Starter setup:
- **Language**: Java
- **IDE**: IntelliJ IDEA or any preferred Java IDE.
- **Docker**: Containerization platform in this app used for zipkin.
- **Database**: PostgreSQL for storing application data.

## Microservices Architecture

<img width="595" alt="Screenshot 2024-06-04 at 09 54 18" src="https://github.com/Jonathanpangkey/springboot_microservices/assets/102292312/d803d0f3-bd9b-4ade-8d6d-8bd7bb329895">


1. **Department Service** - Manages departments.
2. **Employee Service** - Manages employees and interacts with the Department and Project services.
3. **Project Service** - Manages tasks and associates them with employees.
4. **Config Server** - Centralized configuration management.
5. **Service Registry** - Service discovery and registration.
6. **API Gateway** - Acts as an entry point for all client requests.
7. **Zipkin** - Distributed tracing for monitoring (run from docker).

### Service Descriptions

#### Department Service

- **Purpose**: Manages CRUD operations for departments.
- **Endpoints**:
  - Create Department: POST /departments
  - Get All Departments: GET /departments
  - Get Department by ID: GET /departments/{id}
  - Delete Department: DELETE /departments/{id}
  - Check if Department Exists: GET /departments/exists/{id}
- **Inter-Service Communication**: Calls Employee Service to retrieve employees in each department.

#### Employee Service

- **Purpose**: Manages CRUD operations for employees.
- **Endpoints**:
  - Create Employee: POST /employees
  - Get All Employees: GET /employees
  - Get Employee by ID: GET /employees/{id}
  - Get Employees by Department: GET /employees/department/{department_id}
  - Delete Employee: DELETE /employees/{id}
  - Check if Employee Exists: GET /employees/exists/{id}
- **Inter-Service Communication**: 
  - Calls Department Service to validate department existence.
  - Calls Project Service to retrieve tasks for each employee.

#### Project Service

- **Purpose**: Manages CRUD operations for tasks.
- **Endpoints**:
  - Create Task: POST /tasks
  - Get All Tasks: GET /tasks
  - Get Task by ID: GET /tasks/{id}
  - Delete Task: DELETE /tasks/{id}
  - Get Tasks by Employee: GET /tasks/employee/{employee_id}
- **Inter-Service Communication**: Calls Employee Service to validate employee existence.

### Supporting Services

#### Config Server

- **Purpose**: Centralized configuration management.
- **Functionality**: Provides configuration properties for all services.

#### Service Registry

- **Purpose**: Service discovery and registration.
- **Functionality**: Allows services to discover and communicate with each other dynamically.

#### API Gateway

- **Purpose**: Entry point for all client requests.
- **Functionality**: Routes client requests to appropriate services.

#### Zipkin

- **Purpose**: Distributed tracing for monitoring and troubleshooting.
- **Functionality**: Collects and visualizes trace data from different services to analyze request flows and performance.


## Architecture Flow

1. **Client Request**: Enters through the **API Gateway**.
2. **API Gateway**: Routes the request to the appropriate service (Department, Employee, or Project Service).
3. **Service Communication**: Services interact with each other as necessary using the **Service Registry** for discovery.
4. **Configuration**: Services retrieve configuration properties from the **Config Server**.
5. **Tracing**: All interactions are traced by **Zipkin** for monitoring and debugging.


## Running the Application

1. **Start Config Server**:
   - Run the Config Server application to provide configuration to other services.

2. **Start Service Registry**:
   - Run the Service Registry application to enable service discovery.

3. **Start API Gateway**:
   - Run the API Gateway application to handle routing.

4. **Run Zipkin**:
   - Pull and run the zipkin with `docker run -d -p 9411:9411 openzipkin/zipkin.`

4. **Start Microservices**:
   - Run Department, Employee, and Project services to handle respective operations.


