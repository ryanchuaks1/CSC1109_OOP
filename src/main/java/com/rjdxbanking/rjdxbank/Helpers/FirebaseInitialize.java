package com.rjdxbanking.rjdxbank.Helpers;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;

//firebase db
public class FirebaseInitialize {
    public static void initDatabase() {
        try {
            //Fix this file stream later :D
            FileInputStream serviceAccount = new FileInputStream("src/main/java/com/rjdxbanking/rjdxbank/Helpers/ServiceAccountKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://rjdxbanking-719f9.firebaseio.com")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
