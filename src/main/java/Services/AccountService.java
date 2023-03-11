package Services;

import Entity.Account;
import Entity.FixedDepositAccount;
import Entity.SavingsAccount;
import Entity.User;
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
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return account;
    }

    public Account getAccountsByCreditCard(String creditCardNo) {
        Account account = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("accounts").whereEqualTo("creditCardNo", creditCardNo).get();
            QuerySnapshot snapshots = apiFuture.get();
            if (!snapshots.getDocuments().isEmpty()) {
                var accountSnapshot = snapshots.getDocuments().get(0);
                var accountData = accountSnapshot.getData();
                String accountType = (String) accountData.get("accountType");
                if (accountType.equals("Savings")) {
                    account = accountSnapshot.toObject(SavingsAccount.class);
                } else if (accountType.equals("FixedDeposit")) {
                    account = accountSnapshot.toObject(FixedDepositAccount.class);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return account;
    }
}
