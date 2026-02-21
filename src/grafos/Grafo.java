/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import lineales.dinamicas.Lista;

/**
 *
 * @author Brunot
 */
public class Grafo {

    private NodoVert inicio;

    public Grafo() {
        inicio = null;
    }

    public boolean insertarVertice(Object nuevoVertice) {
        boolean exito = false;
        NodoVert aux = this.ubicarVertice(nuevoVertice);
        if (aux == null) {
            this.inicio = new NodoVert(nuevoVertice, this.inicio, null);
            exito = true;
        }

        return exito;

    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }
    public boolean insertarArco(Object origen, Object destino) {
    boolean exito = false;
    //recorremos la lista de vertices
    // localizamos ambos nodos vertices 
    NodoVert auxOri = ubicarVertice(origen);
    NodoVert auxDes = ubicarVertice(destino);
     //si ambos vertices existen
    if (auxOri != null && auxDes != null) {
        //insertamos en la lista de adyacentes de Urigen
        // Se inserta al principio de la lista de adyacencia del nodo origen
        auxOri.setPrimerAdy(new NodoAdy( auxDes,auxOri.getPrimerAdy()));

        //como es grafo insertamos tambien en destino
        auxDes.setPrimerAdy(new NodoAdy( auxOri,auxDes.getPrimerAdy()));
        
        exito = true;
    }
    return exito;
}
    public Lista listarEnProfundidad()
    {
      Lista visitados=new Lista();
      //define un vertice donde comenzar a recorrer
      NodoVert aux=this.inicio;
      while(aux!=null)
      {
        if(visitados.localizar(aux.getElem())<0)
        { //si el vertice no fue visitado aun, avanza en profundidad
          listarEnProfundidadAux(aux,visitados);
        }
        aux=aux.getSigVertice();
      }
      
      return visitados;
    }
    private void listarEnProfundidadAux(NodoVert n,Lista vis)
    {
      if(n!=null)
      {//marca al vertice n como visitado
        vis.insertar(n.getElem(), vis.getLongitud()+1);
        NodoAdy ady=n.getPrimerAdy();
        while(ady!=null)
        {//visita en profundidad los adyacentes de n aun no visitados
          if(vis.localizar(ady.getVertice().getElem())<0)
          {
            listarEnProfundidadAux(ady.getVertice(),vis);
          }
          ady=ady.getSigAdyacente();
        }
        
      }
    
    }
}
