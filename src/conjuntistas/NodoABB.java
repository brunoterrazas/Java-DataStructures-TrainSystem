/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package conjuntistas;

/**
 *
 * @author Brunot
 */
public class NodoABB  {
 private Comparable elem;
 private NodoABB HI;
 private NodoABB HD;
 
    public NodoABB(Comparable elemento)
    {
    elem=elemento;
    HI=null;
    HD=null;
    }
    public NodoABB(Comparable elemento, NodoABB izq,NodoABB der)
 {
    elem=elemento;
    HI=izq;
    HD=der;
 }

    public Comparable getElem() {
        return elem;
    }

    public void setElem(Comparable elem) {
        this.elem = elem;
    }

    public NodoABB getHI() {
        return HI;
    }

    public void setHI(NodoABB HI) {
        this.HI = HI;
    }

    public NodoABB getHD() {
        return HD;
    }

    public void setHD(NodoABB HD) {
        this.HD = HD;
    }
}