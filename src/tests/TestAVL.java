package tests;

import conjuntistas.ArbolAVL;

/**
 *
 * @author Bruno
 */
public class TestAVL {

    static String sOk = "OK!", sErr = "ERROR";

    public static void main(String[] args) {
        testRotaciones();
        testEliminacionBasica();
        testEliminacionAvanzada(); 
    }

    public static void testRotaciones() {
        System.out.println("=============== 1. TEST DE ROTACIONES ===============");
        ArbolAVL a = new ArbolAVL();

        System.out.println("\n--- Rotacion Simple a DERECHA ---");
        System.out.println("Insertamos: 28, 21, 15");
        a.insertar(28); a.insertar(21); a.insertar(15);
        System.out.println(a.toString());
        System.out.println("-> Padre (28) e hijo (21) caen hacia la izquierda (+).");
        System.out.println("-> Aplica Rotacion Simple a Derecha. Raiz esperada: 21.");
        a.vaciar();

        System.out.println("\n--- Rotacion Simple a IZQUIERDA ---");
        System.out.println("Insertamos: 19, 23, 26");
        a.insertar(19); a.insertar(23); a.insertar(26);
        System.out.println(a.toString());
        System.out.println("-> Padre (19) e hijo (23) caen hacia la derecha (-).");
        System.out.println("-> Aplica Rotacion Simple a Izquierda. Raiz esperada: 23.");
        a.vaciar();

        System.out.println("\n--- Rotacion Doble IZQUIERDA-DERECHA ---");
        System.out.println("Insertamos: 28, 21, 26");
        a.insertar(28); a.insertar(21); a.insertar(26);
        System.out.println(a.toString());
        System.out.println("-> Padre (28) caido a la izq (+), hijo (21) caido a la der (-).");
        System.out.println("-> Aplica Rotacion Doble Izquierda-Derecha. Raiz esperada: 26.");
        a.vaciar();

        System.out.println("\n--- Rotacion Doble DERECHA-IZQUIERDA ---");
        System.out.println("Insertamos: 17, 21, 19");
        a.insertar(17); a.insertar(21); a.insertar(19);
        System.out.println(a.toString());
        System.out.println("-> Padre (17) caido a la der (-), hijo (21) caido a la izq (+).");
        System.out.println("-> Aplica Rotacion Doble Derecha-Izquierda. Raiz esperada: 19.");
        a.vaciar();
    }

    public static void testEliminacionBasica() {
        System.out.println("\n=============== 2. TEST DE ELIMINACION ===============");
        ArbolAVL a = new ArbolAVL();

        System.out.println("Armando el arbol inicial (Raiz 15)...");
        a.insertar(15); a.insertar(9); a.insertar(50); a.insertar(4);
        a.insertar(12); a.insertar(24); a.insertar(57); a.insertar(3);
        a.insertar(7); a.insertar(27); a.insertar(53); a.insertar(67);

        System.out.println("\n--- Eliminar nodo HOJA (12) ---");
        a.eliminar(12);
        System.out.println(a.toString());
        System.out.println("-> Esperado: El 9 pierde su hijo derecho.");

        System.out.println("\n--- Eliminar nodo con UN HIJO (24) ---");
        a.eliminar(24);
        System.out.println(a.toString());
        System.out.println("-> Esperado: El 50 puentea al 24 y se engancha con el 27.");

        System.out.println("\n--- Eliminar nodo con DOS HIJOS (Raiz 15) ---");
        a.eliminar(15);
        System.out.println(a.toString());
        System.out.println("-> Esperado: Se busca el candidato (27) para reemplazar la raiz.");
    }

    public static void testEliminacionAvanzada() {
        System.out.println("\n=============== 3. TEST DE CASOS ESPECIALES DE BALANCEO ===============");
        ArbolAVL a = new ArbolAVL();

        System.out.println("\n--- Caso A: Eliminacion donde el hijo tiene balance 0 ---");
        a.insertar(30); a.insertar(20); a.insertar(40); 
        a.insertar(10); a.insertar(25);
        
        System.out.println("Arbol original balanceado (Raiz 30, Altura 2):");
        System.out.println(a.toString());
        
        System.out.println("\nEliminamos el 40.");
        System.out.println("-> La raiz (30) queda desbalanceada. Su hijo (20) tiene balance 0.");
        System.out.println("-> Aplica Rotacion Simple a Derecha.");
        a.eliminar(40);
        
        System.out.println("\nResultado:");
        System.out.println(a.toString());
        System.out.println("-> Nueva raiz esperada: 20 (Altura 2).");
        a.vaciar();

        System.out.println("\n--- Caso B: Rotacion Doble provocada por eliminacion ---");
        a.insertar(50); a.insertar(20); a.insertar(80); 
        a.insertar(10); a.insertar(30); a.insertar(90); a.insertar(25);
        
        System.out.println("Arbol original balanceado:");
        System.out.println(a.toString());
        
        System.out.println("\nEliminamos el 90.");
        System.out.println("-> La raiz (50) pierde peso derecho (+).");
        System.out.println("-> Su hijo izq (20) esta caido a la derecha (-).");
        System.out.println("-> Aplica Rotacion Doble Izquierda-Derecha.");
        a.eliminar(90);
        
        System.out.println("\nResultado:");
        System.out.println(a.toString());
        System.out.println("-> Nueva raiz esperada: 30 (Altura 2).");
        a.vaciar();

        System.out.println("\n--- Caso C: Inserciones secuenciales (1 al 7) ---");
        for (int i = 1; i <= 7; i++) {
            a.insertar(i);
        }
        System.out.println("\nResultado:");
        System.out.println(a.toString());
        System.out.println("-> El arbol se auto-balancea para evitar formar una lista.");
        System.out.println("-> Raiz esperada: 4 (Altura 2).");
    }
}