# Sistema de Gestión Académica (Spring Boot + JPA + Hibernate)

## 1. Descripción General

Este proyecto consiste en una aplicación de gestión académica desarrollada con **Spring Boot**, que evoluciona el modelo de persistencia de JDBC manual hacia un enfoque moderno basado en **JPA (Java Persistence API)** e **Hibernate**.

El objetivo principal de la práctica es implementar:
- **Mapeo Objeto-Relacional (ORM):** Uso de anotaciones `@Entity` para vincular clases Java con tablas de la base de datos.
- **Automatización del Esquema:** Generación automática de tablas y relaciones (OneToOne, ManyToMany) al arrancar la aplicación.
- **Spring Data JPA:** Uso de interfaces `Repository` para simplificar las operaciones CRUD sin escribir SQL.
- **Gestión de Transacciones:** Implementación de `@Transactional` para asegurar la integridad de los datos y permitir rollbacks en caso de error.
- **Pool de Conexiones:** Configuración de **HikariCP** para optimizar el rendimiento y la gestión de conexiones.

---

## 2. Tecnologías Utilizadas

- **Java 25** (OpenJDK)
- **Spring Boot 3.5.5**
- **Spring Data JPA / Hibernate** (ORM)
- **H2 Database** (Base de datos en memoria para desarrollo ágil)
- **HikariCP** (Connection Pool)
- **Lombok** (Para limpieza de código en modelos)
- **Maven** (Gestor de dependencias)

---

## 3. Estructura y Ejecución

### 3.1 Configuración de la Base de Datos
Para esta práctica, se ha configurado una base de datos **H2 en memoria**. Esto permite que el entorno sea totalmente portátil y no requiera de una instalación externa de MySQL/PostgreSQL para validar la lógica de JPA.

La configuración se encuentra en `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:h2:mem:aad
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true