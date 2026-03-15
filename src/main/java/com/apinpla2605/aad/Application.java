package com.apinpla2605.aad;

import com.apinpla2605.aad.service.ManagementService;
import com.apinpla2605.aad.model.Enrollment;
import com.apinpla2605.aad.model.Profile;
import com.apinpla2605.aad.model.Student;
import com.apinpla2605.aad.model.Module;
import com.apinpla2605.aad.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class Application implements CommandLineRunner {

    private final ManagementService managementService;
    private final StudentRepository studentRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Profile perfil = new Profile();
        perfil.setAddress("Calle 123");
        perfil.setPhone("600112233");

        Student luna = new Student();
        luna.setNif("66280457T");
        luna.setName("Luna");
        luna.setEmail("luna@g.educaand.es");
        luna.setCourse("DAM");
        luna.setProfile(perfil);

        Module programacion = new Module();
        programacion.setCode("0485");
        programacion.setName("Programación");
        programacion.setHours(250);

        luna = managementService.createStudent(luna);
        programacion = managementService.createModule(programacion);

        Enrollment enrollment = managementService.enrollStudentInModule(luna.getId(), programacion.getId());
        log.info("Matrícula realizada: {}", enrollment);

        int countEnrollments = managementService.countEnrollments(luna.getId());
        log.info("{} módulos matriculados para el alumno {}", countEnrollments, luna.getName());

        studentRepository.delete(luna);
        log.info("Alumno {} eliminado lógicamente en la sesión", luna.getName());

        log.warn("Lanzando excepción para forzar ROLLBACK...");
        throw new RuntimeException("Forzando rollback de la transacción");
    }
}