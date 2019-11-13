package gogreenclient.screens;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import gogreenclient.datamodel.ExceptionHandler;
import gogreenclient.datamodel.Tree;
import gogreenclient.datamodel.TreeService;
import gogreenclient.datamodel.UserInputValidator;
import gogreenclient.screens.window.SceneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PlantTreeController implements SceneController {

    private ObservableList<String> treeList = FXCollections
        .observableArrayList("ash", "basswood", "bur_oak", "buttonwood",
            "cherry", "chestnut", "elm", "ginkgo", "hackberry", "hemlock",
            "locust", "maple", "metasequoia", "pine", "red_maple",
            "silver_maple", "sugar_maple", "tulip", "white_ash",
            "white_oak", "yellow_birch", "zelvoka");
    @FXML
    private JFXComboBox<String> plantedTree;

    @FXML
    private JFXDatePicker plantDate;

    @FXML
    private Label fillAll;

    @Autowired
    private UserInputValidator validator;

    @Autowired
    private ExceptionHandler exceptionHandler;

    @Autowired
    private TreeService treeService;

    @Autowired
    private Tree tree;
    private ScreenConfiguration screens;


    public PlantTreeController(ScreenConfiguration screens) {
        this.screens = screens;
    }


    public void initialize() {
        plantedTree.setItems(treeList);
        fillAll.setVisible(false);
    }


    /**
     * method for submit button, which will send a request to server. If the request is a success,
     * then the textFields will be cleaned, and the statistic page will be refreshed.
     */
    @FXML
    public void submit() {
        createInsertObjectTree();
        ResponseEntity<String> response = treeService.plantingTree(tree);
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
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
    public void switchRoom() {
        screens.activityController()
            .getWindow().getScene().setRoot(screens.roomScene().getRoot());
    }


    private void createInsertObjectTree() {
        try {
            validator.isNull(plantedTree);
            validator.isNull(plantDate);
            validator.isDateInPast(plantDate);
        } catch (IllegalArgumentException e) {
            exceptionHandler.illegalArgumentExceptionHandler(e);
            return;
        }
        String chosenTree = plantedTree.getValue();
        tree.setTree(chosenTree);
        tree.setAddingDate(plantDate.getValue().atStartOfDay());
    }

    private void clearBoxes() {
        plantedTree.setValue(null);
        plantDate.setValue(null);
    }

}
