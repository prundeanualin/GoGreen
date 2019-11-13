package gogreenclient.screens;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import gogreenclient.datamodel.ExceptionHandler;
import gogreenclient.datamodel.InsertHistory;
import gogreenclient.datamodel.UserInputValidator;
import gogreenclient.screens.window.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;


public class AddTransportController implements SceneController {

    private static final String URL = "https://localhost:8443/api/insertHistory";

    ObservableList<String> transportList = FXCollections
        .observableArrayList("walk", "bike", "bus", "car", "motorbike", "plane");

    @FXML
    private JFXComboBox<String> takenTransportBox;
    @FXML
    private JFXComboBox<String> insteadOfTransportBox;
    @FXML
    private JFXTextField distance;
    @FXML
    private Label fillAll;

    @Autowired
    private UserInputValidator validator;

    private ScreenConfiguration screens;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private InsertHistory insert;

    @Autowired
    private RestTemplate restTemplate;

    public AddTransportController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    /**
     * initializes the dropdown menus.
     *
     * @throws Exception when incorrect text input.
     */
    public void initialize() {
        validator.validateFraction(distance);
        takenTransportBox.setItems(transportList);
        insteadOfTransportBox.setItems(transportList);
        fillAll.setVisible(false);
    }


    /**
     * method for submit button, which will send the data to the server.
     */
    @FXML
    public void submit() {
        createInsertObjectTransport();
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
    public void switchSolar() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.solarPanelScene().getRoot());
    }

    @FXML
    public void switchRoom() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.roomScene().getRoot());
    }

    @FXML
    public void switchPlantTree() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.plantTreeScene().getRoot());
    }

    private void isAllTextFilled() throws IllegalArgumentException {
        validator.isNull(takenTransportBox);
        validator.isNull(insteadOfTransportBox);
        validator.isNull(distance);
    }

    private void clearBoxes() {
        takenTransportBox.setValue(null);
        insteadOfTransportBox.setValue(null);
        distance.clear();
    }

    private void createInsertObjectTransport() {
        try {
            isAllTextFilled();
        } catch (IllegalArgumentException e) {
            exceptionHandler.illegalArgumentExceptionHandler(e);
        }
        String usualTransport = takenTransportBox.getValue();
        String alterTransport = insteadOfTransportBox.getValue();
        float difference = Float.parseFloat(distance.getText());
        insert.setActivityName(usualTransport);
        insert.setAlternateActivity(alterTransport);
        insert.setTransportDistanceKm(difference);
        insert.setInsertDate(LocalDateTime.now());
    }


}
