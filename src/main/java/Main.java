import Clients.Auth;
import Models.User;
import com.password4j.Hash;
import com.password4j.Password;
import com.password4j.jca.providers.Password4jProvider;

import javax.crypto.SecretKeyFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

import static com.password4j.Password.hash;

public class Main {
    public static void main(String[] args) {
        // We shall attempt to create a user from here first.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        String username = scanner.next();
        System.out.print("Please enter your desired password: ");
        String password = scanner.next();

        Hash originalHash = Password.hash(password).addRandomSalt().withArgon2();
        System.out.println(originalHash.getResult());

        boolean verified = Password.check("test123", originalHash.getResult()).withArgon2();
        if (verified) {
            System.out.println("It is the same password");
        }
        else {
            System.out.println("It is a different password");
        }

//        // After authentication
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost.3306/BankCLI", "root", "Trickster123.");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT  * FROM User");
//            while (resultSet.next()) {
//                System.out.println(resultSet.getString("name"));
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }

//        Auth AuthClient = new Auth();
//        User user = AuthClient.Login("username", "password");
//
//        System.out.println("Hello world!");
    }
}