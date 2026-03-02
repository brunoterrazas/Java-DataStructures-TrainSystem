/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema;

/**
 *
 * @author Brunot
 */
public class TestSistema {

    public static void main(String[] args) {
        TrenesSA sistema = new TrenesSA();

        System.out.println("=== Intentando cargar datos ===");
        // Asegúrate de que el archivo se llame igual
        sistema.cargarDatos("sistema.txt");

        System.out.println("\n=== Verificación de Carga ===");
        
        // 1. Verificar Estaciones (AVL)
        // Para esto, necesitamos un método en TrenesSA que nos permita ver el dicc
        System.out.println("Estado del Diccionario de Estaciones:");
        System.out.println(sistema.debugEstaciones());

        // 2. Verificar Grafo
        System.out.println("\nEstado del Mapa de Vías (Grafo):");
        System.out.println(sistema.debugGrafo());
    }
}
