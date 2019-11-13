package gogreenclient.datamodel;

import gogreenclient.screens.InformController;
import gogreenclient.screens.ScreenConfiguration;
import gogreenclient.screens.window.ConfirmDialog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class Messenger {

    @Autowired
    private ScreenConfiguration screens;

    /**
     * This method will pass a message to a pop up window.
     *
     * @param message message to be past.
     */
    public void showMessage(String message) {
        ConfirmDialog informdialog = screens.regularInformDialog();
        InformController controller = (InformController) informdialog.getConfirmDialogController();
        controller.setInformation(message);
        informdialog.showAndWait();
    }
}
