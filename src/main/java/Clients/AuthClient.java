package Clients;

import Entity.User;
import Models.CreateUser;
import Models.LoginUser;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.password4j.Password;

public class AuthClient {
    Firestore db = FirestoreClient.getFirestore();

    public User Login(LoginUser loginUser) {
        try {
            User user;

            ApiFuture<QuerySnapshot> apiFuture = db.collection("users").whereEqualTo("username", loginUser.getUsername()).get();
            QuerySnapshot snapshots = apiFuture.get();
            if (snapshots.isEmpty()) {
                System.out.println("User not found.");
            }

            //Since querySnapshot returns a list
            for (DocumentSnapshot documentSnapshot : snapshots.getDocuments()) {
                String hashPass = documentSnapshot.getString("password");

                assert hashPass != null;
                if (Password.check(loginUser.getPassword(), hashPass).withArgon2()) {
                    user = documentSnapshot.toObject(User.class);
                    return user;
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    //TODO: Check for duplicated Username.
    public void Register(CreateUser createUser) {
        try {
            ApiFuture<WriteResult> apiFuture = db.collection("users").document().set(createUser);
            System.out.printf("Successfully created user at %s \n",apiFuture.get().getUpdateTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}