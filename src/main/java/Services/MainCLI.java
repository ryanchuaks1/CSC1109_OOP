package Services;
import Clients.AuthClient;
import Entity.Account;
import Entity.User;
import Helpers.FirebaseInitialize;
import Models.CreateAccount;
import Models.CreateUser;
import Models.LoginUser;

import java.util.List;

public class MainCLI {
    public static void main(String[] args)
    {
        //Ensure that Firebase is initialized.
        FirebaseInitialize.initDatabase();

        AuthClient client = new AuthClient();
        //CreateUser createUser = new CreateUser("xavieroyj", "trickster123", "Xavier", "Ong", "+6586918172");
        LoginUser loginUser = new LoginUser("xavieroyj", "trickster123");

        try {
            User user = client.Login(loginUser);
            System.out.printf("Full Name: %s(%s)%n", user.getFullName(), user.getId());

            var accounts = user.getAccounts();
            for (Account account : accounts) {
                System.out.printf("TransactionId: %s %n", account.getId());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        //client.Register(createUser);
    }
}
