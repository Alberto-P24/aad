package com.apinpla2605.aad.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @ToString.Exclude
    private Student student;

    @ManyToOne
    @JoinColumn(name = "module_id")
    @ToString.Exclude
    private Module module;

    private LocalDate enrollmentDate;
    private Double finalGrade;

    @Override
    public String toString() {
        return "Enrollment(id=" + id + ", enrollmentDate=" + enrollmentDate + ", finalGrade=" + finalGrade + ")";
    }
}