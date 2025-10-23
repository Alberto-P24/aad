package com.apinpla2605.aad.Repository;

public interface crudRepository<T> {
   T create(T entity);
   T read(T entity);
   T update(T entity);
   boolean delete(T entity);
}
