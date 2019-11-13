package gogreenclient.screens;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import gogreenclient.datamodel.UserCareerService;
import gogreenclient.screens.window.SceneController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class AchievementsController implements SceneController {

    @Autowired
    private ScreenConfiguration screens;
    @Autowired
    private UserCareerService service;

    private List<gogreenclient.datamodel.Achievements> achievementsList;

    @FXML
    private JFXTreeTableView<Achievements> treeView;
    @FXML
    private Label count;


    public AchievementsController(ScreenConfiguration screens) {
        this.screens = screens;
    }


    /**
     * initialize the table view.
     */
    public void initialize() {
        achievementsList = service.getAchievements();
        JFXTreeTableColumn<Achievements, Achievements.AchievementImage> achPhoto =
                new JFXTreeTableColumn<>("Badge");
        achPhoto.setPrefWidth(90);
        achPhoto.setStyle("-fx-alignment: CENTER;");
        count.setText(String.valueOf(achievementsList.size()));
        achPhoto.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Achievements,
                Achievements.AchievementImage>,
                ObservableValue<Achievements.AchievementImage>>() {
            @Override
            public ObservableValue<Achievements.AchievementImage>
                call(TreeTableColumn.CellDataFeatures<Achievements,
                    Achievements.AchievementImage> param) {
                return param.getValue().getValue().img;
            }
        });
        achPhoto.setCellFactory(param -> new JFXTreeTableCell<Achievements,
                Achievements.AchievementImage>() {
            ImageView imageView = new ImageView();
            @Override
            protected void updateItem(Achievements.AchievementImage img, boolean empty) {
                super.updateItem(img, empty);
                try {
                    if (img == null) {
                        setGraphic(null);
                        return;
                    }
                    boolean check = new ClassPathResource("static/"
                            + img.getPhotoPath() + ".png").exists();
                    File file;
                    if (!check) {
                        file = new ClassPathResource("static/green-hibiscus-md.png").getFile();
                    } else {
                        file = new ClassPathResource("static/" + img.getPhotoPath()
                                + ".png").getFile();
                    }
                        BufferedImage imgBuffer = ImageIO.read(file);
                        Image imgz = SwingFXUtils.toFXImage(imgBuffer, null);
                        imageView.setImage(imgz);
                        imageView.setFitWidth(70);
                        imageView.setFitHeight(70);
                    TreeTableRow<Achievements> currentRow = getTreeTableRow();
                    HBox hbox = new HBox();
                    hbox.getChildren().addAll(imageView);
                    setGraphic(hbox);
                } catch (IOException e) {
                    e.printStackTrace();
                    setGraphic(null);
                    }
            }
        });

        JFXTreeTableColumn<Achievements, String> achName =
                new JFXTreeTableColumn<>("Achievements");
        achName.setPrefWidth(150);
        achName.setStyle("-fx-alignment: CENTER;");
        achName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Achievements,
                String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Achievements,
                    String> param) {
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn<Achievements, String> achieved = new JFXTreeTableColumn<>("Achieved");
        achieved.setPrefWidth(70);
        achieved.setStyle("-fx-alignment: CENTER;");
        achieved.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Achievements,
                String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Achievements,
                    String> param) {
                return param.getValue().getValue().achieved;
            }
        });

        JFXTreeTableColumn<Achievements,
                String> description = new JFXTreeTableColumn<>("Description");
        description.setPrefWidth(700);
        description.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Achievements,
                String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Achievements,
                    String> param) {
                return param.getValue().getValue().description;
            }
        });
        ObservableList<Achievements> achievements = FXCollections.observableArrayList();
        Achievements treeHugger = new Achievements("Tree Hugger",
                "Saved more than 100 kg carbon dioxide");
        Achievements anarchoPrimitivist = new Achievements("Anarcho Primitivist",
                "Saved more than 1 ton of carbon dioxide");
        Achievements celebi = new Achievements("Celebi",
                "Saved more than 5 tons of carbon dioxide");
        Achievements stopCheating = new Achievements("Stop Cheating",
                "Saved 20 tons of carbon dioxide. Or, "
                        + "more likely, you're just cheating.");
        Achievements sunAbsorber = new Achievements("Sun Absorber",
                "Your solar panels have produced more than 10 kw");
        Achievements powerPlant = new Achievements("Power Plant",
                "Your solar panels have produced more 100 kw");
        Achievements fiveBillionYears = new Achievements("Five Billion Years",
                "Your solar panels have produced more than 1095 kw."
                        +
                        " You know the sun will die eventually, right?");
        Achievements thankUvMuch = new Achievements("Thank UV Much",
                "Your solar panels have produced more than 5000 kw."
                        +
                        " Absorbed light is lost forever. Congratulations, darkness worshipper");
        Achievements vegetarian = new Achievements("Vegetarian",
                "Your food choices have saved more 100 kg of carbon dioxide");
        Achievements vegan = new Achievements("Vegan",
                "Your food choices have saved more 500 kg of carbon dioxide");
        Achievements photosynthesizer = new Achievements("Photosynthesizer",
                "Your food choices have saved more 1 ton of carbon dioxide");
        Achievements pleaseEat = new Achievements("Please Eat",
                "Your food choices have saved more 5 tons of carbon dioxide."
                        + " Please make sure you're eating properly");
        Achievements dutch = new Achievements("Dutch",
                "100 kg of carbon dioxide saved from transport choices,"
                        + " isn't Holland great?");
        Achievements niceLegs = new Achievements("Nice Legs",
                "500 kg of carbon dioxide saved from transport choices, good work out");
        Achievements teleporter = new Achievements("Teleporter",
                "1 ton of carbon dioxide saved from transport choices");
        Achievements neverSkipLegDay = new Achievements("Never Skip Leg Day",
                "5 tons of carbon dioxide saved from transport choices."
                        + " Eliud Kipchoge wants to know your location");
        Achievements fatWallet = new Achievements("Fat Wallet",
                "Saved 100 euros, get your wallet some hydroxycut");
        Achievements retirementFund = new Achievements("Retirement Fund",
                "Saved 500 euros total. Open an account mate");
        Achievements justBuySomething = new Achievements("Just Buy Something",
                "2000 euros saved, time for a trip to Amsterdam");
        Achievements nokwg29 = new Achievements("nokwg29",
                "10000 euros saved. Tinyurl");
        achievements.addAll(treeHugger, anarchoPrimitivist, celebi, stopCheating,
                sunAbsorber, powerPlant, fiveBillionYears, thankUvMuch, vegetarian,
                vegan, photosynthesizer, pleaseEat, dutch, niceLegs, teleporter,
                neverSkipLegDay, fatWallet, retirementFund, justBuySomething, nokwg29);

        final TreeItem<Achievements> root = new RecursiveTreeItem<Achievements>(achievements,
                RecursiveTreeObject::getChildren);
        treeView.getColumns().setAll(achPhoto, achName, achieved, description);
        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }

    @FXML
    public void addActivity() {
        screens.activityScreen().show();
    }

    @FXML
    public void switchStatistics() {
        screens.startScreen().getScene().setRoot(screens.statisticScene().getRoot());
    }

    @FXML
    public void showFriends() {
        screens.startScreen().getScene().setRoot(screens.friendsScene().getRoot());
    }

    @FXML
    public void closeProgram() {
        screens.exitDialog().showAndWait();
    }


    class Achievements extends RecursiveTreeObject<Achievements> {

        ObjectProperty<AchievementImage> img;
        StringProperty name;
        StringProperty achieved;
        StringProperty description;

        public Achievements(String name, String description) {
            this.name = new SimpleStringProperty(name);
            String achieved = "No";
            for (gogreenclient.datamodel.Achievements achiev : achievementsList) {
                if (achiev.getAchievement().equals(name)) {
                    achieved = "Yes";
                }
            }
            String photoName;
            if (achieved.equals("No")) {
                photoName = "notYet";
            } else {
                photoName = name.replaceAll("\\s+", "");
            }
            this.achieved = new SimpleStringProperty(achieved);
            this.description = new SimpleStringProperty(description);
            this.img = new SimpleObjectProperty<>(new AchievementImage(photoName));
        }

        public ObjectProperty<AchievementImage> imgProperty() {
            return img;
        }

        public class AchievementImage {

            private String imagePath;

            public AchievementImage(String photoPathz) {
                imagePath = photoPathz;
            }

            public String getPhotoPath() {
                return imagePath;
            }
        }
    }
}