package com.rjdxbanking.rjdxbank.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.rjdxbanking.rjdxbank.Entity.User;
import com.rjdxbanking.rjdxbank.Models.CreateUser;

import java.util.List;

//User Service is not utilized fully.
public class UserService {
    Firestore db = FirestoreClient.getFirestore();

    // get all user
    public List<User> getUsers() {
        List<User> user = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("users").get();
            user = apiFuture.get().toObjects(User.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return user;
    }

    // get User by UserId
    public User getUserByUserId(String userId) {
        User account = null;
        try {

            ApiFuture<DocumentSnapshot> apiFuture = db.collection("users").document(userId).get();
            account = apiFuture.get().toObject(User.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return account;
    }

    // get User by Username
    public User getUserByUsername(String username) {
        User account = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("users").whereEqualTo("username", username).get();
            QuerySnapshot snapshots = apiFuture.get();
            if (!snapshots.getDocuments().isEmpty()) {
                var userSnapshot = snapshots.getDocuments().get(0);
                account = userSnapshot.toObject(User.class);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return account;
    }

    // get user by email
    public User getUserByEmail(String email) {
        User account = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("users").whereEqualTo("email", email).get();
            QuerySnapshot snapshots = apiFuture.get();
            var userSnapshot = snapshots.getDocuments().get(0);
            account = userSnapshot.toObject(User.class);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return account;
    }

    // get user by phonenumber
    public User getUserByPhoneNumber(String phoneNumber) {
        User account = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("users").whereEqualTo("username", phoneNumber).get();
            QuerySnapshot snapshots = apiFuture.get();
            if (!snapshots.getDocuments().isEmpty()) {
                var userSnapshot = snapshots.getDocuments().get(0);
                account = userSnapshot.toObject(User.class);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return account;
    }

    // not used
    public void createUser(CreateUser createUser) {
        try {
            ApiFuture<WriteResult> apiFuture = db.collection("users").document().set(createUser);
            System.out.printf("Successfully created user at %s \n", apiFuture.get().getUpdateTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
