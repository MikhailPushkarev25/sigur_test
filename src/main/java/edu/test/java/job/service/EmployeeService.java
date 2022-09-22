package edu.test.java.job.service;

import edu.test.java.job.component.EmployeesMgr;
import edu.test.java.job.model.Department;
import edu.test.java.job.model.Employee;
import edu.test.java.job.repository.EmployeesRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class EmployeeService {


    private final EmployeesRepository employeesRepository;
    private final DepartmentService departmentService;
    private final LocalDate START_DAY = LocalDate.of(2022, 1, 1);
    private final LocalDate END_DAY = LocalDate.of(2022, 12, 31);
    private final Logger logger = LoggerFactory.getLogger(EmployeesMgr.class.getName());

    public Employee newEmployee() {
        Employee employee = new Employee();
        employee.setHireTime(periodRandomDate());
        employee.setCard(randomCard());
        employee.setDepartmentId(departmentService.periodRandomDate());
        employeesRepository.save(employee);
        logger.info("Дата текущего дня - {}, Сотрудник - {}, Нанят - {}, {}",
                periodRandomDate(), employee.getId(), employee.getHireTime(), employee.getDepartmentId().getName());
        System.out.println(logger);
        return employee;
    }

    public void employeeFired() {
        int count = ThreadLocalRandom.current().nextInt(1, 4);
        for (int i = 0; i < count; i++) {
            List<Employee> firedEmp = employeesRepository.findEmployeeByFiredTimeIsNull();
            if (firedEmp.size() > 0) {
                Employee removeEmployee = firedEmp.get(ThreadLocalRandom.current().nextInt(firedEmp.size()));
                removeEmployee.setHireTime(periodRandomDate());
                if (removeEmployee.getId() != null) {
                    LocalDate firedDate = removeEmployee.getHireTime().plusDays(ThreadLocalRandom.current().nextInt(8));
                    removeEmployee.setFiredTime(firedDate);
                    employeesRepository.save(removeEmployee);
                    firedEmp.remove(removeEmployee);
                    logFileFired(generateNewDate(), removeEmployee,
                            removeEmployee.getDepartmentId(),
                            countDays());
                    System.out.println(logger);
                }
            }
        }
    }

    private LocalDate generateNewDate() {
         LocalDate current = START_DAY;
        if (START_DAY.isBefore(END_DAY)) {
            periodRandomDate();
            current = periodRandomDate();
        }
        return current;
    }

    private void logFileFired(LocalDate date, Employee employee, Department department, Long between) {
        String logLine = "Дата текущего дня - {}, Сотрудник - {}, Уволен из - {}, дней - {}";
        logger.info(logLine,
                date, employee.getId(),
                department.getName(),
                between);
    }

    private LocalDate periodRandomDate() {
        long one = START_DAY.toEpochDay();
        long two = END_DAY.toEpochDay();
        long random = ThreadLocalRandom.current().nextLong(one, two);
        return LocalDate.ofEpochDay(random);
    }

    private byte[] randomCard() {
        byte[] bytes = new byte[16];
        new Random().nextBytes(bytes);
        return bytes;
    }

    private long countDays() {
        long days = ChronoUnit.DAYS.between(START_DAY, END_DAY);
        return new Random().nextLong(days);
    }

}
