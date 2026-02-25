/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package grafos;

/**
 *
 * @author Brunot
 */
public class NodoAdy {
  
     private NodoVert vertice;
     private double distancia;
     private NodoAdy sigAdyacente;
     public NodoAdy(NodoVert vert,double km,NodoAdy sigAdy)
     {
         vertice=vert;
         distancia=km;
         sigAdyacente=sigAdy;
     }

   

    public NodoVert getVertice() {
        return vertice;
    }

    public void setSigVertice(NodoVert vert) {
        this.vertice = vert;
    }

    public NodoAdy getSigAdyacente() {
        return sigAdyacente;
    }

    public void setSigAdyacente(NodoAdy sigAdy) {
        this.sigAdyacente = sigAdy;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
