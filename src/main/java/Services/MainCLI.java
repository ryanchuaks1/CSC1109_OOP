package Services;
import Clients.AuthClient;
import Entity.Account;
import Entity.Transaction;
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
                System.out.printf("AccountID: %s %n", account.getId());

                System.out.printf("Can utilise to $%f \n", account.getAvailableBalance());
                System.out.printf("Currently there are $%f pending balances \n", account.getPendingBalance());

                // Each account has their own transaction
                List<Transaction> transactions = account.getTransactions();
                if (transactions != null) {
                    for (Transaction transaction : transactions) {
                        System.out.printf("TransactionID(%s) \t TransactionAmount:($%s)\n", transaction.getId(), transaction.getTransactionAmount());
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }


        //client.Register(createUser);
    }
}
