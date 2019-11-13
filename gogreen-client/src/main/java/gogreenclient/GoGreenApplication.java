package gogreenclient;

import gogreenclient.config.AppConfig;
import gogreenclient.screens.ScreenConfiguration;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Client side of #GoGreen project. The application is able to send HTTP
 * requests throw HTTPS protocol in JSON format or send query with RequestParam
 * to a RESTful API, which will response with a JSON format object.
 */

public class GoGreenApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ScreenConfiguration screens = context.getBean(ScreenConfiguration.class);
        stage.setResizable(false);
        screens.setPrimaryStage(stage);
        screens.loginDialog().show();
    }
}
