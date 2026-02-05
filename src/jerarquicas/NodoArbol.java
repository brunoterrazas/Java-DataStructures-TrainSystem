/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jerarquicas;

/**
 *
 * @author Brunot
 */
public class NodoArbol {
 private Object elem;
 private NodoArbol HI;
 private NodoArbol HD;
 
    public NodoArbol(Object elemento)
    {
    elem=elemento;
    HI=null;
    HD=null;
    }
    public NodoArbol(Object elemento, NodoArbol izq,NodoArbol der)
 {
    elem=elemento;
    HI=izq;
    HD=der;
 }

    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }

    public NodoArbol getHI() {
        return HI;
    }

    public void setHI(NodoArbol HI) {
        this.HI = HI;
    }

    public NodoArbol getHD() {
        return HD;
    }

    public void setHD(NodoArbol HD) {
        this.HD = HD;
    }
}
