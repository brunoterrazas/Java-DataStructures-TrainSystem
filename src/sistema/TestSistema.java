/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.util.Scanner;
import lineales.dinamicas.Lista;

/**
 *
 * @author Brunot
 */
public class TestSistema {

    public static void main(String[] args) {
        TrenesSA sistema = new TrenesSA();
        Scanner sc = new Scanner(System.in);
        System.out.println("--- Cargando datos del sistema ---");
        sistema.cargarDatos("sistema.txt");

        int opcion;

        do {
            System.out.println("\n========================================");
            System.out.println("       SISTEMA TrenesSA - Menu");
            System.out.println("========================================");
            System.out.println("1. ABM Estaciones");
            System.out.println("2. ABM Trenes");
            System.out.println("3. ABM Lineas");
            System.out.println("4. ABM Red de rieles");
            System.out.println("5. Consultar Información");
            System.out.println("6. Consultar sobre viajes (caminos)");
            System.out.println("7. Ver Estado del Sistema (Debug)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    menuABMEstaciones(sistema, sc);
                    break;
                case 2:
                    menuABMTrenes(sistema, sc);
                    break;
                case 3:
                    menuABMLineas(sistema, sc);
                    break;
                case 4:
                    menuABMRieles(sistema, sc);
                    break;
                case 5:
                    menuConsultas(sistema, sc);
                    break;
                case 6:
                    System.out.println(sistema.debugEstaciones());
                    System.out.println(sistema.debugGrafo());
                    break;
                case 7:
                    menuConsultasSobreViajes(sistema, sc);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void menuABMEstaciones(TrenesSA sistema, Scanner sc) {
        int subOpcion;
        System.out.println("\n--- ABM ESTACIONES ---");
        System.out.println("1. Alta de Estación");
        System.out.println("2. Baja de Estación");
        System.out.println("3. Modificación de Estación");
        System.out.print("Opción: ");
        subOpcion = sc.nextInt();
        sc.nextLine();

        switch (subOpcion) {
            case 1: // ALTA
                String[] campos = new String[8];
                campos[0] = "E";
                System.out.print("Nombre: ");
                campos[1] = sc.nextLine();
                System.out.print("Calle: ");
                campos[2] = sc.nextLine();
                System.out.print("Número: ");
                campos[3] = sc.nextLine();
                System.out.print("Ciudad: ");
                campos[4] = sc.nextLine();
                System.out.print("CP: ");
                campos[5] = sc.nextLine();
                System.out.print("Cantidad de Vías: ");
                campos[6] = sc.nextLine();
                System.out.print("Cantidad de Plataformas: ");
                campos[7] = sc.nextLine();

                if (sistema.registrarEstacion(campos)) {
                    System.out.println("Estación dada de alta con éxito.");
                } else {
                    System.out.println("Error: La estación ya existe o faltan datos.");
                }
                break;

            case 2: // BAJA
                System.out.print("Nombre de la estación a eliminar: ");
                String nombreEliminar = sc.nextLine();
                if (sistema.darBajaEstacion(nombreEliminar)) {
                    System.out.println("Estación eliminada del sistema y del mapa.");
                } else {
                    System.out.println("Error: Estación no encontrada.");
                }
                break;

            case 3: // MODIFICACIÓN
                System.out.print("Nombre de la estación a modificar: ");
                String nombreMod = sc.nextLine();
                System.out.print("Calle: ");
                String calle = sc.nextLine();
                System.out.print("Número: ");
                String numero = sc.nextLine();
                System.out.print("Ciudad: ");
                String ciudad = sc.nextLine();
                System.out.print("CP: ");
                String cp = sc.nextLine();
                System.out.print("Nueva cantidad de Vías: ");
                int nVias = sc.nextInt();
                System.out.print("Nueva cantidad de Plataformas: ");
                int nPlat = sc.nextInt();
                String nuevoDomicilio = calle + " " + numero + ", " + ciudad + " (" + cp + ")";
                if (sistema.modificarEstacion(nombreMod, nuevoDomicilio, nVias, nPlat)) {
                    System.out.println("Datos actualizados correctamente.");
                } else {
                    System.out.println("Error: Estación no encontrada.");
                }
                break;
        }
    }

    private static void menuConsultas(TrenesSA sistema, Scanner sc) {
        System.out.println("\n--- CONSULTAS ---");
        System.out.println("1. Ver info de Estación");
        System.out.println("2. Ver info de Tren");
        System.out.println("3. Buscar estaciones por prefijo (Ej: Villa, Gral)");
        System.out.print("Opción: ");
        int op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1:
                System.out.print("Nombre de la estación: ");
                String nombre = sc.nextLine();
                System.out.println(sistema.obtenerInfoEstacion(nombre));
                break;

            case 2:
                System.out.print("Código del tren: ");
                int cod = sc.nextInt();
                System.out.println(sistema.obtenerInfoTren(cod));
                break;

            case 3:
                System.out.print("Ingrese la primer palabra o prefijo del nombre de  la estación: ");
                String prefijo = sc.nextLine();
                Lista encontradas = sistema.obtenerEstacionesPorPrefijo(prefijo);

                if (encontradas.esVacia()) {
                    System.out.println("No se encontraron estaciones que comiencen con '" + prefijo + "'.");
                } else {
                    System.out.println("Estaciones encontradas en el sistema:");
                    System.out.println(encontradas.toString());
                }
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void menuConsultasSobreViajes(TrenesSA sistema, Scanner sc) {
        System.out.println("\n--- CONSULTAS SOBRE VIAJES---");
        System.out.println("1. Obtener camino con menos estaciones");
        System.out.println("2. Obtener camino con menos distacia recorrida en km");
        System.out.println("3. Obtener  todos los caminos sin pasar por una estacion especifica");
        System.out.println("4. Verificar camino que recorra una cantidad maxima de km");
        System.out.print("Origen: ");
        String origen = sc.nextLine();

        System.out.print("Destino: ");
        String destino = sc.nextLine();
        System.out.println();

        System.out.print("Opción: ");
        int op = sc.nextInt();
        sc.nextLine();

        switch (op) {
            case 1:
                System.out.println(sistema.mostrarCaminoMasCorto(origen, destino));
                break;

            case 2:
                System.out.println(sistema.mostrarCaminoMasCortoKm(origen, destino));
                break;

            case 3:
                System.out.print("Ingrese distancia maxima recorrida: ");
                int max = sc.nextInt();
                System.out.println(sistema.mostrarSiExisteCaminoConDistanciaMaxima(origen, destino, max));
                break;
            case 4:

                System.out.print("Ingrese estación por donde no debe pasar: ");
                String estacionC = sc.nextLine();
                System.out.println(sistema.mostrarCaminosQueNoPasanPorUnaEstacion(origen, destino, estacionC));
                break;

            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void menuABMTrenes(TrenesSA sistema, Scanner sc) {
        System.out.println("\n--- ABM TRENES ---");
        System.out.println("1. Alta de Tren");
        System.out.println("2. Baja de Tren (Solo si no está asignado)");
        System.out.println("3. Asignar/Cambiar Línea a Tren");
        System.out.print("Opción: ");
        int subOp = sc.nextInt();
        sc.nextLine();

        switch (subOp) {
            case 1:
                String[] valor = new String[6];
                System.out.print("ID: ");
                valor[1] = sc.nextLine();
                System.out.print("Propulsión: ");
                valor[2] = sc.nextLine();
                System.out.print("Capacidad Pasajeros: ");
                valor[3] = sc.nextLine();
                System.out.print("Capacidad Carga: ");
                valor[4] = sc.nextLine();
                System.out.print("Línea (o 'No-asignado'): ");
                valor[5] = sc.nextLine();
                if (sistema.registrarTren(valor)) {
                    System.out.println("Tren registrado correctamente.");
                } else {
                    System.out.println("Error: ID duplicado o línea inexistente.");
                }
                break;
            case 2:
                System.out.print("ID del tren a eliminar: ");
                int idBaja = sc.nextInt();
                System.out.println(sistema.eliminarTren(idBaja));
                break;
            case 3:
                System.out.print("ID del tren: ");
                int idMod = sc.nextInt();
                sc.nextLine();
                System.out.print("Nombre de nueva Línea (o dejar como 'No-asignado'): ");
                String nL = sc.nextLine();
                if (sistema.asignarLineaTren(idMod, nL)) {
                    System.out.println("Línea actualizada con éxito.");
                } else {
                    System.out.println("Error: No se pudo actualizar (verifique ID y nombre de línea).");
                }
                break;
        }
    }

    private static void menuABMLineas(TrenesSA sistema, Scanner sc) {
        System.out.println("\n--- ABM LÍNEAS ---");
        System.out.println("1. Alta de Línea");
        System.out.println("2. Baja de Línea");
        System.out.println("3. Modificación de Línea");
        System.out.print("Opción: ");
        int subOp = sc.nextInt();
        sc.nextLine();

        switch (subOp) {
            case 1: // ALTA
                String[] campos = new String[4];
                campos[0] = "L"; // Tipo de registro
                System.out.print("Nombre de la Línea: ");
                campos[1] = sc.nextLine();
                System.out.print("Ciudad Origen: ");
                campos[2] = sc.nextLine();
                System.out.print("Ciudad Destino: ");
                campos[3] = sc.nextLine();

                if (sistema.registrarLinea(campos)) {
                    System.out.println("Línea registrada con éxito.");
                } else {
                    System.out.println("Error: La línea ya existe o faltan datos.");
                }
                break;

            case 2: // BAJA
                System.out.print("Nombre de la línea a eliminar: ");
                String nombreEliminar = sc.nextLine();
                if (sistema.eliminarLinea(nombreEliminar)) {
                    System.out.println("Línea eliminada correctamente.");
                } else {
                    System.out.println("Error: Línea no encontrada o tiene trenes asignados.");
                }
                break;

            case 3: // MODIFICACIÓN
                System.out.print("Nombre de la línea a modificar: ");
                String nombreLineaMod = sc.nextLine();

                System.out.println("¿Qué desea hacer?");
                System.out.println("1. Agregar una estación al recorrido");
                System.out.println("2. Quitar una estación del recorrido");
                System.out.print("Opción: ");
                int accionMod = sc.nextInt();
                sc.nextLine();

                if (accionMod == 1) {
                    System.out.print("Nombre de la estación a agregar: ");
                    String estAgregar = sc.nextLine();
                    System.out.print("Posición en el recorrido (ej: 1 para inicio): ");
                    int pos = sc.nextInt();
                    sc.nextLine();

                    if (sistema.agregarEstacionALinea(nombreLineaMod, estAgregar, pos)) {
                        System.out.println("Estación agregada a la línea con éxito.");
                    } else {
                        System.out.println("Error: No se pudo agregar (verifique que la línea y estación existan, y la posición sea válida).");
                    }

                } else if (accionMod == 2) {
                    System.out.print("Nombre de la estación a quitar: ");
                    String estQuitar = sc.nextLine();

                    if (sistema.quitarEstacionDeLinea(nombreLineaMod, estQuitar)) {
                        System.out.println("Estación removida de la línea con éxito.");
                    } else {
                        System.out.println("Error: La estación no pertenece a la línea o la línea no existe.");
                    }

                } else {
                    System.out.println("Opción no válida.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void menuABMRieles(TrenesSA sistema, Scanner sc) {
        System.out.println("\n--- ABM RED DE RIELES (TRAMOS) ---");
        System.out.println("1. Alta de Tramo (Conectar dos estaciones)");
        System.out.println("2. Baja de Tramo (Desconectar estaciones)");
        System.out.println("3. Modificación de Tramo (Cambiar distancia)");
        System.out.print("Opción: ");
        int subOp = sc.nextInt();
        sc.nextLine();

        switch (subOp) {
            case 1: // ALTA (Insertar Arco)
                System.out.print("Estación Origen: ");
                String origenAlta = sc.nextLine();
                System.out.print("Estación Destino: ");
                String destinoAlta = sc.nextLine();
                System.out.print("Distancia (en KM): ");
                String kmAlta = sc.nextLine();
                String[] campos = new String[4];
                campos[0] = "R";
                campos[1] = origenAlta;
                campos[2] = destinoAlta;
                campos[3] = kmAlta;
                // Suponiendo que tu método en TrenesSA se llama insertarTramo y delega al grafo
                if (sistema.agregarRiel(campos)) {
                    System.out.println("Tramo agregado con éxito a la red ferroviaria.");
                } else {
                    System.out.println("Error: No se pudo agregar. Verifique que ambas estaciones existan.");
                }
                break;

            case 2: // BAJA (Eliminar Arco)
                System.out.print("Estación Origen: ");
                String origenBaja = sc.nextLine();
                System.out.print("Estación Destino: ");
                String destinoBaja = sc.nextLine();

                if (sistema.eliminarRiel(origenBaja, destinoBaja)) {
                    System.out.println("Tramo eliminado de la red.");
                } else {
                    System.out.println("Error: No existe conexión directa entre esas estaciones.");
                }
                break;

            case 3: // MODIFICACIÓN (Actualizar Etiqueta del Arco)
                System.out.print("Estación Origen: ");
                String origenMod = sc.nextLine();
                System.out.print("Estación Destino: ");
                String destinoMod = sc.nextLine();
                System.out.print("Nueva Distancia (en KM): ");
                double kmMod = sc.nextDouble();
                sc.nextLine();

                if (sistema.modificarDistanciaTramo(origenMod, destinoMod, kmMod)) {
                    System.out.println("Distancia actualizada con éxito.");
                } else {
                    System.out.println("Error: No se encontró el tramo o las estaciones.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }
}
