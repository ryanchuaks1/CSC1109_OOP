package Helpers;

import java.util.Random;

public class CreditCardHelper {
    private static void getLuhnCheckDigit(String number) {
        
    }

    public static void generateCreditCard() {
        String bankIdentifierNumber = "622925";
        // Credit card has 16 numbers
        // We used up 6 digits for bankIdentifier
        // We have 9 digits left for bank account number.
        // The last digits must be a check digit.
        Random random = new Random();
        String bankAccountNumber = Integer.toString(100000000 + random.nextInt(900000000));

        //String checkDigit = getLuhnCheckDigit(bankIdentifierNumber + bankAccountNumber);
    }
}
