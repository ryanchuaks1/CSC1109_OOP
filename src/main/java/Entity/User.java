package Entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.PropertyName;

import java.util.Date;

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
        return phoneNo;
    }
}
