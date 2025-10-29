                                                                            # Gestor de Logs en Java

## Descripción
Esta aplicación permite gestionar los logs de una aplicación:
- Añadir eventos con fecha y hora.
- Filtrar eventos por fecha.
- Cambiar la codificación del fichero (UTF-8 o ISO-8859-1).

## Requisitos
- Java 8 o superior.
- Dependencias SLF4J para Logger.
- Permisos de escritura en el directorio donde se crea `app.log`.

## Uso
1. Compilar la aplicación:
   javac -cp "ruta-a-slf4j-api.jar;ruta-a-slf4j-simple.jar" src/*.java
2. Ejecutar la aplicación:
   java -cp "src;ruta-a-slf4j-api.jar;ruta-a-slf4j-simple.jar" com.apinpla2605.aad.LogManagerApp.Run
### Opciones del menú
1. **Add event (Añadir evento)**: Escribe un mensaje que se guardará con fecha y hora.
2. **Show events by date (Mostrar eventos por fecha)**: Introduce una fecha (YYYY-MM-DD) para ver los eventos registrados.
3. **Change encoding (Cambiar codificación)**: Permite alternar entre UTF-8 e ISO-8859-1.
4. **Exit (Salir)**: Cierra el programa.

## Ejemplo de registros en app.log
[2025-09-13 18:45:00] Usuario Ana inició sesión
[2025-09-13 18:50:12] Usuario Juan cerró sesión
## Notas
- El fichero `app.log` se crea automáticamente si no existe.
- Se gestionan excepciones para errores de lectura/escritura y codificación.
- Todos los mensajes informativos y errores se registran mediante SLF4J Logger.
- La fecha para filtrar debe introducirse en formato YYYY-MM-DD. Formatos incorrectos mostrarán un mensaje de error.