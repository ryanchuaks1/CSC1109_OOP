package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrawerController {

    @FXML
    private JFXButton Home;

    @FXML
    private JFXButton LG;
	
	@FXML
	private void handleButtonAction(ActionEvent event) throws IOException{
		Stage stage = null; 
		Parent root = null;
		//Login
		if(event.getSource()==LG){
			//get reference to the button's stage         
			stage=(Stage) LG.getScene().getWindow();
			//load up OTHER FXML document
			root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		}
	//	org list
		else if (event.getSource()== Home){
			stage=(Stage)  Home.getScene().getWindow();
			root = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
		}

		//create a new scene with root and set the stage
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
