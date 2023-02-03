package Helpers;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBUtil {
    public static Connection DBConnection;

    public static boolean EstablishConnection() {
        String mysqlUsername = SecretKeyStore.getKey("mysqlUser");
        String mysqlPassword = SecretKeyStore.getKey("mysqlPass");
        try {
            DBConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/BankCLI", mysqlUsername,
                    mysqlPassword);
            return true;
        } catch (Exception ex) {
            System.out.println("Failed to open a connection to SQL Server." + ex.getMessage());
            return false;
        }
    }
}
