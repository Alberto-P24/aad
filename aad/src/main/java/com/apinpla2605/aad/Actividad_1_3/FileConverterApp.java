package com.apinpla2605.aad.Actividad_1_3;
//
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 * Clase principal de la aplicación que permite convertir un fichero CSV
 * con información de alumnos a los formatos JSON y XML.
 *
 * La aplicación cumple los siguientes objetivos:
 * - Lee información desde un fichero CSV.
 * - Convierte los datos a objetos de la clase Student.
 * - Guarda los datos en ficheros JSON y XML.
 * - Gestiona las excepciones mediante registro con log.
 */
public class FileConverterApp {

    /** Objeto Logger para mostrar información y errores en la consola. */
    private static final Logger log = LoggerFactory.getLogger(FileConverterApp.class);

    /**
     * Ejecuta el flujo principal de la aplicación:
     * - Lee los datos del fichero CSV.
     * - Convierte los datos a una lista de objetos Student.
     * - Genera los ficheros de salida en formato JSON y XML.
     *
     * Si ocurre alguna excepción, se registra en el log.
     */
    public void run() {
        try {
            String inputFile = "alumnos.csv";
            List<Student> students = readCsv(inputFile);

            writeJson(students, "students.json");
            writeXml(students, "students.xml");

            log.info("Conversión completada correctamente.");
        } catch (Exception e) {
            log.error("Error durante la conversión: {}", e.getMessage(), e);
        }
    }

    /**
     * Lee un fichero CSV y convierte cada línea (excepto la cabecera)
     * en un objeto de tipo Student.
     *
     * @param filePath ruta del fichero CSV que se desea leer
     * @return una lista de objetos Student con los datos del fichero
     * @throws IOException si el fichero no se encuentra o no puede leerse
     */
    private List<Student> readCsv(String filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Se omite la primera línea (cabecera del CSV)
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            double grade = Double.parseDouble(parts[2]);
            students.add(new Student(id, name, grade));
        }

        log.info("Fichero CSV leído correctamente: {} alumnos cargados.", students.size());
        return students;
    }

    /**
     * Escribe una lista de objetos Student en un fichero JSON.
     * Utiliza la clase ObjectMapper de Jackson con formato legible (PrettyPrinter).
     *
     * @param students lista de alumnos a guardar en el fichero
     * @param outputFile nombre del fichero JSON de salida
     * @throws IOException si ocurre un error al escribir el fichero
     */
    private void writeJson(List<Student> students, String outputFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), students);
        log.info("Fichero JSON creado: {}", outputFile);
    }

    /**
     * Escribe una lista de objetos Student en un fichero XML.
     * Utiliza la clase XmlMapper de Jackson con formato legible (PrettyPrinter).
     *
     * @param students lista de alumnos a guardar en el fichero
     * @param outputFile nombre del fichero XML de salida
     * @throws IOException si ocurre un error al escribir el fichero
     */
    private void writeXml(List<Student> students, String outputFile) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), students);
        log.info("Fichero XML creado: {}", outputFile);
    }

    /**
     * Método de inicio de la aplicación.
     * Crea una instancia de FileConverterApp y ejecuta el método run().
     *
     * @param args argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        new FileConverterApp().run();
    }
}
