/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafos;

import lineales.dinamicas.Cola;
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

    public boolean existeVertice(Object elem) {
        return ubicarVertice(elem) != null;
    }

    private NodoVert ubicarVertice(Object buscado) {
        NodoVert aux = this.inicio;
        while (aux != null && !aux.getElem().equals(buscado)) {
            aux = aux.getSigVertice();
        }
        return aux;
    }

    public boolean insertarArco(Object origen, Object destino, double km) {
        boolean exito = false;
        //recorremos la lista de vertices
        // localizamos ambos nodos vertices 
        NodoVert auxOri = ubicarVertice(origen);
        NodoVert auxDes = ubicarVertice(destino);
        //si ambos vertices existen
        if (auxOri != null && auxDes != null) {
            //insertamos en la lista de adyacentes de Urigen
            // Se inserta al principio de la lista de adyacencia del nodo origen
            auxOri.setPrimerAdy(new NodoAdy(auxDes, km, auxOri.getPrimerAdy()));

            //Como es grafo, insertamos también en destino
            auxDes.setPrimerAdy(new NodoAdy(auxOri, km, auxDes.getPrimerAdy()));

            exito = true;
        }
        return exito;
    }

    public boolean eliminarArco(Object origen, Object destino) {
        boolean exitoOrigen = false;
        boolean exitoDestino = false;
        // localizamos ambos nodos vertices 
        //recorremos la lista de vertices
        NodoVert auxOri = ubicarVertice(origen);
        NodoVert auxDes = ubicarVertice(destino);
        if (auxOri != null && auxDes != null) {

            exitoOrigen = eliminarAdyacente(auxOri, auxDes);

            exitoDestino = eliminarAdyacente(auxDes, auxOri);

        }
        return exitoOrigen && exitoDestino;
    }

    private boolean eliminarAdyacente(NodoVert vertice, NodoVert nodoBuscado) {
        boolean exito = false;
        // buscamos en la lista de adyacentes del vertice
        NodoAdy auxNodoAdy = vertice.getPrimerAdy();
        if (auxNodoAdy != null) {
            //caso 1, eliminar en 1er posicion
            if (auxNodoAdy.getVertice().equals(nodoBuscado)) {
                //enganchamos como primer adyacente del vertice al siguiente adyacente
                vertice.setPrimerAdy(auxNodoAdy.getSigAdyacente());
                exito = true;

            } else {
                NodoAdy anteriorD = auxNodoAdy;
                auxNodoAdy = auxNodoAdy.getSigAdyacente();

                while (auxNodoAdy != null && !exito) {
                    // si el nodo adyacente tiene como adyacente el nodo buscado
                    if (auxNodoAdy.getVertice().equals(nodoBuscado)) {
                        //enganchamos el nodo anterior con el siguiente adyacente del nodo buscado
                        anteriorD.setSigAdyacente(auxNodoAdy.getSigAdyacente());
                        exito = true;
                    } else {

                        auxNodoAdy = auxNodoAdy.getSigAdyacente();
                        anteriorD = anteriorD.getSigAdyacente();
                    }

                }
            }
        }
        return exito;
    }

