package gogreenclient.screens;

import gogreenclient.screens.window.ConfirmDialogController;
import gogreenclient.screens.window.Windows;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class InformController implements ConfirmDialogController {
    private ScreenConfiguration screens;

    private Windows dialog;

    @FXML
    private Label information;

    public InformController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @Override
    public void setWindow(Windows dialog) {
        this.dialog = dialog;
    }

    @Override
    public void yes() {
    }

    @FXML
    @Override
    public void no() {
        dialog.close();
    }

    public void setInformation(String information) {
        this.information.setText(information);
    }
}
