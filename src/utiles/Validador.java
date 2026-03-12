/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles;

import java.util.Scanner;

/**
 *
 * @author Brunot
 */
public class Validador {

    // Método para leer numeros enteros 
    public static int leerEntero(Scanner sc, String mensaje) {
        int numero = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print(mensaje);
            try {
                numero = Integer.parseInt(sc.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println(" Error: Por favor ingrese un número entero válido.");
            }
        }
        return numero;
    }

    //Método para validar numeros decimales mayores a un minimo 
    public static double leerDoubleMin(Scanner sc, String mensaje, double minimo) {
        double numero = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print(mensaje);
            try {
                String entrada = sc.nextLine().trim().replace(",", ".");
                numero = Double.parseDouble(entrada);
                if (numero > minimo) { 
                    valido = true;
                } else {
                    System.out.println("Error: El valor debe ser mayor a " + minimo + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número decimal (ej: 15.5).");
            }
        }
        return numero;
    }
    //Método para leer Strings
    public static String leerStringNoVacio(Scanner sc, String mensaje) {
        String texto = "";
        boolean valido = false;
        
        while (!valido) {
            System.out.print(mensaje);
            texto = sc.nextLine().trim(); // Leemos y quitamos espacios principio y final
            //Verificamos que no este vacio
            if (texto.isEmpty()) {
                System.out.println("Error: Este campo no puede quedar vacío. Por favor, ingrese un valor.");
            } else {
                valido = true;
            }
        }
        return texto;
    }
}
