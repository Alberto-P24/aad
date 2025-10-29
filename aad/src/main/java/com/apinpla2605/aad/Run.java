package com.apinpla2605.aad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

/**
 * Clase principal que ejecuta la aplicación de gestión de logs.
 * Proporciona un menú para añadir eventos, mostrar eventos por fecha
 * y cambiar la codificación del fichero de logs.
 */
public class Run {

    /** Logger para mostrar información y errores */
    private static final Logger log = LoggerFactory.getLogger(Run.class);

    /**
     * Método que ejecuta el programa.
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        LogManager logManager = new LogManager("app.log", Charset.forName("UTF-8"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        while (true) {
            log.info("\n--- GESTOR DE LOGS ---");
            log.info("1. Add event (Añadir evento)");
            log.info("2. Show events by date (Mostrar eventos por fecha)");
            log.info("3. Change encoding (Cambiar codificación)");
            log.info("4. Exit (Salir)");
            log.info("Elige una opción: ");

            String option;
            try {
                option = scanner.nextLine();
            } catch (Exception e) {
                log.error("Entrada inválida, saliendo del programa.");
                break;
            }

            switch (option) {
                case "1":
                    log.info("Introduce el mensaje del evento: ");
                    String message = scanner.nextLine();
                    logManager.addEvent(message);
                    break;
                case "2":
                    log.info("Introduce la fecha (YYYY-MM-DD): ");
                    String dateInput = scanner.nextLine();
                    try {
                        dateFormat.parse(dateInput);
                        logManager.showEventsByDate(dateInput);
                    } catch (ParseException e) {
                        log.info("Formato de fecha incorrecto. Debe ser YYYY-MM-DD.");
                    }
                    break;
                case "3":
                    log.info("Elige codificación: 1) UTF-8  2) ISO-8859-1");
                    String code = scanner.nextLine();
                    if (code.equals("1")) {
                        logManager.setEncoding(Charset.forName("UTF-8"));
                    } else if (code.equals("2")) {
                        logManager.setEncoding(Charset.forName("ISO-8859-1"));
                    } else {
                        log.info("Opción inválida");
                    }
                    break;
                case "4":
                    log.info("Saliendo...");
                    scanner.close();
                    return;
                default:
                    log.info("Opción inválida");
            }
        }
    }

    /**
     * Punto de entrada de la aplicación.
     * @param args Argumentos de línea de comandos (no se utilizan)
     */
    public static void main(String[] args) {
        new Run().run();
    }
}

