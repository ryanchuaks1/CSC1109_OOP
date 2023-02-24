package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Clients.AuthClient;
import Clients.EmailClient;
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
		
		VBox box;
		try {
			box = FXMLLoader.load(getClass().getResource("Drawer.fxml"));
			drawer.setSidePane(box);
			drawer.setMinWidth(-100);
			HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(hamburger);
			burgerTask2.setRate(-1);
			hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) ->{
				burgerTask2.setRate(burgerTask2.getRate()*-1);
				burgerTask2.play();
				
				if(drawer.isOpened()){
					drawer.close();
					drawer.setMouseTransparent(true);
				}
				else {
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
		EmailClient eClient = new EmailClient();
		UserService userService = new UserService();
		
		User user = userService.getUserByEmail(Email);

		

		if (user != null){
			eClient.emailVerification(user);

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
