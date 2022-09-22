package edu.test.java.job.repository;

import edu.test.java.job.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface EmployeesRepository extends CrudRepository<Employee, Integer> {

    List<Employee> findEmployeeByFiredTimeIsNull();
}