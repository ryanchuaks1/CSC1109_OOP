import Clients.Auth;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // After authentication,
        Auth AuthClient = new Auth();

        System.out.println("Please enter your username and password in the following format: ");
        System.out.println("username:password");

        Scanner scanObject = new Scanner(System.in);



        System.out.println("Hello world!");
    }
}