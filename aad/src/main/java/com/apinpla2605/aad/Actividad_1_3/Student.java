package com.apinpla2605.aad.Actividad_1_3;

/**
 * Clase que representa un alumno.
 * Contiene información básica: identificador, nombre y nota.
 */
public class Student {

    /** Identificador del alumno */
    private int id;

    /** Nombre del alumno */
    private String name;

    /** Nota del alumno */
    private double grade;

    /**
     * Constructor vacío necesario para bibliotecas como Jackson.
     */
    public Student() {}

    /**
     * Constructor con parámetros para inicializar un alumno.
     *
     * @param id identificador del alumno
     * @param name nombre del alumno
     * @param grade nota del alumno
     */
    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
    }

    /**
     * Obtiene el identificador del alumno.
     *
     * @return el id del alumno
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador del alumno.
     *
     * @param id nuevo id del alumno
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del alumno.
     *
     * @return el nombre del alumno
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del alumno.
     *
     * @param name nuevo nombre del alumno
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene la nota del alumno.
     *
     * @return la nota del alumno
     */
    public double getGrade() {
        return grade;
    }

    /**
     * Establece la nota del alumno.
     *
     * @param grade nueva nota del alumno
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    /**
     * Devuelve una representación en cadena del objeto Student.
     *
     * @return cadena con los datos del alumno
     */
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }
}
