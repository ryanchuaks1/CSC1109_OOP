package com.rjdxbanking.rjdxbank.Clients;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.IOException;
import com.rjdxbanking.rjdxbank.Helpers.Navigator;

public class TimeoutClient {
    private static final int TIMEOUT_SECONDS = 3;
    Duration delay = Duration.seconds(TIMEOUT_SECONDS);
    PauseTransition transition = new PauseTransition(delay);
    
    public void resetTimer() {
        stopTimer();
        startTimer();
    }

    public void startTimer() {
        transition.setOnFinished(evt -> {
            try {
                Navigator.logout();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        transition.play();
    }

    public void stopTimer() {
        transition.stop();
    }
}
