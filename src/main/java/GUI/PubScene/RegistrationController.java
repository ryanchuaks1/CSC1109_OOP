package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Clients.AuthClient;
import Clients.RegistrationClient;
import Exceptions.UserNotFoundException;
import Models.CreateUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class RegistrationController implements Initializable {

    @FXML
    private DatePicker DOB;

    @FXML
    private Button btnCreateUser;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField email;

    @FXML
    private TextField fName;

    @FXML
    private ImageView homeBackground;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private TextField lName;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private TextField phoneNo;

    @FXML
    private TextField username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path homeBackgroundPath = FileSystems.getDefault().getPath("resources/image/", "RJDX_mainbackground.png");
        Image homeBackgroundImage = new Image(homeBackgroundPath.toUri().toString());
        homeBackground.setImage(homeBackgroundImage);

        Path iconPrimaryPath = FileSystems.getDefault().getPath("resources/image/", "IconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);
    }

    @FXML
    private void onButtonClicked(ActionEvent event) throws IOException, UserNotFoundException {
        // loading.setVisible(true); Not working cuz of javafx logic where UI will hang
        // while code is still running
        if (event.getSource() == btnLogin) {
            Navigate.setRoot("Login");
        } else if (event.getSource() == btnCreateUser) {
            handleCreateUser(event);
        }
    }

    @FXML
    void handleCreateUser(ActionEvent event) throws IOException {
        String Email = email.getText();
        String Username = username.getText();
        String Password = password1.getText();
        String Password2 = password2.getText();
        String firstName = fName.getText();
        String lastName = lName.getText();
        String Contact = phoneNo.getText();
        LocalDate BirthDate = DOB.getValue();

        try {
            checkFields(Email, Username, Password, Password2, firstName,
                    lastName, Contact);
            checkPassword(Password, Password2);
            validateDOB(BirthDate);
            validateEmail(Email);
            validateContact(Contact);
            CreateUser newUser = new CreateUser(Email, Username, Password, firstName, lastName, Contact, BirthDate, false);
            AuthClient.Register(newUser);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // check all text field is filled
    private void checkFields(String... strings) throws Exception {
        for (String s : strings) {
            if (s.isEmpty() || s.trim().isEmpty()) {
                throw new Exception("Please fill in all the fields");
            }
        }

    }

    // Password match and strength
    private void checkPassword(String password, String password2) throws Exception {
        boolean hasUpperCase = false;// at least 1 uppercase
        boolean hasLowerCase = false; // at least 1 lowercase
        boolean hasNumbers = false; // at least 1 digit number

        if (!password.equals(password2)) {
            throw new Exception("Passwords do not match");
        }
        if (password.length() >= 8) {
            for (int i = 0; i < password.length(); i++) {
                char x = password.charAt(i);
                if (Character.isUpperCase(x)) {
                    hasUpperCase = true;
                } else if (Character.isLowerCase(x)) {
                    hasLowerCase = true;
                } else if (Character.isDigit(x)) {
                    hasNumbers = true;
                }
                if (hasUpperCase && hasLowerCase && hasNumbers) {
                    break;
                }
            }
            if (!hasUpperCase || !hasLowerCase || !hasNumbers) {
                throw new Exception("Password is too weak");
            }
        } else {
            throw new Exception("Password is less then 8 characters");
        }
    }

    // Check DOB is filled and age 18 and above
    private void validateDOB(LocalDate birthDate) throws Exception {
        try {
            LocalDate today = LocalDate.now();
            Period period = Period.between(birthDate, today);
            int age = period.getYears();
            if (age < 18) {
                throw new Exception("You must be 18 to create a personal account");
            }
        } catch (NullPointerException e) {
            throw new Exception("Please fill in all the fields");
        }
    }

    // Check if email format is valid
    private void validateEmail(String Email) throws Exception {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(Email);
        if (!m.find() || !m.group().equals(Email)) {
            throw new Exception("Please enter a valid email");
        }
    }

    // Check if contant number is valid
    private void validateContact(String contact) throws Exception {

        if (!(contact.length() == 8)) {
            throw new Exception("Contact should only contain 8 numbers");
        }
        if (!(contact.charAt(0) == '9' || contact.charAt(0) == '8')) {
            throw new Exception("Contact numbers start with 9 or 8");
        }
        for (int i = 0; i < contact.length(); i++) {
            char x = contact.charAt(i);
            if (Character.isLetter(x)) {
                throw new Exception("Contact number should only contain numbers");
            }
            if (Character.isWhitespace(x)) {
                throw new Exception("Remove spaces from contact number");
            }
        }
    }
}
