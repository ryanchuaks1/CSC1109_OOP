package Clients;

import java.util.concurrent.ThreadLocalRandom;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import Entity.User;
import Helpers.SecretKeyStore;
import Models.CreateUser;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PhoneOTPClient {

    static private int randomVerifier;
    static private User usertemp;
    static long totalMiliseconds;

    public void phoneOTP(CreateUser user) {
        Twilio.init(SecretKeyStore.getKey("ACCOUNT_SID"), SecretKeyStore.getKey("AUTH_TOKEN"));

        int verificationNO = ThreadLocalRandom.current().nextInt(100000, 1000000);
        randomVerifier = verificationNO;

        String msg = "Welcome " + user.getFirstName() + " " + user.getLastName() + "\n\n";
        msg += "Thanks for joining RJDX Bank, Below is your 6 digits verification code, please key in the verirication page\n\n";
        msg += verificationNO;

        msg += "\n\nRegards,\n";
        msg += "RDJX Bank";

        Message message = Message.creator(new PhoneNumber(user.getPhoneNo()),
                new PhoneNumber(
                        "+15674122358"),
                msg).create();

        System.out.println(message.getSid());
    }

    public boolean verification(String value) {
        System.out.println(randomVerifier);
        long timeStartMin = totalMiliseconds / 1000 / 60;
        long current = System.currentTimeMillis() / 1000 / 60;
        if ((current - timeStartMin) < 5) {
            if (Integer.toString(randomVerifier).equals(value)) {
                randomVerifier = 0;
                return true;
            }
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Failed");
        alert.setContentText("Timeup. Please verify again.");

        alert.showAndWait();
        return false;
    }

}
