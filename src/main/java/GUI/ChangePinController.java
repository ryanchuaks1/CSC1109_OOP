package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

import com.password4j.Password;

import Clients.SessionClient;
import Entity.Account;
import Services.AccountService;

public class ChangePinController implements Initializable {

    @FXML
    private Button changePin;

    @FXML
    private PasswordField confirmNewpin;

    @FXML
    private PasswordField newPin;

    @FXML
    private PasswordField oldPin;

    SessionClient sClient = new SessionClient();

    @FXML
    void onButtonPress(ActionEvent event) {
        
        String oldpin = oldPin.getText();
        String newpin = newPin.getText();
        String confirmNewin = newPin.getText();

        Account currentAcc = sClient.getAccount();
        AccountService accService = new AccountService();
        //check if old is correct
        if (Password.check(oldpin, currentAcc.getPinNo()).withArgon2()) {
            //check if new pin is same as old pin, if it is not the same perform change of pin
            if(!Password.check(newpin, currentAcc.getPinNo()).withArgon2()){
                if(newpin.equals(confirmNewin)){
                    newpin = com.password4j.Password.hash(newpin).addRandomSalt().withArgon2().getResult();
                    accService.changePin(currentAcc, newpin);
                }
                else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("New pin and confirm New Pin are not the same");
                    alert.setHeaderText(null);
                    alert.setContentText("New pin and confirm New Pin are not the same");
                    alert.showAndWait();
                }
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Pin cannot be the same as old pin");
                alert.setHeaderText(null);
                alert.setContentText("Pin cannot be the same as old pin");
                alert.showAndWait();
            }
        }
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Path iconPrimaryPath = FileSystems.getDefault().getPath("src/main/resources/images/", "IconPrimary.png");
        // Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        // iconPrimary.setImage(iconPrimaryImage);
    }

}
