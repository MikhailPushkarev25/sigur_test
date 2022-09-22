package edu.test.java.job.component;

import edu.test.java.job.service.PassService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PassEmulator {

    private final PassService passService;

    public void next() {
        passService.next();
    }
}
