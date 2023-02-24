package Clients;

import Entity.Account;
import Entity.User;
import Models.CreateUser;
import Models.LoginUser;
import Services.UserService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.password4j.Password;

public class AuthClient {
    UserService userService = new UserService();
    Firestore db = FirestoreClient.getFirestore();

    public User Login(LoginUser loginUser) {
        User user = userService.getUserByUsername(loginUser.getUsername());
        if (Password.check(loginUser.getPassword(), user.getPassword()).withArgon2()) {
            return user;
        }
        return null;
    }

    //TODO: Check for duplicated Username.
    public void Register(CreateUser createUser) {
    }
}