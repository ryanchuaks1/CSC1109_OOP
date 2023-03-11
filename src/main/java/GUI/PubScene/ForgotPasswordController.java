package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Clients.AuthClient;
import Entity.User;
import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ForgotPasswordController implements Initializable{

	@FXML
    private JFXButton LGButton;

    @FXML
    private JFXButton RGButton;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private TextField email;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton submit;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

	@FXML
    void handleLoginPage(MouseEvent event) throws IOException{
		Stage stage = null; 
        Parent root = null;
        //create personal account
        
		stage=(Stage)  LGButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        
        //create a new scene with root and set the stage
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleRegistrationPage(MouseEvent event) throws IOException{
		Stage stage = null; 
        Parent root = null;
        //Registration page

		stage=(Stage)  RGButton.getScene().getWindow();
		root = FXMLLoader.load(getClass().getResource("Registration.fxml"));

		Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    //to be completed after user portion is done
	private void handleSubmitButtonAction(ActionEvent event) throws IOException{

		Stage stage = null; 
		Parent root = null;

		String Email = email.getText();
		boolean success = false;
		
		AuthClient aClient = new AuthClient();
		UserService userService = new UserService();
		
		User user = userService.getUserByEmail(Email);


		if (user != null){

			stage=(Stage)  LGButton.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("verifyReset.fxml"));
			
			//create a new scene with root and set the stage
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();

		} else{
			String failed = "Email Not Found";
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle(failed);
            alert.setContentText("Email not found");

            alert.showAndWait();
		}

	}
}
