/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.dinamicas;

/**
 *
 * @author Brunot
 */
public class Pila {

    private Nodo tope;

    public Pila() {
        this.tope = null;
    }

    public boolean apilar(Object elem) {
        Nodo nuevo = new Nodo(elem, this.tope);
        this.tope = nuevo;
        return true;
    }

    public boolean desapilar() {
        boolean exito;
        exito = false;
        if (!this.esVacia()) {
            this.tope = this.tope.getEnlace();
            exito = true;
        }
        return exito;
    }

    public Object obtenerTope() {
        Object elemento;
        if(this.tope!=null)
        {
         elemento=this.tope.getElem();
        }
        else{
        elemento=null;
        }
        return elemento;
    }

    public boolean esVacia() {
        return this.tope == null;
    }

    public void vaciar() {
        this.tope = null;
    }

    public Pila clone() {
        Pila pilaClon = new Pila();
        if (!this.esVacia()) {

            Nodo aux = this.tope;
            //clonamos el primer elemento 
            pilaClon.tope = new Nodo(aux.getElem());
            //si hay otro elemento
            if (aux.getEnlace() != null) {
                aux = aux.getEnlace();
            }
            while (aux != null) {
                pilaClon.tope = new Nodo(aux.getElem(), pilaClon.tope);
                if (aux.getEnlace() != null) {
                    aux = aux.getEnlace();
                }
            }
        }
        return pilaClon;
    }
  public Pila cloneR() {
        Pila copia = new Pila();
        // Le paso el primer nodo 
        copia.tope = cloneAux(this.tope); 
        return copia;
    }
  private Nodo cloneAux(Nodo nodoActual)
  {
  // A. CASO BASE 
        if (nodoActual == null) {
            return null; // Si llegué al final, devuelvo null
        }

        // B. PASO RECURSIVO (La Descomposición)
        // Primero clono "el resto de la cadena" (voy hasta el fondo)
        Nodo enlaceClonado = cloneAux(nodoActual.getEnlace());

        // C. COMPOSICIÓN 
        // Creo el nodo nuevo usando el elemento actual y enganchándolo 
        // a lo que me devolvió la recursión (el resto ya clonado)
        Nodo nuevoNodo = new Nodo(nodoActual.getElem(), enlaceClonado);
        
        return nuevoNodo;
  }
  /*
    @Override
    public String toString() {
        String str = "";
        if (esVacia()) {
            str = "Pila Vacia";
        } else {
            Nodo aux = this.tope;
            str = "[";

            while (aux != null) {
                str += aux.getElem().toString();
                aux = aux.getEnlace();
                if (aux != null) {
                    str += ",";
                }
            }
        }
        str += "]";
        return str;
    }
*/
}
