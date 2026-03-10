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


import conjuntistas.ArbolAVL;
import lineales.dinamicas.Lista;

public class TestAVL {

    static String sOk = "OK!", sErr = "ERROR";

    public static void main(String[] args) {
        testAVL();
    }

    public static void testAVL() {
        System.out.println("*************** COMIENZO TEST ARBOL AVL ***************");
        ArbolAVL a = new ArbolAVL();

        System.out.println("Es vacio? espera TRUE: " + (a.esVacio() ? sOk : sErr));
        
        // --- TEST DE INSERCIÓN Y BALANCEO ---
        System.out.println("\n--- Test Insercion y Rotaciones ---");
        
        // Rotacion Simple a la Derecha (Insertando en orden descendente)
        System.out.println("Insertando 10, 8, 6 (Debe disparar rotacion simple derecha)...");
        a.insertar(10);
        a.insertar(8);
        a.insertar(6); 
        System.out.println("Estructura actual:\n" + a.toString());
        // El 8 deberia ser raiz

        // Rotacion Simple a la Izquierda
        System.out.println("Insertando 12, 14 (Debe disparar rotacion simple izquierda)...");
        a.insertar(12);
        a.insertar(14);
        System.out.println("Estructura actual:\n" + a.toString());

        // Rotacion Doble
        System.out.println("Insertando 11 (Debe disparar rotacion doble)...");
        a.insertar(11);
        System.out.println("Estructura actual:\n" + a.toString());

        // --- TEST DE BUSQUEDA Y MIN/MAX ---
        System.out.println("\n--- Test Consultas ---");
        System.out.println("Pertenece 8? espera TRUE: " + (a.pertenece(8) ? sOk : sErr));
        System.out.println("Pertenece 100? espera FALSE: " + (!a.pertenece(100) ? sOk : sErr));
        System.out.println("Minimo elemento? espera 6: " + (a.minimoElem().equals(6) ? sOk : sErr));
        System.out.println("Maximo elemento? espera 14: " + (a.maximoElem().equals(14) ? sOk : sErr));

        // --- TEST DE LISTADO Y RANGO ---
        System.out.println("\n--- Test Listados ---");
        System.out.println("Listar (Inorden): " + a.listar().toString());
        
        Lista rango = a.listarRango(7, 12);
        System.out.println("Listar Rango [7, 12] espera [8, 10, 11, 12]: " + rango.toString());

        // --- TEST DE ELIMINACIÓN ---
        System.out.println("\n--- Test Eliminacion ---");
        
        System.out.println("Eliminar hoja 6: " + (a.eliminar(6) ? sOk : sErr));
        System.out.println("Estructura tras eliminar 6:\n" + a.toString());

        System.out.println("Eliminar nodo con dos hijos (12): " + (a.eliminar(12) ? sOk : sErr));
        System.out.println("Estructura tras eliminar 12:\n" + a.toString());

        System.out.println("Eliminar raiz: " + (a.eliminar(a.maximoElem()) ? sOk : sErr));
        System.out.println("Estructura final:\n" + a.toString());

        a.vaciar();
        System.out.println("Vaciar arbol. Es vacio? espera TRUE: " + (a.esVacio() ? sOk : sErr));
    }
}