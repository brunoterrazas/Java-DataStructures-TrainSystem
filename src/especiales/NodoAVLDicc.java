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
