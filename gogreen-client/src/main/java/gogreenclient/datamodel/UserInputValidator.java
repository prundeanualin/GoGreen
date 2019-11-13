package gogreenclient.datamodel;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@Scope("prototype")
public class UserInputValidator {

    /**
     * validate user's fraction input, any non-digit character after the decimal will be
     * removed.
     *
     * @param input user's input.
     */
    public void validateFraction(JFXTextField input) {
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[0-9]*\\.?[0-9]*")) {
                input.setText(oldValue);
            }
        });
    }

    /**
     * validate user's integer input, any non-digit character  will be removed.
     *
     * @param input user's input.
     */
    public void validateInteger(JFXTextField input) {
        input.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                input.setText(oldValue);
            }
        });
    }

    /**
     * validate if a text field is empty.
     *
     * @param input user's input.
     * @throws IllegalArgumentException an exception which will contain the message of the error.
     */
    public void isNull(JFXTextField input) throws IllegalArgumentException {
        if (input.getText().equals("")) {
            throw new IllegalArgumentException(input.getId() + " is empty.");
        }
    }

    /**
     * validate if a combo box is empty.
     *
     * @param box user's input.
     * @throws IllegalArgumentException an exception which will contain the message of the error.
     */
    public void isNull(JFXComboBox<String> box) throws IllegalArgumentException {
        if (box.getValue().equals("")) {
            throw new IllegalArgumentException(box.getId() + " is empty.");
        }
    }

    /**
     * validate if a datepicker is empty.
     *
     * @param datePicker user's input.
     * @throws IllegalArgumentException an exception which will contain the message of the error.
     */
    public void isNull(JFXDatePicker datePicker) throws IllegalArgumentException {
        if (datePicker.getValue() == null) {
            throw new IllegalArgumentException(datePicker.getId() + " is empty.");
        }
    }

    /**
     * validate if a date is in the future.
     *
     * @param datePicker date picker.
     * @throws IllegalArgumentException an exception which will contain the message of the error.
     */
    public void isDateInPast(JFXDatePicker datePicker) throws IllegalArgumentException {
        if (datePicker.getValue().compareTo(LocalDate.now()) > 0) {
            throw new IllegalArgumentException(datePicker.getId() + " can not be in the future!");
        }
    }

}
