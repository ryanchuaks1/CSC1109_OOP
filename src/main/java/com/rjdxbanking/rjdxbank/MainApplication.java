package com.rjdxbanking.rjdxbank;

import com.rjdxbanking.rjdxbank.Helpers.FirebaseInitialize;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    public static void main(String[] args) {
        FirebaseInitialize.initDatabase();
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Navigator.initUserInterface(primaryStage);
    }
}