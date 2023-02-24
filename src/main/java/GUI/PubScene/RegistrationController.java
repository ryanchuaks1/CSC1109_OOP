package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegistrationController implements Initializable {

    @FXML
    private TextField DOB;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXButton LGButton;

    @FXML
    private JFXButton RGButton;

    @FXML
    private TextField countryCode;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private TextField email;

    @FXML
    private TextField fName;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TextField lName;

    @FXML
    private PasswordField password1;

    @FXML
    private PasswordField password2;

    @FXML
    private TextField phoneNO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        VBox box;
        try {
            box = FXMLLoader.load(getClass().getResource("Drawer.fxml"));
            drawer.setSidePane(box);
            drawer.setMinWidth(-100);
            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
            burgerTask2.setRate(-1);
            hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
                burgerTask2.setRate(burgerTask2.getRate() * -1);
                burgerTask2.play();

                if (drawer.isOpened()) {
                    drawer.close();
                    drawer.setMouseTransparent(true);
                } else {
                    drawer.setMouseTransparent(false);
                    drawer.open();
                }
            });

        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    @FXML
    void handleLoginPage(MouseEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        // create personal account

        stage = (Stage) LGButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        // create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleRegistrationPage(MouseEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        // Registration page

        stage = (Stage) RGButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Registration.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleConfirmButtonAction(ActionEvent event) throws IOException {
        String Email = "";
        String Password = "";
        String Password2 = "";
        String firstName = "";
        String lastName = "";
        String countrycode = "";
        String Contact = "";
        String BirthDate = "";

        Email = email.getText();
        Password = password1.getText();
        Password2 = password2.getText();
        firstName = fName.getText();
        lastName = lName.getText();
        countrycode = countryCode.getText();
        Contact = phoneNO.getText();
        BirthDate = DOB.getText();

        String errormessage = verifyRegister(Email, Password, Password2, firstName, lastName, countrycode, Contact,
                BirthDate);

        // Validate Legit email
        String errormessage4 = validateEmail();
        if (errormessage4 != null || errormessage4 != "") {
            errormessage += errormessage4;
        }

        // validatePasswordStrength
        String errormessage2 = passwordStrength(Password);
        if (errormessage2 != null || errormessage2 != "") {
            errormessage += errormessage2;
        }
        // Validate Legit Contact
        String errormessage3 = validateContact(Contact);
        if (errormessage3 != null || errormessage3 != "") {
            errormessage += errormessage3;
        }

        if (errormessage == "") {

            // need to add the registration on authClient

            String Success = "Registration Success";
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(Success);
            alert.setContentText("You have successfully created an Account!");
            alert.showAndWait();

            if (event.getSource() == confirmButton) {
                Stage stage = null;
                Parent root = null;

                // get reference to the button's stage
                stage = (Stage) confirmButton.getScene().getWindow();
                // load up OTHER FXML document (May have to check to link to another
                // verification page through email instead)

                root = FXMLLoader.load(getClass().getResource("PersonalLogin.fxml"));
                // create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } else {
            String failed = "Registration Failed";

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(failed);
            alert.setContentText(errormessage);

            alert.showAndWait();
        }
    }

    // check all text field is filled
    public String verifyRegister(String email, String password, String password2, String fName, String lName,
            String cCode, String contactNumber,
            String birthDate) {
        String errormessage = "";
        // Check if email textfield is empty
        if (email != null && !email.trim().isEmpty()) {
        } else
            errormessage += "Email not inputted\n";

        // Check if password textfield is empty
        if (password != null && !password.trim().isEmpty()) {
        } else
            errormessage += "Password not inputted\n";

        // Check if password2 textfield is empty
        if (password2 != null && !password2.trim().isEmpty()) {
        } else
            errormessage += "Confirm Password not inputted\n";

        // Check if password textfield and password 2 is equal
        if (password.equals(password2)) {
        } else
            errormessage += "Passwords not equal\n";

        // Check if fname textfield is empty
        if (fName != null && !fName.trim().isEmpty()) {
        } else
            errormessage += "First name not inputted\n";

        // Check if lname textfield is empty
        if (lName != null && !lName.trim().isEmpty()) {
        } else
            errormessage += "Last name not inputted\n";

        // Check if contact textfield is empty
        if (contactNumber != null && !contactNumber.trim().isEmpty()) {
        } else
            errormessage += "Contact Number not inputted\n";

        // Check if birthdate textfield is empty
        if (birthDate != null && !birthDate.trim().isEmpty()) {
        } else
            errormessage += "Birthdate not inputted\n";

        // Check if countryCode textfield is empty
        if (cCode == null || cCode == "") {
            errormessage += "Please provide your countryCode\n";
        }

        return errormessage;
    }

    // password Strength
    public String passwordStrength(String password) {

        boolean hasUpperCase = false;// at least 1 uppercase
        boolean hasLowerCase = false; // at least 1 lowercase
        boolean hasNumbers = false; // at least 1 digit number
        String errormessage = "";

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
            if (hasNumbers && hasLowerCase && hasUpperCase) {
                errormessage = "";
            } else {
                errormessage = "Password is not strong. Please check whether your password contains at least 1 uppercase alphabet, 1 lowercase alphabet and 1 number.\n";
            }
        } else
            errormessage = "Password less than 8 characters\n";
        return errormessage;
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
}
