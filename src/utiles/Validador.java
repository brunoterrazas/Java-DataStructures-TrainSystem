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

    // Método  para leer numeros decimales
    public static double leerDouble(Scanner sc, String mensaje) {
        double numero = 0;
        boolean valido = false;
        
        while (!valido) {
            System.out.print(mensaje);
            try {
                // Reemplaza comas por puntos por si el usuario se equivoca al tipear
                String entrada = sc.nextLine().trim().replace(",", ".");
                numero = Double.parseDouble(entrada);
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número decimal (ej: 15.5).");
            }
        }
        return numero;
    }
}
