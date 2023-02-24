package Services;

import Entity.Account;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;

public class AccountService {
    Firestore db = FirestoreClient.getFirestore();

    //Given a userId find the list of accounts associated with it.
    public List<Account> getAccounts(String userId) {
        List<Account> account = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("accounts").whereEqualTo("userId", userId).get();
            account = apiFuture.get().toObjects(Account.class);
            return account;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return account;
    }
}
