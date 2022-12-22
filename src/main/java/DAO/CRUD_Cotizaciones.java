package DAO;

import Object.Cotizacion;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class CRUD_Cotizaciones {

    public static ArrayList<Cotizacion> getCotizaciones() {
        ArrayList<Cotizacion> arrCotizaciones = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT co.cotizacion,li.fecha ,li.fechaTermino ,co.guia ,li.numOC ,li.fechaOC, li.valor FROM dbo.cotizaciones as co "
                    + "INNER JOIN dbo.licitaciones as li on co.licitacion = li.licitacion "
                    + "WHERE co.estado = 1 and numOC is not null")) {
                while (res.next()) {
                    Cotizacion c = new Cotizacion();
                    c.setCotizacion(res.getInt("cotizacion"));
                    c.setFechaCierre(res.getString("fecha"));
                    c.setFechaT(res.getString("fechaTermino"));
                    c.setGuia(res.getInt("guia"));
                    c.setNumOc(res.getString("numOC"));
                    c.setFechaOc(res.getString("fechaOC"));
                    c.setValor(res.getInt("valor"));

                    arrCotizaciones.add(c);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrCotizaciones;
    }

    public static ArrayList<Integer> getCotizacionesSinGuia() {
        ArrayList<Integer> arrCotizaciones = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT cotizacion FROM dbo.cotizaciones WHERE guia is null")) {
                while (res.next()) {
                    arrCotizaciones.add(res.getInt("cotizacion"));
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrCotizaciones;
    }

    public static String getNumOC(String guia) {
        String numOC = "";

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT l.numOC FROM cotizaciones AS c "
                    + "inner join licitaciones AS l ON c.licitacion = l.licitacion "
                    + "WHERE guia = " + guia)) {
                if (res.next()) {
                    numOC = res.getString("numOC");
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return numOC;
    }

    public static boolean setGuiaCotizacion(int coti, int guia) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.cotizaciones SET guia = " + guia + " WHERE cotizacion = " + coti);
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
