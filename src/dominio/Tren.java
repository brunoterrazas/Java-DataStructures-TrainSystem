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
public class Tren {

    private int codigo; // Clave
    private String propulsion;
    private int vagPasajeros;
    private int vagCarga;
    private String linea;

    public Tren(int cod, String prop, int pas, int carga, String lin) {
        this.codigo = cod;
        this.propulsion = prop;
        this.vagPasajeros = pas;
        this.vagCarga = carga;
        this.linea = lin;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }
    
    @Override
    public String toString() {
        return "ID:" + codigo + " [" + propulsion + "] L:" + linea;
    }
}
