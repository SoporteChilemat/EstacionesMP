/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Object.Guia;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class CRUD_Guias {

    public static boolean ingresarGuia(Guia g) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO dbo.guias (guia,fecha,hora,transporte) VALUES (" + g.getGuia() + ","
                    + "'" + g.getFecha() + "',"
                    + "'" + g.getFecha() + "',"
                    + "1)");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<Guia> getGuias() {
        ArrayList<Guia> arrGuias = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT g.guia, g.fecha, g.hora, g.entregado, g.comprar, g.comentario, g.statusOC, g.enviadoT, l.dir as direccion, t.patente as transporte, g.estadoFactura, g.fechaTransito FROM dbo.guias as g "
                    + "INNER JOIN dbo.cotizaciones as c on c.guia = g.guia "
                    + "INNER JOIN dbo.licitaciones as l on l.licitacion = c.licitacion "
                    + "INNER JOIN dbo.transportes as t on t.id = g.transporte")) {
                while (res.next()) {
                    Guia g = new Guia();
                    g.setGuia(res.getInt("guia"));
                    g.setFecha(res.getString("fecha"));
                    g.setHora(res.getString("hora"));
                    g.setEntregado(res.getInt("entregado"));
                    g.setComprar(res.getInt("comprar"));
                    g.setComentario(res.getString("comentario"));
                    g.setStatusOC(res.getString("statusOC"));
                    g.setEnviadoT(res.getInt("enviadoT"));
                    g.setDireccion(res.getString("direccion"));
                    g.setTransporte("transporte");
                    g.setEstadofactura(res.getInt("estadoFactura"));
                    g.setFechaT(res.getString("fechaTransito"));

                    arrGuias.add(g);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrGuias;
    }

    public static boolean setEntregado(boolean estado, int guia) {
        int entrega = 0;
        if (estado) {
            entrega = 1;
        }

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.guias SET entregado = " + entrega + " WHERE guia = " + guia + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean setDespacho(boolean estado, int guia) {
        int enviado = 0;
        if (estado) {
            enviado = 1;
        }

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.guias SET enviadoT = " + enviado + " WHERE guia = " + guia + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean setComprar(boolean estado, int guia) {
        int comprar = 0;
        if (estado) {
            comprar = 1;
        }

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.guias SET comprar = " + comprar + " WHERE guia = " + guia + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean setComentario(String comentario, int guia) {

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.guias SET comentario = '" + comentario + "' WHERE guia = " + guia + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean setTipoDespacho(int status, int guia) {

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.guias SET statusOC = " + status + " WHERE guia = " + guia + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean setTransporte(int idPatente, int guia) {

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.guias SET transporte = " + idPatente + " WHERE guia = " + guia + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean setFacturar(int estadoFactura, int guia) {

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.guias SET estadoFactura = " + estadoFactura + " WHERE guia = " + guia);
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean setFechaTransito(int guia, String fecha) {

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.guias SET fechaTransito = '" + fecha + "', enviadoT = 2 WHERE guia = " + guia + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
