package Entity;

import Models.CreateAccount;
import Services.AccountService;
import Services.UserService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;
import com.google.firebase.cloud.FirestoreClient;

import java.util.List;

public class User {
    @DocumentId
    private String Id;
    @PropertyName("email")
    private String email;
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
    @PropertyName("DOB")
    private String DOB;
    @PropertyName("verified")
    private String verified;

    AccountService accountService = new AccountService();
    //Required empty constructor for firestore
    public User()
    {}

    public String getId() {
        return Id;
    }

    public String getEmail() {
        return email;
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

    public String getDOB() {
        return DOB;
    }
    
    public String getVerified() {
        return verified;
    }

    public List<Account> getAccounts()
    {
        return accountService.getAccounts(this.getId());
    }
}
