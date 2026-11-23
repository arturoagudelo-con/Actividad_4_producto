
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {

    public String probarConexion(String url, String usuario, String contrasenia) {
        try {
            if (url.contains("sqlite")) {
                Class.forName("org.sqlite.JDBC");
            } else if (url.contains("mysql")) {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }

            try (Connection conn = DriverManager.getConnection(url, usuario, contrasenia);
                 Statement stmt = conn.createStatement()) {

                if (conn != null && !conn.isClosed()) {
                    stmt.executeQuery("SELECT 1");
                    return "OK: Conexión exitosa a la base de datos local.";
                }
            }
        } catch (ClassNotFoundException ex) {
            return "ERROR: No se encontró el Driver de la BD.\nDetalle: " + ex.getMessage();
        } catch (SQLException ex) {
            return "ERROR: Fallo de conexión con la base de datos.\nError SQL: " + ex.getMessage();
        }
        return "ERROR: Estado de conexión desconocido.";
    }
}
