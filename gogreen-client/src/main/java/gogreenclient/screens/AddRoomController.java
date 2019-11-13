package gogreenclient.screens;

import com.jfoenix.controls.JFXTextField;
import gogreenclient.datamodel.ExceptionHandler;
import gogreenclient.datamodel.InsertHistory;
import gogreenclient.datamodel.UserInputValidator;
import gogreenclient.screens.window.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


public class AddRoomController implements SceneController {

    private static final String URL = "https://localhost:8443/api/insertHistory";

    private ScreenConfiguration screens;

    @FXML
    private JFXTextField minutes;
    @FXML
    private JFXTextField tempDiff;
    @FXML
    private JFXTextField roomArea;
    @FXML
    private Label fillAll;

    @Autowired
    private UserInputValidator validator;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private InsertHistory insert;

    @Autowired
    private RestTemplate restTemplate;


    public AddRoomController(ScreenConfiguration screens) {
        this.screens = screens;
    }


    /**
     * initialing the validations.
     *
     * @throws Exception exception if not correct.
     */
    public void initialize() {
        validator.validateFraction(tempDiff);
        validator.validateFraction(roomArea);
        validator.validateInteger(minutes);
        fillAll.setVisible(false);
    }

    /**
     * method for submit button, which will send the data to the server.
     */
    @FXML
    public void submit() {
        createInsertObjectRoom();
        ResponseEntity<String> response = restTemplate.postForEntity(URL, insert, String.class);
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            //TODO use logger
            System.out.println(response.getBody());
            clearBoxes();
            screens.statisticController().initialize();
        }
    }

    @FXML
    public void switchFood() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.foodScene().getRoot());
    }

    @FXML
    public void switchTransport() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.transportScene().getRoot());
    }

    @FXML
    public void switchSolar() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.solarPanelScene().getRoot());
    }

    @FXML
    public void switchPlantTree() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.plantTreeScene().getRoot());
    }

    private void clearBoxes() {
        minutes.clear();
        tempDiff.clear();
        roomArea.clear();
    }

    private void isAllTextFilled() throws IllegalArgumentException {
        validator.isNull(minutes);
        validator.isNull(tempDiff);
        validator.isNull(roomArea);
    }

    private void createInsertObjectRoom() {
        try {
            isAllTextFilled();
        } catch (IllegalArgumentException e) {
            exceptionHandler.illegalArgumentExceptionHandler(e);
        }
        float timeAmount = Float.parseFloat(minutes.getText());
        float difference = Float.parseFloat(tempDiff.getText());
        float area = Float.parseFloat(roomArea.getText());
        insert.setActivityName("lowering_temp");
        insert.setEnergyActivityTempAreaM2(area);
        insert.setEnergyActivityTempDegreesDecreased(difference);
        insert.setEnergyActivityDurationMinutes(timeAmount);
    }

    /**
     * Limiting the input of a text field to be only numbers.
     */

}
