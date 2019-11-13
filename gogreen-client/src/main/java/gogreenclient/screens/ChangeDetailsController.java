package gogreenclient.screens;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import gogreenclient.screens.window.ConfirmDialogController;
import gogreenclient.screens.window.Windows;
import javafx.fxml.FXML;

public class ChangeDetailsController implements ConfirmDialogController {

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField newPassword;

    @FXML
    private JFXPasswordField confirmNewPassword;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField newEmail;

    @FXML
    private JFXDatePicker birthDate;

    @FXML
    private JFXTextField deleteBox;

    private ScreenConfiguration screens;

    private Windows dialog;

    public ChangeDetailsController(ScreenConfiguration screens) {
        this.screens = screens;
    }

    @Override
    public void setWindow(Windows dialog) {
        this.dialog = dialog;
    }

    @Override
    public void yes() {
    }

    @Override
    public void no() {
        dialog.close();
    }

    /**
     * check for the passwords. send the new one.
     */
    public void changePassword() {
        password.getText().toString();
        newPassword.getText().toString();
        confirmNewPassword.getText().toString();
    }

    /**
     * check for correct password and matching emails.send them.
     */
    public void changeEmail() {
        password.getText().toString();
        email.getText().toString();
        newPassword.getText().toString();
    }

    public void changeBirthdate() {
        password.getText().toString();
        birthDate.getValue();
    }

    public void deleteAccount() {
        password.getText().toString();
        deleteBox.getText().toString();
    }
}
