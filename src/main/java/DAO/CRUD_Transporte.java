/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Object.Conductor;
import Object.Transporte;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class CRUD_Transporte {

    public static ArrayList<Transporte> getTransitos() {
        ArrayList<Transporte> arrGuias = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT g.guia,l.dir,g.enviadoT,t.patente,con.nombre,g.fechaTransito from cotizaciones AS c "
                    + "INNER JOIN dbo.guias AS g ON c.guia = g.guia "
                    + "INNER JOIN dbo.licitaciones AS l ON c.licitacion = l.licitacion "
                    + "INNER JOIN dbo.transportes AS t ON t.id = g.transporte "
                    + "INNER JOIN dbo.conductores AS con ON con.id = t.idConductor "
                    + "WHERE g.guia is NOT NULL")) {
                while (res.next()) {
                    Transporte t = new Transporte();
                    t.setNumGuia(res.getInt("guia"));
                    t.setDir(res.getString("dir"));
                    t.setTransito(res.getInt("enviadoT"));
                    t.setPatente(res.getString("patente"));
                    t.setNombre(res.getString("nombre"));
                    t.setFecha(res.getString("fechaTransito"));
                    arrGuias.add(t);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrGuias;
    }

    public static boolean ingresarVehiculo(String patente) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO dbo.transportes (patente) VALUES ('" + patente + "')");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean ingresarCondcutor(String nombre) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO dbo.conductores (nombre) VALUES ('" + nombre + "')");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean ingresarVehiculoXID(String patente, int id) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO dbo.transportes (patente,idConductor) VALUES ('" + patente + "'," + id + ")");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<Integer> getGuiasConTransporte() {
        ArrayList<Integer> arrGuias = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT guia FROM  DBO.GUIAS  WHERE enviadoT = 1")) {
                while (res.next()) {
                    arrGuias.add(res.getInt("guia"));
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrGuias;
    }

    public static ArrayList<Integer> getGuiasEntregadas() {
        ArrayList<Integer> arrGuias = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT g.guia FROM dbo.guias AS g "
                    + "INNER JOIN dbo.facturas AS f ON f.guia != g.guia "
                    + "WHERE g.estadoFactura = 1 OR g.entregado = 1")) {
                while (res.next()) {
                    arrGuias.add(res.getInt("guia"));
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (arrGuias.isEmpty()) {
            try ( Statement estatuto = conex.getConnection().createStatement()) {
                try ( ResultSet res = estatuto.executeQuery("SELECT g.guia FROM dbo.guias AS g WHERE g.estadoFactura = 1 OR g.entregado = 1")) {
                    while (res.next()) {
                        arrGuias.add(res.getInt("guia"));
                    }
                }
                estatuto.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return arrGuias;
    }

    public static ArrayList<Transporte> getPatentes() {
        ArrayList<Transporte> arrPatentes = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT DISTINCT id,patente FROM dbo.transportes ORDER BY id ASC")) {
                while (res.next()) {
                    Transporte t = new Transporte();
                    t.setPatente(res.getString("patente"));

                    arrPatentes.add(t);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrPatentes;
    }

    public static boolean validarPatenteConductor(String patente, String nombre) {
        int id = 0;

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT T.id FROM dbo.transportes AS t "
                    + "	 INNER JOIN dbo.conductores AS c ON t.idConductor = c.id "
                    + "	 WHERE t.patente = '" + patente + "' AND c.nombre = '" + nombre + "'")) {
                if (res.next()) {
                    id = res.getInt("id");
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        // id = 0 -> hay que relacionar
        return id == 0;
    }

    public static ArrayList<Conductor> getNombresConductores() {
        ArrayList<Conductor> arrConductores = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT DISTINCT id,nombre FROM dbo.conductores")) {
                while (res.next()) {
                    Conductor c = new Conductor();
                    c.setId(res.getInt("id"));
                    c.setNombre(res.getString("nombre"));
                    arrConductores.add(c);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arrConductores;
    }

    public static int getIdPatente(String patente, String nombre) {
        int id = 1;
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT T.id FROM dbo.transportes AS t "
                    + " INNER JOIN dbo.conductores AS c ON t.idConductor = c.id "
                    + "	WHERE t.patente = '" + patente + "' AND c.nombre = '" + nombre + "'")) {
                if (res.next()) {
                    id = res.getInt("id");
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public static int getIdConductor(String nombre) {
        int id = 1;
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT id FROM dbo.conductores WHERE nombre = '" + nombre + "'")) {
                if (res.next()) {
                    id = res.getInt("id");
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public static boolean validarPatente(String patente) {
        int id = 0;
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT idConductor FROM dbo.transportes WHERE patente = '" + patente + "' AND idConductor IS NULL")) {
                if (res.next()) {
                    id = res.getInt("idConductor");
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return id == 0;
    }

    public static boolean updateIdConductor(String patente, int id) {
        boolean bool = false;
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.transportes SET idConductor = " + id + " WHERE patente = '" + patente + "'");
            estatuto.close();
            bool = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bool;
    }

}
