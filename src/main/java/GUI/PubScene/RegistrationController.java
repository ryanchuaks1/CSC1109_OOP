package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Clients.AuthClient;
import Clients.EmailClient;
import Entity.User;
import Exceptions.UserNotFoundException;
import Models.CreateUser;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class RegistrationController implements Initializable {

    @FXML
    private TextField DOB;

    @FXML
    private Button btnCreateUser;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField countryCode;

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
        String countrycode = countryCode.getText();
        String Contact = phoneNo.getText();
        String BirthDate = DOB.getText();

        try {
            checkFields(Email, Username, Password, Password2, firstName,
                    lastName, countrycode, Contact, BirthDate);
            checkPassword(Password, Password2);
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

        // String errormessage1 = validateEmail();
        // if (errormessage1 != null || errormessage1 != "") {
        // errormessage += errormessage1;
        // }
        // // validatePasswordStrength
        // String errormessage2 = passwordStrength(Password);
        // if (errormessage2 != null || errormessage2 != "") {
        // errormessage += errormessage2;
        // }
        // // Validate Legit Contact
        // String errormessage3 = validateContact(Contact);
        // if (errormessage3 != null || errormessage3 != "") {
        // errormessage += errormessage3;
        // }
        // // validate DOB
        // String errormessage4 = validateDOB(BirthDate);
        // if (errormessage4 != null || errormessage4 != "") {
        // errormessage += errormessage4;
        // }
        // System.out.println("'" + errormessage + "'");

        // if (errormessage.equals("")) {

        // // need to add the registration on authClient
        // AuthClient authclient = new AuthClient();
        // EmailClient emailclient = new EmailClient();

        // CreateUser createuser = new CreateUser(Email, Username, Password, firstName,
        // lastName,
        // (countrycode + Contact), BirthDate, false);
        // authclient.Register(createuser);
        // UserService userService = new UserService();
        // User user = userService.getUserByUsername(Username);
        // emailclient.emailVerification(user, "register");

        // String Success = "Registration Success";
        // Alert alert = new Alert(AlertType.INFORMATION);
        // alert.setTitle(Success);
        // alert.setContentText("You have successfully created an Account! Please check
        // your email for verification");
        // alert.showAndWait();

        // if (event.getSource() == confirmButton) {
        // Stage stage = null;
        // Parent root = null;

        // // get reference to the button's stage
        // stage = (Stage) confirmButton.getScene().getWindow();
        // // load up OTHER FXML document (May have to check to link to another
        // // verification page through email instead)

        // root = FXMLLoader.load(getClass().getResource("verifyRegistration.fxml"));
        // // create a new scene with root and set the stage
        // Scene scene = new Scene(root);
        // stage.setScene(scene);
        // stage.show();
        // }
    }

    // check all text field is filled
    public void checkFields(String... strings) throws Exception {
        for (String s : strings) {
            if (s.isEmpty() || s.trim().isEmpty()) {
                throw new Exception("Please fill in all the fields");
            }
        }

    }

    // password Strength
    public void checkPassword(String password, String password2) throws Exception {
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
            else {
                throw new Exception("OK WE STOP HERE FOR NOW GO SLEEP");
            }
        } else {
            throw new Exception("Password is less then 8 characters");
        }
    }

    // verify Contact
    public String verifyContact(String contact) {
        String error = validateContact(contact);
        if (error.isEmpty() || error == null) {
            return contact;
        } else
            return "";
    }

    // TODO: need to amend to fit for all kinds of country phone numbers
    public String validateContact(String contact) {
        boolean firstDigit = false; // Check if start with 9 or 8
        boolean noalphabet = false; // Check if contact is made out of digits only
        boolean space = false;
        String errormessage = "";

        if (contact.length() == 8) {
            if (contact.charAt(0) == '9') {
            } else if (contact.charAt(0) == '8') {
            } else
                firstDigit = true;

            for (int i = 0; i < contact.length(); i++) {
                char x = contact.charAt(i);
                if (Character.isLetter(x)) {
                    noalphabet = true;
                }
                if (Character.isWhitespace(x)) {
                    space = true;
                }
                if (noalphabet) {
                    break;
                }
                if (space) {
                    break;
                }
            }

            if (noalphabet || space) {
                errormessage += "Invalid Contact number\n";
            }
            if (firstDigit) {
                errormessage += "Contact numbers start with 9 or 8\n";
            }

        } else
            errormessage = "Contact contains only 8 numbers";
        return errormessage;
    }

    private String validateEmail() {
        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(email.getText());
        if (m.find() && m.group().equals(email.getText())) {
            return "";
        } else {
            return "Please provide a valid Email\n";
        }
    }

    // verify Email
    public String verifyEmail(String Email) {
        String error = validateEmail();
        if (error.isEmpty() || error == null) {
            return Email;
        } else
            return "";
    }

    public String validateDOB(String DOB) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dob = LocalDate.parse(DOB, formatter);
        LocalDate now = LocalDate.now();

        long diff = ChronoUnit.YEARS.between(dob, now);
        if (diff < 18) {
            return "Age is not eligible to register or use our bank services.";
        } else {
            return "";
        }

    }
}
