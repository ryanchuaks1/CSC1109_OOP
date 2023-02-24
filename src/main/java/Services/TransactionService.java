package Services;

import Entity.Transaction;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;

public class TransactionService {
    Firestore db = FirestoreClient.getFirestore();

    public Transaction getTransactionById(String transactionId) {
        Transaction transaction = null;
        try {
            var apiFuture = db.collection("transactions").document(transactionId).get();
            transaction = apiFuture.get().toObject(Entity.Transaction.class);
            return transaction;

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return transaction;
    }
}
