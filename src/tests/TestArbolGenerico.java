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


import jerarquicas.ArbolGen;
import lineales.dinamicas.Lista;

public class TestArbolGenerico {

    static String sOk = "OK!", sErr = "ERROR";

    public static void main(String[] args) {
        testArbolGen();
    }

    public static void testArbolGen() {
        System.out.println("*************** COMIENZO TEST ÁRBOL GENÉRICO ***************");
        ArbolGen a = new ArbolGen();

       
        // Estructura: 10 -> 20, 30, 40 | 20 -> 50, 60 | 30 -> 70
        /*
                            10
                -> 20      ->30      ->40
                 ->50->60   ->70
                  
                10
           20       30       40
        50  60     70
        
        
        */
        
        System.out.println("Construyendo árbol...");
        a.insertar(10, null); // Raíz
        a.insertar(20, 10);
        a.insertar(30, 10);
        a.insertar(40, 10);
        a.insertar(50, 20);
        a.insertar(60, 20);
        a.insertar(70, 30);

        System.out.println("Estructura esperada:\n10 -> 20, 30, 40\n20 -> 50, 60\n30 -> 70");
        System.out.println("Estructura real:\n" + a.toString());

      
        System.out.println("\n--- Test Propiedades ---");
        System.out.println("Altura (espera 2): " + a.altura() + " -> " + (a.altura() == 2 ? sOk : sErr));
        System.out.println("Nivel de 70 (espera 2): " + a.nivel(70) + " -> " + (a.nivel(70) == 2 ? sOk : sErr));
        System.out.println("Padre de 60 (espera 20): " + a.padre(60) + " -> " + (a.padre(60).equals(20) ? sOk : sErr));
        System.out.println("Pertenece 50? (espera TRUE): " + (a.pertenece(50) ? sOk : sOk));
        System.out.println("Pertenece 100? (espera FALSE): " + (!a.pertenece(100) ? sOk : sErr));

  
        System.out.println("\n--- Test Recorridos ---");
        System.out.println("PreOrden: " + a.listarPreOrden().toString());
        System.out.println("Por Niveles: " + a.listarPorNiveles().toString());

    
        System.out.println("\n--- Test Ancestros ---");
        Lista anc = a.ancestros(70); // Debería ser [30, 10]
        System.out.println("Ancestros de 70 (espera [30, 10]): " + anc.toString());

   
        System.out.println("\n--- Test Justifica Altura ---");
        Lista cam = a.listaQueJusticaAltura(); 
        // Puede ser [10, 20, 50], [10, 20, 60] o [10, 30, 70]
        System.out.println("Camino más largo: " + cam.toString());

        // --- VACIAMIENTO ---
        a.vaciar();
        System.out.println("\n¿Está vacío después de vaciar()? " + (a.esVacio() ? sOk : sErr));
    }
}
