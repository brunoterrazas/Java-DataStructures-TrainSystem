/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package especiales;

import lineales.dinamicas.Lista;

/**
 *
 * @author Brunot
 */
public class Diccionario {

    private NodoAVLDicc raiz;

    public Diccionario() {
        this.raiz = null;
    }

    private NodoAVLDicc rotacionIzquierda(NodoAVLDicc r) {

        NodoAVLDicc h = r.getHD();       // h = hijo_der(r)
        NodoAVLDicc temp = h.getHI();    // temp = hijo_izq(h)

        h.setHI(r);                  // hijo_izq(h) = r
        r.setHD(temp);               // hijo_der(r) = temp

        r.recalcularAltura();
        h.recalcularAltura();

        //Retorna la nueva raiz del subarbol
        return h;
    }

    private NodoAVLDicc rotacionDerecha(NodoAVLDicc r) {

        NodoAVLDicc h = r.getHI();       // h = hijo_izq(r)
        NodoAVLDicc temp = h.getHD();    // temp = hijo_der(h)

        h.setHD(r);                  // hijo_der(h) = r
        r.setHI(temp);               // hijo_izq(r) = temp

        r.recalcularAltura();
        h.recalcularAltura();

        //Retorna la nueva raiz del subarbol
        return h;
    }

    private NodoAVLDicc rotarDerechaIzquierda(NodoAVLDicc r) {
        NodoAVLDicc h = r.getHD();

        // Rotar al hijo (Derecha)
        r.setHD(rotacionDerecha(h));

        //Rotar al padre (Izquierda)
        return rotacionIzquierda(r);
    }

    private NodoAVLDicc rotarIzquierdaDerecha(NodoAVLDicc r) {
        NodoAVLDicc h = r.getHI();

        // Rotar al hijo (Izquierda)
        r.setHI(rotacionIzquierda(h));

        // Rotar al padre (Derecha)
        return rotacionDerecha(r);
    }

