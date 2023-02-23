package Models;

import com.password4j.Password;

public class CreateUser {
    private String Username;
    private String Password;
    private String FirstName;
    private String LastName;
    private String PhoneNo;

    public CreateUser(String username, String password, String firstName, String lastName, String phoneNo) {
        this.Username = username;
        this.Password = com.password4j.Password.hash(password).addRandomSalt().withArgon2().getResult();
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNo = phoneNo;
    }

    public String getUsername() {
        return Username;
    }

    public String getPassword() {
        return Password;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }
}
