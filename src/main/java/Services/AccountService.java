package Services;

import Entity.Account;
import com.google.firebase.cloud.FirestoreClient;

public class AccountService {
    Forestore db = FirestoreClient.getFirestore();

    public List<Account> getAccounts() {
        List<Account> account = null;
    }
}
