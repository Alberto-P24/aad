package com.apinpla2605.aad.Repository;

import com.apinpla2605.aad.model.Module;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

public class ModuleRepository implements crudRepository<Module>{

    /**
     * @param entity
     * @return
     */
    @Override
    public Module create(Module entity) {
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public Module read(Module entity) {
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public Module update(Module entity) {
        return null;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public boolean delete(Module entity) {
        return false;
    }
}

