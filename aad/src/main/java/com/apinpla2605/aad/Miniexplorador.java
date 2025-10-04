package com.apinpla2605.aad;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Miniexplorador {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public static void main(String[] args) {
        System.out.println("=== MiniExplorador de ficheros ===");
        Path dir = null;

// Bucle para asegurarnos de que el usuario introduce un directorio válido
        while (dir == null) {
            dir = askDirectory();
            if (dir == null) {
                System.out.println("Debes introducir un directorio válido para continuar.");
                System.out.println("Inténtalo de nuevo o pulsa ENTER para salir.");
                String again = scanner.nextLine().trim();
                if (again.isEmpty()) {
                    System.out.println("Saliendo del programa.");
                    return;
                }
            }
        }

        while (true) {
            System.out.println();
            assert dir != null;
            System.out.println("Contenido de: " + dir.toAbsolutePath());
            listDirectory(dir);


            System.out.println();
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        createNewFile(dir);
                        break;
                    case "2":
                        moveFileInteractive();
                        break;
                    case "3":
                        deleteFileInteractive();
                        break;
                    case "4":
                        dir = askDirectory();
                        if (dir == null) {
                            System.out.println("No se cambió el directorio.");
                        }
                        break;
                    case "5":
                        System.out.println("Saliendo...");
                        return;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Se produjo un error al ejecutar la operación: " + e.getMessage());
            }
        }
    }

    /**
     * Pide al usuario la ruta de un directorio y valida que exista y sea un directorio legible.
     * @return Path válido del directorio o null si no es válido.
     */
    private static Path askDirectory() {
        System.out.print("Introduce la ruta del directorio (o deja vacío para salir): ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return null;
        }
        Path path = Paths.get(input);
        if (!Files.exists(path)) {
            System.out.println("El directorio no existe.");
            return null;
        }
        if (!Files.isDirectory(path)) {
            System.out.println("La ruta no es un directorio.");
            return null;
        }
        if (!Files.isReadable(path)) {
            System.out.println("No tiene permisos de lectura para ese directorio.");
            return null;
        }
        return path;
    }

    /**
     * Muestra el contenido de un directorio, diferenciando entre ficheros y subdirectorios.
     * Para cada fichero muestra nombre, tamaño en bytes y fecha de última modificación.
     * Si se produce una excepción durante la lectura, se informa al usuario.
     * @param dir directorio a listar
     */
    private static void listDirectory(Path dir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            System.out.printf("%-60s %-10s %-20s %-10s%n", "Nombre", "Tipo", "Última modificación", "Tamaño");
            System.out.println("-------------------------------------------------------------------------------------------------------");
            for (Path entry : stream) {
                try {
                    BasicFileAttributes attrs = Files.readAttributes(entry, BasicFileAttributes.class);
                    String name = entry.getFileName().toString();
                    String type = attrs.isDirectory() ? "<DIR>" : "<FILE>";
                    String lastMod = dtf.format(Instant.ofEpochMilli(attrs.lastModifiedTime().toMillis()));
                    String size = attrs.isDirectory() ? "-" : String.valueOf(attrs.size());
                    System.out.printf("%-60s %-10s %-20s %-10s%n", name, type, lastMod, size);
                } catch (IOException ioe) {
                    System.out.printf("%-60s %-10s %-20s %-10s%n", entry.getFileName(), "<?>", "<?>", "<?>");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al listar el directorio: " + e.getMessage());
        }
    }

    /**
     * Muestra el menú de opciones disponible para el usuario.
     */
    private static void printMenu() {
        System.out.println();
        System.out.println("Menú:");
        System.out.println("1) Crear un nuevo fichero vacío");
        System.out.println("2) Mover un fichero a otra ubicación");
        System.out.println("3) Borrar un fichero existente");
        System.out.println("4) Cambiar de directorio");
        System.out.println("5) Salir");
        System.out.print("Elige una opción: ");
    }

    /**
     * Crea un nuevo fichero vacío dentro del directorio especificado por el usuario.
     * Gestiona nombres inválidos y permisos insuficientes.
     * @param dir directorio donde crear el fichero
     */
    private static void createNewFile(Path dir) {
        System.out.print("Introduce el nombre del nuevo fichero (relativo al directorio mostrado): ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Nombre vacío. Operación cancelada.");
            return;
        }
        Path target = dir.resolve(name);
        try {
            Path parent = target.getParent();
            if (parent != null && !Files.exists(parent)) {
                System.out.println("El directorio padre no existe: " + parent);
                return;
            }
            if (Files.exists(target)) {
                System.out.println("El fichero ya existe.");
                return;
            }
            Files.createFile(target);
            System.out.println("Fichero creado: " + target.toAbsolutePath());
        } catch (InvalidPathException ipe) {
            System.out.println("Nombre de fichero inválido: " + ipe.getMessage());
        } catch (IOException ioe) {
            System.out.println("No se pudo crear el fichero. Error: " + ioe.getMessage());
        } catch (SecurityException se) {
            System.out.println("Permisos insuficientes para crear el fichero.");
        }
    }

    /**
     * Interfaz interactiva para mover un fichero: pide ruta origen y destino y llama a moveFile.
     */
    private static void moveFileInteractive() {
        System.out.print("Introduce la ruta del fichero a mover: ");
        String src = scanner.nextLine().trim();
        System.out.print("Introduce la ruta destino (puede ser un directorio o ruta completa): ");
        String dst = scanner.nextLine().trim();
        if (src.isEmpty() || dst.isEmpty()) {
            System.out.println("Rutas inválidas. Operación cancelada.");
            return;
        }
        try {
            Path source = Paths.get(src);
            Path target = Paths.get(dst);
            moveFile(source, target);
            System.out.println("Movimiento completado.");
        } catch (InvalidPathException | IOException ipe) {
            System.out.println("Ruta inválida: " + ipe.getMessage());
        }
    }

    /**
     * Mueve un fichero o directorio de source a target.
     * Si target es un directorio existente, el fichero se mueve dentro de ese directorio conservando el nombre.
     * Gestiona excepciones comunes como inexistencia de origen o permisos insuficientes.
     * @param source ruta de origen
     * @param target ruta destino o directorio destino
     * @throws IOException si ocurre un error I/O
     */
    private static void moveFile(Path source, Path target) throws IOException {
        if (!Files.exists(source)) {
            System.out.println("El fichero origen no existe: " + source);
            return;
        }
        if (!Files.isReadable(source)) {
            System.out.println("No tiene permisos de lectura sobre el fichero origen.");
            return;
        }

        Path finalTarget = target;
        if (Files.exists(target) && Files.isDirectory(target)) {
            finalTarget = target.resolve(source.getFileName());
        } else {
            Path parent = target.getParent();
            if (parent != null && !Files.exists(parent)) {
                System.out.println("El directorio destino no existe: " + parent);
                return;
            }
        }

        try {
            Files.move(source, finalTarget, StandardCopyOption.REPLACE_EXISTING);
        } catch (AtomicMoveNotSupportedException amnse) {
            // Intentar sin operaciones atómicas si no se soportan
            Files.move(source, finalTarget, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            System.out.println("No se pudo mover el fichero: " + ioe.getMessage());
            throw ioe;
        } catch (SecurityException se) {
            System.out.println("Permisos insuficientes para mover el fichero.");
        }
    }

    /**
     * Interfaz interactiva para borrar un fichero: pide la ruta y solicita confirmación al usuario.
     */
    private static void deleteFileInteractive() {
        System.out.print("Introduce la ruta del fichero a borrar: ");
        String src = scanner.nextLine().trim();
        if (src.isEmpty()) {
            System.out.println("Ruta inválida. Operación cancelada.");
            return;
        }
        Path path = Paths.get(src);
        if (!Files.exists(path)) {
            System.out.println("El fichero no existe: " + path);
            return;
        }
        System.out.print("¿Estás seguro? Escribe 'SI' para confirmar: ");
        String conf = scanner.nextLine().trim();
        if (!"SI".equalsIgnoreCase(conf)) {
            System.out.println("Operación cancelada por el usuario.");
            return;
        }
        try {
            if (Files.isDirectory(path)) {
                // Solo permitir borrar directorios vacíos para evitar borrados recursivos accidentales
                try (DirectoryStream<Path> ds = Files.newDirectoryStream(path)) {
                    if (ds.iterator().hasNext()) {
                        System.out.println("El directorio no está vacío. No se permite borrar directorios no vacíos.");
                        return;
                    }
                }
            }
            Files.delete(path);
            System.out.println("Fichero borrado: " + path.toAbsolutePath());
        } catch (DirectoryNotEmptyException dnee) {
            System.out.println("El directorio no está vacío.");
        } catch (IOException ioe) {
            System.out.println("No se pudo borrar el fichero: " + ioe.getMessage());
        } catch (SecurityException se) {
            System.out.println("Permisos insuficientes para borrar el fichero.");
        }
    }
}