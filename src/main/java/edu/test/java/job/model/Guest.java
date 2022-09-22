package edu.test.java.job.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Guest extends Person {

    private LocalDate visitDate;
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "emp_id")
    private Employee empId;
}
