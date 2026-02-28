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

    public boolean eliminar(Comparable elem) {
        boolean exito = existeClave(elem);
        if (exito) {
            // Si el arbol no esta vacio
            this.raiz = eliminarAux(elem, this.raiz, null);
        }
        return exito;
    }

    private NodoAVLDicc eliminarAux(Comparable clave, NodoAVLDicc n, NodoAVLDicc padre) {
        NodoAVLDicc salida = n;

        if (n != null) { // si encontramos al elemento
            if (clave.compareTo(n.getClave()) == 0) {

                if (n.getHI() == null && n.getHD() == null) {
                    // Caso 1: Hoja
                    eliminarCaso1(n, padre);
                    salida = null;
                } else if (n.getHI() != null && n.getHD() != null) {
                    // Caso 3: Dos hijos
                    eliminarCaso3Min(n);
                    salida = n;
                } else {
                    // Caso 2: Un solo hijo
                    eliminarCaso2(n, padre);
                    // Forma tradicional sin usar "?"
                    if (n.getHI() != null) {
                        salida = n.getHI();
                    } else {
                        salida = n.getHD();
                    }
                }
            } else {

                if (clave.compareTo(n.getClave()) < 0) {//avanzamos por subarbol izquierdo
                    n.setHI(eliminarAux(clave, n.getHI(), n));
                } else {//avanzamos por subarbol derecho
                    n.setHD(eliminarAux(clave, n.getHD(), n));
                }
                salida = n;
            }

            // recalculamos y balanceamos si es necesario al volver de la recursion
            if (salida != null) {
                salida.recalcularAltura();
                salida = balancear(salida);
            }
        }
        return salida;
    }

    private boolean eliminarCaso1(NodoAVLDicc n, NodoAVLDicc padre) {
        // Metodo para eliminar un nodo que es hoja
        boolean res = true;

        // Si el padre no es nulo
        if (padre != null) {
            // Si n es menor que el padre, n es su hijo izquierdo
            if (n.getClave().compareTo(padre.getClave()) < 0) {
                // Seteo el hijo izquierdo del nodo padre como null
                padre.setHI(null);
            } else {
                // Seteo el hijo derecho del nodo padre como null
                padre.setHD(null);
            }
        } else {
            // Sino n es la raiz y le asigno null
            this.raiz = null;
        }
        return res;
    }

    private boolean eliminarCaso2(NodoAVLDicc n, NodoAVLDicc padre) {
        // Metodo para eliminar al nodo n que tiene al menos un hijo
        boolean res = true;

        // Si el padre no es null
        if (padre != null) {
            // Si n (nodo actual) es hijo derecho (verificamos si el padre es menor que n)
            if (padre.getClave().compareTo(n.getClave()) < 0) {

                if (n.getHI() != null) {
                    // Engancha el HI del nodo n que vamos a eliminar, como HD del nodo padre
                    padre.setHD(n.getHI());
                } else {
                    // Engancha el HD del nodo n que vamos a eliminar, como HD del nodo padre
                    padre.setHD(n.getHD());
                }

            } else {
                // Sino n es hijo izquierdo del padre

                if (n.getHI() != null) {
                    // Le asigno el HI del nodo n que vamos a eliminar, como HI del nodo padre
                    padre.setHI(n.getHI());
                } else {
                    // Le asigno el HD del nodo n que vamos a eliminar, como HI del nodo padre  
                    padre.setHI(n.getHD());
                }
            }
        } else {
            // Sino el nodo n es la raiz
            // Verifico si n tiene hijo izquierdo o derecho y seteo la raiz con el hijo correspondiente
            if (n.getHI() != null) {
                this.raiz = n.getHI();
            } else {
                this.raiz = n.getHD();
            }
        }
        return res;
    }

    private boolean eliminarCaso3Min(NodoAVLDicc n) {
        // Metodo privado del caso 3, cuando el nodo a eliminar tiene dos hijos
        boolean res = true;
        NodoAVLDicc padreAux, aux;

        // Busco como candidato al hijo mas chico del subarbol derecho 
        aux = n.getHD();
        padreAux = n;

        if (aux.getHI() == null) {
            // Si el mismo hijo derecho es el candidato (no tiene HI)
            n.setClave(aux.getClave());
            n.setHD(aux.getHD());

        } else {
            // Busco el candidato bajando por la izquierda hasta encontrar null
            while (aux.getHI() != null) {
                padreAux = aux;
                aux = aux.getHI();
            }

            // Si encuentro el candidato, le asigno ese valor a n (reemplazo valor)
            n.setClave(aux.getClave());

            // Elimino el nodo candidato (que ahora esta duplicado)
            if (aux.getHI() == null && aux.getHD() == null) {
                // Si no tiene hijos es hoja
                eliminarCaso1(aux, padreAux);
            } else {
                // Sino tiene al menos un hijo (solo puede ser derecho en este caso)
                eliminarCaso2(aux, padreAux);
            }
        }
        return res;
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
        cadena += "Nodo: " + n.getClave() + " (Alt: " + n.getAltura() + ")";
        
        // USAR VARIABLES TEMPORALES PARA EVITAR NPE
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
