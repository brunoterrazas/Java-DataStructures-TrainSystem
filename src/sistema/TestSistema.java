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
        utiles.GestorLog.registrarEstadoSistema(sistema, "POST-CARGA INICIAL");
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
                    menuConsultasSobreViajes(sistema, sc);
                    break;
                case 7:

                    System.out.println(sistema.debugEstaciones());
                    System.out.println(sistema.debugLineas());
                    System.out.println(sistema.debugGrafo());
                    System.out.println(sistema.debugTrenes());

                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    utiles.GestorLog.registrarEstadoSistema(sistema, "FIN DE EJECUCIÓN");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 0);
    }

    private static void menuABMEstaciones(TrenesSA sistema, Scanner sc) {
        int subOpcion;
        System.out.println(sistema.debugEstaciones());
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
                String msgAlta;
                if (sistema.registrarEstacion(campos)) {
                    msgAlta = "Estación " + campos[1] + " fue dada de alta con éxito.";
                    System.out.println(msgAlta);
                    System.out.println(sistema.debugEstaciones());
                } else {
                    msgAlta = "Error: La estación " + campos[1] + " ya existe o faltan datos.";
                    System.out.println(msgAlta);

                }
                utiles.GestorLog.registrarOperacion(msgAlta);
                break;

            case 2: // BAJA
                System.out.print("Nombre de la estación a eliminar: ");
                String nombreEliminar = sc.nextLine();
                String msgBaja;
                if (sistema.darBajaEstacion(nombreEliminar)) {
                    msgBaja = "Estación " + nombreEliminar + " eliminada del sistema y del mapa.";
                    System.out.println(msgBaja);
                    System.out.println(sistema.debugEstaciones());
                } else {
                    msgBaja = "Error Baja estación: No existe estación " + nombreEliminar;
                    System.out.println(msgBaja);
                }
                utiles.GestorLog.registrarOperacion(msgBaja);
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
                String msgModificacion;
                if (sistema.modificarEstacion(nombreMod, nuevoDomicilio, nVias, nPlat)) {

                    msgModificacion = "Datos actualizados: Se modificó la estación " + nombreMod;
                    System.out.println(msgModificacion);
                    System.out.println(sistema.debugEstaciones());
                } else {

                    msgModificacion = "Error Modificando estación: No existe estación " + nombreMod;
                    System.out.println(msgModificacion);
                }
                utiles.GestorLog.registrarOperacion(msgModificacion);
                break;
        }
    }

    private static void menuConsultas(TrenesSA sistema, Scanner sc) {
        System.out.println("\n--- CONSULTAS ---");
        System.out.println("1. Ver info de Estación");
        System.out.println("2. Ver info de Tren");
        System.out.println("3. Buscar estaciones por prefijo (Ej: Villa, V.)");
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
                valor[0] = "T";
                System.out.print("Codigo: ");
                valor[1] = sc.nextLine();
                System.out.print("Propulsión: ");
                valor[2] = sc.nextLine();
                System.out.print("Capacidad Pasajeros: ");
                valor[3] = sc.nextLine();
                System.out.print("Capacidad Carga: ");
                valor[4] = sc.nextLine();
                System.out.print("Línea (o 'Libre'): ");
                valor[5] = sc.nextLine();
                String msgAlta;
                if (sistema.registrarTren(valor)) {
                    msgAlta = "Tren con codigo: " + valor[1] + " registrado correctamente.";
                    System.out.println(msgAlta);
                } else {
                    msgAlta = "Error Tren: codigo " + valor[1] + "duplicado o línea inexistente.";
                    System.out.println(msgAlta);                   
                }
                 utiles.GestorLog.registrarOperacion(msgAlta);
                break;
            case 2:
                String msgBaja;
                System.out.print("Codigo del tren a eliminar: ");
                int codBaja = sc.nextInt();
                msgBaja = sistema.eliminarTren(codBaja);
                System.out.println(msgBaja);
                utiles.GestorLog.registrarOperacion(msgBaja);
                break;
            case 3:
                System.out.print("Codigo del tren: ");
                int idMod = sc.nextInt();
                sc.nextLine();
                System.out.print("Nombre de nueva Línea (o dejar como 'No-asignado'): ");
                String nL = sc.nextLine();
                String msgModificacion = sistema.asignarLineaTren(idMod, nL);
                System.out.println(msgModificacion);
                utiles.GestorLog.registrarOperacion(msgModificacion);
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
                String msgAlta;
                if (sistema.registrarLinea(campos)) {
                    msgAlta = "Línea registrada " + campos[1] + "con éxito.";
                    utiles.GestorLog.registrarOperacion(msgAlta);
                    System.out.println(msgAlta);
                } else {
                    msgAlta = "Error: La línea " + campos[1] + " ya existe o faltan datos.";
                    System.out.println(msgAlta);
                }
                break;

            case 2: // BAJA
                System.out.print("Nombre de la línea a eliminar: ");
                String nombreEliminar = sc.nextLine();
                String msgBaja;
                if (sistema.eliminarLinea(nombreEliminar)) {
                    msgBaja = "Línea " + nombreEliminar + " eliminada correctamente.";
                    System.out.println(msgBaja);
                } else {
                    msgBaja ="Error: Línea no encontrada";
                    System.out.println(msgBaja);
                }
                    utiles.GestorLog.registrarOperacion(msgBaja);
                break;

            case 3: // MODIFICACIÓN
                System.out.print("Nombre de la línea a modificar: ");
                String nombreLineaMod = sc.nextLine();
                String str = sistema.mostrarLinea(nombreLineaMod);
                if (!str.equals("Error")) {//Si no hubo error mostrar la linea y podremos agregar o quitar la estacion
                    System.out.println(sistema.debugGrafo());
                    System.out.println(str);

                    System.out.println("¿Qué desea hacer?");
                    System.out.println("1. Agregar una estación al recorrido");
                    System.out.println("2. Quitar una estación del recorrido");
                    System.out.print("Opción: ");
                    int accionMod = sc.nextInt();
                    if (accionMod == 1) {
                        System.out.print("Nombre de la estación a agregar: ");
                        String estAgregar = sc.nextLine();

                        System.out.print("Posición en el recorrido (ej: 1 para inicio): ");
                        int pos = sc.nextInt();
                        sc.nextLine();

                        if (sistema.agregarEstacionALinea(nombreLineaMod, estAgregar, pos)) {
                            System.out.println("Estación agregada a la línea con éxito.");
                        } else {
                            System.out.println("Error: No se pudo agregar (verifique estación exista, y la posición sea válida).");
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
                } else {
                    System.out.println("Error: La línea '" + nombreLineaMod + "' no existe en el sistema.\n");
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
                String msgAlta;
                if (sistema.agregarRiel(campos)) {
                    msgAlta = "Tramo " + origenAlta + " <--" + kmAlta + " km--> " + destinoAlta + "  agregado con éxito a la red ferroviaria.";
                    System.out.println(msgAlta);

                } else {
                    msgAlta="Error tramo " + origenAlta + " <--" + kmAlta + " km--> " + destinoAlta + " : No se pudo agregar.";
                    System.out.println(msgAlta);
                }
                utiles.GestorLog.registrarOperacion(msgAlta);
                break;

            case 2: // BAJA (Eliminar Arco)
                System.out.print("Estación Origen: ");
                String origenBaja = sc.nextLine();
                System.out.print("Estación Destino: ");
                String destinoBaja = sc.nextLine();
                String msgBaja;
                if (sistema.eliminarRiel(origenBaja, destinoBaja)) {
                    msgBaja="Tramo eliminado " + origenBaja + " <----> " + destinoBaja + " de la red.";
                    System.out.println(msgBaja);
                    System.out.println(sistema.debugGrafo());
                } else {
                    msgBaja="Error: No existe conexión directa entre " + origenBaja + " <----> " + destinoBaja + " .";
                    System.out.println(msgBaja);
                }
                 utiles.GestorLog.registrarOperacion(msgBaja);
                break;

            case 3: // MODIFICACIÓN (Actualizar Etiqueta del Arco)
                System.out.print("Estación Origen: ");
                String origenMod = sc.nextLine();
                System.out.print("Estación Destino: ");
                String destinoMod = sc.nextLine();
                System.out.print("Nueva Distancia (en KM): ");
                double kmMod = sc.nextDouble();
                sc.nextLine();
                String msgModificacion;
                if (sistema.modificarDistanciaTramo(origenMod, destinoMod, kmMod)) {
                    msgModificacion="Distancia entre " + origenMod + " <----> " + destinoMod + " actualizada con éxito.";
                    System.out.println(msgModificacion);
                } else {
                    msgModificacion="Error: No se encontró el tramo entre " + origenMod + " <----> " + destinoMod + "";
                    System.out.println(msgModificacion);
                }
                utiles.GestorLog.registrarOperacion(msgModificacion);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }
}
