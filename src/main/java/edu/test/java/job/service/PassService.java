package edu.test.java.job.service;

import edu.test.java.job.component.EmployeesMgr;
import edu.test.java.job.model.Employee;
import edu.test.java.job.model.Guest;
import edu.test.java.job.model.Person;
import edu.test.java.job.repository.EmployeesRepository;
import edu.test.java.job.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PassService {

    private final EmployeesRepository employeesRepository;
    private final GuestRepository guestRepository;
    private final DepartmentService departmentService;
    private final LocalDate START_DAY = LocalDate.of(2022, 1, 1);
    private final LocalDate END_DAY = LocalDate.of(2022, 12, 31);
    private final Logger logger = LoggerFactory.getLogger(EmployeesMgr.class.getName());

    public void next() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 10; i++) {
            if (random.nextInt(0, 100) < 20) {
                checkCardEmployee(randomCard(), periodRandomDate());
            } else {
                List<Person> people = getAllPersonAndGuest();
                Person peopleRandom = people.get(ThreadLocalRandom.current().nextInt(people.size()));
                checkCardEmployee(peopleRandom.getCard(), periodRandomDate());
            }
        }
    }

    private List<Person> getAllPersonAndGuest() {
        Iterable<Employee> employees = employeesRepository.findAll();
        Iterable<Guest> guests = guestRepository.findAll();
        List<Person> personList = new ArrayList<>();
        employees.iterator().forEachRemaining(personList::add);
        guests.iterator().forEachRemaining(personList::add);
        return personList;
    }

    private void logFileAccessPerson(byte[] hex, LocalDate date) {
        Guest guest = new Guest();
        Employee employee = new Employee();
        employee.setDepartmentId(departmentService.periodRandomDate());
        employeesRepository.save(employee);
        guestRepository.save(guest);
       if (employee.getFiredTime() != null) {
           logger.info("{}, доступ запрещен сотруднику - {}, сотруднику - {}, отдел - {},",
                   date, employee.getId(), employee.getDepartmentId().getName(), hexCard(hex));
       } else {
           logger.info("{}, доступ разрешен сотруднику - {}, {}, код карты - {}",
                   date, employee.getId(), employee.getDepartmentId().getName(), hexCard(hex));
       }
       if (employee.getFiredTime() == null) {
            logger.info("{}, доступ к гостю - {} пришел к сотруднику - {}, отдел - {}, код карты - {}",
                    date, guest.getId(), employee.getId(),  employee.getDepartmentId().getName(), hexCard(hex));
        } else {
            logger.info("{}, доступ запрещен гостю - {}, код карты - {}",
                    date, guest.getId(), hexCard(hex));
        }
            logger.info("Поднесена неизвестная карта - {}",
                    hexCard(hex));
    }

    private void checkCardEmployee(byte[] code, LocalDate date) {
        List<Person> people = getAllPersonAndGuest();
        Optional<Person> person = people.stream()
                .filter(p ->  Arrays.equals(code, p.getCard()))
                .findAny();
        if (person.isPresent()) {
            logFileAccessPerson(code, date);
        }
    }

    private LocalDate periodRandomDate() {
        long one = START_DAY.toEpochDay();
        long two = END_DAY.toEpochDay();
        long random = ThreadLocalRandom.current().nextLong(one, two);
        return LocalDate.ofEpochDay(random);
    }

    private String hexCard(byte[] card) {
        StringBuilder sb = new StringBuilder();
        for (byte b : card) {
                sb.append(String.format("%02X", b));
            }
            return sb.toString();
    }

    private byte[] randomCard() {
        byte[] bytes = new byte[16];
        new Random().nextBytes(bytes);
        return bytes;
    }
}
