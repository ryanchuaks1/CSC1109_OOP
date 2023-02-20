package Helpers;

import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

// Import the User object
import Models.User;

public class UserService {

    Firestore db = FirestoreClient.getFirestore();

    // Ser the user into the database
	public String saveUserDetails(User user) throws InterruptedException, ExecutionException {
        ApiFuture<WriteResult> future = db.collection("users").document(user.getUserID()).set(user);
        return future.get().getUpdateTime().toString();
    }

	public User getUserDetails(String UserID) throws InterruptedException, ExecutionException {
		DocumentReference docRef = db.collection("users").document(UserID);
		ApiFuture<DocumentSnapshot> future = docRef.get();
		DocumentSnapshot document = future.get();
		User user = null;
		user = document.toObject(User.class);
        
		if (document.exists()) {
            user = document.toObject(User.class);
		  System.out.println(user);
		  return user;
		} else {
		  System.out.println("No such document!");
		  return null;
		}
	}
	
	public String deleteUser(String UserID) throws InterruptedException, ExecutionException {
		Firestore db = FirestoreClient.getFirestore();
		ApiFuture<WriteResult> writeResult = db.collection("users").document(UserID).delete();
		return writeResult.get().getUpdateTime().toString();
	}
}
