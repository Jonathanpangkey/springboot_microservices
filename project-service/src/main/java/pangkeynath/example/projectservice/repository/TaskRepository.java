package pangkeynath.example.projectservice.repository;

import org.apache.juli.logging.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import pangkeynath.example.projectservice.model.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByEmployeeId(Long employeeId);
}
