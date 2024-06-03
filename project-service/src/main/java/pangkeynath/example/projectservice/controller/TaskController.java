package pangkeynath.example.projectservice.controller;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;
import pangkeynath.example.projectservice.client.EmployeeClient;
import pangkeynath.example.projectservice.model.Task;
import pangkeynath.example.projectservice.repository.TaskRepository;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private EmployeeClient employeeClient;

    @GetMapping // ReadAll
    public ResponseEntity<List<Task>> findAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return ResponseEntity.ok(tasks);
    }

    @PostMapping // Create
    public ResponseEntity<Object> addTask(@RequestBody Task task) {
        // Check taskname
        if (task.getTaskName() == null || task.getTaskName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Task name cannot be empty.");
        }

        // Check if the employee exists
        if (!employeeClient.doesEmployeeExist(task.getEmployeeId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Employee with ID " + task.getEmployeeId() + " does not exist.");
        }
        // Save the task
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }


    @GetMapping("/{id}") // Read by ID
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            return ResponseEntity.ok(taskOptional);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found with id: " + id);
        }
    }


    @DeleteMapping("/{id}") // Delete
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
            return ResponseEntity.ok("Task successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Task not found with id: " + id);
        }
    }

    @GetMapping("/employee/{employee_id}") // Read by emp Id
    public ResponseEntity<Object> findByEmployee(@PathVariable Long employee_id) {
        try {
            // Check if the employee exists
            Boolean employeeExists = employeeClient.doesEmployeeExist(employee_id);
            if (!employeeExists) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Employee with ID " + employee_id + " does not exist.");
            }

            List<Task> tasks = taskRepository.findByEmployeeId(employee_id);
            return ResponseEntity.ok(tasks);
        } catch (WebClientException e) {
            // If the employee service is down or not available
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve employee from employee-service.");
        }
    }


}