public boolean eliminarVertice(Object buscado) {
        boolean exito = false;

        NodoVert anterior, aux;
        anterior = null;
        aux = this.inicio;
        while (aux != null && !exito) {
             
            if (aux.getElem().equals(buscado)) {//Cuando encontramos al vertice
                //Como es grafo no dirigido,
                //Eliminamos el vertice buscado usando la referencia de cada vertice de la lista de sus adyacentes  
                //aprovechando que estan conectados desde ambos vertices

                NodoAdy auxAdy = aux.getPrimerAdy();
                //Eliminamos el vertice en sus adyacentes

                while (auxAdy != null) {
                    //borramos usando la referencia del vertice del lado invertido  
                    eliminarAdyacente(auxAdy.getVertice(), aux);
                    auxAdy = auxAdy.getSigAdyacente();
                }
                //Eliminamos de la lista de vertices
                //caso 1 primer nodo Buscado
                if (aux == this.inicio) {
                    this.inicio = this.inicio.getSigVertice();

                } else {

                    //engancho el nodo anterior con el siguiente del nodo buscado
                    if (anterior != null) {
                        anterior.setSigVertice(aux.getSigVertice());
                    }

                }
                exito = true;
            } else {
                anterior = aux;
                aux = aux.getSigVertice();

            }
        }

        return exito;
    }


    public boolean existeArco(Object origen, Object destino) {
        boolean exito = false;
        // ubicamos las referencias de ambos nodos
        NodoVert auxO = ubicarVertice(origen);
        NodoVert auxD = ubicarVertice(destino);

        // si existen buscamos si estan conectados
        if (auxO != null && auxD != null) {
            // buscamos en la lista de adyacentes del origen
            NodoAdy auxAdy = auxO.getPrimerAdy();

            while (auxAdy != null && !exito) {
                // si el nodo origen tiene como adyacente el destino, como es grafo tiene el arco invertido tambien
                if (auxAdy.getVertice().equals(auxD)) {
                    exito = true;
                }
                auxAdy = auxAdy.getSigAdyacente();
            }
        }
        return exito;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        //define un vertice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) { //si el vertice no fue visitado aun, avanza en profundidad
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }

        return visitados;
    }

    private void listarEnProfundidadAux(NodoVert n, Lista vis) {
        if (n != null) {//marca al vertice n como visitado
            vis.insertar(n.getElem(), vis.getLongitud() + 1);
            NodoAdy ady = n.getPrimerAdy();
            while (ady != null) {//visita en profundidad los adyacentes de n aun no visitados
                if (vis.localizar(ady.getVertice().getElem()) < 0) {
                    listarEnProfundidadAux(ady.getVertice(), vis);
                }
                ady = ady.getSigAdyacente();
            }

        }

    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        //define un vertice donde comenzar a recorrer
        NodoVert aux = this.inicio;
        while (aux != null) {
            if (visitados.localizar(aux.getElem()) < 0) { //si el vertice no fue visitado aun, avanza en profundidad
                listarEnAnchuraDesdeAux(aux, visitados);
            }
            aux = aux.getSigVertice();
        }

        return visitados;
    }

    private void listarEnAnchuraDesdeAux(NodoVert verticeInicial, Lista visitados) {
        Cola Q = new Cola();
        visitados.insertar(verticeInicial.getElem(), visitados.getLongitud() + 1);
        Q.poner(verticeInicial);

        while (!Q.esVacia()) {
            NodoVert u = (NodoVert) Q.obtenerFrente();
            Q.sacar();

            // Para cada adyacente v de u
            NodoAdy ady = u.getPrimerAdy();
            while (ady != null) {
                NodoVert v = ady.getVertice();
                // Si v no está en visitados
                if (visitados.localizar(v.getElem()) < 0) {
                    visitados.insertar(v.getElem(), visitados.getLongitud() + 1);
                    Q.poner(v); // ponemos el vecino en la cola para explorarlo luego
                }
                ady = ady.getSigAdyacente();
            }
        }
    }

    public boolean esVacio() {
        return inicio == null;
    }

    public boolean existeCamino(Object origen, Object destino) {//verifica si ambos vertices existen
        boolean exito = false;
        NodoVert auxO = null;
        NodoVert auxD = null;
        NodoVert aux = this.inicio;
        //recorrer hasta encontrar ambos vertices o toda la lista de vertices
        while ((auxO == null || auxD == null) && aux != null) {
            if (aux.getElem().equals(origen)) {
                auxO = aux;
            }
            if (aux.getElem().equals(destino)) {
                auxD = aux;
            }

            aux = aux.getSigVertice();
        }
        if (auxO != null && auxD != null) {
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }

        return exito;
    }

    private boolean existeCaminoAux(NodoVert n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            if (n.getElem().equals(dest)) {//si encontramos destino existe camino
                exito = true;
            } else {//si no es el destino verifica si hay un camino entre n y destino
                vis.insertar(n.getElem(), vis.getLongitud() + 1);
                NodoAdy ady = n.getPrimerAdy();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getVertice().getElem()) < 0) {
                        exito = existeCaminoAux(ady.getVertice(), dest, vis);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista resCamino = new Lista();
        NodoVert vertOrigen = ubicarVertice(origen);
        NodoVert vertDestino = ubicarVertice(destino);
        //Verificamos si existen los dos vertices (estaciones)
        if (vertOrigen != null && vertDestino != null) {
            Lista caminoActual = new Lista();
            resCamino = caminoMasCortoAux(vertOrigen, vertDestino, caminoActual, resCamino);
        }
        return resCamino;
    }

    private Lista caminoMasCortoAux(NodoVert n, NodoVert destino, Lista actual, Lista res) {
        if (n != null) {
            actual.insertar(n.getElem(), actual.getLongitud() + 1);
            if (n.equals(destino)) {
                 res = actual.clone();//Guardamos una copia del camino actual
                
            } else {
                //  Seguimos buscando si no hay un camino O si el camino actual es menor al camino guardado
                if (res.esVacia() || (actual.getLongitud()+1) < res.getLongitud()) {
                    //seguimos buscando un mejor camino
                    NodoAdy ady = n.getPrimerAdy();
                    while (ady != null) {
                        //Verificamos si ya pasamos por ese vertice (estacion)
                        if (actual.localizar(ady.getVertice().getElem()) < 0) {
                            res = caminoMasCortoAux(ady.getVertice(), destino, actual, res);
                        }
                        ady = ady.getSigAdyacente();
                    }
                }
            }
//A la vuelta de la recursión, quitamos el vertice (estacion) actual agregado
            actual.eliminar(actual.getLongitud());
        }
        return res;
    }

    public Lista caminoMasCortoKm(Object origen, Object destino) {
        Lista res = new Lista();
        Lista actual = new Lista();
        double[] min = new double[1];
        min[0] = 999999999.0;
        NodoVert vertOrigen, vertDestino;
        vertOrigen = ubicarVertice(origen);
        vertDestino = ubicarVertice(destino);
        //Verificamos si existen los dos vertices (estaciones)
        if (vertOrigen != null && vertDestino != null) {
            res = caminoMasCortoKmAux(vertOrigen, vertDestino, min, 0, actual, res);
        }
        return res;
    }

    private Lista caminoMasCortoKmAux(NodoVert n, NodoVert destino, double[] kmMin, double acum, Lista actual, Lista res) {
        if (n != null) {

            actual.insertar(n.getElem(), actual.getLongitud() + 1);
            if (n.equals(destino)) {
               
                    kmMin[0] = acum;//Asignamos el km acumulado del camino actual como km min 
                    res = actual.clone();//Guardamos una copia del camino actual encontrado
               
            } else {
               
                    NodoAdy ady = n.getPrimerAdy();
                    while (ady != null) {
                        //Verificamos si ya pasamos por ese vertice (estación)
                        if (actual.localizar(ady.getVertice().getElem()) < 0&&acum+ ady.getDistancia() < kmMin[0]) {//Sumamos la distancia acumulada: acum+distancia (km) 
                            res = caminoMasCortoKmAux(ady.getVertice(), destino, kmMin, (acum + ady.getDistancia()), actual, res);
                        }
                        ady = ady.getSigAdyacente();
                    }

                
            }//A la vuelta de la recursión, quitamos el vertice (estacion) actual agregado 
            actual.eliminar(actual.getLongitud());
        }

        return res;
    }

    public boolean existeCaminoConDistanciaMaxima(Object origen, Object destino, int max) {
        boolean exito = false;
        Lista actual = new Lista();
        NodoVert vertOrigen, vertDestino;
        vertOrigen = ubicarVertice(origen);
        vertDestino = ubicarVertice(destino);
        //verificamos que existan ambos vertices (estaciones)
        if (vertOrigen != null && vertDestino != null) {
            exito = existeCaminoConDistanciaMaximaAux(vertOrigen, vertDestino, 0, max, actual);
        }
        return exito;
    }

    private boolean existeCaminoConDistanciaMaximaAux(NodoVert n, NodoVert destino, double acum, int max, Lista actual) {
        boolean exito = false;
        if (n != null) {
            actual.insertar(n.getElem(), actual.getLongitud() + 1);
            if (n.equals(destino)) {//Si llegamos a destino

                exito = true;

            } else {
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null && !exito) {
                    //verificamos que si ya pasamos por ese vertice (estacion) 
                    if (actual.localizar(ady.getVertice().getElem()) < 0) {
                        if (acum + ady.getDistancia() <= max) {//Verificamos si el valor acumulado se pasa del maximo antes de seguir avanzando 
                            exito = existeCaminoConDistanciaMaximaAux(ady.getVertice(), destino, acum + ady.getDistancia(), max, actual);
                        }
                    }
                    ady = ady.getSigAdyacente();
                }

            }
            //A la vuelta de la recursión, quitamos el vertice (estacion) actual agregado 
            actual.eliminar(actual.getLongitud());

        }
        return exito;
    }

    public Lista listarCaminosQueNoPasanPorUnaEstacion(Object origen, Object destino, Object estacionC) {
        Lista caminos = new Lista();
        Lista actual = new Lista();
        NodoVert vertOrigen, vertDestino;
        vertOrigen = ubicarVertice(origen);
        vertDestino = ubicarVertice(destino);
        //Verificamos que existan los vertices (estaciones) y que no sean iguales a la estacion C por donde no tienen que pasar
        if (vertOrigen != null && vertDestino != null && !origen.equals(estacionC) && !destino.equals(estacionC)) {
            listarCaminosQueNoPasanPorUnaEstacionAux(vertOrigen, vertDestino, estacionC, caminos, actual);
        }
        return caminos;
    }

    private void listarCaminosQueNoPasanPorUnaEstacionAux(NodoVert n, NodoVert destino, Object estacionC, Lista caminos, Lista actual) {

        if (n != null) {
            actual.insertar(n.getElem(), actual.getLongitud() + 1);
            if (n.equals(destino)) {
                //Si llegamos a destino, hacemos una copia del camino actual y lo agregamos a la lista caminos
                Lista copiaCamino = actual.clone();
                caminos.insertar(copiaCamino, caminos.getLongitud() + 1);

            } else {
                NodoAdy ady = n.getPrimerAdy();
                while (ady != null) { 
                    //Verificamos si ya pasamos por ese vertice (estacion) y que no sea igual a la estacion C, por donde no deben pasar 
                    if (actual.localizar(ady.getVertice().getElem()) < 0 && !ady.getVertice().getElem().equals(estacionC)) {
                        listarCaminosQueNoPasanPorUnaEstacionAux(ady.getVertice(), destino, estacionC, caminos, actual);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            //A la vuelta de la recursión, quitamos el vertice (estacion) actual agregado 
            actual.eliminar(actual.getLongitud());
        }

    }

    @Override
    public String toString() {
        String s = "=== Mapa DE VÍAS (CONEXIONES Y DISTANCIAS) ===\n";
        NodoVert auxVert = this.inicio;

        while (auxVert != null) {
            s += "[" + auxVert.getElem() + "]";
            NodoAdy auxAdy = auxVert.getPrimerAdy();

            if (auxAdy == null) {
                s += " <--- (Sin conexiones) --->";
            } else {
                while (auxAdy != null) {
                    s += "\n   --- " + auxAdy.getDistancia() + " km ---> [" + auxAdy.getVertice().getElem() + "]";
                    auxAdy = auxAdy.getSigAdyacente();
                }
            }
            s += "\n--------------------------------------------\n";
            auxVert = auxVert.getSigVertice();
        }
        return s;
    }
}
