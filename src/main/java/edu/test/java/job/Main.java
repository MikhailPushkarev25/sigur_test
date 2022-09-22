package edu.test.java.job;

import edu.test.java.job.component.EmployeesMgr;
import edu.test.java.job.component.GuestMgr;
import edu.test.java.job.component.PassEmulator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@RequiredArgsConstructor
public class Main implements CommandLineRunner {
    private final ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

    }

    @Override
    public void run(String... args) {
        try {
            EmployeesMgr employee = context.getBean(EmployeesMgr.class);
            GuestMgr guest = context.getBean(GuestMgr.class);
            PassEmulator pass = context.getBean(PassEmulator.class);

            employee.newEmployee();
            employee.employeeFired();
            guest.visitGuest();
            guest.meetingGuest();
            pass.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
