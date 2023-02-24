package Clients;

import Entity.Account;
import Entity.User;
import Exceptions.UserNotFoundException;
import Models.CreateUser;
import Models.LoginUser;
import Services.UserService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.password4j.Password;

public class AuthClient {
    UserService userService = new UserService();

    public User Login(LoginUser loginUser) throws UserNotFoundException {
        User user = userService.getUserByUsername(loginUser.getUsername());
        if (Password.check(loginUser.getPassword(), user.getPassword()).withArgon2()) {
            return user;
        }
        throw new UserNotFoundException();
    }

    // TODO: Check for duplicated Username.
    // TODO:
    public void Register(CreateUser createUser) {
        userService.createUser(createUser);
    }
}