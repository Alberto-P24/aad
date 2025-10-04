package com.apinpla2605.aad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

@SpringBootApplication
@Slf4j
public class Miniexplorador implements CommandLineRunner {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(Miniexplorador.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("=== MiniExplorador de ficheros ===");
        File dir = pedirDirectorio();
        if (dir == null) return;

        while (true) {
            log.info("\nContenido de: " + dir.getAbsolutePath());
            listarDirectorio(dir);

            mostrarMenu();
            String opcion = sc.nextLine().trim();

            switch (opcion) {
                case "1": crearFichero(dir); break;
                case "2": moverFichero(); break;
                case "3": borrarFichero(); break;
                case "4":
                    File nuevo = pedirDirectorio();
                    if (nuevo != null) {
                        dir = nuevo; // solo cambiar si el usuario introduce algo válido
                    } else {
                        log.info("No se cambió el directorio.");
                    }
                    break;
                case "5": log.info("Saliendo..."); return;
                default: log.info("Opción no válida");
            }

        }
    }

    // Pide un directorio válido y lo devuelve
    private static File pedirDirectorio() {
        while (true) {
            log.info("Ruta del directorio: ");
            String ruta = sc.nextLine().trim();
            if (ruta.isEmpty()) return null;

            File f = new File(ruta);
            if (!f.exists()) {
                log.info("El directorio no existe.");
            } else if (!f.isDirectory()) {
                log.info("La ruta no es un directorio.");
            } else if (!f.canRead()) {
                log.info("No tienes permisos de lectura.");
            } else {
                return f; // Directorio válido
            }
        }
    }

    // Lista el contenido del directorio
    private static void listarDirectorio(File dir) {
        File[] lista = dir.listFiles();
        if (lista == null) {
            log.info("No se pudo leer el directorio.");
            return;
        }
        log.info("%-40s %-10s %-25s %-10s%n", "Nombre", "Tipo", "Última modificación", "Tamaño");
        for (File f : lista) {
            String tipo = f.isDirectory() ? "<DIR>" : "<FILE>";
            String fecha = new Date(f.lastModified()).toString();
            String size = f.isDirectory() ? "-" : f.length() + "B";
            log.info("%-40s %-10s %-25s %-10s%n", f.getName(), tipo, fecha, size);
        }
    }

    // Muestra menú de opciones
    private static void mostrarMenu() {
        log.info("\nMenú:");
        log.info("1) Crear fichero vacío");
        log.info("2) Mover fichero");
        log.info("3) Borrar fichero");
        log.info("4) Cambiar de directorio");
        log.info("5) Salir");
        log.info("Opción: ");
    }

    // Crear un nuevo fichero vacío
    private static void crearFichero(File dir) {
        log.info("Nombre del nuevo fichero: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            log.info("Nombre vacío. Cancelado.");
            return;
        }
        File f = new File(dir, nombre);
        try {
            if (f.exists()) {
                log.info("Ya existe un fichero con ese nombre.");
            } else if (f.createNewFile()) {
                log.info("Fichero creado: " + f.getAbsolutePath());
            } else {
                log.info("No se pudo crear el fichero.");
            }
        } catch (IOException e) {
            log.info("Error al crear fichero: " + e.getMessage());
        }
    }

    // Mover un fichero
    private static void moverFichero() {
        log.info("Ruta del fichero a mover: ");
        String srcPath = sc.nextLine().trim();
        if (srcPath.isEmpty()) { log.info("Ruta inválida."); return; }

        File origen = new File(srcPath);
        if (!origen.exists()) { log.info("El fichero no existe."); return; }

        log.info("Ruta destino: ");
        String dstPath = sc.nextLine().trim();
        if (dstPath.isEmpty()) { log.info("Ruta inválida."); return; }

        File destino = new File(dstPath);
        if (destino.isDirectory()) destino = new File(destino, origen.getName());

        if (origen.renameTo(destino)) {
            log.info("Fichero movido correctamente.");
        } else {
            log.info("No se pudo mover el fichero.");
        }
    }

    // Borrar un fichero (con confirmación)
    private static void borrarFichero() {
        log.info("Ruta del fichero a borrar: ");
        String path = sc.nextLine().trim();
        if (path.isEmpty()) { log.info("Ruta inválida."); return; }

        File f = new File(path);
        if (!f.exists()) { log.info("El fichero no existe."); return; }

        log.info("Escribe SI para confirmar borrado: ");
        if (!"SI".equalsIgnoreCase(sc.nextLine().trim())) {
            log.info("Cancelado por el usuario.");
            return;
        }

        if (f.isDirectory() && f.listFiles() != null && f.listFiles().length > 0) {
            log.info("El directorio no está vacío. No se borra.");
            return;
        }

        if (f.delete()) {
            log.info("Fichero borrado.");
        } else {
            log.info("No se pudo borrar.");
        }
    }
}
