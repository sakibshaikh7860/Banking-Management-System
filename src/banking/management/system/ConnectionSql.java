package banking.management.system;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionSql {
    public Connection con;
    public Statement s;

    public ConnectionSql() {
        String url = "jdbc:mysql://localhost:3306/bankingsystem_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "root";
        String password = "";  // agar password hai to yahan daal de (jaise "root" ya jo bhi set kiya hai)

        try {
            // Driver load (9.4.0 mein optional hai lekin safe rakh le)
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, password);
            s = con.createStatement();

            System.out.println("✅ Database Connected Successfully! (URL: " + url + ")");

        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found! JAR missing or wrong path.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Connection Failed! Check these:");
            System.err.println("   - MySQL server running hai? (XAMPP/WAMP/MySQL service start kar)");
            System.err.println("   - Database 'bankingsystem_db' bana hai?");
            System.err.println("   - Username 'root' aur password sahi hai?");
            System.err.println("   - Port 3306 pe conflict to nahi?");
            System.err.println("Error Message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Optional: close karne ke liye (future mein use kar sakta hai)
    public void close() {
        try {
            if (s != null) s.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}