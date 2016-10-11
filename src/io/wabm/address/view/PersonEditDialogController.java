package io.wabm.address.view;

/**
 * Created by MainasuK on 2016-10-11.
 */

import io.wabm.address.model.Person;
import io.wabm.address.util.DateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Dialog to edit details of a person
 */
public class PersonEditDialogController {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField streetTextField;

    @FXML
    private TextField postalCodeTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private TextField birthdayTextField;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        firstNameTextField.setText(person.getFirstName());
        lastNameTextField.setText(person.getLastName());
        streetTextField.setText(person.getStreet());
        postalCodeTextField.setText(Integer.toString(person.getPostalCode()));
        cityTextField.setText(person.getCity());
        birthdayTextField.setText(DateUtil.format(person.getBirthday()));
        birthdayTextField.setPromptText("yyyy-MM-dd");
    }

    /**
     * Returns true if the user clicked OK, false otherwise
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void okButtonPressed() {
        if (isInputValid()) {
            person.setFirstName(firstNameTextField.getText());
            person.setLastName(lastNameTextField.getText());
            person.setStreet(streetTextField.getText());
            person.setPostalCode(Integer.parseInt(postalCodeTextField.getText()));
            person.setCity(cityTextField.getText());
            person.setBirthday(DateUtil.parse(birthdayTextField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel
     */
    @FXML
    private void cancelButtonPressed() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields
     *
     * @return
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (firstNameTextField.getText() == null || firstNameTextField.getText().length() == 0) {
            errorMessage += "No valid first name.\n";
        }

        if (lastNameTextField.getText() == null || lastNameTextField.getText().length() == 0) {
            errorMessage += "No valid last name.\n";
        }

        if (streetTextField.getText() == null || streetTextField.getText().length() == 0) {
            errorMessage += "No valid street.\n";
        }

        if (postalCodeTextField.getText() == null || postalCodeTextField.getText().length() == 0) {
            errorMessage += "No valid postal code\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeTextField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)\n";
            }
        }

        if (cityTextField.getText() == null || cityTextField.getText().length() == 0) {
            errorMessage += "No valid city.\n";
        }

        if (birthdayTextField.getText() == null || birthdayTextField.getText().length() == 0) {
            errorMessage += "No valid birthday.\n";
        } else {
            if (!DateUtil.validDate(birthdayTextField.getText())) {
                errorMessage += "No valid birthday. Use the format yyyy-MM-dd.\n";
            }
        }


        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Plase correct invalid fields");
            alert.setContentText(errorMessage);
            alert.show();

            return false;
        }
    }
}
