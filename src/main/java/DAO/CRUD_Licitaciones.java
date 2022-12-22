package DAO;

import Object.Cotizacion;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD_Licitaciones {

    public static boolean setFechas(String oc, String fechaI, String fechaT) {
        boolean bool = false;
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.licitaciones SET fechaI = '" + fechaI + "', fechaT = '" + fechaT + "' WHERE numOC = '" + oc + "'");
            estatuto.close();
            bool = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bool;
    }

    public static String getOrgnismo(int cotizacion) {
        String organismo = "";

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT li.organismo FROM DBO.licitaciones AS li "
                    + "INNER JOIN DBO.cotizaciones AS co on li.licitacion = co.licitacion "
                    + "Where co.cotizacion = " + cotizacion)) {
                if (res.next()) {
                    organismo = (res.getString("organismo"));
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return organismo;
    }

}
