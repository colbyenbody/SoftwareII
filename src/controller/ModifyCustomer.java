package controller;

import model.*;
import utilities.DBConnect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller for updating existing customer
 */
public class ModifyCustomer implements Initializable {

    private ObservableList<Divisions> comboDivisions = Divisions.getAllDivisions();
    private ObservableList<Countries> comboCountries = Countries.getAllCountries();

    @FXML private TextField modifyPostalCodeTextField;
    @FXML private TextField modifyPhoneTextField;
    @FXML private TextField modifyCustomerIDTextField;
    @FXML private TextField modifyNameTextField;
    @FXML private TextField modifyAddressTextField;
    
    
    @FXML private ComboBox<Divisions> divisionCombobox;
    @FXML private ComboBox<Countries> countryCombobox;

    private Customers selectedCustomer;


    @FXML private Button exitButton;

    /**
     * Initializes with information from selected customer.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCustomer = MainScreen.customerToModify();
        modifyCustomerIDTextField.setText(String.valueOf(selectedCustomer.getCustomerId()));
        modifyNameTextField.setText(selectedCustomer.getCustomerName());
        modifyAddressTextField.setText(selectedCustomer.getAddress());
        modifyPostalCodeTextField.setText(selectedCustomer.getPostalCode());
        modifyPhoneTextField.setText(selectedCustomer.getPhone());
        for (Divisions division : comboDivisions) {
            if (division.getDivisionId() != selectedCustomer.getDivisionId()) continue;
            divisionCombobox.setValue(division);
            for (Countries countries : comboCountries) {
                if (countries.getCountryId() != division.getDivCountryID()) continue;
                countryCombobox.setValue(countries);
            }
        }
        divisionCombobox.setItems(comboDivisions);
        countryCombobox.setItems(comboCountries);
    }

    /**
     * Returns to main screen.
     * @param actionEvent returns to main screen.
     * @throws IOException if there is an I/O error.
     */
    public void onExit (ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Scene scene = new Scene(root);
        Stage currentStage = (Stage) exitButton.getScene().getWindow();
        currentStage.close();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Check for valid changes, saves changes to database
     * @param actionEvent returns to main screen when save is selected
     * @throws IOException if there is an I/O error
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if there is a class error
     */
    public void onSave(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        String name = modifyNameTextField.getText();
        String address = modifyAddressTextField.getText();
        String phone = modifyPhoneTextField.getText();
        String zip = modifyPostalCodeTextField.getText();
        Divisions division = divisionCombobox.getValue();
        Countries countries = countryCombobox.getValue();

        if (division == null) {
            errorAlert("Error", selectedCustomer.getCustomerName()+" has not been edited. Please select a country/zipcode");
            return;
        }
        else if(name.isBlank())
        {
            errorAlert("Error", "Customer has not been edited. Please enter a name");
            return;
        }
        else if(address.isBlank())
        {
            errorAlert("Error", "Customer has not been edited. Please enter an address");
            return;
        }
        else if(phone.isBlank())
        {
            errorAlert("Error", "Customer has not been edited. Please enter a phone number");
            return;
        }
        else if(zip.isBlank())
        {
            errorAlert("Error", "Customer has not been edited. Please enter a zip code");
            return;
        }
        if (!name.isEmpty() || address.length() != 0 || !zip.isEmpty()) {
            modifyCustomer(name, address,zip, phone, division.getDivisionId());
            confirmAlert("Confirm", selectedCustomer.getCustomerName() + " has been edited");
            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
            stage.setTitle("Main Screen");
            stage.setScene(new Scene(scene));
            stage.show();
            Customers.clearAllCustomers();
            Customers.setAllCustomers();
        } else {
            errorAlert("Error", selectedCustomer.getCustomerName()+" has not been edited.");
        }
    }

    /** Filters list based on country. */
    public void updateDivision() {
        int countryID = countryCombobox.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Divisions> filteredDivisions = FXCollections.observableArrayList();
        for (Divisions division : comboDivisions) {
            if (division.getDivCountryID() == countryID) {
                filteredDivisions.add(division);
                if (countryID == 1) {
                } else {
                }
            }
        }
        divisionCombobox.setItems(filteredDivisions);
    }

    /**
     * Selects country and filters list.
     * @param actionEvent Chooses a country.
     */
    public void onCountry(ActionEvent actionEvent) {updateDivision();}


    /**
     * updates customers information in database
     * @param name customer name
     * @param address address
     * @param zip postal code
     * @param phone phone
     * @param divisionID division id
     * @throws SQLException if there is an SQL error.
     */
    void modifyCustomer(String name, String address, String zip, String phone, int divisionID) throws SQLException {
        String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?";
        int id = MainScreen.customerToModify().getCustomerId();
        try {
            PreparedStatement ps = DBConnect.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, zip);
            ps.setString(4, phone);
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, Users.getLoggedName());
            ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(8, Users.getLoggedName());
            ps.setInt(9, divisionID);
            ps.setInt(10,id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    /**
     * @param title Type of alert.
     * @param context Reason for alert.
     */
    static void confirmAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(context);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        } else {

        }
    }

    /**
     * @param title Type of alert.
     * @param context Reason for alert.
     */
    static void errorAlert(String title, String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(context);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        } else {
        }
    }

}
