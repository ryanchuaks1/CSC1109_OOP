package Entity;

import Models.CreateAccount;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import com.google.firebase.cloud.FirestoreClient;

import javax.swing.text.Document;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    @DocumentId
    private String Id;
    @PropertyName("username")
    private String username;
    @PropertyName("password")
    private String password;
    @PropertyName("firstName")
    private String firstName;
    @PropertyName("lastName")
    private String lastName;
    @PropertyName("phoneNo")
    private String phoneNo;

    Firestore db = FirestoreClient.getFirestore();

    //Required empty constructor for firestore
    public User()
    {}

    public String getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public List<Account> getAccounts()
    {
        List<Account> accounts = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> apiFuture = db.collection("accounts").whereEqualTo("userId", this.Id).get();
            QuerySnapshot snapshots = apiFuture.get();

            accounts = snapshots.toObjects(Account.class);
            return accounts;
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return accounts;
    }

    //TODO: Add different account type for different interest name.
    public void CreateAccount()
    {
        CreateAccount createAccount = new CreateAccount(this.Id);
        try {
            ApiFuture<WriteResult> apiFuture = db.collection("accounts").document().set(createAccount);
            System.out.printf("Successfully created account for UserId(%s) at %s %n", this.Id , apiFuture.get().getUpdateTime());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
