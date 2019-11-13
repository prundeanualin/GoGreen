package gogreenclient.screens.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

/**
 * A confirm dialog which is a stage. And this kind of dialog will have at most two buttons,
 * you can customize the functionality of each button.
 */
public class ConfirmDialog extends gogreenclient.screens.window.Windows {

    private ConfirmDialogController confirmDialogController;

    /**
     * Model class of all confirming dialogs which has a yes button and a no button.
     *
     * @param controller your stage controller.
     * @param owner      your stage owner.
     * @param style      your stage style.
     */
    public ConfirmDialog(final ConfirmDialogController controller, URL fxml,
                         Window owner, StageStyle style) {
        super(style);
        initOwner(owner);
        initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(fxml);
        try {
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aclass) {
                    return controller;
                }
            });
            controller.setWindow(this);
            confirmDialogController = controller;
            Scene scene = new Scene((Parent) loader.load());
            scene.getStylesheets().add("/static/hover.css");
            setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ConfirmDialogController getConfirmDialogController() {
        return confirmDialogController;
    }
}
