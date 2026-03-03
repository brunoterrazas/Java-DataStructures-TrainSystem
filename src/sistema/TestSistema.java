/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import java.util.Scanner;

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
            System.out.println("2. Consultar Información");
            System.out.println("3. Ver Estado del Sistema (Debug)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = sc.nextInt();
            sc.nextLine(); 

            switch (opcion) {
                case 1:
                    menuABMEstaciones(sistema, sc);
                    break;
                case 2:
                    menuConsultas(sistema, sc);
                    break;
                case 3:
                    System.out.println(sistema.debugEstaciones());
                    System.out.println(sistema.debugGrafo());
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
                campos[0] = "E"; // Simulamos el tipo de registro
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
                String nuevoDomicilio= calle + " " + numero + ", " + ciudad + " (" + cp + ")";
                if (sistema.modificarEstacion(nombreMod,nuevoDomicilio, nVias, nPlat)) {
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
        System.out.print("Opción: ");
        int op = sc.nextInt();
        sc.nextLine();

        if (op == 1) {
            System.out.print("Nombre de estación: ");
            System.out.println(sistema.obtenerInfoEstacion(sc.nextLine()));
        } else if (op == 2) {
            System.out.print("Codigo de tren: ");
            System.out.println(sistema.obtenerInfoTren(sc.nextInt()));
        }
    }
}
