//package com.apinpla2605.aad;
//
//import com.apinpla2605.aad.model.Student;
//import com.apinpla2605.aad.model.StudentRepository;
//import org.springframework.stereotype.Service;
//
//
//@Service
//public class StudentService implements Service<Student> {
//
//    /**
//     * @param entity
//     * @return
//     */
//    @Override
//    public boolean validate(Student entity) {
//        return entity.getDni().isBlank() && entity.getName().isBlank();
//    }
//
//    public boolean createStudent(Student student) {
//        if (validate(student)) {
//           Student create = studentRepository.create(student);
//            return true;
//        }
//        return false;
//    }
//}