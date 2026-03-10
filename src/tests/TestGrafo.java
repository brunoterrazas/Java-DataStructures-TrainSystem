/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

/**
 *
 * @author Brunot
 */
import grafos.Grafo;
import lineales.dinamicas.Lista;

public class TestGrafo {

    public static void main(String[] args) {
        Grafo tren = new Grafo();

        System.out.println("--- Cargando Estaciones (Nodos) ---");
        tren.insertarVertice("A");
        tren.insertarVertice("B");
        tren.insertarVertice("C");
        tren.insertarVertice("D");
        tren.insertarVertice("E");

        System.out.println("--- Conectando Vías (Arcos con KM) ---");
        // Ruta 1 (Larga en estaciones): A -> B -> C -> D 
        tren.insertarArco("A", "B", 5);
        tren.insertarArco("B", "C", 5);
        tren.insertarArco("C", "D", 5);

        // Ruta 2 (Corta en estaciones): A -> E -> D 
        tren.insertarArco("A", "E", 50);
        tren.insertarArco("E", "D", 50);

        System.out.println("\n--- Estado del Grafo ---");
        System.out.println(tren.toString());

        System.out.println("\n--- Camino con menos estaciones (A -> D) ---");
        Lista rutaEstaciones = tren.caminoMasCorto("A", "D");
        System.out.println("Ruta mas corta (estaciones): " + rutaEstaciones.toString());
        System.out.println("Cantidad de estaciones: " + rutaEstaciones.getLongitud());
        System.out.println("\n--- Camino con menos KM (A -> D) ---");
        // [A, B, C, D] (15 km)
        Lista resKM = tren.caminoMasCortoKm("A", "D");
        System.out.println("Ruta elegida: " + resKM.toString());
        System.out.println("> De 'A' a 'C' con Max 10km.");
        System.out.println("  Esperado: true  : " + tren.existeCaminoConDistanciaMaxima("A", "C", 10));

        System.out.println("\n> De 'A' a 'D' con Max 10km.");
        System.out.println("  Esperado: false : " + tren.existeCaminoConDistanciaMaxima("A", "D", 10));

        System.out.println("\n> De 'A' a 'D' con Max 20km.");
        System.out.println("  Esperado: true  : " + tren.existeCaminoConDistanciaMaxima("A", "D", 20));
        
        
        System.out.println("\n--- Todos los caminos de A a D sin pasar por una estación especifica ---");
        
        // Agregamos un arco de B a D para que existan múltiples caminos sin pasar por C
        tren.insertarArco("B", "D", 15); 
        System.out.println("Se agregó vía B");

        Lista caminosSinC = tren.listarCaminosQueNoPasanPorUnaEstacion("A", "D", "C");
        
        System.out.println("> Esperado: Al menos dos caminos, ej: [A, B, D] y [A, E, D]");
        
        if (caminosSinC.esVacia()) {
            System.out.println("Obtenido: No se encontraron caminos.");
        } else {
            // Como es una Lista de Listas, el toString() va a imprimir los corchetes anidados automáticamente
            System.out.println("Obtenido: " + caminosSinC.toString());
        }
        
        //Si la estación prohibida es la de origen
        System.out.println("\n> De 'A' a 'D' sin pasar por 'A' (Debería dar lista vacía)");
        Lista caminoInvalido = tren.listarCaminosQueNoPasanPorUnaEstacion("A", "D", "A");
        System.out.println("Obtenido: " + caminoInvalido.toString());

    }
}
