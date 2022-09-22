package edu.test.java.job.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Employee extends Person {

    private LocalDate hireTime;
    private LocalDate firedTime;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department departmentId;

}
