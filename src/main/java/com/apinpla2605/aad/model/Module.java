package com.apinpla2605.aad.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "module")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String code;
    private String name;
    private Integer hours;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Enrollment> enrollments;

    @Override
    public String toString() {
        return "Module(id=" + id + ", code=" + code + ", name=" + name + ", hours=" + hours + ")";
    }
}