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

    private void registrarRiel(String[] valor) {
        // Insertamos el arco en el grafo con su etiqueta de distancia
        double kms = Double.parseDouble(valor[3].trim());
        mapaVias.insertarArco(valor[1], valor[2], kms);
    }

    public boolean registrarTren(String[] valor) {
        // Creamos el objeto Tren y lo guardamos en el AVL de trenes
       boolean exito=false;
        int id = Integer.parseInt(valor[1].trim());
        //si el hashmap de lineas incluye la linea o esta como linea No-asignado 
         if(lineas.containsKey(valor[5])||valor[5].equals("No-asignado"))
        {
        Tren t = new Tren(id, valor[2], Integer.parseInt(valor[3]), Integer.parseInt(valor[4]), valor[5]);
        exito=trenes.insertar(id, t);
        }
        
        return exito;
    }
    public boolean asignarLineaTren(int idTren, String nomLinea)
    {//Este metodo asigna una linea al Tren
     boolean exito=false;
     //Buscamos si esta en el diccionario de trenes
     Tren tren=(Tren)trenes.obtenerDato(idTren);
     //Si el tren esta en el diccionario
      if(tren!=null)
      { //si el hashmap de lineas incluye la linea o esta como No-asigando 
        if(lineas.containsKey(nomLinea)||nomLinea.equals("No-asignado"))
        {
           tren.setLinea(nomLinea);
           exito=true;
        }
      }
     
     return exito;
    }
        public String eliminarTren(int id)
    {
        String msg;
        Tren tren = (Tren) trenes.obtenerDato(id);

    if (tren != null) {
        // Si el tren no tiene asignada a una linea
        if (tren.getLinea().equals("No-asignado")) {
            if (trenes.eliminar(id)) {
                msg = "Tren " + id + " eliminado correctamente.";
            } else {
                msg = "Error inesperado al eliminar el tren con id: "+id;
            }
        } else {
            msg = "No se puede eliminar: El tren está asignado a la línea '" + tren.getLinea() + "'.";
        }
    }
    else{
       msg="El tren con ID " + id + " no existe!";
       
    }
        return msg;
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
    public boolean registrarEstacion(String[] valor) {
        boolean exito = false;//Da de alta la estación
        if (valor.length >= 8) {
            // Concatenamos el domicilio: Calle Nro, Ciudad (CP)
            String ciudad= valor[4];
            String cp= valor[5];
            String dom = valor[2] + " " + valor[3] + ", " + ciudad + " (" + cp + ")";
            int vias = Integer.parseInt(valor[6].trim());
            int plat = Integer.parseInt(valor[7].trim());

            Estacion est = new Estacion(valor[1], dom,ciudad,cp, vias, plat);

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
            
            // Recorremos todas las líneas y si la estación estaba, la quitamos
        for (Lista recorrido : lineas.values()) {
            int pos = recorrido.localizar(nombre);
            while (pos != -1) { // Por si está repetida en la lista
                recorrido.eliminar(pos);
                pos = recorrido.localizar(nombre);
            }
        }
            // Eliminamos del Grafo (esto borra el vértice y sus arcos/rieles)
            mapaVias.eliminarVertice(nombre);
        }
        return exito;
    }

}
