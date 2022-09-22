package edu.test.java.job.component;

import edu.test.java.job.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuestMgr {

    private final GuestService guestService;

    public void visitGuest() {
        guestService.visitGuest();
    }

    public void meetingGuest() {
        guestService.meetingGuest();
    }
}
