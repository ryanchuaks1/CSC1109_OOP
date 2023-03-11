package Services;
import Clients.AccountClient;
import Clients.AuthClient;
import Clients.PhoneOTPClient;
import Entity.*;
import Exceptions.UserDuplicateFoundException;
import Helpers.FirebaseInitialize;
import Models.CreateAccount;
import Models.CreateUser;
import Models.LoginUser;

import java.time.LocalDate;
import java.util.List;

import com.google.firebase.auth.PhoneIdentifier;
import com.password4j.Password;

public class MainCLI {
    public static void main(String[] args)
    {
        //Ensure that Firebase is initialized.
        FirebaseInitialize.initDatabase();

        AccountClient accountClient = new AccountClient();
        Account account = accountClient.Login("6229259821434678", "123456");
        account.Withdraw(600);
        System.out.println(account.getAvailableBalance());
        System.out.println(account.getYearlyProjectedInterestRate());
    }
}
