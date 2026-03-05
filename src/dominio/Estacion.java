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
    private String domicilio; // Agrupamos calle y nro
    private String ciudad;
    private String cp;
    private int cantVias;
    private int cantPlataformas;

    public Estacion(String nombre, String domicilio,String ciudad,String codpostal, int cantVias, int plat) {
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.ciudad=ciudad;
        this.cp=codpostal;
        this.cantVias = cantVias;
        this.cantPlataformas = plat;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public int getCantVias() {
        return cantVias;
    }

    public void setCantVias(int cantVias) {
        this.cantVias = cantVias;
    }

    public int getCantPlataformas() {
        return cantPlataformas;
    }

    public void setCantPlataformas(int cantPlataformas) {
        this.cantPlataformas = cantPlataformas;
    }

    public String getNombre() { return nombre; }

    @Override
    public String toString() {
        return nombre + " Calle: " + domicilio + "," + ciudad+"("+cp+"), Vias:" + cantVias + ", Plataformas:" + cantPlataformas;
    }
}