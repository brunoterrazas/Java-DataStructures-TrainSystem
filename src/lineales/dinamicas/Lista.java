/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lineales.dinamicas;

/**
 *
 * @author Brunot
 */
public class Lista {

    private Nodo cabecera;
    private int longitud;

    public Lista() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public boolean insertar(Object nuevoElem, int pos) { //inserta el elemento nuevo en la posicion pos
        boolean exito = true;
        if (esPosicionInvalida(pos)) {//error posicion invalida
            exito = false;
        } else {
            //caso 1, primer posicion
            if (pos == 1) {
                this.cabecera = new Nodo(nuevoElem, this.cabecera);
            } else {//avanza hasta el elemento en posicion -1 lugar
                int i = 1;
                Nodo aux = this.cabecera;
                while (i < pos - 1) {
                    aux = aux.getEnlace();
                    i++;
                }
                //crea el nodo y lo enlaza
                Nodo nuevo = new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);

            }
            this.longitud++;
        }

        return exito;
    }

    public boolean eliminar(int pos) {
        boolean exito;
        if (esVacia()) {
            exito = false;
        } else {
            if (!esPosicionInvalida(pos)) {

                if (pos == 1) {
                    this.cabecera = this.cabecera.getEnlace();
                } else {
                    int i = 1;
                    Nodo aux = this.cabecera;
                    while (i < pos - 1) {
                        // if(aux!=null)
                        aux = aux.getEnlace();

                        i++;
                    }
                    aux.setEnlace(aux.getEnlace().getEnlace());

                }
                this.longitud--;
                exito = true;
            } else {
                exito = false;
            }
        }
        return exito;
    }

    public int longitud() {
        return longitud;
    }

    public void vaciar() {
        this.cabecera = null;
        this.longitud = 0;
    }

    public Object recuperar(int pos) {
        Object buscado = null;

        if (pos >= 1 && pos <= this.longitud) {
            Nodo aux = this.cabecera;

            int posicionActual = 1;
            while (posicionActual < pos) {

                aux = aux.getEnlace();

                posicionActual++;
            }
            buscado = aux.getElem();

        }

        return buscado;
    }

    public int localizar(Object elem) {
        int res, i;
        Nodo auxiliar;
        boolean encontrado;
        encontrado = true;
        auxiliar = cabecera;
        i = 1;
        res = -1;
        while (auxiliar != null && encontrado) {
            if (auxiliar.getElem().equals(elem)) {
                encontrado = false;
                res = i;
            }
            auxiliar = auxiliar.getEnlace();
            i++;
        }
        return res;
    }

    public boolean esVacia() {

        return this.cabecera == null;

    }

    @Override
    public String toString() {
        String str = "[";
        Nodo aux = this.cabecera;

        if (!esVacia()) {
            while (aux != null) {
                str = str + aux.getElem().toString();
                if (aux.getEnlace() != null) {
                    str += ",";
                }
                aux = aux.getEnlace();
            }
        }
        str += "]";
        return str;
    }

    @Override
    public Lista clone() {
        Lista copiaLista = new Lista();
        if (!esVacia()) {
            Nodo aux = this.cabecera;
            copiaLista.cabecera = new Nodo(aux.getElem());
            copiaLista.longitud++;
            aux = aux.getEnlace();
            Nodo auxClon = copiaLista.cabecera;
            while (aux != null) {
                auxClon.setEnlace(new Nodo(aux.getElem()));
                copiaLista.longitud++;
                auxClon = auxClon.getEnlace();
                aux = aux.getEnlace();
            }
        }

        return copiaLista;
    }

    private boolean esPosicionInvalida(int pos) {
        return pos < 1 || pos > this.longitud + 1;
    }

    public int getLongitud() {
        return longitud;
    }

  
    
    public void invertir() {
        if (!this.esVacia()) {
            this.cabecera = invertirAux(this.cabecera);
        }
    }

    private Nodo invertirAux(Nodo nodoAux) {
        Nodo nuevaCabecera;
        if (nodoAux.getEnlace() == null) {
            nuevaCabecera = nodoAux;
        } else {
            nuevaCabecera = invertirAux(nodoAux.getEnlace());
            nodoAux.getEnlace().setEnlace(nodoAux);
            nodoAux.setEnlace(null);
        }
        return nuevaCabecera;
    }
}
