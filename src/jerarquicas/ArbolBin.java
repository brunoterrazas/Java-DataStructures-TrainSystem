/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import lineales.dinamicas.Nodo;

/**
 *
 * @author Brunot
 */
public class ArbolBin {

    private NodoArbol raiz;

    public ArbolBin() {
        this.raiz = null;
    }

    public boolean insertar(Object elemNuevo, Object elemPadre, char lugar) {
        boolean exito = true;
        if (this.esVacio()) {
            this.raiz = new NodoArbol(elemNuevo);
        } else {
            //si el arbol no esta vacio busca al nodo padre
            NodoArbol nPadre = obtenerNodo(this.raiz, elemPadre);
            if (nPadre != null) {
                //si el nodo padre existe y el lugar no esta ocupado lo pone como hijo, sino da error
                if (lugar == 'I' && nPadre.getHI() == null) {
                    nPadre.setHI(new NodoArbol(elemNuevo));
                } else if (lugar == 'D' && nPadre.getHD() == null) {
                    nPadre.setHD(new NodoArbol(elemNuevo));
                } else {

                    exito = false;
                }

            } else {
                exito = false;
            }

        }
        return exito;
    }

    private NodoArbol obtenerNodo(NodoArbol n, Object buscado) {
        NodoArbol resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                //si el elemento buscado es n, lo devuelve
                resultado = n;
            } else {
                //sino busca primero en el hijo izquierdo
                if (n.getHI() != null) {
                    resultado = obtenerNodo(n.getHI(), buscado);
                }

                if (resultado == null && n.getHD() != null) {
                    //si no lo encuentra, busca en el hijo derecho
                   resultado = obtenerNodo(n.getHD(), buscado);
                }
            }
        }
            return resultado;
        }

    

    public Object padre(Object elem) {
        //este metodo retorna el padre del elemento buscado
        Object elemPadre = null;
        //verifica que el arbol no vacio y que el elemento no sea del nodo raiz
        if (!esVacio() && !(this.raiz.getElem().equals(elem))) {
            elemPadre = padreAux(this.raiz, elem);
        }
        return elemPadre;
    }

    private Object padreAux(NodoArbol n, Object elemHijo) {
        Object padre;

        padre = null;
        if (n != null) {//verifica el hijo izquierdo
            if (n.getHI() != null) {
                if (n.getHI().getElem().equals(elemHijo)) {
                    padre = n.getElem();
                } else {
                    padre = padreAux(n.getHI(), elemHijo);
                }
            }  //si no encontró al padre
            if (padre == null && n.getHD() != null) {//verifica el hijo derecho
                if (n.getHD().getElem().equals(elemHijo)) {
                    padre = n.getElem();
                } else {
                    padre = padreAux(n.getHD(), elemHijo);
                }
            }

        }
        return padre;
    }
  public int nivel(Object elemento){
        int res;
        res = nivelAux(raiz,elemento);
        return res;
    }
   private int nivelAux(NodoArbol n, Object elemento) {
        int res = -1;

    if (n != null) {
        
        if (n.getElem().equals(elemento)) {
            res = 0;
        } else {
            // buscamos por la izquierda
            res = nivelAux(n.getHI(), elemento);
            
            // Si lo encontré a la izquierda (res > -1), le sumo 1 
            if (res > -1) {
                res++; 
            } else {
                //Si no estaba a la izquierda, busco a la DERECHA
                res = nivelAux(n.getHD(), elemento);
                
                // Si lo encontré a la derecha, le sumo 1
                if (res > -1) {
                    res++;
                }
            }
        }
    }
    
    return res;
}
    public int altura (){
        int altura;
        altura = alturaAux(raiz);
        return altura;
    }
    private int alturaAux (NodoArbol n){
         
        int res;
        res = -1;//La altura de un arbol vacio es -1
       
        //La altura de un arbol no vacio es 1 + la altura mayor entre sus subarboles izquierdo y derecho
        if (n != null){
            int altDer, altIz;
            altIz = alturaAux(n.getHI());
            altDer =alturaAux(n.getHD());
            if (altDer > altIz){
                res = altDer;
            }
            else{
                res = altIz;
            }
            res++;
        }
        return res;
    }

    public Lista listarPreorden() { //retorna una lista con los elementos del arbol en preorden
        Lista lis = new Lista();
        listarPreordenAux(this.raiz, lis);
        return lis;
    }

    private void listarPreordenAux(NodoArbol nodo, Lista lis) {
        if (nodo != null) { //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.getLongitud() + 1);
            //recorre a sus hijo en preorden
            listarPreordenAux(nodo.getHI(), lis);
            listarPreordenAux(nodo.getHD(), lis);
        }
    }

    public Lista listarInorden() { //retorna una lista con los elementos del arbol en inorden
        Lista lis = new Lista();
        listarInordenAux(this.raiz, lis);
        return lis;
    }

    private void listarInordenAux(NodoArbol nodo, Lista lis) {
        if (nodo != null) {
            //recorre a sus hijo izquierdo
            listarPreordenAux(nodo.getHI(), lis);
            //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.getLongitud() + 1);
            //recorre a sus hijo derecho
            listarPreordenAux(nodo.getHD(), lis);
        }
    }

    public Lista listarPosorden() { //retorna una lista con los elementos del arbol en posorden
        Lista lis = new Lista();
        listarPosordenAux(this.raiz, lis);
        return lis;
    }

    private void listarPosordenAux(NodoArbol nodo, Lista lis) {
        if (nodo != null) {
            //recorre a sus hijo izquierdo
            listarPreordenAux(nodo.getHI(), lis);
            //recorre a sus hijo derecho
            listarPreordenAux(nodo.getHD(), lis);
            //visita el elemento en el nodo
            lis.insertar(nodo.getElem(), lis.getLongitud() + 1);
        }
    }

    public Lista listarNiveles() {
        Cola Q = new Cola();
        Lista lis = new Lista();
        NodoArbol nodoActual;
        Q.poner(this.raiz);
        while (!Q.esVacia()) {
            nodoActual = new NodoArbol(Q.obtenerFrente());
            Q.sacar();
            lis.insertar(nodoActual, lis.getLongitud() + 1);
            if (nodoActual.getHI() != null) {
                Q.poner(nodoActual.getHI());
            }
            if (nodoActual.getHD() != null) {

                Q.poner(nodoActual.getHD());
            }

        }
        return lis;
    }

    @Override
    public ArbolBin clone() {
        ArbolBin copia = new ArbolBin();
        if (!esVacio()) {
            copia.raiz = cloneAux(this.raiz);
        }
        return copia;
    }

    private NodoArbol cloneAux(NodoArbol n) {
        NodoArbol nuevo = new NodoArbol(n.getElem());
        if (n.getHI() != null) {
            nuevo.setHI(cloneAux(n.getHI()));
        }
        if (n.getHD() != null) {
            nuevo.setHD(cloneAux(n.getHD()));
        }
        return nuevo;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }

}
