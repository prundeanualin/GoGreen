package gogreenclient.screens;

import gogreenclient.datamodel.Achievements;
import gogreenclient.datamodel.InsertHistoryCo2;
import gogreenclient.datamodel.Records;
import gogreenclient.datamodel.UserCareerService;
import gogreenclient.screens.window.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class StatisticController implements SceneController {


    private ScreenConfiguration screens;

    @Autowired
    private UserCareerService userCareerService;

    private Records records;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label userName;

    @FXML
    private Label totalSaved;

    @FXML
    private Label savedMoney;

    @FXML
    private Label totalAchievements;

    @FXML
    private Label recentActivityOne;

    @FXML
    private Label recentActivityTwo;

    @FXML
    private Label firstActivityDate;

    @FXML
    private Label firstActivityAmount;

    @FXML
    private Label secondActivityDate;

    @FXML
    private Label secondActivityAmount;

    @FXML
    private Label totalActivities;

    @FXML
    private Label totalDays;

    @FXML
    private Circle circlePic;

    private List<Achievements> achievementsList;

    private List<InsertHistoryCo2> insertHistoryList;


    public StatisticController(ScreenConfiguration screens) {
        this.screens = screens;
    }


    public StatisticController() {
    }

    /**
     * initialize pieChart values.
     */
    public void initialize() {
        try {
            records = userCareerService.getCareer();
            achievementsList = userCareerService.getAchievements();
            insertHistoryList = userCareerService.getRecentTwoInsertHistory();
            statisticInitialize();
            userNameInitialize();
            pieChyartInitialize();
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        /*
        if (!getLastAchievements().equals("Blue")) {
            achievement.setText(getLastAchievements());
        } else {
            achievement.setText("No achievement earned yet.Go and earn some!");
        }*/
        totalAchievements.setText(getAchievementsAmount());
        Image image = userCareerService.showPhoto();
        circlePic.setStroke(Color.SEAGREEN);
        circlePic.setFill(new ImagePattern(image));
        circlePic.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        recentActivityInit();
        totalActivitiesInit();
        totalActiveDays();
        savedMoneyInit();
    }

    @FXML
    public void addActivity() {
        screens.activityScreen().show();
    }

    @FXML
    public void switchAchievements() {
        screens.startScreen().getScene().setRoot(screens.achievementsScene().getRoot());
    }

    @FXML
    public void showFriends() {
        screens.startScreen().getScene().setRoot(screens.friendsScene().getRoot());
    }

    @FXML
    public void switchUserScene() {
        screens.startScreen().getScene().setRoot(screens.userScene().getRoot());
    }

    private void statisticInitialize() {
        totalSaved.setText(String.valueOf(Math.round(records.getSavedCo2Total())));
    }

    private void userNameInitialize() {
        String username = userCareerService.getUsername();
        userName.setText(username);
    }

    private void pieChyartInitialize() {
        int food = Math.round(records.getSavedCo2Food());
        int transport = Math.round(records.getSavedCo2Transport());
        int solarPaner = Math.round(records.getSavedCo2Solarpanels());
        int temperature = Math.round(records.getSavedCo2Energy());
        int tree = Math.round(records.getSavedCo2tree());
        if (food == 0 && transport == 0 && solarPaner == 0 && temperature == 0 && tree == 0) {
            food = transport = solarPaner = temperature = tree = 10;
        }
        ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                new PieChart.Data("Food", food),
                new PieChart.Data("Transport", transport),
                new PieChart.Data("Solar panels", solarPaner),
                new PieChart.Data("Lower temperature", temperature),
                new PieChart.Data("Plant a tree", tree)
            );
        pieChart.setData(pieChartData);
    }

    private String getLastAchievements() {
        String result = null;
        if (achievementsList.isEmpty()) {
            result = "Blue";
        } else {
            result = achievementsList.get(0).getAchievement();
        }
        return result;
    }

    private String getAchievementsAmount() {
        int amount = 0;
        if (!achievementsList.isEmpty()) {
            amount = achievementsList.size();
        }
        return String.valueOf(amount);
    }

    private void recentActivityInit() {
        if (insertHistoryList.isEmpty()) {
            recentActivityOne.setText("");
            recentActivityTwo.setText("");
            firstActivityDate.setText("");
            firstActivityAmount.setText("");
            secondActivityDate.setText("");
            secondActivityAmount.setText("");
        }

        if (insertHistoryList.size() >= 1) {
            recentActivityOne.setText(insertHistoryList.get(0).activityName());
            firstActivityDate.setText(insertHistoryList.get(0).getInsertDate()
                .toLocalDate().toString());
            firstActivityAmount.setText(String
                .valueOf(Math.round(insertHistoryList.get(0).getCo2Saved())) + "KG saved");
        }
        if (insertHistoryList.size() == 2) {
            recentActivityTwo.setText(insertHistoryList.get(1).activityName());
            secondActivityDate.setText(insertHistoryList.get(1).getInsertDate()
                .toLocalDate().toString());
            secondActivityAmount.setText((String
                .valueOf(Math.round(insertHistoryList.get(1).getCo2Saved()))) + "KG saved");
        }

    }

    private void totalActivitiesInit() {
        String activites = userCareerService.getActivityAmount();
        totalActivities.setText((activites == null) ? "0" : activites);
    }

    private void totalActiveDays() {
        String days = userCareerService.getActiveDays();
        totalDays.setText((days.equals("")) ? "0" : days);
    }

    private void savedMoneyInit() {
        String moneySaved = String.valueOf(Math.round(records.getSavedPriceTotal()));
        savedMoney.setText(moneySaved + "€");
    }

    @FXML
    public void closeProgram() {
        screens.exitDialog().showAndWait();
    }
}
