package Models;

import com.password4j.Password;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class CreateUser {
    private String Email;
    private String Username;
    private String Password;
    private String FirstName;
    private String LastName;
    private String PhoneNo;
    private String DOB;
    private boolean Verified;

    public CreateUser(String email, String username, String password, String firstName, String lastName, String phoneNo, LocalDate dateOfBirth, boolean verified) {
        this.Email = email;
        this.Username = username;
        this.Password = com.password4j.Password.hash(password).addRandomSalt().withArgon2().getResult();
        this.FirstName = firstName;
        this.LastName = lastName;
        this.PhoneNo = phoneNo;
        this.DOB = dateOfBirth.atStartOfDay().atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssX"));
        //N for not verified, V for Verified, B for Blocked/banned (Due to excessive usage spamming lockin and got locked)
        this.Verified = verified;
    }

    public String getEmail() {
        return Email;
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

    public String getDOB() {
        return DOB;
    }

    public boolean getVerified() {
        return Verified;
    }
}
