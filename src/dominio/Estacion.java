/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dominio;

/**
 *
 * @author Brunot
 */

public class Estacion {
    private String nombre; // Clave
    private String domicilio; // Agrupamos calle, nro, ciudad y CP
    private int vias;
    private int plataformas;

    public Estacion(String nombre, String domicilio, int vias, int plat) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.vias = vias;
        this.plataformas = plat;
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre + " (" + domicilio + ") V:" + vias + " P:" + plataformas;
    }
}