package gogreenclient.screens;

import gogreenclient.screens.window.ConfirmDialog;
import gogreenclient.screens.window.FxmlWindow;
import gogreenclient.screens.window.MainWindow;
import gogreenclient.screens.window.SceneController;
import gogreenclient.screens.window.SwitchabScene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

/**
 * Configurations of all the GUI relative elements, including stages, scenes, and controllers. All
 * of these elements will be a Bean and can be used by dependency injection.
 *
 * <p>If you want to add a stage or scene please add it here.
 */
@Configuration
@Lazy
public class ScreenConfiguration {

    private Stage primaryStage;


    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    //-----------------------------------------stages----------------------------------------------

    /**
     * Stage 1.
     * Login window which is a individual stage with one scene.
     *
     * @return a singleton instance of FxmlWindow.
     */
    @Bean
    public FxmlWindow loginDialog() {
        return new FxmlWindow(loginController(), getClass().getResource("/views/Log_in.fxml"),
            primaryStage, StageStyle.DECORATED);
    }

    @Bean
    LoginController loginController() {
        return new LoginController(this);
    }

    /**
     * Stage 2.
     * Create account window which is a individual stage with one scene.
     *
     * @return a singleton instance of FxmlWindow.
     */
    @Bean
    public FxmlWindow createAccountDialog() {
        return new FxmlWindow(createAccountController(), getClass()
            .getResource("/views/Create_account.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    CreateAccountController createAccountController() {
        return new CreateAccountController(this);
    }

    /**
     * A pop up exit window which is a individual stage with one scene.
     *
     * @return a singleton instance of ExitDialog which is a confirmDialog.
     */
    @Bean
    public ConfirmDialog exitDialog() {
        return new ConfirmDialog(exitController(), getClass()
            .getResource("/views/ExitConfirmDialog.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    ExitController exitController() {
        return new ExitController(this);
    }

    /**
     * beans for the popups regarding change details/delete account.
     * @return beans for them.
     */
    @Bean
    public ConfirmDialog changePasswordDialog() {
        return new ConfirmDialog(changeDetailsController(), getClass()
            .getResource("/views/ChangePassword.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    public ConfirmDialog changeEmailDialog() {
        return new ConfirmDialog(changeDetailsController(), getClass()
            .getResource("/views/ChangeEmail.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    public ConfirmDialog changeBdayDialog() {
        return new ConfirmDialog(changeDetailsController(), getClass()
            .getResource("/views/ChangeBirthday.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    public ConfirmDialog deleteAccountDialog() {
        return new ConfirmDialog(changeDetailsController(), getClass()
            .getResource("/views/Delete_account.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    ChangeDetailsController changeDetailsController() {
        return new ChangeDetailsController(this);
    }

    @Bean
    public ConfirmDialog addFriendDialog() {
        return new ConfirmDialog(addFriendPopController(), getClass()
            .getResource("/views/AddFriend_Popup.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    AddFriendPopController addFriendPopController() {
        return new AddFriendPopController(this);
    }

    @Bean
    @Scope("prototype")
    public ConfirmDialog friendDetailsDialog() {
        return new ConfirmDialog(friendDetailsController(), getClass()
            .getResource("/views/FriendDetails.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    @Scope("prototype")
    FriendDetailsController friendDetailsController() {
        return new FriendDetailsController(this);
    }

    @Bean
    @Scope("prototype")
    public ConfirmDialog regularInformDialog() {
        return new ConfirmDialog(regularInformController(), getClass()
            .getResource("/views/RegularInformDialog.fxml"), primaryStage, StageStyle.DECORATED);
    }

    @Bean
    @Scope("prototype")
    InformController regularInformController() {
        return new InformController(this);
    }

    /**
     * Stage 3.
     * The main stage of start/menu phase. Three scenes are associated with this stage, namely
     * achievement scene, friends scene and statistic scene.
     *
     * @return a singleton instance of MainWindow.
     */
    @Bean
    public MainWindow startScreen() {
        return new MainWindow(startController(), primaryStage, StageStyle.DECORATED, false);
    }

    @Bean
    StartController startController() {
        return new StartController(this);
    }

    /**
     * Stage 4.
     * The main stage of adding activity phase. Five scenes are associated with this stage, namely
     * food scene, transport scene, solar panel scene, room scene and plant tree scene.
     *
     * @return a singleton instance of MainWindow.
     */
    @Bean
    AddActivityController activityController() {
        return new AddActivityController(this);
    }

    @Bean
    public MainWindow activityScreen() {
        return new MainWindow(activityController(), primaryStage, StageStyle.DECORATED, true);
    }

    //---------------------------------------------scenes------------------------------------------

    /**
     * The scene for adding food activities in stage 4.
     *
     * @return A singleton instance of SwitchableScene.
     */
    @Bean
    public SwitchabScene foodScene() {
        return new SwitchabScene(foodController(), getClass()
            .getResource("/views/Add_FoodActivity.fxml"), "/static/hover.css");
    }

    @Bean
    SceneController foodController() {
        return new AddFoodController(this);
    }


    /**
     * The scene for adding transport activities in stage 4.
     *
     * @return A singleton instance of SwitchableScene.
     */
    @Bean
    public SwitchabScene transportScene() {
        return new SwitchabScene(transportController(), getClass()
            .getResource("/views/Add_TransportActivity.fxml"), "/static/hover.css");
    }

    @Bean
    SceneController transportController() {
        return new AddTransportController(this);
    }

    /**
     * The scene for adding solar panel in stage 4.
     *
     * @return A singleton instance of SwitchableScene.
     */
    @Bean
    public SwitchabScene solarPanelScene() {
        return new SwitchabScene(solarPanelController(), getClass()
            .getResource("/views/Add_SolarPanel.fxml"), "/static/hover.css");
    }

    @Bean
    SceneController solarPanelController() {
        return new AddSolarPanelController(this);
    }

    /**
     * The scene for turn down room temperature in stage 4.
     *
     * @return A singleton instance of SwitchableScene.
     */
    @Bean
    public SwitchabScene roomScene() {
        return new SwitchabScene(roomController(), getClass()
            .getResource("/views/Add_RoomHeating.fxml"), "/static/hover.css");
    }

    @Bean
    SceneController roomController() {
        return new AddRoomController(this);
    }

    /**
     * The scene for showing statistics in stage 3.
     *
     * @return A singleton instance of SwitchableScene.
     */
    @Bean
    public SwitchabScene statisticScene() {
        return new SwitchabScene(statisticController(), getClass()
            .getResource("/views/StartView_Picture.fxml"));
    }

    @Bean
    StatisticController statisticController() {
        return new StatisticController(this);
    }

    /**
     * The scene for showing achievements in stage 3.
     *
     * @return A singleton instance of SwitchableScene.
     */
    @Bean
    public SwitchabScene achievementsScene() {
        return new SwitchabScene(achieveController(), getClass()
            .getResource("/views/Achievements.fxml"));
    }

    @Bean
    AchievementsController achieveController() {
        return new AchievementsController(this);
    }

    @Bean
    public SwitchabScene userScene() {
        return new SwitchabScene(userScreenController(), getClass()
            .getResource("/views/UserScreen.fxml"));
    }

    @Bean
    UserScreenController userScreenController() {
        return new UserScreenController(this);
    }

    @Bean
    public SwitchabScene friendsScene() {
        return new SwitchabScene(showFriendsController(), getClass()
            .getResource("/views/ShowFriends.fxml"));
    }

    @Bean
    ShowFriendsController showFriendsController() {
        return new ShowFriendsController(this);
    }


    @Bean
    public SwitchabScene plantTreeScene() {
        return new SwitchabScene(plantTreeController(), getClass()
            .getResource("/views/Plant_Tree.fxml"), "/static/hover.css");
    }

    @Bean
    SceneController plantTreeController() {
        return new PlantTreeController(this);
    }

}
