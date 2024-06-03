package com.pangkeynath.departmentservice.controller;
import com.pangkeynath.departmentservice.client.EmployeeClient;
import com.pangkeynath.departmentservice.model.Department;
import com.pangkeynath.departmentservice.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeClient employeeClient;

    @GetMapping // ReadAll
    public ResponseEntity<Object> findAll() {
        try {
            List<Department> departments = departmentRepository.findAll();
            departments.forEach(department -> department.setEmployees(
                    employeeClient.findByDepartment(department.getId())
            ));
            return ResponseEntity.ok(departments);
        } catch (WebClientException e) {
            // If the employee service is down or not available
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve employees from employee-service");
        }
    }


    @PostMapping // Create
    public ResponseEntity<Object> addDepartment(@RequestBody Department department) {
        if (department.getName() == null || department.getName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Department name cannot be empty.");
        }
        departmentRepository.save(department);
        return ResponseEntity.ok(department);
    }



    @GetMapping("/{id}") // Read by ID
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent()) {
            return ResponseEntity.ok(departmentOptional);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Department not found with id: " + id);
        }
    }


    @DeleteMapping("/{id}") // Delete
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return ResponseEntity.ok("Department successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Department not found with id: " + id);
        }
    }


    @GetMapping("/exists/{id}") // Check if department exists
    public ResponseEntity<Boolean> doesDepartmentExist(@PathVariable Long id) {
        boolean exists = departmentRepository.existsById(id);
        return ResponseEntity.ok(exists);
    }
}
