package com.pangkeynath.employeeservice.controller;
import com.pangkeynath.employeeservice.Client.DepartmentClient;
import com.pangkeynath.employeeservice.Client.TaskClient;
import com.pangkeynath.employeeservice.model.Employee;
import com.pangkeynath.employeeservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentClient departmentClient;
    @Autowired
    private TaskClient taskClient;

    @GetMapping // ReadAll
    public ResponseEntity<Object> findAll() {
        try {
            List<Employee> employees = employeeRepository.findAll();
            employees.forEach(employee -> employee.setTasks(taskClient.findByEmployee(employee.getId())));
            return ResponseEntity.ok(employees);
        } catch (WebClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve employees' tasks from task service.");
        }
    }


    @PostMapping // Create
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
        try {
            // Validate employee data
            if (employee.getName() == null || employee.getName().isEmpty() ||
                    employee.getDepartmentId() == null || employee.getPosition() == null || employee.getPosition().isEmpty()) {
                return ResponseEntity.badRequest().body("Invalid employee data.");
            }

            // Check if the department exists
            if (!departmentClient.doesDepartmentExist(employee.getDepartmentId())) {
                return ResponseEntity.badRequest().
                        body("Department with ID " + employee.getDepartmentId() + " does not exist.");
            }

            Employee savedEmployee = employeeRepository.save(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (WebClientException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve department from department-service");
        }
    }


    @GetMapping("/{id}") // Read by ID
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);
        if (employeeOptional.isPresent()) {
            return ResponseEntity.ok(employeeOptional);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found with id: " + id);
        }
    }


    @DeleteMapping("/{id}") // Delete
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.ok("Employee successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee not found with id: " + id);
        }
    }

    @GetMapping("/department/{department_id}") // ReadByDep
    public ResponseEntity<Object> findByDepartment(@PathVariable("department_id") Long department_id) {
        try {
            // Check if the department exists
            Boolean departmentExists = departmentClient.doesDepartmentExist(department_id);
            if (!departmentExists) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Department with ID " + department_id + " does not exist.");
            }

            List<Employee> employees = employeeRepository.findByDepartmentId(department_id);
            return ResponseEntity.ok(employees);
        } catch (WebClientException e) {
            // If the department service is down or not available
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve department from department-service.");
        }
    }


    @GetMapping("/exists/{id}") // Check if employee exists
    public ResponseEntity<Boolean> doesEmployeeExist(@PathVariable Long id) {
        boolean exist = employeeRepository.existsById(id);
        return ResponseEntity.ok(exist);
    }

}
