package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.ResourceBundle;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import Clients.AuthClient;
import javafx.collections.ObservableList;
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
    private JFXButton createAcc;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private TextField email;

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
    private void handleButtonAction(ActionEvent event) throws IOException{
        Stage stage = null; 
        Parent root = null;
        //create personal account
        if (event.getSource()== createAcc){
            stage=(Stage)  createAcc.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
        }
        //forget password
        else if (event.getSource()== forgetPassword){
            stage=(Stage)  forgetPassword.getScene().getWindow();
            root = FXMLLoader.load(getClass().getResource("ForgotPassword.fxml"));
            }
        //create a new scene with root and set the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    @FXML
    ////to be completed need to rework User Object First
    private void handleLoginButtonAction(ActionEvent event) throws Exception{ 
        Stage stage = null; 
	    Parent root = null;

        String Email = email.getText();
	    String Password = password.getText();

        AuthClient client = new AuthClient();
        
        // verify user account, if they are true go to profile page -> show all the other profile related stuff
        boolean verified = VerifyAccount(Email,Password);
	     
        if(verified== true){
                
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Login Success");
            alert.setContentText("Login Successfully!");
            alert.showAndWait();
                
            if(event.getSource()==login){
                    //get reference to the button's stage         
                    stage=(Stage) login.getScene().getWindow();
                    //load up OTHER FXML document
                    root = FXMLLoader.load(getClass().getResource("ProfileMainWindow.fxml"));
            }
            //create a new scene with root and set the stage
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    //to be completed need to rework User Object First
    public boolean VerifyAccount(String Email,String Password) throws Exception {
		
		boolean success = false;
		String errormessage = "";

		// for (PerModel f:accountList) {
			
		// 	if (f.getEmail().equals(Email) && decrypted.equals(Password)){
				
		// 		account = f;
		// 		success = true;
		// 		PerModel.setAccounts(f);
		// 		break;
		// 	} 
		// 	// Check if email textfield is empty
		// 	if (Email != null && !Email.trim().isEmpty()) {
		// 	}else{
		// 		errormessage += "Email not inputted\n";
		// 		success = false;
		// 	}
			
		// 	// Check if password textfield is empty
		// 	if (Password != null && !Password.trim().isEmpty()) {
		// 	}  else {
		// 		errormessage += "Password not inputted\n";
		// 		success = false;
		// 	}
			
		// }
		// if(success == false){
		// 	Alert alert = new Alert(AlertType.INFORMATION);
		// 	if(errormessage==""){

		// 		alert.setTitle("Login Fail");
		// 		alert.setContentText("You have entered either an incorrect password or email. Please try again.");
		// 		alert.showAndWait();
		// 	}else{
		// 		alert.setTitle("Error");
		// 		alert.setContentText(errormessage);
		// 		alert.showAndWait();
		// 	}
		// }
		return success;
	}
}
