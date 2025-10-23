package com.apinpla2605.aad.Actividad_1_3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileConverterApp {

    private static final Logger log = LoggerFactory.getLogger(FileConverterApp.class);

    public void run() {
        try {
            String inputFile = "alumnos.csv";
            List<Student> students = readCsv(inputFile);

            writeJson(students, "students.json");
            writeXml(students, "students.xml");

            log.info("Conversion completed successfully.");
        } catch (Exception e) {
            log.error("Error during conversion: {}", e.getMessage(), e);
        }
    }

    private List<Student> readCsv(String filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // Skip header line
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            double grade = Double.parseDouble(parts[2]);
            students.add(new Student(id, name, grade));
        }

        log.info("CSV file read successfully: {} students loaded", students.size());
        return students;
    }

    private void writeJson(List<Student> students, String outputFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), students);
        log.info("JSON file created: {}", outputFile);
    }

    private void writeXml(List<Student> students, String outputFile) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writerWithDefaultPrettyPrinter().writeValue(new File(outputFile), students);
        log.info("XML file created: {}", outputFile);
    }

    public static void main(String[] args) {
        new FileConverterApp().run();
    }
}
