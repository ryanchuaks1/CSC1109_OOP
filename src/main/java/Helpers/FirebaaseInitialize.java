package Helpers;

import java.io.FileInputStream;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public class FirebaaseInitialize {

    public void initDatabase() throws Exception {
        try {
            FileInputStream serviceAccount = new FileInputStream("../../../ServiceAccountKey.json");

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();
    
            FirebaseApp.initializeApp(options);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
