/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package especiales;

/**
 *
 * @author Brunot
 */
public class NodoAVLDicc {

    private Comparable clave;
    private Object dato;

    private int altura;
    private NodoAVLDicc HI;
    private NodoAVLDicc HD;

    public NodoAVLDicc(Comparable elemento, Object unDato) {
        clave = elemento;
        dato = unDato;
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

    private int alturaAux(NodoAVLDicc n) {
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

    public Comparable getClave() {
        return clave;
    }

    public void setClave(Comparable clave) {
        this.clave = clave;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public NodoAVLDicc getHI() {
        return HI;
    }

    public void setHI(NodoAVLDicc HI) {
        this.HI = HI;
    }

    public NodoAVLDicc getHD() {
        return HD;
    }

    public void setHD(NodoAVLDicc HD) {
        this.HD = HD;
    }
}
