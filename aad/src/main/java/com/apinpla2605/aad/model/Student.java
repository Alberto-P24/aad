package com.apinpla2605.aad.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student extends Person{
    private String curso;

    public Student(String dni, String name, String surname){
        super(dni, name, surname);
    }

    public Student(String dni, String name, String surname, String curso) {
        super(dni, name, surname);
        this.curso = curso;
    }
}

