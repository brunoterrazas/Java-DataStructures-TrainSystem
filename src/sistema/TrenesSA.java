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

    // Constructor e inicialización...
    public TrenesSA() {
        // Inicializar las estructuras
        this.estaciones = new Diccionario();
        this.trenes = new Diccionario();
        this.mapaVias = new Grafo();
        this.lineas = new HashMap<>();
    }

    // Tu método de carga que ya definimos...
    public void cargarDatos(String rutaArchivo) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(rutaArchivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                // Usamos split para separar por punto y coma
                String[] campos = linea.split(";");
                if (campos.length == 0) {
                    continue; // Saltea líneas vacías
                }
                String tipo = campos[0];

                switch (tipo) {
                    case "E":
                        // El split genera un arreglo. Vamos a ser cuidadosos con los índices:
                        // campos[0] = E
                        // campos[1] = Nombre
                        // campos[2] = Calle
                        // campos[3] = Numero
                        // campos[4] = Ciudad
                        // campos[5] = CP
                        // campos[6] = Vias
                        // campos[7] = Plataformas

                        if (campos.length >= 8) {
                            // Unimos los datos de dirección en un solo String "domicilio"
                            String domicilio = campos[2] + " " + campos[3] + ", " + campos[4] + " (" + campos[5] + ")";

                            // Parseamos los últimos dos campos que son los numéricos
                            int vias = Integer.parseInt(campos[6].trim());
                            int plat = Integer.parseInt(campos[7].trim());

                            Estacion est = new Estacion(campos[1], domicilio, vias, plat);

                            // Guardamos en las estructuras
                            estaciones.insertar(est.getNombre(), est);
                            mapaVias.insertarVertice(est.getNombre());
                        } else {
                            System.out.println("⚠️ Saltando línea mal formada: " + linea);
                        }
                        break;

                    case "L":
                        // Formato: L;NombreLinea;Estacion1;Estacion2;...
                        String nombreLinea = campos[1];
                        Lista listaEstaciones = new Lista();
                        // Recorremos desde el índice 2 hasta el final del arreglo campos
                        for (int i = 2; i < campos.length; i++) {
                            listaEstaciones.insertar(campos[i], listaEstaciones.longitud() + 1);
                        }
                        // Guardamos en el HashMap de Java
                        lineas.put(nombreLinea, listaEstaciones);
                        break;

                    case "R":
                        // Formato: R;Origen;Destino;Kilometros
                        String origen = campos[1];
                        String destino = campos[2];
                        double kms = Double.parseDouble(campos[3]);
                        // Se inserta como arco en el Grafo Etiquetado
                        mapaVias.insertarArco(origen, destino, kms);
                        break;

                    case "T":
                        // Formato: T;Codigo;Propulsion;Pasajeros;Carga;Linea
                        int cod = Integer.parseInt(campos[1]);
                        String prop = campos[2];
                        int pas = Integer.parseInt(campos[3]);
                        int car = Integer.parseInt(campos[4]);
                        String lin = campos[5];

                        Tren tren = new Tren(cod, prop, pas, car, lin);
                        // Se guarda en el segundo Diccionario (AVL)
                        trenes.insertar(cod, tren);
                        break;
                }
            }
            br.close();
            System.out.println("Carga de datos finalizada con éxito.");
        } catch (Exception e) {
            System.out.println("Error al leer archivo: " + e.getMessage());
            e.printStackTrace(); // Esto te ayuda a ver en qué línea falló el parseo
        }
    }

    public String debugEstaciones() {
        // Retorna el toString de tu árbol AVL
        return this.estaciones.toString();
    }

    public String debugGrafo() {
        // Retorna el toString de tu Grafo
        return this.mapaVias.toString();
    }

    public String obtenerInfoEstacion(String nombre) {
        String resultado = "La estación no existe en el sistema.";
        // Usamos el obtenerDato del Diccionario (AVL)
        Estacion est = (Estacion) estaciones.obtenerDato(nombre);
        if (est != null) {
            resultado = est.toString();
        }
        return resultado;
    }

    public String obtenerInfoTren(int id) {
        String resultado = "El tren con ID " + id + " no existe.";
        Tren t = (Tren) trenes.obtenerDato(id);
        if (t != null) {
            resultado = t.toString();
        }
        return resultado;
    }

}
