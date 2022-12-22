/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

/**
 *
 * @author Jason
 */
public class Permiso {

    String nombre;
    boolean admini;
    boolean coti;
    boolean guias;
    boolean despacho;
    boolean factura;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isAdmini() {
        return admini;
    }

    public void setAdmini(boolean admini) {
        this.admini = admini;
    }

    public boolean isCoti() {
        return coti;
    }

    public void setCoti(boolean coti) {
        this.coti = coti;
    }

    public boolean isGuias() {
        return guias;
    }

    public void setGuias(boolean guias) {
        this.guias = guias;
    }

    public boolean isDespacho() {
        return despacho;
    }

    public void setDespacho(boolean despacho) {
        this.despacho = despacho;
    }

    public boolean isFactura() {
        return factura;
    }

    public void setFactura(boolean factura) {
        this.factura = factura;
    }

}
