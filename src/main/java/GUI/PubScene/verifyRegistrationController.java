package GUI.PubScene;

import java.util.ResourceBundle;
import java.io.IOException;
import java.net.URL;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Clients.EmailClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class verifyRegistrationController implements Initializable {

    @FXML
    private JFXButton LGButton;

    @FXML
    private JFXButton RGButton;

    @FXML
    private JFXButton confirmButton;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private TextField verification;

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

    //TODO update db wth the verification from 'N' to 'V'
    @FXML
    void handleConfirmButtonAction(ActionEvent event) throws IOException {
        EmailClient eClient = new EmailClient();

        String verificationNo = verification.getText();

        boolean check = eClient.verification(verificationNo);
        Alert alert = new Alert(AlertType.INFORMATION);
        
        if(check){
            //update db to 
            String Success = "Registration Completed";
            alert.setTitle(Success);
            alert.setContentText("You have successfully completed registration!");
            alert.showAndWait();

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
        else{
            Alert error = new Alert(AlertType.ERROR);
            String failed = "Wrong Value";
            error.setTitle(failed);
            error.setContentText("Verification Value is wrong!");
            error.showAndWait(); 
        }
    }

}
