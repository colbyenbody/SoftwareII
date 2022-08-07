package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import model.Contacts;
import model.Countries;
import utilities.SceneSwitcher;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for displaying report based on appointment country
 */
public class AreaReport implements Initializable {

    @FXML private TableView<Appointments> areaTable;
    @FXML private TableColumn<Appointments, Integer> appointmentIDCol;
    @FXML private TableColumn<Appointments, String> titleCol;
    @FXML private TableColumn<Appointments, String> typeCol;
    @FXML private TableColumn<Appointments, String> descriptionCol;
    @FXML private TableColumn<Appointments, LocalDateTime> startCol;
    @FXML private TableColumn<Appointments, LocalDateTime> endCol;
    @FXML private TableColumn<Appointments, Integer> customerIDCol;

    private final ObservableList<Countries> countries = FXCollections.observableArrayList();
    private final ObservableList<Appointments> appointments = FXCollections.observableArrayList();
    private final FilteredList<Appointments> filteredAppointments = new FilteredList<>(appointments);

    @FXML private ComboBox<Countries> countriesComboBox;

    /**
     * Initializes table and comboboxes
     * @param url pointer
     * @param resourceBundle table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadAreaTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * lambda displays appointment based on the selected country
     * @param actionEvent selected contact
     * Justification: allows simple country filtering
     */
    @FXML public void onCountriesComboBox (ActionEvent actionEvent) {
        Countries selected = countriesComboBox.getSelectionModel().getSelectedItem();
        filteredAppointments.setPredicate(appointment -> appointment.getApptContactID() == selected.getCountryId());
    }

    /**
     * loads information to each report
     * @throws Exception if there is an error
     */
    public void loadInformation() throws  Exception{

        appointments.addAll(Appointments.getAllAppointments());
        countries.addAll(Countries.getAllCountries());
        countriesComboBox.setItems(countries);
    }

    /**
     * loads information to appointment table
     * @throws Exception if there is an error
     */
    public void loadAreaTable () throws Exception {
        loadInformation();
        areaTable.setItems(filteredAppointments);
        appointmentIDCol.setCellValueFactory(new PropertyValueFactory("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory("Title"));
        typeCol.setCellValueFactory(new PropertyValueFactory("Type"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory("Description"));
        startCol.setCellValueFactory(new PropertyValueFactory("Start"));
        endCol.setCellValueFactory(new PropertyValueFactory("End"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory("apptCustomerID"));
    }

    /**
     * onExitButton is used to switch from to main screen
     * @param event exit button
     * @throws IOException if there is an IO error
     */
    @FXML
    void onExitButton(ActionEvent event) throws IOException {

        SceneSwitcher.switchToMain(event);

    }
}
