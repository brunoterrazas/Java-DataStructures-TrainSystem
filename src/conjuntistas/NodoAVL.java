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

    public NodoAVL(Comparable elemento, NodoAVL izq, NodoAVL der) {
        elem = elemento;
        HI = izq;
        altura = alturaAux(this);
        HD = der;
    }
    private int alturaAux(NodoAVL n)
    {
        int altura=-1;
        if(n!=null)
        {
        int altDer,altIzq;
          altIzq=alturaAux(n.getHI());
        altDer =alturaAux(n.getHD());
            if (altDer > altIzq){
                altura = altDer;
            }
            else{
                altura = altIzq;
            }
            altura++;
        }
        
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
