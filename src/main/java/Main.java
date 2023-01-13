import Helpers.DBUtil;

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
    }
}