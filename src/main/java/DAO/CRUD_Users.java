package DAO;

import Object.User;
import static Procedure.Procedures.conex;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Jason
 */
public class CRUD_Users {

    public static boolean ingresarUsuario(String nombre, String email, String contrasena) {
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            estatuto.executeUpdate("INSERT INTO dbo.Usuarios (nombre,email,contrasena) VALUES ('" + nombre + "','" + email + "','" + contrasena + "')");
            estatuto.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public static User getUser(String nombre, String contrasena) {
        User user = null;
        try ( Statement estatuto = conex.getConnection().createStatement()) {
            try ( ResultSet res = estatuto.executeQuery("SELECT * FROM dbo.Usuarios WHERE nombre = '" + nombre + "' AND contrasena = '" + contrasena + "'")) {
                if (res.next()) {
                    user = new User();
                    user.setNombre(res.getString("nombre"));
                    user.setEmail(res.getString("email"));
                }
            }
            estatuto.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

}
