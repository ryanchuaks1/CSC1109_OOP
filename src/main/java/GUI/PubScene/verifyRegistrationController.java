package GUI.PubScene;

import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Clients.EmailClient;
import Clients.PhoneOTPClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class verifyRegistrationController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private ImageView homeBackground;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private Button resendOTP;

    @FXML
    private TextField verification;

    @FXML
    private Button verifyOTP;

    @FXML
    void onButtonClicked(ActionEvent event) throws Exception {
        // loading.setVisible(true); Not working cuz of javafx logic where UI will hang
        // while code is still running
        if (event.getSource() == btnLogin) {
            Navigate.setRoot("Login");
        } else if (event.getSource() == resendOTP) {
            handleResendOTP(event);
        } else if (event.getSource() == verifyOTP) {
            handleConfirmButtonAction(event);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Path homeBackgroundPath = FileSystems.getDefault().getPath("resources/image/", "RJDX_mainbackground.png");
        Image homeBackgroundImage = new Image(homeBackgroundPath.toUri().toString());
        homeBackground.setImage(homeBackgroundImage);

        Path iconPrimaryPath = FileSystems.getDefault().getPath("resources/image/", "IconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

    }

    //TODO update db wth the verification from 'N' to 'V'
    @FXML
    void handleConfirmButtonAction(ActionEvent event) throws IOException{
        PhoneOTPClient pClient = new PhoneOTPClient();

        String verificationNo = verification.getText();

        boolean check = pClient.verification(verificationNo);
        Alert alert = new Alert(AlertType.INFORMATION);
        
        if(check){
            //update db to 
            String Success = "Registration Completed";
            alert.setTitle(Success);
            alert.setContentText("You have successfully completed registration!");
            alert.showAndWait();

            Navigate.setRoot("Login");
        }
        else{
            Alert error = new Alert(AlertType.ERROR);
            String failed = "Wrong Value";
            error.setTitle(failed);
            error.setContentText("Verification Value is wrong!");
            error.showAndWait(); 
        }
    }

    //TODO update db wth the verification from 'N' to 'V'
    @FXML
    void handleResendOTP(ActionEvent event) throws Exception{
        PhoneOTPClient pClient = new PhoneOTPClient();

        try{
            //OTP
            pClient.phoneOTP(pClient.getUser());
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("OTP updated");
            alert.setContentText("OTP updated.!");
            alert.showAndWait();
        } catch (Exception e){
            System.out.println(e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("OTP Failed");
            alert.setContentText("OTP failed due to unknown error!");
            alert.showAndWait();
        }
        
    }

}
