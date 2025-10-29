package com.apinpla2605.aad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Clase responsable de la gestión de logs de la aplicación.
 * Proporciona métodos para añadir eventos, mostrar eventos por fecha
 * y configurar la codificación del fichero de logs.
 */
public class LogManager {

    /** Logger para mostrar información y errores */
    private static final Logger log = LoggerFactory.getLogger(LogManager.class);

    /** Fichero de logs */
    private File logFile;

    /** Codificación de caracteres */
    private Charset encoding;

    /**
     * Constructor de LogManager.
     * @param filePath Ruta del fichero de logs
     * @param encoding Codificación para leer/escribir el fichero
     */
    public LogManager(String filePath, Charset encoding) {
        this.logFile = new File(filePath);
        this.encoding = encoding;

        try {
            if (!logFile.exists()) {
                File parent = logFile.getParentFile();
                if (parent != null) {
                    parent.mkdirs(); // solo crea directorios si existen
                }
                logFile.createNewFile();
                log.info("Fichero de logs creado: {}", filePath);
            }
        } catch (IOException e) {
            log.error("Error creando el fichero de logs: {}", e.getMessage(), e);
        }
    }

    /**
     * Añade un evento al fichero de logs con fecha y hora.
     * @param message Mensaje del evento
     */
    public void addEvent(String message) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(logFile, true), encoding))) {

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write("[" + timestamp + "] " + message);
            writer.newLine();
            log.info("Evento añadido: {}", message);

        } catch (IOException e) {
            log.error("Error escribiendo en el log: {}", e.getMessage(), e);
        }
    }

    /**
     * Muestra todos los eventos de una fecha específica.
     * @param date Fecha en formato YYYY-MM-DD
     */
    public void showEventsByDate(String date) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(logFile), encoding))) {

            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("[" + date)) {
                    log.info(line);
                    found = true;
                }
            }
            if (!found) {
                log.info("No se encontraron eventos para la fecha: {}", date);
            }

        } catch (IOException e) {
            log.error("Error leyendo el log: {}", e.getMessage(), e);
        }
    }

    /**
     * Cambia la codificación de lectura/escritura del fichero de logs.
     * @param encoding Nueva codificación
     */
    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
        log.info("Codificación cambiada a {}", encoding.displayName());
    }
}