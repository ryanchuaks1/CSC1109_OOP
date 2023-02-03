package Interfaces;

import Models.User;

import java.sql.Date;

public interface IAuth {
    User Login(String username, String password);

    boolean Register(String Username, String Password, String Email, String FirstName, String LastName,
            String Singapore, int PhoneNo, Date DateOfBirth);

    void ResetPassword(String email);
}