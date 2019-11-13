package gogreenclient.screens;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gogreenclient.datamodel.ExceptionHandler;
import gogreenclient.datamodel.User;
import gogreenclient.datamodel.UserAccountValidator;
import gogreenclient.datamodel.UserService;
import gogreenclient.screens.window.WindowController;
import gogreenclient.screens.window.Windows;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.net.URISyntaxException;

public class CreateAccountController implements WindowController {

    @FXML
    JFXPasswordField password;
    @FXML
    JFXPasswordField repeatPassword;
    @FXML
    JFXTextField username;
    @FXML
    JFXTextField email;
    @FXML
    JFXDatePicker bday;
    @FXML
    Label incorrect;
    @FXML
    Label uploadPath;
    @FXML
    Label errorLabel;
    @FXML
    Label textHideError;
    @FXML
    AnchorPane pane;

    private File file = null;
    private ScreenConfiguration screens;

    @Autowired
    private UserService userService;

    @Autowired
    private UserAccountValidator validator;

    @Autowired
    private ExceptionHandler exceptionHandler;

    private Windows dialog;


    public CreateAccountController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    public Windows getWindow() {
        return dialog;
    }

    @Override
    public void setWindow(Windows dialog) {
        this.dialog = dialog;
    }

    /**
     * initialize the create account scene.
     */
    public void initialize() {
        pane.setStyle("-fx-background-image: url(static/login_background.png);");
        dialog.setOnCloseRequest(e -> {
            e.consume();
            dialog.close();
            screens.loginDialog().show();
        });
    }

    /**
     * the create button function.
     */
    @FXML
    public void createAccount() {
        try {
            validator.accountValidate(username.getText(), password.getText(),
                repeatPassword.getText(), bday.getValue(), email.getText());
        } catch (IllegalArgumentException e) {
            exceptionHandler.illegalArgumentExceptionHandler(e);
            return;
        }
        ResponseEntity<User> response = null;
        ResponseEntity<String> photoUploadResponse = null;
        try {
            response = userService.addUser(username.getText(), password.getText(),
                bday.getValue(), email.getText());
            photoUploadResponse = uploadPhoto();
        } catch (URISyntaxException e) {
            System.out.println("Wrong URI");
        } finally {
            //TODO when creating account success, popup window shows
            if (response != null && response.getStatusCode() == HttpStatus.OK
                    && photoUploadResponse.getStatusCode() == HttpStatus.OK) {
                dialog.close();
                screens.loginDialog().show();
            } else {
                errorLabel.setText("Failed to create account");
                textHideError.setText("");
            }
        }
    }

    /**
     * function for the other button to go the the login view.
     */
    @FXML
    public void switchToLogin() {
        dialog.close();
        screens.loginDialog().show();
    }

    /**.
     * function for recieving a photo from user system and storing it inside a File variable
     */
    @FXML
    public void savePhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an image");
        fileChooser.getExtensionFilters().addAll(new FileChooser
            .ExtensionFilter("Image files (JPEG, PNG)", "*.jpg", "*.png"));
        file = fileChooser.showOpenDialog(null);
        uploadPath.setText(file.getName());
    }

    /**.
     * sending image by using userService send methods
     * @return the response of the actual sending method
     */
    public ResponseEntity<String> uploadPhoto() {
        if (file != null) {
            String userName = username.getText();
            return userService.uploadPhoto(file, userName);
        } else {
            return new ResponseEntity<String>("No Picture", HttpStatus.OK);
        }
    }
}