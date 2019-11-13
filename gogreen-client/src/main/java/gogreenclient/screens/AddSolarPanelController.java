package gogreenclient.screens;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import gogreenclient.datamodel.AddSolarpanels;
import gogreenclient.datamodel.SolarPanelService;
import gogreenclient.datamodel.UserInputValidator;
import gogreenclient.screens.window.SceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;



public class AddSolarPanelController implements SceneController {

    @FXML
    private JFXTextField sizeOfSolarPanel;
    @FXML
    private Label initialSize;
    @FXML
    private Label fillAll;
    @FXML
    private JFXDatePicker date;

    private ScreenConfiguration screens;

    private float solarSzie;

    @Autowired
    private UserInputValidator validator;
    @Autowired
    private AddSolarpanels addSolarpanels;

    @Autowired
    private SolarPanelService solarPanelService;


    public AddSolarPanelController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    /**
     * initialize the solar panel page.
     */
    public void initialize() {
        validator.validateFraction(sizeOfSolarPanel);
        solarSzie = solarPanelService.getSolarPanelSize();
        initialSize.setText(String.valueOf(Math.round(solarSzie)));
        fillAll.setVisible(false);
    }

    private void isAllFieldFilled() throws IllegalArgumentException {
        validator.isNull(sizeOfSolarPanel);
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
    public void switchRoom() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.roomScene().getRoot());
    }

    @FXML
    public void switchPlantTree() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.plantTreeScene().getRoot());
    }

    /**
     * .
     * Connecting the button to the actual action of sending the solarpanels info to the server
     */
    @FXML
    public void submitCO2Saved() {
        if (date.getValue() != null && sizeOfSolarPanel.getText() != null) {
            addSolarpanels.setDate(date.getValue());
            addSolarpanels.setArea(Float.parseFloat(sizeOfSolarPanel.getText()));
            addSolarpanels.setProducedKwh(0.0F);
            ResponseEntity<String> response = solarPanelService.incrementSize(addSolarpanels);
            if (response != null) {
                screens.statisticController().initialize();
                clearBox();
                initialize();
            }
        } else {
            fillAll.setVisible(true);
        }
    }

    private void clearBox() {
        sizeOfSolarPanel.setText("");
        date.setValue(null);
    }
}
