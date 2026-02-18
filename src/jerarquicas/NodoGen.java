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
public class NodoGen {
  private NodoGen HEI;
  private NodoGen HD;
  private Object elem;
  public NodoGen(Object elemento, NodoGen izq, NodoGen der)
  {
    elem=elemento;
    HEI=izq;
    HD=der;
  }

    public NodoGen getHEI() {
        return HEI;
    }

    public void setHEI(NodoGen HEI) {
        this.HEI = HEI;
    }

    public NodoGen getHD() {
        return HD;
    }

    public void setHD(NodoGen HD) {
        this.HD = HD;
    }

    public Object getElem() {
        return elem;
    }

    public void setElem(Object elem) {
        this.elem = elem;
    }
}
