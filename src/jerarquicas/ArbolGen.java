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
                NodoGen hijoIzq;
                hijoIzq = n.getHEI();
                if (hijoIzq != null) {
                    resultado = obtenerNodo(n.getHEI(), buscado);

                    if (resultado == null) {
                        NodoGen hermanoDer = hijoIzq.getHD();
                        while (hermanoDer != null) {
                            resultado = obtenerNodo(hermanoDer, buscado);
                            hermanoDer = hermanoDer.getHD();

                        }

                    }
                }
            }
        }

        return resultado;

    }
     public boolean insertar(Object elem, Object padre) {
        boolean exito;
        exito = false;
        NodoGen nodoPadre = obtenerNodo(this.raiz, padre);
        if (nodoPadre != null) {
            NodoGen hijoIzq = nodoPadre.getHEI();
            exito = true;
            if (hijoIzq == null) {
                nodoPadre.setHEI(new NodoGen(elem,null,null));
            } else {
                NodoGen hermanoAnt = hijoIzq;

                while (hermanoAnt.getHD() != null) {

                    hermanoAnt = hermanoAnt.getHD();

                }

                hermanoAnt.setHD(new NodoGen(elem,null,null));

            }
        } else {
            this.raiz = new NodoGen(elem,null,null);
            exito = true;
        }
        return exito;
    }

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
