package edu.test.java.job.component;

import edu.test.java.job.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class EmployeesMgr {

    private final EmployeeService employeeService;

    public void newEmployee() {
        employeeService.newEmployee();
    }

   public void employeeFired() {
        employeeService.employeeFired();
    }

}
