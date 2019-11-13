package gogreenclient.screens;

import com.jfoenix.controls.JFXCheckBox;
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


public class AddFoodController implements SceneController {


    private static final String URL = "https://localhost:8443/api/insertHistory";
    //list for the tree view
    ObservableList<String> mealList = FXCollections
        .observableArrayList("beans", "vegetables", "cheese",
            "chocolate", "fruit", "lentils", "milk", "nuts", "pannekoeken",
            "poffertjes", "potatoes", "rice", "stroopwafel", "tofu", "beef",
            "bitterballen", "chicken", "eggs", "kroket", "lamb",
            "pork", "tuna", "turkey");
    @FXML
    private JFXTextField costTaken;
    @FXML
    private JFXTextField costInstead;
    @FXML
    private JFXComboBox<String> takenMealBox;
    @FXML
    private JFXComboBox<String> insteadOfMealBox;
    @FXML
    private Label fillAll;
    @FXML
    private JFXCheckBox localProduct;
    private ScreenConfiguration screens;
    @Autowired
    private UserInputValidator validator;
    @Autowired
    private ExceptionHandler exceptionHandler;
    @Autowired
    private InsertHistory insert;
    @Autowired
    private RestTemplate restTemplate;


    public AddFoodController(ScreenConfiguration screens) {
        this.screens = screens;
    }


    /**
     * sets the combo box elements.
     */
    public void initialize() {
        validator.validateFraction(costTaken);
        validator.validateFraction(costInstead);
        takenMealBox.setItems(mealList);
        insteadOfMealBox.setItems(mealList);
        fillAll.setVisible(false);
    }


    /**
     * method for submit button, which will send the data to the server.
     */
    @FXML
    public void submit() {
        createInsertObjectFood();
        ResponseEntity<String> response = restTemplate.postForEntity(URL, insert, String.class);
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            //TODO use logger
            System.out.println(response.getBody());
            clearBoxes();
            screens.statisticController().initialize();
        }
    }

    /**
     * goes to transportation activities.
     * need a transportation scene.
     */
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
    public void switchRoom() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.roomScene().getRoot());
    }

    @FXML
    public void switchPlantTree() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.plantTreeScene().getRoot());
    }

    private void isAllFieldFilled() throws IllegalArgumentException {
        validator.isNull(takenMealBox);
        validator.isNull(insteadOfMealBox);
        validator.isNull(costTaken);
        validator.isNull(costInstead);
    }

    private void createInsertObjectFood() {
        try {
            isAllFieldFilled();
        } catch (IllegalArgumentException e) {
            exceptionHandler.illegalArgumentExceptionHandler(e);
        }
        String usualFood = takenMealBox.getValue();
        String alterFood = insteadOfMealBox.getValue();
        boolean isLocalProduct = localProduct.isSelected();
        float usualCost = Float.parseFloat(costTaken.getText());
        float alterCost = Float.parseFloat(costInstead.getText());
        insert.setActivityName(usualFood);
        insert.setAlternateActivity(alterFood);
        insert.setActivityIsLocalproduce(isLocalProduct);
        insert.setActivityPrice(usualCost);
        insert.setAlternateActivityPrice(alterCost);
        insert.setInsertDate(LocalDateTime.now());
    }

    private void clearBoxes() {
        takenMealBox.setValue(null);
        insteadOfMealBox.setValue(null);
        costTaken.clear();
        costInstead.clear();
        localProduct.setSelected(false);
    }

}
