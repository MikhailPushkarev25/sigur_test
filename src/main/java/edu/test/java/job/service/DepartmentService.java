package edu.test.java.job.service;

import edu.test.java.job.model.Department;
import edu.test.java.job.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department periodRandomDate() {
        List<Department> departments = (List<Department>) departmentRepository.findAll();
        return departments.get(new Random().nextInt(departments.size()));
    }
}
