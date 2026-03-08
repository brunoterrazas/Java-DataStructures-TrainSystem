/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tp.pkgfinal.edd;

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
        System.out.println("\n--- 5. Camino con menos KM (A -> D) ---");
        // [A, B, C, D] (15 km)
                Lista resKM = tren.caminoMasCortoKm("A", "D");
        System.out.println("Ruta elegida: " + resKM.toString());
   }
}