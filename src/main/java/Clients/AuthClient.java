package Clients;

import Entity.User;
import Exceptions.UserDuplicateFoundException;
import Exceptions.UserNotFoundException;
import Models.CreateUser;
import Models.LoginUser;
import Services.UserService;
import com.password4j.Password;

public class AuthClient {
    UserService userService = new UserService();
    PhoneOTPClient otpClient = new PhoneOTPClient();

    public User Login(LoginUser loginUser) throws UserNotFoundException {
        User user = userService.getUserByUsername(loginUser.getUsername());
        if (user.getVerified() == true) {

            if (Password.check(loginUser.getPassword(), user.getPassword()).withArgon2()) {
                return user;
            }
        } else {
            // send a alert to user and update on the part. Maybe counter to increment such
            // that multiple login failure attempt will lead to ban/ lock of accounts
        }
        throw new UserNotFoundException();
    }

    public void Register(CreateUser createUser) throws UserDuplicateFoundException {
        if (userService.getUserByUsername(createUser.getUsername()) != null) {
            throw new UserDuplicateFoundException();
        } else {
            userService.createUser(createUser);
            otpClient.phoneOTP(createUser);
        }
    }
}