package gogreenclient.screens;

import com.jfoenix.controls.JFXTextField;
import gogreenclient.datamodel.FriendService;
import gogreenclient.datamodel.Messenger;
import gogreenclient.screens.window.ConfirmDialogController;
import gogreenclient.screens.window.Windows;
import javafx.fxml.FXML;
import org.springframework.beans.factory.annotation.Autowired;

public class AddFriendPopController implements ConfirmDialogController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private Messenger messenger;

    @FXML
    private JFXTextField friendUser;

    private ScreenConfiguration screens;

    private Windows dialog;

    public AddFriendPopController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @Override
    public void setWindow(Windows dialog) {
        this.dialog = dialog;
    }

    /**
     * the function for the submit button.
     */
    @Override
    public void yes() {
        String friendName = friendUser.getText();
        int status = friendService.addFriend(friendName);
        if (status == 1) {
            messenger.showMessage(friendName + " is your friend now.");
        } else if (status == 0) {
            messenger.showMessage(friendName + " is already your friend.");
        } else {
            messenger.showMessage("Something wrong please try again.");
        }
        screens.showFriendsController().initialize();
        dialog.close();
    }

    @Override
    public void no() {
        dialog.close();
    }
}
