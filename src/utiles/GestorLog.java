/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles;

/**
 *
 * @author Brunot
 */


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import sistema.TrenesSA; 

public class GestorLog {

    private static final String ARCHIVO_LOG = "log.txt";

    public static void registrarOperacion(String accion) {
        String fecha = obtenerFechaHora();
        escribirEnArchivo("[" + fecha + "] OPERACIÓN: " + accion);
    }

    public static void registrarEstadoSistema(TrenesSA sistema, String momento) {
        
        String estado = "\n=======================================================\n";
        estado += "ESTADO DEL SISTEMA - " + momento + " (" + obtenerFechaHora() + ")\n";
        estado += "=======================================================\n";
        
        estado += sistema.debugEstaciones() + "\n";
        estado += sistema.debugLineas() + "\n";
        estado += sistema.debugGrafo() + "\n";
      
        estado += "=======================================================\n";

        escribirEnArchivo(estado);
    }

    private static String obtenerFechaHora() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return ahora.format(formato);
    }

    private static void escribirEnArchivo(String contenido) {
        // El 'true' permite ir agregando al final del archivo sin borrar lo anterior
        try (FileWriter fw = new FileWriter(ARCHIVO_LOG, true);
             PrintWriter pw = new PrintWriter(fw)) {
            pw.println(contenido);
        } catch (IOException e) {
            System.err.println("No se pudo escribir en el archivo log: " + e.getMessage());
        }
    }
}