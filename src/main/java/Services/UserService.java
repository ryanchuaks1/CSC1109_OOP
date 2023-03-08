package Services;

import Entity.Account;
import Entity.User;
import Models.CreateUser;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    Firestore db = FirestoreClient.getFirestore();

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

    public void createUser(CreateUser createUser) {
        try {
            ApiFuture<WriteResult> apiFuture = db.collection("users").document().set(createUser);
            System.out.printf("Successfully created user at %s \n", apiFuture.get().getUpdateTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
