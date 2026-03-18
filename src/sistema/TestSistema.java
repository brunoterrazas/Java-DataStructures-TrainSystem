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

            opcion = utiles.Validador.leerEntero(sc, "Seleccione una opción: ");

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
        subOpcion = utiles.Validador.leerEntero(sc, "Opción: ");
        switch (subOpcion) {
            case 1: // ALTA
                String[] campos = new String[8];

                String nombre = utiles.Validador.leerStringNoVacio(sc, "Nombre de la estación: ");
                campos[0] = "E";
                campos[1] = sistema.verificarNombreEstacion(sc, nombre);//Validamos nombre disponible antes de cargar los demas datos bucando diccionario
                campos[2] = utiles.Validador.leerStringNoVacio(sc, "Calle: ");
                campos[3] = utiles.Validador.leerStringNoVacio(sc, "Número: ");
                campos[4] = utiles.Validador.leerStringNoVacio(sc, "Ciudad: ");
                campos[5] = utiles.Validador.leerStringNoVacio(sc, "CP: ");
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
                String nombreEliminar = utiles.Validador.leerStringNoVacio(sc, "Nombre de la estación a eliminar: ");
                nombreEliminar = sistema.existeEstacion(sc, nombreEliminar, "Nombre de estación");//Validamos si la estación existe
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
                String nombreMod = utiles.Validador.leerStringNoVacio(sc, "Nombre de la estación a modificar: ");
                nombreMod = sistema.existeEstacion(sc, nombreMod, "Nombre de estación");//Validamos si la estación existe
                String calle = utiles.Validador.leerStringNoVacio(sc, "Calle: ");
                String numero = utiles.Validador.leerStringNoVacio(sc, "Número: ");
                String ciudad = utiles.Validador.leerStringNoVacio(sc, "Ciudad: ");
                String cp = utiles.Validador.leerStringNoVacio(sc, "CP: ");
                int nVias = utiles.Validador.leerEntero(sc, "Nueva cantidad de Vías: ");
                int nPlat = utiles.Validador.leerEntero(sc, "Nueva cantidad de Plataformas: ");
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
        int op = utiles.Validador.leerEntero(sc, "Opción: ");
        switch (op) {
            case 1:
                String nombre = utiles.Validador.leerStringNoVacio(sc, "Nombre de la estación: ").toUpperCase();
                nombre = sistema.existeEstacion(sc, nombre, "Estacion");
                System.out.println(sistema.obtenerInfoEstacion(nombre));
                break;

            case 2:
                int cod = utiles.Validador.leerEntero(sc, "Código del tren: ");
                System.out.println(sistema.obtenerInfoTren(cod));
                break;

            case 3:
                String prefijo = utiles.Validador.leerStringNoVacio(sc, "Ingrese la primer palabra o prefijo del nombre de la estación: ");
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
        String origen = utiles.Validador.leerStringNoVacio(sc, "Origen: ");
        origen = sistema.existeEstacion(sc, origen, "Origen");
        String destino = utiles.Validador.leerStringNoVacio(sc, "Destino: ");
        destino = sistema.existeEstacion(sc, destino, "Destino");
        System.out.println();

        int op = utiles.Validador.leerEntero(sc, "Opción: ");

        switch (op) {
            case 1:
                System.out.println(sistema.mostrarCaminoMasCorto(origen, destino));
                break;

            case 2:
                System.out.println(sistema.mostrarCaminoMasCortoKm(origen, destino));
                break;

            case 3:
                String estacionC = utiles.Validador.leerStringNoVacio(sc, "Ingrese estación por donde no debe pasar: ");
                estacionC = sistema.existeEstacion(sc, estacionC, "Estacion donde no pasar");
                System.out.println(sistema.mostrarCaminosQueNoPasanPorUnaEstacion(origen, destino, estacionC));
                break;
            case 4:
                int max = utiles.Validador.leerEntero(sc, "Ingrese distancia maxima recorrida: ");
                System.out.println(sistema.mostrarSiExisteCaminoConDistanciaMaxima(origen, destino, max));

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
        int subOp = utiles.Validador.leerEntero(sc, "Opción: ");
        switch (subOp) {
            case 1:
                String[] valor = new String[6];
                String linea = utiles.Validador.leerStringNoVacio(sc, "Línea (o 'Libre'): ");
                int cod = utiles.Validador.leerEntero(sc, "Codigo: ");
                valor[0] = "T";
                valor[1] = String.valueOf(sistema.verificarCodigoTren(sc, cod));
                valor[2] = utiles.Validador.leerStringNoVacio(sc, "Propulsión: ");
                valor[3] = String.valueOf(utiles.Validador.leerEntero(sc, "Capacidad Pasajeros: "));
                valor[4] = String.valueOf(utiles.Validador.leerEntero(sc, "Capacidad Carga: "));
                valor[5] = sistema.existeLinea(sc, linea, "Linea");
                String msgAlta;
                if (sistema.registrarTren(valor)) {
                    msgAlta = "Tren con codigo: " + valor[1] + " registrado correctamente.";
                    System.out.println(msgAlta);
                } else {
                    msgAlta = "Error Tren: codigo " + valor[1] + " duplicado o línea inexistente.";
                    System.out.println(msgAlta);
                }
                utiles.GestorLog.registrarOperacion(msgAlta);
                break;
            case 2:
                String msgBaja;
                int codBaja = utiles.Validador.leerEntero(sc, "Codigo del tren a eliminar: ");
                codBaja = sistema.verificarCodigoTren(sc, codBaja);
                msgBaja = sistema.eliminarTren(codBaja);
                System.out.println(msgBaja);
                utiles.GestorLog.registrarOperacion(msgBaja);
                break;
            case 3:
                int codMod = utiles.Validador.leerEntero(sc, "Codigo del tren: ");
                codMod = sistema.verificarCodigoTren(sc, codMod);
                String nL = utiles.Validador.leerStringNoVacio(sc, "Nombre de nueva Línea (o dejar como 'Libre'): ");
                String msgModificacion = sistema.asignarLineaTren(codMod, nL);
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
        int subOp = utiles.Validador.leerEntero(sc, "Opción: ");

        switch (subOp) {
            case 1: // ALTA
                String[] campos = new String[3];
                campos[0] = utiles.Validador.leerStringNoVacio(sc, "Nombre de la Línea: ");
                campos[1] = utiles.Validador.leerStringNoVacio(sc, "Estacion Origen: ");
                campos[2] = utiles.Validador.leerStringNoVacio(sc, "Estacion Destino: ");

                // Llamamos a tu método de Alta
                String msgAlta = sistema.agregarLineaDesdeCamino(campos);
                System.out.println(msgAlta);
                utiles.GestorLog.registrarOperacion(msgAlta);

                break;

            case 2: // BAJA
                String nombreEliminar = utiles.Validador.leerStringNoVacio(sc, "Nombre de la línea a eliminar: ");
                String msgBaja;
                if (sistema.eliminarLinea(nombreEliminar)) {
                    msgBaja = "Línea " + nombreEliminar + " eliminada correctamente.";
                    System.out.println(msgBaja);
                } else {
                    msgBaja = "Error: Línea " + nombreEliminar + " no encontrada";
                    System.out.println(msgBaja);
                }
                utiles.GestorLog.registrarOperacion(msgBaja);
                break;

            case 3: // MODIFICACIÓN
                String[] valor = new String[3];
                valor[0] = utiles.Validador.leerStringNoVacio(sc, "Nombre de la línea a modificar: ");

                System.out.println("¿Qué tipo de modificación desea realizar?");
                System.out.println("1. Cambiar recorrido de la linea");
                System.out.println("2. Refrescar recorrido"); //Si hubo cambios en las vías
                System.out.print("Opción: ");
                int accionMod = utiles.Validador.leerEntero(sc, "Opción: ");

                switch (accionMod) {
                    case 1:
                        valor[1] = utiles.Validador.leerStringNoVacio(sc, "Nueva Estacion Origen: ");
                        valor[2] = utiles.Validador.leerStringNoVacio(sc, "Nueva Estacion Destino: ");
                        String msgModificacion = sistema.modificarLinea(valor);
                        System.out.println(msgModificacion);
                        utiles.GestorLog.registrarOperacion(msgModificacion);
                        break;
                    case 2:
                        String msgRefr = sistema.refrescarRecorridoLinea(valor[0]);
                        System.out.println(msgRefr);
                        utiles.GestorLog.registrarOperacion(msgRefr);
                        break;
                    default:
                        System.out.println("Opción no válida.");
                        break;
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
        int subOp = utiles.Validador.leerEntero(sc, "Opción: ");

        switch (subOp) {
            case 1: // ALTA (Insertar Arco)

                String[] campos = new String[4];
                String ori,
                 des;
                ori = utiles.Validador.leerStringNoVacio(sc, "Estación Origen: ");
                des = utiles.Validador.leerStringNoVacio(sc, "Estación Destino: ");
                campos[0] = "R";
                campos[1] = sistema.existeEstacion(sc, ori, "Estacion Origen");
                campos[2] = sistema.existeEstacion(sc, des, "Estacion Destino");

                // Usamos doubleMin para que no pongan kilómetros negativos
                double distAlta = utiles.Validador.leerDoubleMin(sc, "Distancia (en KM): ", 0);
                campos[3] = String.valueOf(distAlta);
                String msgAlta;
                if (sistema.agregarRiel(campos)) {
                    msgAlta = "Tramo " + campos[1] + " <--" + campos[3] + " km--> " + campos[2] + "  agregado con éxito a la red ferroviaria.";
                    System.out.println(msgAlta);

                } else {
                    msgAlta = "Error tramo " + campos[1] + " <--" + campos[3] + " km--> " + campos[2] + " : No se pudo agregar.";
                    System.out.println(msgAlta);
                }
                utiles.GestorLog.registrarOperacion(msgAlta);
                break;

            case 2: // BAJA (Eliminar Arco)
                String origenBaja = utiles.Validador.leerStringNoVacio(sc, "Estación Origen: ");
                String destinoBaja = utiles.Validador.leerStringNoVacio(sc, "Estación Destino: ");
                origenBaja = sistema.existeEstacion(sc, origenBaja, "Estación Origen");
                destinoBaja = sistema.existeEstacion(sc, destinoBaja, "Estación Destino");
                String msgBaja;
                if (sistema.eliminarRiel(origenBaja, destinoBaja)) {
                    msgBaja = "Tramo eliminado " + origenBaja + " <----> " + destinoBaja + " de la red.";
                    System.out.println(msgBaja);
                    System.out.println(sistema.debugGrafo());
                } else {
                    msgBaja = "Error: No existe conexión directa entre " + origenBaja + " <----> " + destinoBaja + " .";
                    System.out.println(msgBaja);
                }
                utiles.GestorLog.registrarOperacion(msgBaja);
                break;

            case 3: // MODIFICACIÓN (Actualizar Etiqueta del Arco)
                String origenMod = utiles.Validador.leerStringNoVacio(sc, "Estación Origen: ");
                String destinoMod = utiles.Validador.leerStringNoVacio(sc, "Estación Destino: ");
                origenMod = sistema.existeEstacion(sc, origenMod, "Estacion Origen");
                destinoMod = sistema.existeEstacion(sc, destinoMod, "Estacion Destino");
                double kmMod = utiles.Validador.leerDoubleMin(sc, "Nueva Distancia (en KM): ", 0);
                String msgModificacion;
                if (sistema.modificarDistanciaTramo(origenMod, destinoMod, kmMod)) {
                    msgModificacion = "Distancia entre " + origenMod + " <----> " + destinoMod + " actualizada con éxito.";
                    System.out.println(msgModificacion);
                } else {
                    msgModificacion = "Error: No se encontró el tramo entre " + origenMod + " <----> " + destinoMod + "";
                    System.out.println(msgModificacion);
                }
                utiles.GestorLog.registrarOperacion(msgModificacion);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }
}
