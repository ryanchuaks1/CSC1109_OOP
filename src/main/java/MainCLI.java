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
        CreateUser createUser = new CreateUser("xavieroyj", "trickster123", "Xavier", "Ong", "+6586918172");
        LoginUser loginUser = new LoginUser("xavieroyj", "trickster123");

        User user = client.Login(loginUser);

        System.out.println(user.getFullName());
        user.CreateAccount();

        List<Account> accountsList = user.getAccounts();
        Account account1 = accountsList.get(0);
        account1.Transfer(500, "012121212");


        //client.Register(createUser);
    }
}
