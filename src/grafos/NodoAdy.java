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
     private NodoAdy sigAdyacente;
     public NodoAdy(NodoVert vert,NodoAdy sigAdy)
     {
         vertice=vert;
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
}
