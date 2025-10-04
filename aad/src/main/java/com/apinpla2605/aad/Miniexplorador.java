package com.apinpla2605.aad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

@SpringBootApplication
public class Miniexplorador implements CommandLineRunner {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(Miniexplorador.class, args);
    }

    @Override
    public void run(String... args) {
        System.out.println("=== MiniExplorador de ficheros ===");
        File dir = pedirDirectorio();
        if (dir == null) return;

        while (true) {
            System.out.println("\nContenido de: " + dir.getAbsolutePath());
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
                        System.out.println("No se cambió el directorio.");
                    }
                    break;
                case "5": System.out.println("Saliendo..."); return;
                default: System.out.println("Opción no válida");
            }

        }
    }

    // Pide un directorio válido y lo devuelve
    private static File pedirDirectorio() {
        while (true) {
            System.out.print("Ruta del directorio: ");
            String ruta = sc.nextLine().trim();
            if (ruta.isEmpty()) return null;

            File f = new File(ruta);
            if (!f.exists()) {
                System.out.println("El directorio no existe.");
            } else if (!f.isDirectory()) {
                System.out.println("La ruta no es un directorio.");
            } else if (!f.canRead()) {
                System.out.println("No tienes permisos de lectura.");
            } else {
                return f; // Directorio válido
            }
        }
    }

    // Lista el contenido del directorio
    private static void listarDirectorio(File dir) {
        File[] lista = dir.listFiles();
        if (lista == null) {
            System.out.println("No se pudo leer el directorio.");
            return;
        }
        System.out.printf("%-40s %-10s %-25s %-10s%n", "Nombre", "Tipo", "Última modificación", "Tamaño");
        for (File f : lista) {
            String tipo = f.isDirectory() ? "<DIR>" : "<FILE>";
            String fecha = new Date(f.lastModified()).toString();
            String size = f.isDirectory() ? "-" : f.length() + "B";
            System.out.printf("%-40s %-10s %-25s %-10s%n", f.getName(), tipo, fecha, size);
        }
    }

    // Muestra menú de opciones
    private static void mostrarMenu() {
        System.out.println("\nMenú:");
        System.out.println("1) Crear fichero vacío");
        System.out.println("2) Mover fichero");
        System.out.println("3) Borrar fichero");
        System.out.println("4) Cambiar de directorio");
        System.out.println("5) Salir");
        System.out.print("Opción: ");
    }

    // Crear un nuevo fichero vacío
    private static void crearFichero(File dir) {
        System.out.print("Nombre del nuevo fichero: ");
        String nombre = sc.nextLine().trim();
        if (nombre.isEmpty()) {
            System.out.println("Nombre vacío. Cancelado.");
            return;
        }
        File f = new File(dir, nombre);
        try {
            if (f.exists()) {
                System.out.println("Ya existe un fichero con ese nombre.");
            } else if (f.createNewFile()) {
                System.out.println("Fichero creado: " + f.getAbsolutePath());
            } else {
                System.out.println("No se pudo crear el fichero.");
            }
        } catch (IOException e) {
            System.out.println("Error al crear fichero: " + e.getMessage());
        }
    }

    // Mover un fichero
    private static void moverFichero() {
        System.out.print("Ruta del fichero a mover: ");
        String srcPath = sc.nextLine().trim();
        if (srcPath.isEmpty()) { System.out.println("Ruta inválida."); return; }

        File origen = new File(srcPath);
        if (!origen.exists()) { System.out.println("El fichero no existe."); return; }

        System.out.print("Ruta destino: ");
        String dstPath = sc.nextLine().trim();
        if (dstPath.isEmpty()) { System.out.println("Ruta inválida."); return; }

        File destino = new File(dstPath);
        if (destino.isDirectory()) destino = new File(destino, origen.getName());

        if (origen.renameTo(destino)) {
            System.out.println("Fichero movido correctamente.");
        } else {
            System.out.println("No se pudo mover el fichero.");
        }
    }

    // Borrar un fichero (con confirmación)
    private static void borrarFichero() {
        System.out.print("Ruta del fichero a borrar: ");
        String path = sc.nextLine().trim();
        if (path.isEmpty()) { System.out.println("Ruta inválida."); return; }

        File f = new File(path);
        if (!f.exists()) { System.out.println("El fichero no existe."); return; }

        System.out.print("Escribe SI para confirmar borrado: ");
        if (!"SI".equalsIgnoreCase(sc.nextLine().trim())) {
            System.out.println("Cancelado por el usuario.");
            return;
        }

        if (f.isDirectory() && f.listFiles() != null && f.listFiles().length > 0) {
            System.out.println("El directorio no está vacío. No se borra.");
            return;
        }

        if (f.delete()) {
            System.out.println("Fichero borrado.");
        } else {
            System.out.println("No se pudo borrar.");
        }
    }
}
