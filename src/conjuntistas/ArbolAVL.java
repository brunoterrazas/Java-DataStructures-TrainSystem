/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conjuntistas;

import lineales.dinamicas.Lista;

/**
 *
 * @author Brunot
 */
public class ArbolAVL {

    private NodoAVL raiz;

    public ArbolAVL() {
        this.raiz = null;
    }

    private NodoAVL rotacionIzquierda(NodoAVL r) {

        NodoAVL h = r.getHD();       // h = hijo_der(r)
        NodoAVL temp = h.getHI();    // temp = hijo_izq(h)

        h.setHI(r);                  // hijo_izq(h) = r
        r.setHD(temp);               // hijo_der(r) = temp

        r.recalcularAltura();
        h.recalcularAltura();

        //Retorna la nueva raiz del subarbol
        return h;
    }

    private NodoAVL rotacionDerecha(NodoAVL r) {

        NodoAVL h = r.getHI();       // h = hijo_izq(r)
        NodoAVL temp = h.getHD();    // temp = hijo_der(h)

        h.setHD(r);                  // hijo_der(h) = r
        r.setHI(temp);               // hijo_izq(r) = temp

        r.recalcularAltura();
        h.recalcularAltura();

        //Retorna la nueva raiz del subarbol
        return h;
    }

    private NodoAVL rotarDerechaIzquierda(NodoAVL r) {
        NodoAVL h = r.getHD();

        // Rotar al hijo (Derecha)
        r.setHD(rotacionDerecha(h));

        //Rotar al padre (Izquierda)
        return rotacionIzquierda(r);
    }

    private NodoAVL rotarIzquierdaDerecha(NodoAVL r) {
        NodoAVL h = r.getHI();

        // Rotar al hijo (Izquierda)
        r.setHI(rotacionIzquierda(h));

        // Rotar al padre (Derecha)
        return rotacionDerecha(r);
    }

    public int bal(NodoAVL n) { //metodo para calcular el balance de un nodo
        int altIzq, altDer;
        altIzq = -1;
        altDer = -1;
        if (n.getHI() != null) {
            altIzq = n.getHI().getAltura();
        }
        if (n.getHD() != null) {
            altDer = n.getHD().getAltura();
        }

        return altIzq - altDer;
    }

    private NodoAVL balancear(NodoAVL n) {
        int balance = bal(n);
        NodoAVL nodo;
        nodo = n;//si balance=(-1, 0, 1) correctamente balanceado 
        //Caso 1: caido a la derecha
        if (balance < -1) {
            // Si el hijo derecho tiene balance positivo
            if (bal(n.getHD()) > 0) {
                nodo = rotarDerechaIzquierda(n);
            } else {
                nodo = rotacionIzquierda(n);
            }
        }
        //Caso 2: caido a la izquierda
        if (balance > 1) {
            // Si el hijo izquierdo tiene balance negativo
            if (bal(n.getHI()) < 0) {
                nodo = rotarIzquierdaDerecha(n);
            } else {
                nodo = rotacionDerecha(n);
            }
        }

        return nodo;
    }

    public boolean insertar(Comparable elemento) {
        boolean exito = !pertenece(elemento);//si no pertenece al arbol
        if (exito) {//agrega el elemento nuevo
            this.raiz = insertarAux(this.raiz, elemento);
        }
        return exito;
    }

    private NodoAVL insertarAux(NodoAVL n, Comparable elemento) {
        NodoAVL salida = n;
        if (n == null) {
            salida = new NodoAVL(elemento);//agregamos el elemento como raiz
        } else {
            if (elemento.compareTo(n.getElem()) < 0) {
                // bajamos y enlazamos el hijo izquierdo
                n.setHI(insertarAux(n.getHI(), elemento));
            } else {
                // bajamos y enlazamos el hijo derecho
                n.setHD(insertarAux(n.getHD(), elemento));
            }

            // cada nodo en el camino de regreso se recalcula y balancea
            n.recalcularAltura();
            salida = balancear(n);
        }

        return salida;
    }

    public boolean pertenece(Comparable elem) {//Devuelve verdadero si el elemento recibido por parametro esta en el arbol el elemento
        boolean exito = false;
        if (!esVacio()) {
            exito = perteneceAux(this.raiz, elem);
        }
        return exito;
    }

    private boolean perteneceAux(NodoAVL n, Comparable elem) {
        boolean exito = false;
        if (n != null) {
            if (n.getElem().compareTo(elem) == 0)//si encuentra elemento igual
            {
                exito = true;
            } else if (elem.compareTo(n.getElem()) < 0)//si el elemento es menor
            {
                if (n.getHI() != null)//si tiene hijo izquierdo, avanzamos por el subarbol izquierdo  
                {
                    exito = perteneceAux(n.getHI(), elem);
                }

            } else {//si tiene hijo derecho, avanzamos por el subarbol izquierdo
                if (n.getHD() != null) {
                    exito = perteneceAux(n.getHD(), elem);
                }
            }
        }

        return exito;
    }

    public boolean eliminar(Comparable elem) {
        boolean exito = pertenece(elem);
        if (exito) {
            //Si el elemento existe en el árbol.
            this.raiz = eliminarAux(elem, this.raiz);
        }
        return exito;
    }

