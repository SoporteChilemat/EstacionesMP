package DAO;

import Object.Factura;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CRUD_Facturas {

    public static ArrayList<Factura> getFacturas() {
        ArrayList<Factura> arrCotizaciones = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT f.guia,l.numOC,f.factura, cl.cliente FROM DBO.facturas AS f "
                    + "INNER JOIN dbo.cotizaciones AS c ON f.guia = c.guia "
                    + "INNER JOIN dbo.licitaciones AS l ON c.licitacion = l.licitacion "
                    + "INNER JOIN dbo.clientes AS cl ON f.rut = cl.rut")) {
                while (res.next()) {
                    Factura f = new Factura();
                    f.setGuia(res.getString("guia"));
                    f.setNumOc(res.getString("numOC"));
                    f.setFactura(res.getString("factura"));
                    f.setCliente(res.getString("cliente"));

                    arrCotizaciones.add(f);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrCotizaciones;
    }

    public static boolean relacionarFact_Guia_Client(String factura, int rut, String guia) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO facturas (factura, rut, guia) VALUES ("
                    + "'" + factura + "',"
                    + "'" + rut + "',"
                    + "'" + guia + "')");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

/*
SELECT f.guia,l.numOC,f.factura, cl.cliente FROM DBO.facturas as f
INNER JOIN dbo.cotizaciones as c on f.guia = c.guia
INNER JOIN dbo.licitaciones as l on c.licitacion = l.licitacion
INNER JOIN dbo.clientes as cl on f.rut = cl.rut
 */
