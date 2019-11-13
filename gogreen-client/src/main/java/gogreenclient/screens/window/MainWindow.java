package gogreenclient.screens.window;

import gogreenclient.screens.ScreenConfiguration;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A stage contains a scene which is customizable. You can switching between different screens
 * by switching the root node of that scene.
 */
public class MainWindow extends gogreenclient.screens.window.Windows {

    @Autowired
    private ScreenConfiguration screens;

    /**
     * The main window of the application, which is able to switch between different scenes.
     *
     * @param controller the stage controller.
     * @param owner      the stage owner.
     * @param style      the stage style.
     * @param popup      whether this stage is a popup window, if so the close button will only
     *                   close this stage instead of the whole application like what's done in
     *                   non-popup window.
     */
    public MainWindow(final WindowController controller, Window owner,
                      StageStyle style, boolean popup) {
        super(style);
        initOwner(owner);
        initModality(Modality.WINDOW_MODAL);
        controller.setWindow(this);
        if (popup) {
            setOnCloseRequest(e -> {
                e.consume();
                close();
            });
        } else {
            setOnCloseRequest(e -> {
                e.consume();
                screens.exitDialog().showAndWait();
            });
        }
    }
}
