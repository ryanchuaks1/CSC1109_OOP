package com.rjdxbanking.rjdxbank;

import com.rjdxbanking.rjdxbank.Clients.ATMClient;
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
        //Initialise it with a fixed amount.
        ATMClient client = new ATMClient();
        var changes = client.WithdrawCash(420);
        System.out.println("There are " + changes.getHundredDollars() + " hundred dollars bills");
        System.out.println("There are " + changes.getFiftyDollars() + " fifty dollars bills");
        System.out.println("There are " + changes.getTenDollars() + " ten dollars bills");
        System.out.println("There are " + changes.getFiveDollars() + " five dollars bills");
        System.out.println("There are " + changes.getTwoDollars() + " two dollars bills");

        Navigator.initUserInterface(primaryStage);
    }
}