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
import utilities.SceneSwitcher;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for displaying reports based on contact
 */
public class ContactReport implements Initializable {

    @FXML private TableView<Appointments> appointmentTable;
    @FXML private TableColumn<Appointments, Integer> appointmentIDCol;
    @FXML private TableColumn<Appointments, String> titleCol;
    @FXML private TableColumn<Appointments, String> typeCol;
    @FXML private TableColumn<Appointments, String> descriptionCol;
    @FXML private TableColumn<Appointments, LocalDateTime> startCol;
    @FXML private TableColumn<Appointments, LocalDateTime> endCol;
    @FXML private TableColumn<Appointments, Integer> customerIDCol;

    private final ObservableList<Contacts> contacts = FXCollections.observableArrayList();
    private final ObservableList<Appointments> appointments = FXCollections.observableArrayList();
    private final FilteredList<Appointments> filteredAppointments = new FilteredList<>(appointments);

    @FXML private ComboBox<Contacts> contactComboBox;

    /**
     * Initializes table and comboboxes
     * @param url pointer
     * @param resourceBundle table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            loadAppointmentTable();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * lambda displays appointment based on the selected contact
     * @param actionEvent selected contact
     * Justification: allows simple contact filtering
     */
    @FXML public void onContactComboBox (ActionEvent actionEvent) {
        Contacts selected = contactComboBox.getSelectionModel().getSelectedItem();
        /**
          lambda test
         */
        filteredAppointments.setPredicate(appointment -> appointment.getApptContactID() == selected.getContactID());
    }

    /**
     * loads information to each report
     * @throws Exception if there is an error
     */
    public void loadInformation() throws  Exception{

        appointments.addAll(Appointments.getAllAppointments());
        contacts.addAll(Contacts.getAllContacts());
        contactComboBox.setItems(contacts);
    }

    /**
     * loads information to appointment table
     * @throws Exception if there is an error
     */
    public void loadAppointmentTable () throws Exception {
        loadInformation();
        appointmentTable.setItems(filteredAppointments);
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
