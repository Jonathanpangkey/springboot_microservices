package com.pangkeynath.departmentservice.repository;

import com.pangkeynath.departmentservice.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}

