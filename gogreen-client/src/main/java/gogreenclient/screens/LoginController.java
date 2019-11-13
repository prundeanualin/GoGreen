package gogreenclient.screens;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gogreenclient.config.AppConfig;
import gogreenclient.datamodel.ExceptionHandler;
import gogreenclient.datamodel.InsertHistory;
import gogreenclient.datamodel.UserAccountValidator;
import gogreenclient.screens.window.WindowController;
import gogreenclient.screens.window.Windows;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * First Screen.
 *
 * <p>Login controller, the entry point of client side application. User can login with their
 * userName and password, if the account exists then it will be directed to the main menu.Otherwise,
 * there will be a sign which will tell user the userName & password combination is not right.
 *
 * <p>User can create an account by clicking the link.
 *
 * <p>TODO remember me implementation.
 */
public class LoginController implements WindowController {

    @FXML
    public JFXTextField username;
    @FXML
    public JFXPasswordField password;
    @FXML
    public Label combinationLabel;
    @FXML
    Hyperlink create;
    @FXML
    AnchorPane loadingPane;
    @FXML
    AnchorPane pane;

    private Windows dialog;


    private ScreenConfiguration screens;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private UserAccountValidator validator;

    @Autowired
    private ExceptionHandler exceptionHandler;

    public LoginController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    /**
     * initialize the login screen.
     */
    public void initialize() {
        pane.setStyle("-fx-background-image: url(static/login_background.png);");
        dialog.setOnCloseRequest(e -> {
            e.consume();
            screens.exitDialog().showAndWait();
        });
    }

    /**
     * This method will connect this controller to a stage in the FxmlWindow, and
     * set the scene of this stage by load a scene from FXML file.
     *
     * @param dialog a dialog stage.
     */
    @Override
    public void setWindow(Windows dialog) {
        this.dialog = dialog;
    }


    /**
     * The method which is connected to the "Login" button on login screen. Click on the login
     * button this method will collect username and password entered by user, and pass it to
     * the singleton bean appConfig.
     *
     * <p>Because this screen is the entry point of the application,and all of the Beans in
     * appConfig is Lazy, so when RestTemplateBuilder Bean is instantiated,username and password
     * fields are already set to user's input. Those Beans are also singleton, so it will be shared
     * every time it's called. Thus, the username and password is contained in every instance of
     * restTemplate. So when you want to send a Http request, please use @Autowired resTemplate.
     * Otherwise, your request will fail.
     *
     * @throws Exception exception threw by restTemplate.
     */
    @FXML
    public void login() throws Exception {
        combinationLabel.setVisible(false);
        try {
            validator.loginValidate(username.getText(), password.getText());
        } catch (IllegalArgumentException e) {
            exceptionHandler.illegalArgumentExceptionHandler(e);
            return;
        }
        appConfig.setUsername(username.getText());
        appConfig.setPassword(password.getText());
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = appConfig.loginRestTemplate();
        try {
            response = restTemplate
                .getForEntity(new URI("https://localhost:8443/api/login"),
                    String.class);
        } catch (URISyntaxException e) {
            //TODO exception handler
            System.out.println("wrong URI");
        } catch (HttpClientErrorException e) {
            if (e instanceof HttpClientErrorException.Unauthorized) {
                combinationLabel.setVisible(true);
                return;
            } else {
                System.out.println(e.getMessage());
                return;
            }
        }
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            loadingPane.setVisible(true);
            appConfig.setRestTemplate(restTemplate);
            restTemplate.postForEntity("https://localhost:8443/api/insertHistory",
                new InsertHistory(username.getText()), String.class);
            screens.startScreen().show();
            dialog.close();

        } else {
            return;
        }
    }


    /**
     * This method is connected to "create account" Hyper link, which is a individual stage
     * that will allow user to create her/his own account.
     */
    @FXML
    public void switchToCreate() {
        combinationLabel.setVisible(false);
        screens.createAccountDialog().show();
    }

}
