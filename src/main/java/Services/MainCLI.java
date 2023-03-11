package Services;
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

public class MainCLI {
    public static void main(String[] args)
    {
        //Ensure that Firebase is initialized.
        FirebaseInitialize.initDatabase();
    }
}
