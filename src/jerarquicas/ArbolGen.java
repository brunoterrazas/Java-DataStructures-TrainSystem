/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jerarquicas;

import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;

/**
 *
 * @author Brunot
 */
public class ArbolGen {

    private NodoGen raiz;

    public ArbolGen() {
        this.raiz = null;
    }

    public Lista listarPreOrden() {
        Lista salida = new Lista();
        listarPreOrdenAux(this.raiz, salida);
        return salida;
    }

    private void listarPreOrdenAux(NodoGen n, Lista ls) {
        if (n != null) {
            //visitamos el nodo, lo agregamos a la lista
            ls.insertar(n.getElem(), ls.getLongitud() + 1);
            //llamado recursivo con el primer hijo de n
            if (n.getHEI() != null) {
                listarPreOrdenAux(n.getHEI(), ls);
            }

            //llamados recursivos con los otros hijos de n
            if (n.getHEI() != null) {
                NodoGen hijo = n.getHEI().getHD();
                while (hijo != null) {
                    listarPreOrdenAux(n.getHEI(), ls);
                    hijo = hijo.getHD();
                }
            }

        }

    }

    public Lista listarInOrden() {
        Lista salida = new Lista();
        listarInOrdenAux(this.raiz, salida);
        return salida;
    }

    private void listarInOrdenAux(NodoGen n, Lista ls) {
        if (n != null) {
            //llamado recursivo con el primer hijo de n
            if (n.getHEI() != null) {
                listarInOrdenAux(n.getHEI(), ls);
            }
            //visitamos el nodo, lo agregamos a la lista
            ls.insertar(n.getElem(), ls.getLongitud() + 1);
            //llamados recursivos con los otros hijos de n
            if (n.getHEI() != null) {
                NodoGen hijo = n.getHEI().getHD();
                while (hijo != null) {
                    listarInOrdenAux(n.getHEI(), ls);
                    hijo = hijo.getHD();
                }
            }

        }

    }

    public Lista listarPosOrden() {
        Lista salida = new Lista();
        listarPosOrdenAux(this.raiz, salida);
        return salida;
    }

    private void listarPosOrdenAux(NodoGen n, Lista ls) {
        if (n != null) {
            //llamado recursivo con el primer hijo de n
            if (n.getHEI() != null) {
                listarPosOrdenAux(n.getHEI(), ls);
            }

            //llamados recursivos con los otros hijos de n
            if (n.getHEI() != null) {
                NodoGen hijo = n.getHEI().getHD();
                while (hijo != null) {
                    listarPosOrdenAux(n.getHEI(), ls);
                    hijo = hijo.getHD();
                }
            }
            //visitamos el nodo, lo agregamos a la lista
            ls.insertar(n.getElem(), ls.getLongitud() + 1);
        }

    }

    public Lista listarPorNiveles() {
        Lista lis = new Lista();
        Cola Q = new Cola();
        Q.poner(this.raiz);//ponemos en la cola la raiz
        NodoGen nodoActual;
        while (!Q.esVacia()) {
            nodoActual = (NodoGen) Q.obtenerFrente();
            Q.sacar();//sacamos el frente de la cola
            lis.insertar(nodoActual.getElem(), lis.getLongitud() + 1);
            //para cada hijo de nodoActual 
            if (nodoActual.getHEI() != null) {
                Q.poner(nodoActual.getHEI());

                NodoGen hijo = nodoActual.getHEI().getHD();
                while (hijo != null) {
                    Q.poner(hijo);
                    hijo = hijo.getHD();
                }
            }
        }
        return lis;
    }

