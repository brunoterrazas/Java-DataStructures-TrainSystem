/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.estaticas;

/**
 *
 * @author Brunot
 */
public class Pila {

    private Object[] arreglo;
    private static final int TAMANIO = 10;

    private int tope;
//Constructor 

    public Pila() {
        this.arreglo = new Object[TAMANIO];
        this.tope = -1;
    }
//Observadores

    public Object obtenerTope() {
        /*Este modulo devuelve el elemento en el tope de la pila */
        Object top;
        if (!esVacia()) {
            top = this.arreglo[tope];

        } else {
            top = null;
        }
        return top;
    }

    @Override
    public String toString() {
        /*Este modulo devuelve una cadena de caracteres formada por todos
        los elementos de la pila para poder mostrar en pantalla*/
        String res, arre;
        res = "Pila{";
        arre = "";

        for (int i = 0; i <= this.tope; i++) {
            arre = arre + "[" + this.arreglo[i] + "]";
        }
        res = arre + res + "}";
        return res;
    }

    public boolean esVacia() {
        //Este modulo si la pila esta vacia(tope==-1) retorna true, sino false

        return this.tope == -1;
    }
//Modificadores

    public boolean apilar(Object nuevoElem) {
        /**
         * Este metodo pone el nuevo elemento en el tope de la pila
         */
        boolean exito;
        //si tope mayor o igual tamanio maximo
        if (this.tope + 1 >= this.TAMANIO) {
            exito = false;
            //Pila llena
        } else {//sino agrega nuevo elemento

            this.tope++;
            this.arreglo[tope] = nuevoElem;
            exito = true;
        }
        return exito;
    }

    public boolean desapilar() {
        /**
         * Saca el elemento del tope de la fila, retorna true si la Pila no
         * estaba vacia y pudo desapilar o en caso contrario falso
         */
        boolean exito;
        if (esVacia()) {
            exito = false;
            //Pila Vacia
        } else {
            this.arreglo[tope] = null;
            this.tope = this.tope - 1;
            exito = true;
        }
        return exito;

    }

//De aplicación
    public void vaciar() {
        //Este modulo saca todos los nodos de la pila
        for (int i = 0; i < this.tope; i++) {
            this.arreglo[i] = null;
        }
    }

//Propia de la clase
    public Pila clone() {
        /*
    Este modulo Devuelve una copia exacta de la estructura de la original y respetando
    el orden de los mismos en una estructura del mismo tipo.
         */
        Pila clon = new Pila();
        clon.tope = this.tope;
        int i = 0;
        while (i < this.tope) {
            clon.arreglo[i] = this.arreglo[i];
            i++;
        }
//copia superficialmente
        return clon;
    }

}
