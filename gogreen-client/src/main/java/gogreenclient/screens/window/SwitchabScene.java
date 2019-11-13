package gogreenclient.screens.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

/**
 * Instance of this class will contain a root node which can be switched by a multi-scene stage,
 * like MainWindow or StartWindow. The node is loaded from a fxml file.
 */
public class SwitchabScene {

    private HBox root;


    /**
     * Another constructor without the ability of customizing the width and the height of the scene.
     *
     * @param controller the controller of this node.
     * @param fxml       the URI of the fxml file.
     */
    public SwitchabScene(final SceneController controller, URL fxml) {
        FXMLLoader loader = new FXMLLoader(fxml);
        try {
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aclass) {
                    return controller;
                }
            });
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructor used to build a switchable scene with a css file.
     *
     * @param controller the controller of this node.
     * @param fxml       the URI of the fxml file.
     * @param cssSheet   the URI of the css sheet.
     */
    public SwitchabScene(final SceneController controller, URL fxml, String cssSheet) {
        FXMLLoader loader = new FXMLLoader(fxml);
        try {
            loader.setControllerFactory(new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> aclass) {
                    return controller;
                }
            });
            root = loader.load();
            root.getStylesheets().add(cssSheet);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Pane getRoot() {
        return root;
    }
}
