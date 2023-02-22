package Models;

import com.password4j.Password;

public class CreateUser {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNo;

    public CreateUser(String username, String password, String firstName, String lastName, String phoneNo) {
        this.username = username;
        this.password = Password.hash(password).addRandomSalt().withArgon2().getResult();
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNo = phoneNo;
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

    public String getPhoneNo() {
        return phoneNo;
    }
}
