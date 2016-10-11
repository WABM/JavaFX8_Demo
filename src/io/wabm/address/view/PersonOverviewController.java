package io.wabm.address.view;

import io.wabm.address.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import io.wabm.address.model.Person;
import io.wabm.address.util.DateUtil;

import java.util.Optional;

/**
 * Created by MainasuK on 2016-10-9.
 */
public class PersonOverviewController {
    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    @FXML
    private Button deleteButton;

    // Reference to the main application.
    private Main main;

    /**
     * The constructor.
     * The constructor is called before the initialze() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // Clear person details.
        showPersonDetials(null);

        personTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetials(newValue)
        );
    }

    /**
     * Fills all text fields to show details about the person.
     * If the specifed persion is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetials(Person person) {
        if (person != null) {
            // Fill the label with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));

            // Enable delete button
            deleteButton.setDisable(false);
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");

            // Disable delete button
            deleteButton.setDisable(true);
        }
    }

    @FXML
    private void newButtonPressed() {
        Person tempPerson = new Person();
        boolean okClicked = main.showPersonEditDialog(tempPerson);

        if (okClicked) {
            main.getPersonData().add(tempPerson);
        }
    }

    @FXML
    private void editButtonClicked() {
        Person selectedPerson = personTableView.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = main.showPersonEditDialog(selectedPerson);

            if (okClicked) {
                showPersonDetials(selectedPerson);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Edit");
            alert.setHeaderText("No Selection");
            alert.setContentText("Please select a person in the table.");

            alert.show();
        }
    }

    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void deleteButtonPressed() {
        int index = personTableView.getSelectionModel().getSelectedIndex();
        Person person = personTableView.getItems().get(index);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("确认删除");
        alert.setContentText("删除 " + person.getFirstName() + " " + person.getLastName());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            personTableView.getItems().remove(index);
        } else {
            // Cancel
        }

    }

    /**
     * Is called by the main application to give a reference back to itself.
     * @param main
     */
    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the table
        personTableView.setItems(main.getPersonData());
    }
}
