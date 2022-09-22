package edu.test.java.job.service;

import edu.test.java.job.component.EmployeesMgr;
import edu.test.java.job.model.Employee;
import edu.test.java.job.model.Guest;
import edu.test.java.job.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final DepartmentService departmentService;
    private final LocalDate START_DAY = LocalDate.of(2022, 1, 1);
    private final LocalDate END_DAY = LocalDate.of(2022, 12, 31);
    private final Logger logger = LoggerFactory.getLogger(EmployeesMgr.class.getName());


    public void visitGuest() {
        Employee employee = new Employee();
        Guest guest = new Guest();
        employee.setDepartmentId(departmentService.periodRandomDate());
        guest.setVisitDate(periodRandomDate());
        guest.setCard(randomCard());
        guest.setEmpId(employee);
        guestRepository.save(guest);
        logger.info("Гостю - {}, назначена встреча с сотрудником {}, {}, {}",
                guest.getId(), employee.getId(), employee.getDepartmentId().getName(),
                countDays());

    }

    public void meetingGuest() {
        for (Guest guest : guestRepository.findAll()) {
            Employee employee = guest.getEmpId();
            if (employee != null && employee.getFiredTime() != null && employee.getFiredTime().isBefore(guest.getVisitDate())) {
                logger.info("Встреча гостя - {}, с сотрудником - {} отменена, отдел - {}, дата встречи - {}, дата увольнения - {}",
                        guest.getId(),
                        employee.getId(),
                        employee.getDepartmentId().getName(),
                        guest.getVisitDate(),
                        employee.getFiredTime());
            }
        }
    }
    private long countDays() {
        long days = ChronoUnit.DAYS.between(START_DAY.plusMonths(6), END_DAY);
        return new Random().nextLong(days);
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

}
