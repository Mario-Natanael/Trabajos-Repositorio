import java.sql.Connection;
import java.sql.DriverManager;

public class conexion {

    public static Connection conectar() {
        Connection con = null;

        try {
            String url = "jdbc:mysql://localhost:3306/login_db";
            String user = "root";
            String password = "admin"; // tu contraseña

            con = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }

    public static void main(String[] args) {
        conectar(); // 👈 aquí lo ejecutas
    }
}