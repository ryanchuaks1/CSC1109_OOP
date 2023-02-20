package Models;

import Clients.Bank;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private String UserID;
    private String Email;
    private String Username;
    private String Password;
    private String FirstName;
    private String LastName;
    private String CountryCode;
    private int PhoneNo;
    private BigDecimal MaximumDebt;
    private float DebtInterestRate;
    private Date DateOfBirth;

    public User(String userID, String email, String username, String password, String firstName, String lastName,
            String countryCode, int phoneNo, BigDecimal maximumDebt, float debtInterestRate, Date dateOfBirth) {
        UserID = userID;
        Email = email;
        Username = username;
        Password = password;
        FirstName = firstName;
        LastName = lastName;
        CountryCode = countryCode;
        PhoneNo = phoneNo;
        MaximumDebt = maximumDebt;
        DebtInterestRate = debtInterestRate;
        DateOfBirth = dateOfBirth;
    }

    public String getUserID() {
        return UserID;
    }
}
