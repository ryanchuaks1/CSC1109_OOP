package com.rjdxbanking.rjdxbank.Services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Entity.FixedDepositAccount;
import com.rjdxbanking.rjdxbank.Entity.SavingsAccount;
import com.rjdxbanking.rjdxbank.Models.CreateAccount;

import java.util.List;

public class AccountService {
    Firestore db = FirestoreClient.getFirestore();

    // Given a userId find the list of accounts associated with it.
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

    // Given a accountNumber find the account associated with it.
    public Account getAccountsByNumber(String accountNumber) {
        Account account = null;
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("accounts").whereEqualTo("accountNumber", accountNumber)
                    .get();
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

    // Check if account existed
    public boolean checkAccountExist(String accountNo) {
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("accounts").whereEqualTo("accountNumber", accountNo)
                    .get();
            QuerySnapshot snapshots = apiFuture.get();
            if (snapshots.isEmpty())
                return false;
            return true;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    // create Account, not used in our GUI
    public void createAccount(CreateAccount createAccount) {
        try {
            ApiFuture<WriteResult> apiFuture = db.collection("accounts").document().set(createAccount);
            System.out.printf("Successfully created account at %s \n", apiFuture.get().getUpdateTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Update account limit, field {atmWithdrawalField, localTransferLimit,
    // internationalTransferLimit}
    public void updateAccountLimits(Account account, String field, Double value) {
        try {
            DocumentReference docref = db.collection("accounts").document(account.getId());
            docref.update(field, value);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Update account status,
    // use for login - if user key wrongly for 5 times, lock the account temporary
    public void updateAccountStatus(Account account, boolean value) {
        try {
            DocumentReference docref = db.collection("accounts").document(account.getId());
            docref.update("isLocked", value);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    // Settings Change pin of account in firebase. value in argon format
    public void changePin(Account account, String value) {
        try {
            DocumentReference docref = db.collection("accounts").document(account.getId());
            docref.update("pinNo", value);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