    public int bal(NodoAVLDicc n) { //metodo para calcular el balance de un nodo
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

    private NodoAVLDicc balancear(NodoAVLDicc n) {
        int balance = bal(n);
        NodoAVLDicc nodo;
        nodo = n;//si balance=(-1, 0, 1) correctamente balanceado 
        //Caso 1: caido a la derecha
        if (balance < -1) {
            // Si el hijo derecho tiene balance positivo
            if (bal(n.getHD()) > 0) {
                nodo = rotarDerechaIzquierda(n);
            } else {
                nodo = rotacionIzquierda(n);
            }
        }//cambiar else
        //Caso 2: caido a la izquierda
        else {
            if (balance > 1) {
                // Si el hijo izquierdo tiene balance negativo
                if (bal(n.getHI()) < 0) {
                    nodo = rotarIzquierdaDerecha(n);
                } else {
                    nodo = rotacionDerecha(n);
                }
            }
        }

        return nodo;
    }

    public boolean insertar(Comparable clave, Object dato) {
        boolean exito = !existeClave(clave);//si no pertenece al arbol
        if (exito) {//agrega el elemento nuevo
            this.raiz = insertarAux(this.raiz, clave, dato);
        }
        return exito;
    }

    private NodoAVLDicc insertarAux(NodoAVLDicc n, Comparable clave, Object dato) {
        NodoAVLDicc salida = n;
        if (n == null) {
            salida = new NodoAVLDicc(clave, dato);//agregamos el elemento como raiz
        } else {
            if (clave.compareTo(n.getClave()) < 0) {
                // bajamos y enlazamos el hijo izquierdo
                n.setHI(insertarAux(n.getHI(), clave, dato));
            } else {
                // bajamos y enlazamos el hijo derecho
                n.setHD(insertarAux(n.getHD(), clave, dato));
            }

            // cada nodo en el camino de regreso se recalcula y balancea
            n.recalcularAltura();
            salida = balancear(n);
        }

        return salida;
    }

    public boolean existeClave(Comparable clave) {//Devuelve verdadero si el elemento recibido por parametro esta en el arbol el elemento
        boolean exito = false;
        if (!esVacio()) {
            exito = existeClaveAux(this.raiz, clave);
        }
        return exito;
    }

    private boolean existeClaveAux(NodoAVLDicc n, Comparable clave) {
        boolean exito = false;
        if (n != null) {
            if (n.getClave().compareTo(clave) == 0)//si encuentra elemento igual
            {
                exito = true;
            } else if (clave.compareTo(n.getClave()) < 0)//si el elemento es menor
            {
                if (n.getHI() != null)//si tiene hijo izquierdo, avanzamos por el subarbol izquierdo  
                {
                    exito = existeClaveAux(n.getHI(), clave);
                }

            } else {//si tiene hijo derecho, avanzamos por el subarbol izquierdo
                if (n.getHD() != null) {
                    exito = existeClaveAux(n.getHD(), clave);
                }
            }
        }

        return exito;
    }

    
    public boolean eliminar(Comparable clave) {
        boolean exito = existeClave(clave);
        if (exito) {
            //Si la clave existe en el árbol.
            this.raiz = eliminarAux(clave, this.raiz);
        }
        return exito;
    }

   private NodoAVLDicc eliminarAux(Comparable clave, NodoAVLDicc n) {
        NodoAVLDicc salida = n;

        if (n != null) { 
            if (clave.compareTo(n.getClave()) == 0) {
           
                if (n.getHI() == null && n.getHD() == null) {
                    // CASO 1: hoja                   
                    // El padre va a recibir este null 
                    salida = null;

                } else if (n.getHI() != null && n.getHD() != null) {
                    // CASO 3: dos hijos
                                     
                    // Buscamos el candidato (el menor del subárbol derecho)
                    NodoAVLDicc candidato = obtenerNodoMinimo(n.getHD());
                    
                    //Cambiamos el valor del nodo actual con el del candidato
                    n.setClave(candidato.getClave());
                    n.setDato(candidato.getDato());
                    
                    //Mandamos a borrar el nodo original del candidato
                    n.setHD(eliminarAux(candidato.getClave(), n.getHD()));
                    
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
                 if (clave.compareTo(n.getClave()) < 0) {
                    // avanzamos por subarbol izquierdo y reenganchamos a la vuelta
                    n.setHI(eliminarAux(clave, n.getHI()));
                } else {
                    // avanzamos por subarbol derecho y reenganchamos a la vuelta
                    n.setHD(eliminarAux(clave, n.getHD()));
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
   private NodoAVLDicc obtenerNodoMinimo(NodoAVLDicc n) {
    // Bajamos por la izquierda iterativamente hasta el final
    while (n.getHI() != null) {
        n = n.getHI();
    }
    return n; // Retornamos el nodo completo
   }
    public Object obtenerDato(Comparable clave) {
        Object resultado = null;
        if (this.raiz != null) {
            resultado = obtenerDatoAux(this.raiz, clave);
        }
        return resultado;
    }

    private Object obtenerDatoAux(NodoAVLDicc n, Comparable clave) {
        Object res = null;
        if (n != null) {
            int comparacion = clave.compareTo(n.getClave());
            if (comparacion == 0) {
                res = n.getDato();
            } else if (comparacion < 0) {
                // la clave buscada es menor, vamos a la izquierda
                res = obtenerDatoAux(n.getHI(), clave);
            } else {
                // la clave buscada es mayor, vamos a la derecha
                res = obtenerDatoAux(n.getHD(), clave);
            }
        }
        return res;
    }

    public Lista listarLlaves() {
        Lista lis = new Lista();
        if (!this.esVacio()) {
            lis = listarLlavesAux(this.raiz, lis);
        }
        return lis;
    }

    private Lista listarLlavesAux(NodoAVLDicc n, Lista lis) {
        if (n != null) {
            if (n.getHI() != null) {//avanzo por el hijo izquierdo
                lis = listarLlavesAux(n.getHI(), lis);
            }
            //agrego el valor del nodo actual a lista
            lis.insertar(n.getClave(), lis.longitud() + 1);
            if (n.getHD() != null) {//avanzo por el hijo izquierdo
                lis = listarLlavesAux(n.getHD(), lis);
            }
        }
        return lis;
    }

    public Lista listarDatos() {
        Lista lis = new Lista();
        if (!this.esVacio()) {
            listarDatosAux(this.raiz, lis);
        }
        return lis;
    }

    private void listarDatosAux(NodoAVLDicc n, Lista lis) {
        if (n != null) {
            listarDatosAux(n.getHI(), lis);
            lis.insertar(n.getDato(), lis.longitud() + 1);
            listarDatosAux(n.getHD(), lis);
        }
    }

    @Override
    public String toString() {
        return toStringAux(this.raiz);
    }

    private String toStringAux(NodoAVLDicc n) {
        String cadena = "";
        if (n != null) {
            cadena += n.getClave() + " (Alt: " + n.getAltura() + ")";

            NodoAVLDicc hi = n.getHI();
            NodoAVLDicc hd = n.getHD();

            cadena += " | HI: " + ((hi != null) ? hi.getClave() : "-");
            cadena += " | HD: " + ((hd != null) ? hd.getClave() : "-") + "\n";

            cadena += toStringAux(hi);
            cadena += toStringAux(hd);
        }
        return cadena;
    }

    public Lista listarRango(Comparable min, Comparable max) {
        Lista lis = new Lista();
        listarRangoAux(this.raiz, lis, min, max);
        return lis;
    }

    private void listarRangoAux(NodoAVLDicc n, Lista lis, Comparable min, Comparable max) {
        if (n != null) {

            // Avanzamos subarbol de la izquierda 
            // si el nodo actual es MAYOR que el minimo. 
            // (Si n es menor o igual al minimo, todo lo que esté a su izquierda no se considera).
            if (n.getClave().compareTo(min) > 0) {
                listarRangoAux(n.getHI(), lis, min, max);
            }

            // Visitar nodo actual
            // Si está dentro del rango inclusivo [min, max], lo guardamos.
            if (n.getClave().compareTo(min) >= 0 && n.getClave().compareTo(max) <= 0) {
                lis.insertar(n.getClave(), lis.longitud() + 1);
            }

            // Avanzamos subarbol de la DERECHA
            // Solo bajamos si el nodo actual es MENOR que el maximo.
            // (Si n es mayor o igual al maximo, todo lo que esté a su derecha no se considera).
            if (n.getClave().compareTo(max) < 0) {
                listarRangoAux(n.getHD(), lis, min, max);
            }
        }
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public void vaciar() {
        this.raiz = null;
    }
}
