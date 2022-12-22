package Object;

import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class Guia {

    int guia;
    String fecha;
    String hora;
    String direccion;
    ArrayList<Producto> detalle;

// Informacion ya trabajada
    int entregado;
    int comprar;
    String comentario;
    String statusOC;
    String transporte;
    int estadofactura;
    int enviadoT;
    String fechaT;

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public ArrayList<Producto> getDetalle() {
        return detalle;
    }

    public void setDetalle(ArrayList<Producto> detalle) {
        this.detalle = detalle;
    }

    public int getGuia() {
        return guia;
    }

    public void setGuia(int guia) {
        this.guia = guia;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getEntregado() {
        return entregado;
    }

    public void setEntregado(int entregado) {
        this.entregado = entregado;
    }

    public int getComprar() {
        return comprar;
    }

    public void setComprar(int comprar) {
        this.comprar = comprar;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getStatusOC() {
        return statusOC;
    }

    public void setStatusOC(String statusOC) {
        this.statusOC = statusOC;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public int getEstadofactura() {
        return estadofactura;
    }

    public void setEstadofactura(int estadofactura) {
        this.estadofactura = estadofactura;
    }

    public int getEnviadoT() {
        return enviadoT;
    }

    public void setEnviadoT(int enviadoT) {
        this.enviadoT = enviadoT;
    }

    public String getFechaT() {
        return fechaT;
    }

    public void setFechaT(String fechaT) {
        this.fechaT = fechaT;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

}
