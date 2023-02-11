package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ForgotPasswordController implements Initializable{

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
			box = FXMLLoader.load(getClass().getResource("/CSGO/PubView/Drawer.fxml"));
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
    //to be completed after user portion is done
	private void handleSubmitButtonAction(ActionEvent event) throws IOException{

		Stage stage = null; 
		Parent root = null;

		String Email = email.getText();
		boolean success = false;
		// ObservableList<PerModel> accountList=dao.getAllAccounts();
		// PerModel account=null;
		// for (PerModel f:accountList) {

		// 	if (f.getEmail().equals(Email)){

		// 		account = f;
		// 		PerModel.setAccounts(account);
		// 		success = true;
		// 		break;
		// 	} 

		// }
		// if(success){
		// 	//get reference to the button's stage         
		// 	stage=(Stage) submit.getScene().getWindow();
		// 	//load up OTHER FXML document
		// 	root = FXMLLoader.load(getClass().getResource("/CSGO/PubView/PerSecurityQuestion.fxml"));
		// 	//create a new scene with root and set the stage
		// 	Scene scene = new Scene(root);
		// 	stage.setScene(scene);
		// 	stage.show();
		// }
		// else {
		// 	Alert alert = new Alert(AlertType.INFORMATION);
		// 	alert.setTitle("Incorrect Email");
		// 	alert.setContentText("You have entered an incorrect email. Please try again.");
		// 	alert.showAndWait();
		// }

	}
}
