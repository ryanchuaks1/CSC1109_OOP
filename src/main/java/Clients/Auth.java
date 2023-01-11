package Clients;

import Interfaces.IAuth;
import Models.User;

public class Auth implements IAuth {
    @Override
    public User Login(String username, String password) {
        //Hash the password and crosscheck the hash with the database

        //Upon success return a Bank
        return null;
    }

    @Override
    public void Register(String Username, String Email, String password) {
        //Generate a salt. Hash it and place the salt + hashed value in the database.
    }

    @Override
    public void ResetPassword(String email) {
        // Throw invalid account exception if there's no user.
    }
}


