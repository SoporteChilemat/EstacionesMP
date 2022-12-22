package DAO;

import Object.Cliente;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CRUD_Clientes {

    public static boolean ingresarCliente(String cliente, String rut) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO dbo.clientes (cliente,rut) VALUES ('" + cliente + "','" + rut + "')");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<Cliente> getClientes() {
        ArrayList<Cliente> arrClientes = new ArrayList<>();

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT * FROM dbo.clientes")) {
                while (res.next()) {
                    Cliente cli = new Cliente();
                    cli.setCliente(res.getString("cliente"));
                    cli.setRut(res.getString("rut"));

                    arrClientes.add(cli);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrClientes;
    }

    public static ArrayList<String> getNombresClientes() {
        ArrayList<String> arrClientes = new ArrayList<>();
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT cliente FROM dbo.clientes")) {
                while (res.next()) {
                    arrClientes.add(res.getString("cliente"));
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrClientes;
    }

    public static boolean validarCliente(String cliente, String rut) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT * FROM dbo.clientes WHERE cliente = '" + cliente + "' AND rut = " + rut + "")) {
                if (res.next()) {
                    Cliente cli = new Cliente();
                    cli.setCliente(res.getString("cliente"));
                    cli.setRut(res.getString("rut"));
                    return true;
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static int getRutCliente(String cliente) {
        int rut = 0;

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT rut FROM clientes WHERE cliente = '" + cliente + "'")) {
                if (res.next()) {
                    rut = res.getInt("rut");
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rut;
    }

    public static boolean eliminarCliente(String cliente, String rut) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("DELETE FROM dbo.clientes WHERE cliente = '" + cliente + "' AND rut = " + rut + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
