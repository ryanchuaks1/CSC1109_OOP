package Models;

import Clients.Bank;

import java.math.BigDecimal;
import java.util.Date;

import com.google.type.Decimal;

public class User {
    private String UserID;
    private String Email;
    private String Username;
    private String Password;
    private String FirstName;
    private String LastName;
    private String CountryCode;
    private int PhoneNo;
    private double MaximumDebt;
    private double DebtInterestRate;
    private Date DateOfBirth;

    public User(
            String userID,
            String email,
            String username,
            String password,
            String firstName,
            String lastName,
            String countryCode,
            int phoneNo,
            Double maximumDebt,
            Double debtInterest) {

        UserID = userID;
        Email = email;
        Username = username;
        Password = password;
        FirstName = firstName;
        LastName = lastName;
        CountryCode = countryCode;
        PhoneNo = phoneNo;
        MaximumDebt = maximumDebt;
        DebtInterestRate = debtInterest;
    }
    
    public String getUserID() {
        return UserID;
    }

    public String getEmail() {
        return Email;
    }
    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public int getPhoneNo() {
        return PhoneNo;
    }

    public double getMaximumDebt() {
        return MaximumDebt;
    }

    public double getDebtInterestRate() {
        return DebtInterestRate;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }


}
