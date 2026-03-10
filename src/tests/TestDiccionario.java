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


import especiales.Diccionario;
import especiales.NodoAVLDicc; // O la clase que uses para los datos, ej: Estacion
import lineales.dinamicas.Lista;

public class TestDiccionario {

    static String sOk = "OK!", sErr = "ERROR";

    public static void main(String[] args) {
        testDiccionario();
    }

    public static void testDiccionario() {
        System.out.println("*************** COMIENZO TEST TDA DICCIONARIO (AVL) ***************");
        Diccionario dicc = new Diccionario();

        // --- TEST DE INSERCIÓN (Clave, Dato) ---
        System.out.println("\n--- Test Inserción ---");
        // Insertamos usando Strings como claves (como serían los nombres de estaciones)
        System.out.print("Inserta ('B', 'Dato B') espera TRUE: " + (dicc.insertar("B", "Dato B") ? sOk : sErr));
        System.out.println("\t--> " + dicc.toString());
        
        System.out.print("Inserta ('A', 'Dato A') espera TRUE: " + (dicc.insertar("A", "Dato A") ? sOk : sErr));
        System.out.println("\t--> " + dicc.toString());
        
        // Aquí debería ocurrir una rotación si insertamos 'C' (caído a la derecha) o algo similar
        System.out.print("Inserta ('C', 'Dato C') espera TRUE: " + (dicc.insertar("C", "Dato C") ? sOk : sErr));
        System.out.println("\t--> " + dicc.toString());

        System.out.print("Inserta clave duplicada ('B', 'Nuevo') espera FALSE: " + (!dicc.insertar("B", "Nuevo") ? sOk : sErr));

        // --- TEST DE BÚSQUEDA (obtenerDato) ---
        System.out.println("\n--- Test obtenerDato (Búsqueda) ---");
        System.out.println("Busca 'B': espera 'Dato B', retorna '" + dicc.obtenerDato("B") + "': " + (dicc.obtenerDato("B").equals("Dato B") ? sOk : sErr));
        System.out.println("Busca 'A': espera 'Dato A', retorna '" + dicc.obtenerDato("A") + "': " + (dicc.obtenerDato("A").equals("Dato A") ? sOk : sErr));
        System.out.println("Busca 'Z' (no existe): espera null, retorna " + dicc.obtenerDato("Z") + ": " + (dicc.obtenerDato("Z") == null ? sOk : sErr));

        // --- TEST DE EXISTENCIA ---
        System.out.println("\n--- Test existeClave ---");
        System.out.println("Existe 'C'? espera TRUE: " + (dicc.existeClave("C") ? sOk : sErr));
        System.out.println("Existe 'W'? espera FALSE: " + (!dicc.existeClave("W") ? sOk : sErr));

        // --- TEST DE LISTADO ---
        System.out.println("\n--- Test Listados ---");
        Lista claves = dicc.listarLlaves();
        System.out.println("Listar llaves (orden alfabetico): " + claves.toString());
        
        Lista datos = dicc.listarDatos();
        System.out.println("Listar datos (asociados): " + datos.toString());

        // --- TEST DE ELIMINACIÓN ---
        System.out.println("\n--- Test Eliminación ---");
        System.out.print("Elimina 'A' (Hoja) espera TRUE: " + (dicc.eliminar("A") ? sOk : sErr));
        System.out.println("\t--> " + dicc.toString());
        
        System.out.print("Elimina 'B' (Raiz) espera TRUE: " + (dicc.eliminar("B") ? sOk : sErr));
        System.out.println("\t--> " + dicc.toString());

        // --- TEST FINAL ---
        System.out.println("\n--- Estado Final ---");
        System.out.println("Es vacio? espera FALSE: " + (!dicc.esVacio() ? sOk : sErr));
        dicc.vaciar();
        System.out.println("Vaciamos el diccionario. Es vacio? espera TRUE: " + (dicc.esVacio() ? sOk : sErr));
    }
}
