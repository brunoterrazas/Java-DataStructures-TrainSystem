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

    public boolean insertar(Comparable elemento) {
        boolean exito = true;
        if (esVacio()) {//agrega el elemento nuevo
            this.raiz = new NodoAVL(elemento);
        } else {
            exito = insertarAux(this.raiz, elemento);
        }
        return exito;
    }

    private boolean insertarAux(NodoAVL n, Comparable elemento) {
        boolean exito = true;
        if (elemento.compareTo(n.getElem()) == 0) {
            exito = false;//Error elemento repetido
        } else if (elemento.compareTo(n.getElem()) < 0) {
            //si el elemento es menor avanza al subarbol izquierdo
            if (n.getHI() != null) {//si tiene HI
                exito = insertarAux(n.getHI(), elemento);
            } else {
                //sino lo agrega como hijo izquierdo
                n.setHI(new NodoAVL(elemento));
            }
        } else if (n.getHD() != null) {//si el elemento es mayor avanza al subarbol derecho
            //si tiene HD
            exito = insertarAux(n.getHD(), elemento);
        } else {
            n.setHD(new NodoAVL(elemento));
            //sino lo agrega como hijo derecho
        }
        return exito;
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
        boolean exito;
        if (!this.esVacio()) {
            // Si el arbol no esta vacio
            exito = eliminarAux(elem, this.raiz, null);
        } else {
            exito = false;
        }
        return exito;
    }

    private boolean eliminarAux(Comparable elem, NodoAVL n, NodoAVL padre) {
        boolean exito = false;
        NodoAVL hijoIzq, hijoDer;

        if (n != null) {
            hijoIzq = n.getHI();
            hijoDer = n.getHD();

            // Si el elemento es igual que n (nodo actual)
            if (elem.compareTo(n.getElem()) == 0) {
                if (hijoIzq == null && hijoDer == null) {
                    // Caso 1: El nodo n no tiene hijos (es hoja)
                    exito = eliminarCaso1(n, padre);

                } else {
                    if ((hijoIzq == null && hijoDer != null) || (hijoIzq != null && hijoDer == null)) {
                        // Caso 2: El nodo n tiene un solo hijo
                        exito = eliminarCaso2(n, padre);

                    } else {
                        // Caso 3: El nodo n tiene los dos hijos
                        exito = eliminarCaso3Min(n);
                    }
                }

            } else {
                // Sino, si es elemento es menor que n (nodo actual)
                if (elem.compareTo(n.getElem()) < 0) {
                    // Recorro el subarbol izquierdo
                    exito = eliminarAux(elem, n.getHI(), n);
                } else {
                    // Recorro el subarbol derecho
                    exito = eliminarAux(elem, n.getHD(), n);
                }
            }
        }
        return exito;
    }

    private boolean eliminarCaso1(NodoAVL n, NodoAVL padre) {
        // Metodo para eliminar un nodo que es hoja
        boolean res = true;

        // Si el padre no es nulo
        if (padre != null) {
            // Si n es menor que el padre, n es su hijo izquierdo
            if (n.getElem().compareTo(padre.getElem()) < 0) {
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

    private boolean eliminarCaso2(NodoAVL n, NodoAVL padre) {
        // Metodo para eliminar al nodo n que tiene al menos un hijo
        boolean res = true;

        // Si el padre no es null
        if (padre != null) {
            // Si n (nodo actual) es hijo derecho (verificamos si el padre es menor que n)
            if (padre.getElem().compareTo(n.getElem()) < 0) {

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

    private boolean eliminarCaso3Min(NodoAVL n) {
        // Metodo privado del caso 3, cuando el nodo a eliminar tiene dos hijos
        boolean res = true;
        NodoAVL padreAux, aux;

        // Busco como candidato al hijo mas chico del subarbol derecho 
        aux = n.getHD();
        padreAux = n;

        if (aux.getHI() == null) {
            // Si el mismo hijo derecho es el candidato (no tiene HI)
            n.setElem(aux.getElem());
            n.setHD(aux.getHD());

        } else {
            // Busco el candidato bajando por la izquierda hasta encontrar null
            while (aux.getHI() != null) {
                padreAux = aux;
                aux = aux.getHI();
            }

            // Si encuentro el candidato, le asigno ese valor a n (reemplazo valor)
            n.setElem(aux.getElem());

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

            if (n.getHI() == null && n.getHD() == null) {
                // Caso especial: Es una HOJA 
                cadena += "Hoja: " + n.getElem() + "\n";
            } else {
                // Caso normal: Es un PADRE (mostramos sus enlaces)
                cadena += "Nodo: " + n.getElem();

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
