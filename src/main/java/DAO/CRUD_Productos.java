/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Object.Producto;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jason
 */
public class CRUD_Productos {

    public static boolean ingresarProductos(ArrayList<Producto> arrProductos, int guia) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            arrProductos.forEach((Producto p) -> {
                try {
                    estatuto.executeUpdate("INSERT INTO  dbo.productos (producto,cantidad,codigo,precio,guia) VALUES ('" + p.getProducto() + "',"
                            + "" + p.getCantidad() + ","
                            + "'" + p.getCodigo() + "',"
                            + "" + p.getValorNeto() + ","
                            + "'" + guia + "')");
                } catch (SQLException ex) {
                    Logger.getLogger(CRUD_Guias.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static ArrayList<Producto> getProductos(int guia) {

        ArrayList<Producto> arr = new ArrayList<>();
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT * FROM dbo.productos WHERE guia = " + guia)) {
                while (res.next()) {
                    Producto p = new Producto();

                    p.setProducto(res.getString("producto"));
                    p.setCodigo(res.getString("codigo"));
                    p.setComprar(res.getInt("comprar"));
                    p.setCantidad(res.getInt("cantidad"));
                    p.setValorNeto(res.getDouble("precio"));

                    arr.add(p);
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return arr;
    }

    public static boolean setComprar(boolean estado, String codigo) {
        int comprar = 0;
        if (estado) {
            comprar = 1;
        }

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.productos SET comprar = " + comprar + " WHERE codigo = '" + codigo + "'");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static boolean setComprarNumGuia(boolean estado, int guia) {
        int comprar = 0;
        if (estado) {
            comprar = 1;
        }

        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("UPDATE dbo.productos SET comprar = " + comprar + " WHERE guia = " + guia + "");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

}
