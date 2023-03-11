package Services;

import Clients.AccountClient;
import Clients.BankIdentificationClient;
import Entity.*;
import Helpers.FirebaseInitialize;

public class MainCLI {
    public static void main(String[] args) {
        // Ensure that Firebase is initialized.
        FirebaseInitialize.initDatabase();

        // EXAMPLE DATA TO TRY
        // Real card number: 6229 2598 2143 4678 pin 123456

        tryLogin("6229259821434678");
    }

    private static void tryLogin(String fullCardNumber) {
        AccountClient accountClient = new AccountClient();
        BankIdentificationClient BinClient = new BankIdentificationClient();
        String binNum = fullCardNumber.substring(0, 6);
        String cardNum = fullCardNumber.substring(6, 15);

        System.out.println(fullCardNumber);
        System.out.println(binNum);
        System.out.println(cardNum);

        if (BinClient.CheckBIN(binNum)) {
            System.out.println("Exisit in our bank");
            Account account = accountClient.Login(cardNum, "123456");
            System.out.println("Account number: " + account.getaccountNumber());
        } else {
            System.out.println("Exist in other bank");
        }
    }
}
