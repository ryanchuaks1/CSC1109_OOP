package GUI.PubScene;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import MainApp.Mainapp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainWindowController implements Initializable{

    @FXML
    private JFXButton LGButton;

    @FXML
    private JFXButton RGButton;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXButton next;

    @FXML
    private JFXButton previous;

    @FXML
    private StackPane stack;

    ImageView[] slides;
    int numberOfSlides;
    int currentSlide;

    private Mainapp mainApp;

    @FXML
    void LoginPage(MouseEvent event) {

    }

    @FXML
    void RegistrationPage(MouseEvent event) {

    }

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
		
		//slideshow part
		
		slides = getPictureFiles();
        numberOfSlides = slides.length;
        
        //timer 
        
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), e-> nextSlide())); //changes every 4 second

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

//        // pause when mouse clicked
//        stack.setOnMouseClicked(e-> {
//            if (timeline.getStatus() == Animation.Status.RUNNING) {
//                timeline.pause();
//            } else {
//                timeline.play();
//            }
//        });
        
        //pause when mouse is at the slideshow
        stack.setOnMouseEntered(e-> {
            if (timeline.getStatus() == Animation.Status.RUNNING) 
                timeline.pause();
        });
        
        //continue when mouse exit the slideshow
        stack.setOnMouseExited(e->timeline.play() );
        
        //change to next slide 
        stack.getChildren().add(slides[currentSlide++]);
        
        //next button
        next.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t){

            	stack.getChildren().clear();
                stack.getChildren().add(slides[(currentSlide++) % slides.length]);
            }
        });
        
        //previous button
        previous.setOnAction(new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent t){
            	
            	if(currentSlide == 0){ 
            		currentSlide = slides.length - 1;
            		stack.getChildren().clear();
            		stack.getChildren().add(slides[(currentSlide)]);
            	}else if(currentSlide < 12 && currentSlide >0){ 
            		stack.getChildren().clear();
            		stack.getChildren().add(slides[(--currentSlide)]);
            	}else{
            		stack.getChildren().clear();
            		stack.getChildren().add(slides[(currentSlide--) % slides.length]);
            	}
        	}
        });
        
	}

    public ImageView[] getPictureFiles() {
    	ImageView[] imageView = new ImageView[12];
    	Path dPath = FileSystems.getDefault().getPath("Resources/image/","pic1.png");
    	Image image = new Image(dPath.toUri().toString());
    	Path dPath2 = FileSystems.getDefault().getPath("Resources/image/","pic2.png");
    	Image image2 = new Image(dPath2.toUri().toString());
    	Path dPath3 = FileSystems.getDefault().getPath("Resources/image/","pic3.png");
    	Image image3 = new Image(dPath3.toUri().toString());
        Path dPath4 = FileSystems.getDefault().getPath("Resources/image/","pic4.png");
    	Image image4 = new Image(dPath4.toUri().toString());
    	Path dPath5 = FileSystems.getDefault().getPath("Resources/image/","pic5.png");
    	Image image5 = new Image(dPath5.toUri().toString());
    	Path dPath6 = FileSystems.getDefault().getPath("Resources/image/","pic6.png");
    	Image image6 = new Image(dPath6.toUri().toString());
        Path dPath7 = FileSystems.getDefault().getPath("Resources/image/","pic7.png");
    	Image image7 = new Image(dPath7.toUri().toString());
    	Path dPath8 = FileSystems.getDefault().getPath("Resources/image/","pic8.png");
    	Image image8 = new Image(dPath8.toUri().toString());
    	Path dPath9 = FileSystems.getDefault().getPath("Resources/image/","pic9.png");
    	Image image9 = new Image(dPath9.toUri().toString());
        Path dPath10 = FileSystems.getDefault().getPath("Resources/image/","pic10.png");
    	Image image10 = new Image(dPath10.toUri().toString());
    	Path dPath11 = FileSystems.getDefault().getPath("Resources/image/","pic11.png");
    	Image image11 = new Image(dPath11.toUri().toString());
    	Path dPath12 = FileSystems.getDefault().getPath("Resources/image/","pic12.png");
    	Image image12 = new Image(dPath12.toUri().toString());
    	
    	imageView[0] = new ImageView(image);
    	imageView[1] = new ImageView(image2);
    	imageView[2] = new ImageView(image3);
        imageView[3] = new ImageView(image4);
    	imageView[4] = new ImageView(image5);
    	imageView[5] = new ImageView(image6);
        imageView[6] = new ImageView(image7);
    	imageView[7] = new ImageView(image8);
    	imageView[8] = new ImageView(image9);
        imageView[9] = new ImageView(image10);
    	imageView[10] = new ImageView(image11);
    	imageView[11] = new ImageView(image12);
           
        for (int i = 0; i < imageView.length; i++){
        	imageView[i].setFitWidth(680);
        	imageView[i].setFitHeight(410);
       }
        
        return imageView;
    }

    private void nextSlide() {
        stack.getChildren().clear();
        stack.getChildren().add(slides[(currentSlide++) % slides.length]);
    }
    
	// Path dPath = FileSystems.getDefault().getPath("Resources/image/profile.jpg");
	// private String imageUrl = dPath.toAbsolutePath().toString();
	
     // getter for mainApp
     public Mainapp getMainApp() {
        return mainApp;
    }

    // setter for mainApp
    public void setMainApp(Mainapp testmainApp) {
        this.mainApp = testmainApp;
    }

}
