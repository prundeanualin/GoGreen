package gogreenclient.screens;

import gogreenclient.datamodel.UserCareerService;
import gogreenclient.screens.window.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.springframework.beans.factory.annotation.Autowired;


public class UserScreenController implements SceneController {

    @FXML
    private Label setEmail;

    @FXML
    private Label setBirthdate;

    @FXML
    private Label setDateCreated;

    @FXML
    private Label setUsername;

    @FXML
    private Circle myCircle;

    @Autowired
    private UserCareerService service;

    private ScreenConfiguration screens;

    public UserScreenController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    /**
     * initializing the circle picture.
     */
    public void initialize() {
        myCircle.setStroke(Color.SEAGREEN);
        Image image = service.showPhoto();
        myCircle.setFill(new ImagePattern(image));
        myCircle.setEffect(new DropShadow(+25d,0d,+2d,Color.DARKSEAGREEN));
    }


    public void addActivity() {
        screens.activityScreen().show();
    }

    public void switchAchievements() {
        screens.startScreen().getScene().setRoot(screens.achievementsScene().getRoot());
    }

    public void showFriends() {
        screens.startScreen().getScene().setRoot(screens.friendsScene().getRoot());
    }

    public void switchStatistics() {
        screens.startScreen().getScene().setRoot(screens.statisticScene().getRoot());
    }

    @FXML
    public void closeProgram() {
        screens.exitDialog().showAndWait();
    }

    @FXML
    public void changePassword() {
        screens.changePasswordDialog().show();
    }

    @FXML
    public void changeEmail() {
        screens.changeEmailDialog().show();
    }

    @FXML
    public void changeBirthdate() {
        screens.changeBdayDialog().show();
    }

    @FXML
    public void deleteAccount() {
        screens.deleteAccountDialog().show();
    }

}
