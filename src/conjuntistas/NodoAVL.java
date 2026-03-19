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
public class NodoAVL {

    private Comparable elem;
    private int altura;
    private NodoAVL HI;
    private NodoAVL HD;

    public NodoAVL(Comparable elemento) {
        elem = elemento;
        altura = 0;
        HI = null;
        HD = null;
    }

    public void recalcularAltura() {
         int altIzq = -1, altDer = -1;

        if (this.getHI() != null) {
            altIzq = this.getHI().getAltura();
        }

        if (this.getHD() != null) {
            altDer = this.getHD().getAltura();
        }
        //Verificamos altura maxima
        if (altIzq > altDer) {
            this.altura = altIzq+1;
        } else {
           this.altura = altDer+1;
        }
}

    public int getAltura() {
        return altura;
    }

   

    public Comparable getElem() {
        return elem;
    }

    public void setElem(Comparable elem) {
        this.elem = elem;
    }

    public NodoAVL getHI() {
        return HI;
    }

    public void setHI(NodoAVL HI) {
        this.HI = HI;
    }

    public NodoAVL getHD() {
        return HD;
    }

    public void setHD(NodoAVL HD) {
        this.HD = HD;
    }
}
