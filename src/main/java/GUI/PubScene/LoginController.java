package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.password4j.Password;

import Clients.AuthClient;
import Entity.User;
import Models.LoginUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private JFXButton LGButton;

    @FXML
    private JFXButton RGButton;

    @FXML
    private JFXButton createAcc;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private TextField Username;

    @FXML
    private JFXButton forgetPassword;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton login;

    @FXML
    private PasswordField password;

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
    private void handleButtonAction(ActionEvent event) throws IOException {
        Stage stage = null;
        Parent root = null;
        // create personal account
        if (event.getSource() == createAcc) {
            stage = (Stage) createAcc.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
        }
        // forget password
        else if (event.getSource() == forgetPassword) {
            stage = (Stage) forgetPassword.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
        }
        // create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    //// to be completed need to rework User Object First
    private void handleLoginButtonAction(ActionEvent event) throws Exception {
        Stage stage = null;
        Parent root = null;

        String username = Username.getText();
        String Password = password.getText();

        AuthClient client = new AuthClient();

        LoginUser loginUser = new LoginUser(username, Password);

        // verify user account, if they are true go to profile page -> show all the
        // other profile related stuff
        // boolean verified = VerifyAccount(Email, Password);

        User userconfirmation = client.Login(loginUser);

        if (userconfirmation != null) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login Success");
            alert.setContentText("Login Successfully!");
            alert.showAndWait();

            if (event.getSource() == login) {
                // get reference to the button's stage
                stage = (Stage) login.getScene().getWindow();
                // load up OTHER FXML document
                root = FXMLLoader.load(getClass().getResource("ProfileMainWindow.fxml"));
            }
            // create a new scene with root and set the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            // may consider adding condition to prevent user from continuous spamming on
            // login and maybe lock account.
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login failure");
            alert.setContentText("Username or password is wrong.");
            alert.showAndWait();
        }
    }

    // to be completed need to rework User Object First
    public boolean VerifyAccount(String Email, String Password) throws Exception {

        boolean success = false;
        String errormessage = "";

        // for (PerModel f:accountList) {

        // if (f.getEmail().equals(Email) && decrypted.equals(Password)){

        // account = f;
        // success = true;
        // PerModel.setAccounts(f);
        // break;
        // }
        // // Check if email textfield is empty
        // if (Email != null && !Email.trim().isEmpty()) {
        // }else{
        // errormessage += "Email not inputted\n";
        // success = false;
        // }

        // // Check if password textfield is empty
        // if (Password != null && !Password.trim().isEmpty()) {
        // } else {
        // errormessage += "Password not inputted\n";
        // success = false;
        // }

        // }
        // if(success == false){
        // Alert alert = new Alert(AlertType.INFORMATION);
        // if(errormessage==""){

        // alert.setTitle("Login Fail");
        // alert.setContentText("You have entered either an incorrect password or email.
        // Please try again.");
        // alert.showAndWait();
        // }else{
        // alert.setTitle("Error");
        // alert.setContentText(errormessage);
        // alert.showAndWait();
        // }
        // }
        return success;
    }
}
