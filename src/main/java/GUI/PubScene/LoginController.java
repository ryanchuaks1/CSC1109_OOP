package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import Clients.AuthClient;
import Entity.User;
import Exceptions.UserNotFoundException;
import Models.LoginUser;

// FXML imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;


public class LoginController implements Initializable {

    @FXML
    private Button btnForgetPassword;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private ImageView homeBackground;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;
    
    @FXML
    private VBox loading;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path homeBackgroundPath = FileSystems.getDefault().getPath("resources/image/", "RJDX_mainbackground.png");
        Image homeBackgroundImage = new Image(homeBackgroundPath.toUri().toString());
        homeBackground.setImage(homeBackgroundImage);

        Path iconPrimaryPath = FileSystems.getDefault().getPath("resources/image/", "IconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

        this.btnLogin.getStyleClass().setAll("btn", "btn-danger");
        this.btnRegister.getStyleClass().setAll("btn", "btn-default");
    }

    // Handle Button Click
    @FXML
    private void onButtonClicked(ActionEvent event) throws IOException, UserNotFoundException {
        // loading.setVisible(true); Not working cuz of javafx logic where UI will hang while code is still running
        if (event.getSource() == btnLogin) {
            handleLogin();
        } else if (event.getSource() == btnRegister) {
            Navigate.setRoot("Registration");
        } else if (event.getSource() == btnForgetPassword) {
            Navigate.setRoot("ForgotPassword");
        }
    }

    // Login Function
    private void handleLogin() throws UserNotFoundException, IOException {
        AuthClient client = new AuthClient();
        String username = usernameField.getText();
        String Password = passwordField.getText();
        LoginUser loginUser = new LoginUser(username, Password);
        try {
            User user = client.Login(loginUser);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login Success");
            alert.setHeaderText(null);
            alert.setContentText("Login Successfully!");
            alert.showAndWait();
            loading.setVisible(false);
        } catch (Exception ex) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Login failure");
            alert.setHeaderText(null);
            alert.setContentText("Username or password is wrong.");
            alert.showAndWait();
            loading.setVisible(false);
        }
    }
}
