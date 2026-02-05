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
public class Cola {

    private Nodo frente;
    private Nodo fin;

    public Cola() {
        this.frente = null;
        this.fin = null;
    }

    public boolean poner(Object elemento) {
        //caso 1: cola vacia
        Nodo nuevo;
        nuevo = new Nodo(elemento);
        if (esVacia()) {
            this.frente = nuevo;
           
        } else {
            //caso 2: hay al menos un elemento

            this.fin.setEnlace(nuevo);

        }
         this.fin = nuevo;
        return true;
    }

    public boolean sacar() {
        boolean exito;
        if (!esVacia()) {// si hay al menos un elemento
            this.frente = this.frente.getEnlace();
            //si no hay nodo en el frente o queda vacia actualizamos el fin 
            if (this.frente == null) {
                this.fin = null;
            }

            exito = true;
        } else {
            exito = false;

        }
        return exito;
    }

    public boolean esVacia() {

        return this.frente == null && fin == null;
    }

    public Object obtenerFrente() {
        Object elemento;
        if (this.frente != null) {
            elemento = this.frente.getElem();
        } else {
            elemento = null;
        }
        return elemento;
    }

    public void vaciar() {
        this.frente = null;
        this.fin = null;
    }


    @Override
    public Cola clone() {
        Cola colaClon = new Cola();
        if (!esVacia()) {
            Nodo aux = this.frente;//nodo o puntero auxiliar de la cola original
            colaClon.frente = new Nodo(this.frente.getElem());
            Nodo auxClon=colaClon.frente;
            aux=aux.getEnlace();
            while(aux!=null)
            {
              auxClon.setEnlace(new Nodo(aux.getElem()));
              aux=aux.getEnlace();
              auxClon=auxClon.getEnlace();          
              
            }
             
                colaClon.fin=auxClon;
              
        }
        return colaClon;
    }
    /*
    @Override
    public String toString() {
        String str = "[";
       Nodo aux;
       aux=this.frente;
       while(aux!=null)
       {
           str+=aux.getElem().toString();
             if(aux.getEnlace()!=null)
           {
               str+=",";
           }
           aux=aux.getEnlace();
         
         
       }
       str += "]";
        return str;
    }*/
}