   private NodoAVL eliminarAux(Comparable elem, NodoAVL n) {
        NodoAVL salida = n;

        if (n != null) { 
            if (elem.compareTo(n.getElem()) == 0) {
           
                if (n.getHI() == null && n.getHD() == null) {
                    // CASO 1: hoja                   
                    // El padre va a recibir este null 
                    salida = null;

                } else if (n.getHI() != null && n.getHD() != null) {
                    // CASO 3: dos hijos
                                     
                    // Buscamos el candidato (el menor del subárbol derecho)
                    Comparable candidato = minimoElemAux(n.getHD());
                    
                    //Cambiamos el valor del nodo actual con el del candidato
                    n.setElem(candidato);
                    
                    //Mandamos a borrar el nodo original del candidato
                    n.setHD(eliminarAux(candidato, n.getHD()));
                    
                    salida = n; // La salida sigue siendo el nodo actual, pero con el valor nuevo

                } else {
                    // CASO 2: un solo hijo
                   // // Devolvemos el único hijo para que el padre se enganche directo a él, salteando el nodo actual.
                    if (n.getHI() != null) {
                        salida = n.getHI();
                    } else {
                        salida = n.getHD();
                    }
                }

            } else {
                //bajamos por los subarboles
                 if (elem.compareTo(n.getElem()) < 0) {
                    // avanzamos por subarbol izquierdo y reenganchamos a la vuelta
                    n.setHI(eliminarAux(elem, n.getHI()));
                } else {
                    // avanzamos por subarbol derecho y reenganchamos a la vuelta
                    n.setHD(eliminarAux(elem, n.getHD()));
                }
                salida = n;
            }
            // Recalculamos si el nodo no es nulo
            if (salida != null) {
                salida.recalcularAltura();
                salida = balancear(salida);
            }
        }
        return salida;
    }
    

    public Lista listar() {
        Lista lis = new Lista();
        if (!this.esVacio()) {
            lis = listarAux(this.raiz, lis);
        }
        return lis;
    }

    private Lista listarAux(NodoAVL n, Lista lis) {
        if (n != null) {
            if (n.getHI() != null) {//avanzo por el hijo izquierdo
                lis = listarAux(n.getHI(), lis);
            }
            //agrego el valor del nodo actual a lista
            lis.insertar(n.getElem(), lis.longitud() + 1);
            if (n.getHD() != null) {//avanzo por el hijo izquierdo
                lis = listarAux(n.getHD(), lis);
            }
        }
        return lis;
    }

    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVL n) {
        String cadena = "";

        if (n != null) {
          int altura = n.getAltura();
            if (n.getHI() == null && n.getHD() == null) {
                // Caso especial: Es una HOJA 
                cadena += "Hoja: " + n.getElem()+ " [Alt:" + altura + "]" + "\n";
            } else {
                // Caso normal: Es un PADRE (mostramos sus enlaces)
                cadena += "Nodo: " + n.getElem()+ " [Alt:" + altura + "]";

                if (n.getHI() != null) {
                    cadena += " -> HI: " + n.getHI().getElem();
                } else {
                    cadena += " -> HI: -"; // Guion para indicar vacío visualmente
                }

                if (n.getHD() != null) {
                    cadena += " -> HD: " + n.getHD().getElem() + "\n";
                } else {
                    cadena += " -> HD: -\n";
                }
            }

            // avanzamos subarbol izquierdo
            if (n.getHI() != null) {
                cadena += toStringAux(n.getHI());
            }

            // avanzamos subarbol derecho
            if (n.getHD() != null) {
                cadena += toStringAux(n.getHD());
            }
        }
        return cadena;
    }

    public Lista listarRango(Comparable min, Comparable max) {
        Lista lis = new Lista();
        listarRangoAux(this.raiz, lis, min, max);
        return lis;
    }

    private void listarRangoAux(NodoAVL n, Lista lis, Comparable min, Comparable max) {
        if (n != null) {

            // Avanzamos subarbol de la izquierda 
            // si el nodo actual es MAYOR que el minimo. 
            // (Si n es menor o igual al minimo, todo lo que esté a su izquierda no se considera).
            if (n.getElem().compareTo(min) > 0) {
                listarRangoAux(n.getHI(), lis, min, max);
            }

            // Visitar nodo actual
            // Si está dentro del rango inclusivo [min, max], lo guardamos.
            if (n.getElem().compareTo(min) >= 0 && n.getElem().compareTo(max) <= 0) {
                lis.insertar(n.getElem(), lis.longitud() + 1);
            }

            // Avanzamos subarbol de la DERECHA
            // Solo bajamos si el nodo actual es MENOR que el maximo.
            // (Si n es mayor o igual al maximo, todo lo que esté a su derecha no se considera).
            if (n.getElem().compareTo(max) < 0) {
                listarRangoAux(n.getHD(), lis, min, max);
            }
        }
    }

    public Comparable minimoElem() {
        Comparable elem = null;
        if (!esVacio()) {
            elem = minimoElemAux(this.raiz);
        }

        return elem;
    }

    private Comparable minimoElemAux(NodoAVL n) {
        Comparable elem;
        //si no tiene hijo izquierdo es el minimo 
        if (n.getHI() == null) {
            elem = n.getElem();
        } else //bajamos por el subarbol izquierdo
        {
            elem = minimoElemAux(n.getHI());
        }

        return elem;

    }

    public Comparable maximoElem() {
        Comparable elem = null;
        if (!this.esVacio()) {
            elem = maximoAux(this.raiz);
        }

        return elem;
    }

    private Comparable maximoAux(NodoAVL n) {
        Comparable elem;
        //si no tiene hijo derecho es el maximo 
        if (n.getHD() == null) {
            elem = n.getElem();
        } else //bajamos por el subarbol derecho
        {
            elem = maximoAux(n.getHD());
        }

        return elem;
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }
}
