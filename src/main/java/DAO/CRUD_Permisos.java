/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Object.Permiso;
import static Procedure.Functions.permiso;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Jason
 */
public class CRUD_Permisos {

    public static boolean ingresarAPermisos(String nombre) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO  dbo.Permisos (usuario) VALUES ('" + nombre + "')");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static Permiso getPermiso(String nombre) {
        Permiso p = new Permiso();
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT * FROM dbo.permisos WHERE usuario = '" + nombre + "'")) {
                while (res.next()) {
                    p.setAdmini(res.getInt("administrador") == 1);
                    p.setCoti(res.getInt("cotizaciones") == 1);
                    p.setGuias(res.getInt("guias") == 1);
                    p.setDespacho(res.getInt("despacho") == 1);
                    p.setFactura(res.getInt("factura") == 1);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return p;
    }

    public static ArrayList<Permiso> getUsuariosPermiso() {
        ArrayList<Permiso> arrUsuarios = new ArrayList<>();
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT * FROM dbo.permisos")) {
                while (res.next()) {
                    Permiso p = new Permiso();
                    p.setNombre(res.getString("usuario"));
                    p.setAdmini(res.getInt("administrador") == 1);
                    p.setCoti(res.getInt("cotizaciones") == 1);
                    p.setGuias(res.getInt("guias") == 1);
                    p.setDespacho(res.getInt("despacho") == 1);
                    p.setFactura(res.getInt("factura") == 1);

                    arrUsuarios.add(p);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return arrUsuarios;
    }

    public static boolean actualizarPermisos(ArrayList<Permiso> arrPermisos) {

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            for (Permiso p : arrPermisos) {
                int admin = permiso(p.isAdmini());
                int coti = permiso(p.isCoti());
                int guia = permiso(p.isGuias());
                int desp = permiso(p.isDespacho());
                int fact = permiso(p.isFactura());

                estatuto.executeUpdate("UPDATE dbo.permisos SET administrador = " + admin + ", "
                        + "cotizaciones = " + coti + ", "
                        + "guias = " + guia + ", "
                        + "despacho = " + desp + ", "
                        + "factura = " + fact + " WHERE usuario = '" + p.getNombre() + "'");
            }
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
