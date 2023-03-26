package com.rjdxbanking.rjdxbank.Clients;

import com.rjdxbanking.rjdxbank.Entity.Account;
import com.rjdxbanking.rjdxbank.Entity.User;
import com.rjdxbanking.rjdxbank.Helpers.SecretKeyStore;
import com.rjdxbanking.rjdxbank.Services.UserService;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import javafx.scene.control.Alert;

import java.util.concurrent.ThreadLocalRandom;

public class PhoneClient {
    static private int randomVerifier;
    static private User cuser;
    static long totalMiliseconds;
    UserService userService = new UserService();

    public User getUser() {
        return cuser;
    }

    public void phoneOTP(Account account) {
        cuser = userService.getUserByUserId(account.getUserId());
        Twilio.init(SecretKeyStore.getKey("ACCOUNT_SID"), SecretKeyStore.getKey("AUTH_TOKEN"));

        int verificationNO = ThreadLocalRandom.current().nextInt(100000, 1000000);
        randomVerifier = verificationNO;

        String msg = "Hello " + cuser.getFirstName() + " " + cuser.getLastName() + "\n\n";
        msg += "Thanks for using RJDX Bank. Below is your 6 digits verification code, please key in the verirication page to process your change of pin.\n\n";
        msg += verificationNO;

        msg += "\n\nRegards,\n";
        msg += "RDJX Bank";

        Message message = Message.creator(new PhoneNumber(cuser.getPhoneNo()),
                new PhoneNumber(
                        "+15674122358"),
                msg).create();
        totalMiliseconds = System.currentTimeMillis();
        System.out.println(message.getSid());
    }

    public boolean verification(String value) {
        System.out.println(randomVerifier);
        long timeStartMin = totalMiliseconds / 1000 / 60;
        long current = System.currentTimeMillis() / 1000 / 60;
        if ((current - timeStartMin) < 1) {
            if (Integer.toString(randomVerifier).equals(value)) {
                randomVerifier = 0;
                return true;
            }
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Failed");
        alert.setContentText("Timeup. Please verify again.");

        alert.showAndWait();
        return false;
    }

    public void warning(Account account) {
        cuser = userService.getUserByUserId(account.getUserId());
        Twilio.init(SecretKeyStore.getKey("ACCOUNT_SID"), SecretKeyStore.getKey("AUTH_TOKEN"));

        String msg = "Hello " + cuser.getFirstName() + " " + cuser.getLastName() + "\n\n";
        msg += "There is suspicious activity on your account, it will be temporary locked. Please change your pin and unlock your account through the official branch.\n\n";
        msg += "\n\nRegards,\n";
        msg += "RDJX Bank";
        try {
            Message.creator(new PhoneNumber(cuser.getPhoneNo()),
                    new PhoneNumber(
                            "+15674122358"),
                    msg).create();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        // System.out.println(message.getSid());
    }
}
