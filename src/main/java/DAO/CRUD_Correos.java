/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Object.Correo;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class CRUD_Correos {

    public static boolean ingresarCorreoCliente(String correo, String rut) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO dbo.correos (correo,rut) VALUES ('" + correo + "','" + rut + "')");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<Correo> getCorreos() {
        ArrayList<Correo> arrCorreos = new ArrayList<>();
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT c.correo, cli.cliente, c.rut, c.seleccion FROM dbo.correos AS c "
                    + " INNER JOIN dbo.clientes AS cli ON cli.rut = c.rut")) {
                while (res.next()) {
                    Correo c = new Correo();
                    c.setMail(res.getString("correo"));
                    c.setCliente(res.getString("cliente"));
                    c.setRut(res.getString("rut"));
                    c.setSeleccion(res.getInt("seleccion"));

                    arrCorreos.add(c);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrCorreos;
    }

    public static ArrayList<String> getCorreosSeleccionados(String cliente) {
        ArrayList<String> arrCorreos = new ArrayList<>();
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT c.correo FROM clientes AS cli "
                    + "INNER JOIN correos AS c ON cli.rut = c.rut "
                    + "WHERE cli.cliente = '" + cliente + "' AND c.seleccion = 1")) {
                while (res.next()) {
                    arrCorreos.add(res.getString("correo"));
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrCorreos;
    }

    public static boolean eliminarCorreo(String correo, String rut) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("DELETE FROM dbo.correos WHERE correo = '" + correo + "' AND rut = " + rut + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean updateSelccion(String correo, int selec) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.correos SET seleccion = " + selec + " WHERE correo = '" + correo + "'");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
