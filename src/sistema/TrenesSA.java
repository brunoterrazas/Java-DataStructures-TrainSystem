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
                agregarRiel(campos);
                break;
            case "T":
                registrarTren(campos);
                break;
        }
    }

    public boolean registrarLinea(String[] c) {
        // Usamos Lista para almacenar el recorrido
        String nombreL = c[1];
        Lista listaEst = new Lista();
        boolean exito;
        // Recorremos las estaciones de la línea (desde el índice 2 en adelante)
        for (int i = 2; i < c.length; i++) {
            listaEst.insertar(c[i], listaEst.getLongitud() + 1);
        }
        lineas.put(nombreL, listaEst);
        exito = true;
        return exito;
    }

    public boolean agregarEstacionALinea(String nombreL, String nombreEst, int pos) {
        boolean exito = false;

        // Buscamos la lista de esa línea en el hashmap
        Lista recorrido = (Lista) lineas.get(nombreL);

        if (recorrido != null) {
            int maxPosValida = recorrido.getLongitud() + 1;
            //Validamos que sea un posicion valida
            if (pos >= 1 && pos <= maxPosValida) {
                // Si la estación existe en el diccionario de estaciones (AVL)
                if (estaciones.obtenerDato(nombreEst) != null) {
                    // Insertamos en la lista (se actualiza en el hashmap por referencia)
                    exito = recorrido.insertar(nombreEst, pos);
                }
            }
        }
        return exito;
    }

    public boolean quitarEstacionDeLinea(String nombreLinea, String nombreEst) {
        boolean exito = false;
        // Buscamos la lista de esa línea en el hashmap
        Lista recorrido = (Lista) lineas.get(nombreLinea);

        if (recorrido != null) {
            // Buscamos en qué posición de la lista está esa estación
            int pos = recorrido.localizar(nombreEst);
            if (pos > 0) {
                // Si la encontramos, la eliminamos de la lista de la línea
                exito = recorrido.eliminar(pos);
            }
        }
        return exito;
    }

    public boolean eliminarLinea(String nombreLinea) {
        boolean exito = false;

        //Verificamos si la línea existe en el HashMap de líneas y Diccionario
        if (lineas.get(nombreLinea) != null) {

            // Obtenemos los trenes
            Lista listaTrenes = trenes.listarDatos();

            int i = 1;
            while (i <= listaTrenes.getLongitud()) {
                // Recuperamos el tren de la posición i
                Tren tren = (Tren) listaTrenes.recuperar(i);

                // Si el tren estaba asignado a la línea que vamos a eliminar
                if (tren != null && tren.getLinea().equals(nombreLinea)) {
                    // Lo dejamos como Libre No asignado
                    tren.setLinea("libre");
                }
                i++;
            }

            //Eliminamos la línea definitivamente
            lineas.remove(nombreLinea);
            exito = true;
        }

        return exito;
    }

    public boolean agregarRiel(String[] valor) {
        // Insertamos el arco en el grafo con su etiqueta de distancia
        double kms = Double.parseDouble(valor[3].trim());
        return mapaVias.insertarArco(valor[1], valor[2], kms);
    }

    public boolean eliminarRiel(Object origen, Object destino) {
        // Quitamos el arco en el grafo del tramo(origen,destino)

        return mapaVias.eliminarArco(origen, destino);
    }

    public boolean modificarDistanciaTramo(String origen, String destino, double nuevaDistancia) {
        boolean exito = false;

        // 1. Verificamos que la conexión exista actualmente en el grafo
        if (mapaVias.existeArco(origen, destino)) {

            // 2. Eliminamos la vía vieja
            mapaVias.eliminarArco(origen, destino);

            // 3. Insertamos la vía nueva con la distancia actualizada
            exito = mapaVias.insertarArco(origen, destino, nuevaDistancia);
        }

        return exito;
    }

    public boolean registrarTren(String[] valor) {
        // Creamos el objeto Tren y lo guardamos en el AVL de trenes
        boolean exito = false;
        int id = Integer.parseInt(valor[1].trim());
        //si el hashmap de lineas incluye la linea o esta como linea Libre No-asignado 
        if (lineas.containsKey(valor[5]) || valor[5].equalsIgnoreCase("libre")) {
            Tren tren = new Tren(id, valor[2], Integer.parseInt(valor[3]), Integer.parseInt(valor[4]), valor[5]);
            exito = trenes.insertar(id, tren);
        }

        return exito;
    }

    public String asignarLineaTren(int codTren, String nomLinea) {//Este metodo asigna una linea al Tren
        String msg;
        //Buscamos si esta en el diccionario de trenes
        Tren tren = (Tren) trenes.obtenerDato(codTren);
        //Si el tren esta en el diccionario
        if (tren != null) { //si el hashmap de lineas incluye la linea o esta como Libre No-asigando 
            if (lineas.containsKey(nomLinea) || nomLinea.equalsIgnoreCase("libre")) {
                tren.setLinea(nomLinea);
                msg = "Tren actualizado, con codigo: " + codTren + ", se asigno la linea " + nomLinea + "correctamente";
            } else {
                msg = "No se se pudo actualizar el Tren con codigo " + codTren + ", porque  la linea no es correcta";
            }
        } else {
            msg = "No se encuentra un tren con codigo: " + codTren;
        }

        return msg;
    }

    public String eliminarTren(int cod) {
        String msg;
        Tren tren = (Tren) trenes.obtenerDato(cod);

        if (tren != null) {
            // Si el tren no tiene asignada a una linea
            if (tren.getLinea().equalsIgnoreCase(("libre"))) {
                if (trenes.eliminar(cod)) {
                    msg = "Tren " + cod + " eliminado correctamente.";
                } else {
                    msg = "Error inesperado al eliminar el tren con codigo: " + cod;
                }
            } else {
                msg = "No se puede eliminar: El tren está asignado a la línea '" + tren.getLinea() + "'.";
            }
        } else {
            msg = "El tren con codigo " + cod + " no existe!";

        }
        return msg;
    }

    public String debugEstaciones() {
        // Retorna el toString de tu arbol AVL
        String str = "=== ESTACIONES ===\n";
        return str + this.estaciones.toString();
    }

    public String debugGrafo() {
        // Retorna el toString de tu Grafo

        return this.mapaVias.toString();
    }
    public String debugTrenes() {
        // Retorna el toString de tu arbol AVL
        String str = "=== TRENES ===\n";
        return str + this.trenes.toString();
    }
    public String debugLineas() {
        String resultado = "=== LÍNEAS DEL SISTEMA ===\n";

        if (lineas.isEmpty()) {
            resultado += "No hay líneas registradas en el sistema.\n";
        } else {
            // Recorremos todas las claves (nombres de líneas) del HashMap
            for (String nombreLinea : lineas.keySet()) {
                // Obtenemos la lista de estaciones de esa línea
                Lista recorrido = lineas.get(nombreLinea);

                resultado += "Línea: " + nombreLinea + "\n";
                resultado += "Recorrido: " + recorrido.toString() + "\n";
                resultado += "----------------------------------------\n";
            }
        }

        return resultado;
    }

    public String mostrarLinea(String nombreLinea) {
        String resultado = "";

        // Buscamos la lista de la línea en el HashMap
        Lista recorrido = lineas.get(nombreLinea);

        // Verificamos si realmente existe
        if (recorrido != null) {
            resultado += "=== INFORMACIÓN DE LA LÍNEA: " + nombreLinea + " ===\n";

            // Verificamos si la lista está vacía
            if (recorrido.esVacia()) {
                resultado += "Recorrido: sin estaciones \n";
            } else {
                resultado += "Recorrido: " + recorrido.toString() + "\n";
            }
            resultado += "-------------------------------\n";
        } else {
            // No existe esa clave en el HashMap
            resultado = "Error";
        }

        return resultado;
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
        String resultado = "El tren con codigo " + cod + " no existe.";
        Tren tren = (Tren) trenes.obtenerDato(cod);
        if (tren != null) {
            resultado = "=== INFO TREN con codigo: " + cod + " ===\n" + tren.toString();

            if (tren.getLinea().equals("libre")) {
                resultado = resultado + "\nEstado: Disponible (Sin línea asignada).";
            } else {
                // Llamamos al método obtener cidudades
                resultado = resultado + "\n\n" + obtenerCiudadesVisitadas(tren.getLinea());
            }
        }
        return resultado;
    }

    private String obtenerCiudadesVisitadas(String nombreLinea) {
        String itinerario = "";
        Lista recorrido = lineas.get(nombreLinea);

        if (recorrido == null || recorrido.esVacia()) {
            itinerario = "No hay paradas registradas para la línea " + nombreLinea;
        } else {
            itinerario = "Ciudades que visitara:";
            itinerario = itinerario + "\n--------------------------------------------";

            for (int i = 1; i <= recorrido.longitud(); i++) {
                String nombreEst = (String) recorrido.recuperar(i);
                // Buscamos el objeto Estacion en el AVL para extraer la ciudad
                Estacion estObj = (Estacion) estaciones.obtenerDato(nombreEst);

                itinerario = itinerario + "\n [" + i + "] " + nombreEst;

                if (estObj != null) {
                    itinerario = itinerario + " - Ciudad: " + estObj.getCiudad();
                } else {
                    itinerario = itinerario + " - (Sin datos de ciudad)";
                }
            }
            itinerario = itinerario + "\n--------------------------------------------";
        }
        return itinerario;
    }

    public String mostrarTodosLosTrenes() {
        String resultado = "=== TODOS LOS TRENES REGISTRADOS ===\n\n";

        // 1. Obtenemos todos los trenes almacenados en el AVL
        Lista listaTrenes = trenes.listarDatos();

        if (listaTrenes.esVacia()) {
            resultado += "No hay trenes registrados en el sistema.\n";
        } else {
            for (int i = 1; i <= listaTrenes.longitud(); i++) {
                // Recuperamos el objeto Tren de la posición actual
                Tren tren = (Tren) listaTrenes.recuperar(i);

                resultado += obtenerInfoTren(tren.getCodigo()) + "\n";
                resultado += "............................................\n\n";
            }
        }

        return resultado;
    }

    //ABM ESTACIÓN
    public boolean registrarEstacion(String[] valor) {
        boolean exito = false;//Da de alta la estación
        if (valor.length >= 8) {
            // Concatenamos el domicilio: Calle, Nro
            String ciudad = valor[4];
            String cp = valor[5];
            String dom = valor[2] + " " + valor[3] + ", " + ciudad + " (" + cp + ")";
            int vias = Integer.parseInt(valor[6].trim());
            int plat = Integer.parseInt(valor[7].trim());

            Estacion est = new Estacion(valor[1], dom, ciudad, cp, vias, plat);

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

    public Lista obtenerEstacionesPorPrefijo(String prefijo) {
        // Definimos el rango: desde el prefijo hasta el prefijo + "ZZZZ"
        String inicio = prefijo;//subcadena
        String fin = prefijo + "ZZZZ";

        // Llamamos al método listarRango del AVL de estaciones
        return estaciones.listarRango(inicio, fin);
    }

    public String mostrarCaminoMasCorto(Object origen, Object destino) {
        String str = "\n--- Camino con menos estaciones (" + origen + " -> " + destino + ") ---\n";
        Lista rutaEstaciones = mapaVias.caminoMasCorto(origen, destino);
        str += "Ruta mas corta (estaciones): " + rutaEstaciones.toString();

        return str;
    }

    public String mostrarCaminoMasCortoKm(Object origen, Object destino) {
        String str = "\n--- Camino con menos distancia recorrida en KM  (" + origen + " -> " + destino + ") ---\n";
        Lista rutaEstaciones = mapaVias.caminoMasCortoKm(origen, destino);
        str += "Ruta con menos KM): " + rutaEstaciones.toString();

        return str;
    }

    public String mostrarCaminosQueNoPasanPorUnaEstacion(Object origen, Object destino, Object estacionC) {
        String str = "\n--- Todos los caminos de " + origen + " -> " + destino + " ---\n";
        Lista rutaEstaciones = mapaVias.listarCaminosQueNoPasanPorUnaEstacion(origen, destino, estacionC);
        str += "Caminos: " + rutaEstaciones.toString();

        return str;
    }

    public String mostrarSiExisteCaminoConDistanciaMaxima(Object origen, Object destino, int maximaDistancia) {
        String str;
        if (mapaVias.existeCaminoConDistanciaMaxima(origen, destino, maximaDistancia)) {
            str = "\n> Existe camino De '" + origen + "' a '" + destino + "' con Max: " + maximaDistancia + "km.";
        } else {
            str = "\n> No existe camino De '" + origen + "' a '" + destino + "' con Max: " + maximaDistancia + "km.";
        }

        return str;
    }

}
