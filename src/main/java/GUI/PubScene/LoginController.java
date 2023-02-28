package GUI.PubScene;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class LoginController implements Initializable {

    @FXML
    private Text btnForgetPassword;

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

    @FXML
    private void 
}
