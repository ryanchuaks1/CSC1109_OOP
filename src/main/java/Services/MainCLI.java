package Services;
import Clients.AuthClient;
import Clients.PhoneOTPClient;
import Entity.Account;
import Entity.Transaction;
import Entity.User;
import Exceptions.UserDuplicateFoundException;
import Helpers.FirebaseInitialize;
import Models.CreateAccount;
import Models.CreateUser;
import Models.LoginUser;

import java.time.LocalDate;
import java.util.List;

import com.google.firebase.auth.PhoneIdentifier;

public class MainCLI {
    public static void main(String[] args)
    {
        //Ensure that Firebase is initialized.
        FirebaseInitialize.initDatabase();
        AuthClient client = new AuthClient();
        LocalDate dateOfBirth = LocalDate.of(1999, 2, 3);
        CreateUser createUser = new CreateUser("xavier.oyj1999@gmail.com", "xavieroyj199", "cateatdog123", "Xaiver", "Ong", "+6586918172", dateOfBirth, true);
        LoginUser loginUser = new LoginUser("xavieroyj", "trickster123");

        try {
            client.Register(createUser);
        }  catch (UserDuplicateFoundException ex) {
            ex.getStackTrace();
            System.out.println("Duplication user found.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        try {
//            User user = client.Login(loginUser);
//            System.out.printf("Full Name: %s(%s)%n", user.getFullName(), user.getId());
//
//            var accounts = user.getAccounts();
//            for (Account account : accounts) {
//                System.out.printf("AccountID: %s %n", account.getId());
//
//                System.out.printf("Can utilise to $%f \n", account.getAvailableBalance());
//                System.out.printf("Currently there are $%f pending balances \n", account.getPendingBalance());
//
//                // Each account has their own transaction
//                List<Transaction> transactions = account.getTransactions();
//                if (transactions != null) {
//                    for (Transaction transaction : transactions) {
//                        System.out.printf("TransactionID(%s) \t TransactionAmount:($%s)\n", transaction.getId(), transaction.getTransactionAmount());
//                    }
//                }
//            }
//            //PhoneOTPClient otpClient = new PhoneOTPClient();
//            //otpClient.phoneOTP(user);
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
        

        //client.Register(createUser);
    }
}
