package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Appointments;
import model.Countries;
import model.Customers;
import model.Divisions;
import utilities.Alerts;
import utilities.SceneSwitcher;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * Controller for adding a new customer
 */
public class AddCustomer implements Initializable {

    @FXML private TextField phoneTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField postalCodeTextField;

    private ObservableList<Divisions> comboDivisions = Divisions.getAllDivisions();
    private ObservableList<Countries> comboCountries = Countries.getAllCountries();


    @FXML private ComboBox<Divisions> divisionCombobox;
    @FXML private ComboBox<Countries> countryCombobox;

    /**
     * Filters first level division based on country
     * @param event combobox selection
     * @throws SQLException if there is a sql error
     */
    @FXML
    void onActionCountryComboBox(ActionEvent event) throws SQLException {

        int id = countryCombobox.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Divisions> newStateList = Divisions.countrySelectionStateReturn(id);
        divisionCombobox.setItems(newStateList);
        divisionCombobox.getSelectionModel().selectFirst();

    }

    /**
     * Checks if form is valid and adds customer to database
     * @param actionEvent press save button
     * @throws SQLException if there is a sql error
     * @throws ClassNotFoundException if there is a class error
     * @throws IOException if there is an IO Error
     */
    public void onSave(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        int customerId =0;
        String customerName = nameTextField.getText();
        String address = addressTextField.getText();
        String phone = phoneTextField.getText();
        String postalCode = postalCodeTextField.getText();
        int countryId = 0;
        String state = String.valueOf(divisionCombobox.getValue());
        int divId = Divisions.getCurrentDivisionId(state);

        if (state == null) {
            Alerts.errorAlert("State is blank");
            return;
        }
        else if(divId == 0)
        {
            Alerts.errorAlert("Country is blank");
            return;
        }
        else if(address.isBlank())
        {
            Alerts.errorAlert("Address is blank");
            return;
        }
        else if(postalCode.isBlank())
        {
            Alerts.errorAlert("Postal Code is blank");
            return;
        }
        else if(customerName.isBlank())
        {
            Alerts.errorAlert("Customer Name is blank");
            return;
        }
        else if(phone.isBlank())
        {
            Alerts.errorAlert("Phone number is blank");
            return;
        }

        if (!customerName.isEmpty() || address.length() != 0 || !postalCode.isEmpty()){
            Customers.addCustomer(customerId,customerName, address, postalCode, phone, countryId, state, divId);
            SceneSwitcher.switchToMain(actionEvent);
            Alerts.customerAdded();
            Customers.clearAllCustomers();
            Customers.setAllCustomers();
        }
        else {
            Alerts.customerNotAdded();
        }
    }

    /**
     * onExitButton is used to switch from Add Customer form to main screen
     * @param event exit button
     * @throws IOException if there is an error
     */
    @FXML
    void onExitButton(ActionEvent event) throws IOException {

        SceneSwitcher.switchToMain(event);

    }
    /**
     * Initializes comboboxes
     * @param url pointer
     * @param resourceBundle combobox
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        divisionCombobox.setItems(comboDivisions);
        countryCombobox.setItems(comboCountries);

    }

}
