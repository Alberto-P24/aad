package com.apinpla2605.aad.Repository;

import com.apinpla2605.aad.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
public class PersonRepository implements crudRepository<Person>{
    /**
     * @param entity
     * @return
     */
    @Override
    public Person create(Person entity) {
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public Person read(Person entity) {
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public Person update(Person entity) {
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public boolean delete(Person entity) {
        return false;
    }
}
