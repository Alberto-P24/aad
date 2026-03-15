package com.apinpla2605.aad.service;

import com.apinpla2605.aad.model.Enrollment;
import com.apinpla2605.aad.model.Module;
import com.apinpla2605.aad.model.Student;
import com.apinpla2605.aad.repository.EnrollmentRepository;
import com.apinpla2605.aad.repository.ModuleRepository;
import com.apinpla2605.aad.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ManagementService {
    private final StudentRepository studentRepository;
    private final ModuleRepository moduleRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Transactional
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public Module createModule(Module module) {
        return moduleRepository.save(module);
    }

    @Transactional
    public Enrollment enrollStudentInModule(Integer studentId, Integer moduleId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Módulo no encontrado"));

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setModule(module);
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setFinalGrade(8.0);
        return enrollmentRepository.save(enrollment);
    }

    public int countEnrollments(Integer studentId) {
        return enrollmentRepository.countByStudentId(studentId); // Uso del nuevo método eficiente
    }
}