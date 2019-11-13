package gogreenclient.screens.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

;

/**
 * A stage which contains only one scene, which is loaded from a fxml file. Switching scene will
 * be difficult using this kind of stage.
 */
public class FxmlWindow extends gogreenclient.screens.window.Windows {

    /**
     * The model of all the stages that will load a fxml file and will not change it's
     * scene or root of the scene.
     *
     * @param controller your stage controller.
     * @param fxml       the URI of the fxml file.
     * @param owner      the owner of the stage.
     * @param style      the style of the stage.
     */
    public FxmlWindow(final WindowController controller, URL fxml, Window owner, StageStyle style) {
        super(style);
        initOwner(owner);
        initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(fxml);
        try {
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aclass) {
                    return controller;
                }
            });
            controller.setWindow(this);
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add("/static/hover.css");
            setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
