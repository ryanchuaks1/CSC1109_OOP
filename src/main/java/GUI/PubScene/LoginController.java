package GUI.PubScene;

import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LoginController implements Initializable {

    @FXML
    private ImageView homeBackground;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private VBox homeVbox;

    @FXML
    private Button button;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Path homeBackgroundPath = FileSystems.getDefault().getPath("resources/image/", "RJDX_mainbackground.png");
        Image homeBackgroundImage = new Image(homeBackgroundPath.toUri().toString());
        homeBackground.setImage(homeBackgroundImage);

        Path iconPrimaryPath = FileSystems.getDefault().getPath("resources/image/", "IconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

        this.button.getStyleClass().setAll("btn","btn-danger");

    }
}
