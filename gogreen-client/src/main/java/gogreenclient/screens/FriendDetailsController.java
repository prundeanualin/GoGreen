package gogreenclient.screens;

import gogreenclient.datamodel.Records;
import gogreenclient.datamodel.UserCareerService;
import gogreenclient.screens.window.ConfirmDialogController;
import gogreenclient.screens.window.Windows;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;

public class FriendDetailsController implements ConfirmDialogController {

    private Windows dialog;

    private ScreenConfiguration screens;

    @Autowired
    private UserCareerService userCareerService;

    private Records records;

    @FXML
    private PieChart pieChart;

    @FXML
    private Label setFriendUser;

    @FXML
    private Label setTotalAct;

    @FXML
    private Label setTotalDays;

    @FXML
    private Label setTotalAchievements;

    public FriendDetailsController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @Override
    public void setWindow(Windows dialog) {
        this.dialog = dialog;
    }

    /**
     * initializes pieChart values.
     * the text fields are commented out.
     */
    public void initialize() {
        String friendName = screens.showFriendsController().getFriendName();
        setFriendUser.setText(friendName);
        userCareerService.setUsername(friendName);
        records = userCareerService.getCareer();
        pieCharInit();
        String activityAmount = userCareerService.getActivityAmount();
        setTotalAct.setText(activityAmount);
        String activityDays = userCareerService.getActiveDays();
        setTotalDays.setText(activityDays);
        setTotalAchievements.setText(String.valueOf(userCareerService.getAchievements().size()));
    }

    private void pieCharInit() {
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


    @Override
    public void yes() {
        dialog.close();
    }

    @Override
    public void no() {
        dialog.close();
    }
}
