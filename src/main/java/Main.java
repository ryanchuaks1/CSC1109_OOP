import Clients.AuthClient;
import Helpers.DBUtil;
import Models.User;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (!DBUtil.EstablishConnection()) {
            System.out.println("Failed establishing a connection to MySQL Server. Please try again soon.");
            return;
        }
        System.out.println("Welcome to ABC Bank!");
        System.out.println("Please select an option below");
        System.out.println("[1] Login");
        System.out.println("[2] Register");
        Scanner scanner = new Scanner(System.in);
        int optSelection;
        do {
            System.out.print("Please enter your selection: ");
            optSelection = scanner.nextInt();
        } while (optSelection != 1 && optSelection != 2);


        // Testing out how the flow would work like
        AuthClient client = new AuthClient();
        String dateString = "1999-08-18";
        Date date = Date.valueOf(dateString);

        var test = client.Login("xavieroyj1999", "trickster123");
        // Creating an account

    }
}