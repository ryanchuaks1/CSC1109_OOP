package Helpers;

import java.util.Random;

public class CreditCardHelper {
    private static String getLuhnCheckDigit(String number) {
        int nDigits = number.length();
        int nSum = 0;
        boolean isSecond = false;

        for (int i = nDigits - 1; i >= 0; i--) 
        {
            int d = number.charAt(i) - '0';
            if (isSecond == true)
                d = d * 2;
            // We add two digits to handle
            // cases that make two digits 
            // after doubling
            nSum += d / 10;
            nSum += d % 10;
    
            isSecond = !isSecond;
        }
        
        String value = "";
        if((nSum %10) == 0){
            return value + 0;
        }
        else{
            return value + (10-(nSum %10));
        }
    }

    // private static String luhn_calculate(String partcode) {
    //     var checksum = getLuhnCheckDigit(partcode + "0");
    //     String value = "";
    //     if(checksum == 0){
    //         return value + 0;
    //     }
    //     else{
    //         return value + (10-checksum);
    //     }
    // }

    public static String generateCreditCard() {
        String cardNo;
        String bankIdentifierNumber = "622925";
        // Credit card has 16 numbers
        // We used up 6 digits for bankIdentifier
        // We have 9 digits left for bank account number.
        // The last digits must be a check digit.
        Random random = new Random();
        String bankAccountNumber = Integer.toString(100000000 + random.nextInt(900000000));

        String checkDigit = getLuhnCheckDigit(bankIdentifierNumber + bankAccountNumber + "0");
        cardNo = bankIdentifierNumber + bankAccountNumber + checkDigit;

        return cardNo;
    }

    public static boolean checkLuhn(String cardNo)
    {
        int nDigits = cardNo.length();
        int nSum = 0;
        boolean isSecond = false;

        for (int i = nDigits - 1; i >= 0; i--) 
        {
            int d = cardNo.charAt(i) - '0';
            if (isSecond == true)
                d = d * 2;
            // We add two digits to handle
            // cases that make two digits 
            // after doubling
            nSum += d / 10;
            nSum += d % 10;
    
            isSecond = !isSecond;
        }
        return (nSum % 10 == 0);
    }
}
