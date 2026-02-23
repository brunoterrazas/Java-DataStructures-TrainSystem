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

public class TestGrafo {
    public static void main(String[] args) {
        Grafo trenDelValle = new Grafo();

        System.out.println("--- Cargando Estaciones ---");
        trenDelValle.insertarVertice("Neuquen");
        trenDelValle.insertarVertice("Cipolletti");
        trenDelValle.insertarVertice("Plottier");
        trenDelValle.insertarVertice("Senillosa");

        System.out.println("--- Conectando Vías (Arcos) ---");
        // Conectamos Neuquen con cipolleti y plottier
        trenDelValle.insertarArco("Neuquen", "Cipolletti");
        trenDelValle.insertarArco("Neuquen", "Plottier");
        
        // Conectamos Plottier con Senillosa
        trenDelValle.insertarArco("Plottier", "Senillosa");

        System.out.println("\n--- Estado del Grafo ---");
        System.out.println(trenDelValle.toString());

        System.out.println("\n--- Prueba de Recorrido en Profundidad (DFS) ---");
        // Debería listar todas las estaciones conectadas
        System.out.println("DFS: " + trenDelValle.listarEnProfundidad().toString());
    }
}