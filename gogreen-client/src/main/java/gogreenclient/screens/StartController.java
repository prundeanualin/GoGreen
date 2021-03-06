package gogreenclient.screens;

import gogreenclient.screens.window.WindowController;
import gogreenclient.screens.window.Windows;
import javafx.scene.Scene;

public class StartController implements WindowController {

    private ScreenConfiguration screens;
    private Windows window;

    public StartController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    public Windows getWindow() {
        return window;
    }

    @Override
    public void setWindow(Windows window) {
        this.window = window;
        Scene activityScene = new Scene(screens.statisticScene().getRoot());
        window.setScene(activityScene);
    }
}
