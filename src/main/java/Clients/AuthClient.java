package Clients;

import Exceptions.UserNotFoundException;
import Helpers.DBUtil;
import Interfaces.IAuth;
import Models.User;
import com.mysql.cj.x.protobuf.MysqlxPrepare;
import com.password4j.Hash;
import com.password4j.Password;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class AuthClient implements IAuth {
    // TODO: Return a User object.
    // TODO: Handle exceptions more graceful?
    @Override
    public User Login(String username, String password) {
        User user;
        try {
            String sqlQuery = "SELECT * FROM User WHERE User.Username = ?";
            PreparedStatement stmt = DBUtil.DBConnection.prepareStatement(sqlQuery);
            stmt.setString(1, username);
            var results = stmt.executeQuery();
            if (!results.next()) {
                throw new UserNotFoundException();
            }
            String hashPass = results.getString("Password");
            if (Password.check(password, hashPass).withArgon2()) {
                // Get all the data out from table to the user object
                System.out.println("Successfully login");
            }
            return null;
        } catch (Exception ex) {
            System.out.println("Exception occurred: " + ex.getMessage());
        }
        return null;
    }

    // TODO: Move parameters to a DTO class so there's no need for massive
    // parameters
    // TODO: Validation check to see if it clashes with any other username (Although
    // database is already listed as unique)
    @Override
    public boolean Register(String Username, String Pass, String Email, String FirstName, String LastName,
            String CountryCode, int PhoneNo, Date DateOfBirth) {
        Hash hashPass = Password.hash(Pass).addRandomSalt().withArgon2();
        try {
            // We are ignoring some parameter first.
            String sqlQuery = "INSERT INTO User(Email, Username ,Password, FirstName, LastName, DOB) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = DBUtil.DBConnection.prepareStatement(sqlQuery);
            stmt.setString(1, Email);
            stmt.setString(2, Username);
            stmt.setString(3, hashPass.getResult());
            stmt.setString(4, FirstName);
            stmt.setString(5, LastName);
            stmt.setDate(6, DateOfBirth);

            // Maybe check on the number of rows affected by the query
            // Maybe also automatically login by passing it a user object
            stmt.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        // Generate a salt. Hash it and place the salt + hashed value in the database.
        return false;
    }

    // TODO: SMTP implementation
    // TODO: Just find some library to do TOTP/HOTP
    @Override
    public void ResetPassword(String email) {
        // Throw invalid account exception if there's no user.
    }
}