    private NodoGen obtenerNodo(NodoGen n, Object buscado) {
        NodoGen resultado = null;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                resultado = n;
            } else {
                NodoGen hijo = n.getHEI();

                while (hijo != null && resultado == null) {
                    resultado = obtenerNodo(hijo, buscado);
                    hijo = hijo.getHD();

                }

            }

        }

        return resultado;

    }

    public boolean insertar(Object elem, Object padre) {
        boolean exito = false;

        if (this.esVacio()) {
            // si arbol vacío
            this.raiz = new NodoGen(elem);
            exito = true;
        } else {
            // buscamos al padre
            NodoGen nodoPadre = obtenerNodo(this.raiz, padre);

            if (nodoPadre != null) {
                //si no tiene hijo extremo izquierdo
                if (nodoPadre.getHEI() == null) {
                    // Es el primer hijo
                    nodoPadre.setHEI(new NodoGen(elem));
                } else {
                    // sino ya tiene hijos, lo enlazamos al final de los hermanos
                    NodoGen auxHermano = nodoPadre.getHEI();
                    while (auxHermano.getHD() != null) {
                        auxHermano = auxHermano.getHD();
                    }
                    auxHermano.setHD(new NodoGen(elem));
                }
                exito = true;
            }

        }
        return exito;
    }

    public boolean pertenece(Object elem) {
        boolean exito = false;
        if (!esVacio()) {
            exito = perteneceAux(this.raiz, elem);
        }

        return exito;

    }

    private boolean perteneceAux(NodoGen n, Object buscado) {
        boolean exito = false;
        if (n != null) {
            if (n.getElem().equals(buscado)) {
                exito = true;
            } else {

                NodoGen hijo = n.getHEI();
                while (hijo != null && !exito) {
                    exito = perteneceAux(hijo, buscado);
                    hijo = hijo.getHD();
                }

            }

        }
        return exito;
    }

    public Object padre(Object elem) {
        Object padreBuscado = null;
        if (!esVacio() && !this.raiz.getElem().equals(elem)) {
            padreBuscado = padreAux(this.raiz, elem);
        }
        return padreBuscado;
    }

    public Object padreAux(NodoGen n, Object elem) {
        Object padreBuscado = null;
        if (n != null) {
            if (n.getHEI() != null) {
                NodoGen hijo = n.getHEI();
                //verificamos si es un de sus hijos
                while (hijo != null && padreBuscado == null) {
                    if (hijo.getElem().equals(elem)) {
                        padreBuscado = n.getElem();
                    } else {
                        padreBuscado = padreAux(hijo, elem);
                    }
                    hijo = hijo.getHD();
                }
            }
        }
        return padreBuscado;
    }

    public int altura() {
        int alt = -1;
        if (!esVacio()) {
            alt = alturaAux(this.raiz);
        }
        return alt;
    }

    private int alturaAux(NodoGen n) {
        int aux = -1;
        int altMax = -1;
        if (n != null) {
            NodoGen h = n.getHEI();
            while (h != null) {
                aux = alturaAux(h);
                if (aux > altMax) {
                    altMax = aux;
                }
                h = h.getHD();
            }

            altMax = altMax + 1;
        }
        return altMax;
    }

    public int nivel(Object elem) {
        int niv = -1;
        if (!esVacio()) {
            niv = nivelAux(this.raiz, elem);
        }
        return niv;
    }

    private int nivelAux(NodoGen n, Object elemento) {
        int res = -1;

        if (n != null) {
            if (n.getElem().equals(elemento)) {
                res = 0;
            } else {
                // Si no,buscamos en sus hijos recursivamente
                NodoGen hijo = n.getHEI();

                // Recorremos los hermanos hasta encontrarlo  o agotar hijos
                while (hijo != null && res == -1) {
                    res = nivelAux(hijo, elemento);
                    hijo = hijo.getHD();
                }

                // si se encontró en algún subárbol, sumamos 1 al nivel
                if (res > -1) {
                    res++;
                }
            }
        }
        return res;
    }

    public Lista ancestros(Object elem) {
        Lista lis = new Lista();
        if (!esVacio()) {
            ancestrosAux(this.raiz, elem, lis);
        }
        return lis;
    }

   private boolean ancestrosAux(NodoGen n, Object buscado, Lista ls) {
    boolean encontrado = false;

    if (n != null) {
           if (n.getElem().equals(buscado)) {
            encontrado = true;
        } else {
            
            NodoGen hijo = n.getHEI();
            while (hijo != null && !encontrado) {
                encontrado = ancestrosAux(hijo, buscado, ls);
                hijo = hijo.getHD(); // Paso al siguiente hermano
            }

            if (encontrado) {
                ls.insertar(n.getElem(), ls.getLongitud() + 1);
            }
        }
    }
    return encontrado;
}

    @Override
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoGen n) {
        String s = "";
        if (n != null) {
            //visita del nodo n
            s += n.getElem().toString() + " -> ";
            NodoGen hijo = n.getHEI();
            while (hijo != null) {
                s += hijo.getElem().toString() + ", ";
                hijo = hijo.getHD();
            }
            //comienza recorrido de los hijos de n llamados recursivamente
            //para que cada hijo agrega su subcadena a la general
            hijo = n.getHEI();
            while (hijo != null) {
                s += "\n " + toStringAux(hijo);
                hijo = hijo.getHD();
            }
        }

        return s;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }
}
