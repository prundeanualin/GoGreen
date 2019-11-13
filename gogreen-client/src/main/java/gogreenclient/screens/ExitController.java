package gogreenclient.screens;

import gogreenclient.screens.window.ConfirmDialogController;
import gogreenclient.screens.window.Windows;
import javafx.application.Platform;

public class ExitController implements ConfirmDialogController {


    private ScreenConfiguration screens;

    private Windows dialog;

    public ExitController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @Override
    public void setWindow(Windows dialog) {
        this.dialog = dialog;
    }

    @Override
    public void yes() {
        dialog.close();
        Platform.exit();
    }

    @Override
    public void no() {
        dialog.close();
    }
}
