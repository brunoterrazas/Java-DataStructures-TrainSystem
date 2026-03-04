/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import dominio.Estacion;
import dominio.Tren;

import especiales.Diccionario;
import grafos.Grafo;
import java.io.BufferedReader;
import java.io.FileReader;
import lineales.dinamicas.Lista;
import java.util.HashMap;

/**
 *
 * @author Brunot
 */
public class TrenesSA {

    private Diccionario estaciones;
    private Diccionario trenes;
    private Grafo mapaVias;
    private HashMap<String, Lista> lineas;

    public TrenesSA() {

        this.estaciones = new Diccionario();
        this.trenes = new Diccionario();
        this.mapaVias = new Grafo();
        this.lineas = new HashMap<>();
    }

    public void cargarDatos(String rutaArchivo) {
//Precargamos datos del sistema
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String registro;
            while ((registro = br.readLine()) != null) {
                if (registro.trim().isEmpty()) {
                    continue;
                }

                String[] campos = registro.split(";");
                procesarEntrada(campos);
            }
            System.out.println("Carga de estructuras finalizada exitosamente.");
        } catch (Exception e) {
            System.err.println("Error en la carga de datos: " + e.getMessage());
        }
    }

    private void procesarEntrada(String[] campos) {
        String tipo = campos[0];
        switch (tipo) {
            case "E":
                registrarEstacion(campos);
                break;
            case "L":
                registrarLinea(campos);
                break;
            case "R":
                registrarRiel(campos);
                break;
            case "T":
                registrarTren(campos);
                break;
        }
    }

    public void registrarLinea(String[] c) {
        // Usamos Lista para almacenar el recorrido
        String nombreL = c[1];
        Lista listaEst = new Lista();

        // Recorremos las estaciones de la línea (desde el índice 2 en adelante)
        for (int i = 2; i < c.length; i++) {
            listaEst.insertar(c[i], listaEst.longitud() + 1);
        }
        lineas.put(nombreL, listaEst);
    }

    public boolean agregarEstacionALinea(String nombreL, String nombreEst, int pos) {
        boolean exito = false;

        // Buscamos la lista de esa línea en el hashmap
        Lista recorrido = (Lista) lineas.get(nombreL);

        if (recorrido != null) {
            // Si la estación existe en el diccionario de estaciones (AVL)
            if (estaciones.obtenerDato(nombreEst) != null) {
                // Insertamos en la lista (se actualiza en el hashmap por referencia)
                exito = recorrido.insertar(nombreEst, pos);
            }
        }
        return exito;
    }

    private void registrarRiel(String[] c) {
        // Insertamos el arco en el grafo con su etiqueta de distancia
        double kms = Double.parseDouble(c[3].trim());
        mapaVias.insertarArco(c[1], c[2], kms);
    }

    private void registrarTren(String[] c) {
        // Creamos el objeto Tren y lo guardamos en el AVL de trenes
        int id = Integer.parseInt(c[1].trim());
        Tren t = new Tren(id, c[2], Integer.parseInt(c[3]), Integer.parseInt(c[4]), c[5]);
        trenes.insertar(id, t);
    }

    public String debugEstaciones() {
        // Retorna el toString de tu arbol AVL
        return this.estaciones.toString();
    }

    public String debugGrafo() {
        // Retorna el toString de tu Grafo
        return this.mapaVias.toString();
    }

    public String obtenerInfoEstacion(String nombre) {
        String resultado = "La estación no existe en el sistema.";
        Estacion est = (Estacion) estaciones.obtenerDato(nombre);
        if (est != null) {
            resultado = est.toString();
        }
        return resultado;
    }

    public String obtenerInfoTren(int cod) {
        String resultado = "El tren con ID " + cod + " no existe.";
        Tren t = (Tren) trenes.obtenerDato(cod);
        if (t != null) {
            resultado = t.toString();
        }
        return resultado;
    }

    //ABM ESTACIÓN
    public boolean registrarEstacion(String[] c) {
        boolean exito = false;//Da de alta la estación
        if (c.length >= 8) {
            // Concatenamos el domicilio: Calle Nro, Ciudad (CP)
            String dom = c[2] + " " + c[3] + ", " + c[4] + " (" + c[5] + ")";
            int vias = Integer.parseInt(c[6].trim());
            int plat = Integer.parseInt(c[7].trim());

            Estacion est = new Estacion(c[1], dom, vias, plat);

            // Insertamos en el Diccionario para búsquedas y Grafo como vértice
            estaciones.insertar(est.getNombre(), est);
            mapaVias.insertarVertice(est.getNombre());
            exito = true;
        }
        return exito;
    }

    public boolean modificarEstacion(String nombre, String nuevoDom, int nuevasVias, int nuevasPlat) {
        boolean exito = false;

        //Buscamos si existe
        Object dato = estaciones.obtenerDato(nombre);

        if (dato != null) {
            Estacion est = (Estacion) dato;

            // Validamos cantidades
            if (nuevasVias >= 0 && nuevasPlat >= 0) {
                est.setDomicilio(nuevoDom);
                est.setCantVias(nuevasVias);
                est.setCantPlataformas(nuevasPlat);
                exito = true;
            }
        }
        return exito;
    }

    public boolean darBajaEstacion(String nombre) {
        boolean exito;
        // Eliminamos del Diccionario
        exito = estaciones.eliminar(nombre);
        if (exito) {
            // Eliminamos del Grafo (esto borra el vértice y sus arcos/rieles)
            mapaVias.eliminarVertice(nombre);
        }
        return exito;
    }

}
