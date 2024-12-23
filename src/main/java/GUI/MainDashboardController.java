package GUI;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;

import Clients.SessionClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainDashboardController implements Initializable {

    @FXML
    private Button btnChinese;

    @FXML
    private Button btnEnglish;

    @FXML
    private Button btnMalay;

    @FXML
    private ImageView iconDeposit;

    @FXML
    private ImageView iconPrimary;

    @FXML
    private ImageView iconSettings;

    @FXML
    private ImageView iconTransfer;

    @FXML
    private ImageView iconWithdraw;

    @FXML
    private ImageView iconTransHist;

    @FXML
    private ImageView iconExit;

    @FXML
    private Label nameLabel;

    @FXML
    private Pane transHistoryPane;

    @FXML
    private Pane depositPane;

    @FXML
    private Pane exitPane;

    @FXML
    private Pane settingsPane;

    @FXML
    private Pane transferPane;

    @FXML
    private Pane withdrawPane;
    SessionClient sc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Path iconPrimaryPath = FileSystems.getDefault().getPath("src/main/resources/images/", "WhiteIconPrimary.png");
        Image iconPrimaryImage = new Image(iconPrimaryPath.toUri().toString());
        iconPrimary.setImage(iconPrimaryImage);

        Path iconDepositPath = FileSystems.getDefault().getPath("src/main/resources/images/", "DepositIcon.png");
        Image iconDepositImage = new Image(iconDepositPath.toUri().toString());
        iconDeposit.setImage(iconDepositImage);

        Path iconTransferPath = FileSystems.getDefault().getPath("src/main/resources/images/", "TransferIcon.png");
        Image iconTransferImage = new Image(iconTransferPath.toUri().toString());
        iconTransfer.setImage(iconTransferImage);

        Path iconWithdrawPath = FileSystems.getDefault().getPath("src/main/resources/images/", "WithdrawIcon.png");
        Image iconWithdrawImage = new Image(iconWithdrawPath.toUri().toString());
        iconWithdraw.setImage(iconWithdrawImage);

        Path iconSettingsPath = FileSystems.getDefault().getPath("src/main/resources/images/", "SettingsIcon.png");
        Image iconSettingsImage = new Image(iconSettingsPath.toUri().toString());
        iconSettings.setImage(iconSettingsImage);

        Path iconTransHistPath = FileSystems.getDefault().getPath("src/main/resources/images/", "TransHistoryIcon.png");
        Image iconTransHistImage = new Image(iconTransHistPath.toUri().toString());
        iconTransHist.setImage(iconTransHistImage);

        Path iconExitPath = FileSystems.getDefault().getPath("src/main/resources/images/", "ExitIcon.png");
        Image iconExitImage = new Image(iconExitPath.toUri().toString());
        iconExit.setImage(iconExitImage);

        nameLabel.setText(" " + SessionClient.getAccount().getId().toString());
    }

    @FXML
    void setLanguage(ActionEvent event) throws IOException {
        if (event.getSource() == btnEnglish) {
            Navigate.setLocale(Locale.forLanguageTag("en"), "MainDashboard");
        } else if (event.getSource() == btnChinese) {
            Navigate.setLocale(Locale.forLanguageTag("zh"), "MainDashboard");
        } else if (event.getSource() == btnMalay) {
            Navigate.setLocale(Locale.forLanguageTag("ms"), "MainDashboard");
        }
    }


    @FXML
    void onMouseNavigate(MouseEvent event) throws IOException {
        if (event.getSource() == transHistoryPane) {
            Navigate.setRoot("TransHistory");
        }
        else if (event.getSource() == depositPane) {
            SessionClient.setNavState("Deposit");
            Navigate.setRoot("DepositWithdraw");
        }
        else if (event.getSource() == withdrawPane) {
            SessionClient.setNavState("Withdraw");
            Navigate.setRoot("DepositWithdraw");
        }
        else if (event.getSource() == exitPane) {
            Navigate.logout();
        }
    }
}
