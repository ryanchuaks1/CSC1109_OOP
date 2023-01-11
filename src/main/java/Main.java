import Clients.Bank;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // We shall attempt to create a user from here first.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your username: ");
        String username = scanner.next();
        System.out.print("Please enter your desired password: ");
        String password = scanner.next();

        Bank bank = new Bank();
    }
}