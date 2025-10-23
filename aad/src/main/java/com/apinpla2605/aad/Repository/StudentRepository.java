package com.apinpla2605.aad.Repository;

import com.apinpla2605.aad.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class StudentRepository implements crudRepository<Student> {

    /**
     * @param entity
     * @return
     */
    @Override
    public Student create(Student entity) {
        log.info("Insert: {}", entity.toString());
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public Student read(Student entity) {
        log.info("Read: {}", entity.toString());
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public Student update(Student entity) {
        log.info("Update: {}", entity.toString());
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public boolean delete(Student entity) {
        log.info("Delete: {}", entity.toString());
        return false;
    }
}
