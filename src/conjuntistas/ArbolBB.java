/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;

import jerarquicas.NodoArbol;

/**
 *
 * @author Brunot
 */
public class ArbolBB {

    private NodoABB raiz;

    public ArbolBB() {
        this.raiz = null;
    }

    public boolean insertar(Comparable elemento) {
        boolean exito = true;
        if (esVacio()) {//agrega el elemento nuevo
            this.raiz = new NodoABB(elemento);
        } else {
            exito = insertarAux(this.raiz, elemento);
        }
        return exito;
    }

    private boolean insertarAux(NodoABB n, Comparable elemento) {
        boolean exito = true;
        if (elemento.compareTo(n.getElem()) == 0) {
            exito = false;//Error elemento repetido
        } else if (elemento.compareTo(n.getElem()) < 0) {
            //si el elemento es menor avanza al subarbol izquierdo
            if (n.getHI() != null) {//si tiene HI
                exito = insertarAux(n.getHI(), elemento);
            } else {
                //sino lo agrega como hijo izquierdo
                n.setHI(new NodoABB(elemento));
            }
        } else if (n.getHD() != null) {//si el elemento es mayor avanza al subarbol derecho
               //si tiene HD
            exito = insertarAux(n.getHD(), elemento);
        } else {
            n.setHD(new NodoABB(elemento));
            //sino lo agrega como hijo derecho
        }
        return exito;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }
}
