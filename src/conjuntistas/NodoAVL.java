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
        altura = alturaAux(this);
        HI = null;
        HD = null;
    }

    public void recalcularAltura() {
        altura = alturaAux(this);
    }

    public int getAltura() {
        return altura;
    }

    private int alturaAux(NodoAVL n) {
        int altMaxima = -1;
        if (n != null) {
            int altDer, altIzq;
            altIzq = alturaAux(n.getHI());
            altDer = alturaAux(n.getHD());
            if (altDer > altIzq) {
                altMaxima = altDer + 1;
            } else {
                altMaxima = altIzq + 1;
            }
        }

        return altMaxima;
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
