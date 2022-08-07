package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import utilities.Alerts;
import utilities.SceneSwitcher;
import utilities.timeConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Controller for adding a new appointment
 */
public class AddAppointment implements Initializable {


    private ObservableList<Users> comboUsers = Users.getAllUsers();
    private ObservableList<Contacts> comboContacts = Contacts.getAllContacts();
    private ObservableList<Customers> comboCustomers = Customers.getAllCustomers();

    @FXML private ComboBox<Users> userComboBox;
    @FXML private ComboBox<Contacts> contactsComboBox;
    @FXML private ComboBox<Customers> customersComboBox;

    @FXML private TextField titleTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField locationTextField;

    @FXML private DatePicker startPicker;
    @FXML private DatePicker endPicker;
    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private ComboBox<LocalTime> endTimeComboBox;


    /**
     * onSaveButton updates selected appointment with new data
     * @param event save
     * @throws SQLException if there is a sql error
     * @throws ClassNotFoundException if there is a class error
     * @throws IOException if there is an IO Error
     */
    @FXML
    void onSaveButton (ActionEvent event) throws SQLException, ClassNotFoundException, IOException {

        int timeStart = timeConverter.setStartHours();
        String startTime = "" + startPicker.getValue() +
                " " +
                startTimeComboBox.getValue() +
                ":00";

        int timeEnd = timeConverter.setEndHours();
        String endTime = "" + endPicker.getValue() +
                " " +
                endTimeComboBox.getValue() +
                ":00";

        int appointmentId = 0;
        String title =  titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();
        String start = startTime;
        String utcStart = timeConverter.toUTC(start);
        String end = endTime;
        String utcEnd = timeConverter.toUTC(end);
        int customerId = customersComboBox.getValue().getCustomerId();
        int userId = userComboBox.getValue().getUserID();
        int contactId = contactsComboBox.getValue().getContactID();

        int secondEnd = 0;
        int secondStart = 0;


        if(title.isBlank())
        {
            Alerts.errorAlert("Please enter a valid title");
            return;
        }
        else if(description.isBlank())
        {
            Alerts.errorAlert("Please enter a valid description");
            return;
        }
        else if(location.isBlank())
        {
            Alerts.errorAlert("Please enter a valid location");
            return;
        }
        else if(customersComboBox.getValue() == null)
        {
            Alerts.errorAlert("Customer is blank");
            return;
        }
        else if(userComboBox.getValue() == null)
        {
            Alerts.errorAlert("User is blank");
            return;
        }
        else if(contactsComboBox.getValue() == null)
        {
            Alerts.errorAlert("Contact");
            return;
        }
        else if(type.isBlank())
        {
            Alerts.errorAlert("Please enter a valid type");
            return;
        }
        else if(startTimeComboBox.getValue().isAfter(endTimeComboBox.getValue()) && (startPicker.getValue().isEqual(endPicker.getValue()) || startPicker.getValue().isAfter(endPicker.getValue())))
        {
            Alerts.errorAlert("Start time is after end time");
            return;
        }
        else if(startTimeComboBox.getValue().isBefore(endTimeComboBox.getValue()) && (startPicker.getValue().isAfter(endPicker.getValue())))
        {
            Alerts.errorAlert("Start date is after end date");
            return;
        }

        else if(startTimeComboBox.getValue().isBefore(LocalTime.of(timeStart,secondStart )) || endTimeComboBox.getValue().isAfter(LocalTime.of(timeEnd, secondEnd)))
        {
            Alerts.errorAlert("Appointment scheduled outside business hours, please try again");
            return;
        }
        else if(Appointments.checkForOverlap(utcStart, utcEnd, customerId))
        {
            Alerts.errorAlert("Another appointment exists for this time, please pick a different time");
            return;
        }
        else
        {
            Appointments.addAppointment(appointmentId,title,description,location,type,utcStart,utcEnd,customerId,userId,contactId);
            SceneSwitcher.switchToMain(event);
            Alerts.appointmentAdded();
            titleTextField.clear();
            descriptionTextField.clear();
            locationTextField.clear();
            typeTextField.clear();
            customersComboBox.setValue(null);
            userComboBox.setValue(null);
            contactsComboBox.setValue(null);
            startPicker.setValue(null);
            startTimeComboBox.setValue(null);
            endTimeComboBox.setValue(null);
            endPicker.setValue(null);
            Appointments.clearAllAppointments();
            Appointments.setAllAppointments();
        }
    }

    /**
     * onActionCloseBtn is used to close the form and return to the main screen.
     * @param event exit
     * @throws IOException if there is an error
     */
    @FXML
    void onExitButton(ActionEvent event) throws IOException {

        SceneSwitcher.switchToMain(event);
    }

    /**
     * Initializes datepickers and comboboxes
     * @param url pointer
     * @param resourceBundle Localtime/date
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userComboBox.setItems(comboUsers);
        userComboBox.getSelectionModel().selectFirst();
        contactsComboBox.setItems(comboContacts);
        contactsComboBox.getSelectionModel().selectFirst();
        customersComboBox.setItems(comboCustomers);
        customersComboBox.getSelectionModel().selectFirst();

        LocalTime start = LocalTime.of(0,0 );
        LocalTime end = LocalTime.of(23,0);
        LocalDate date = LocalDate.now();
        startPicker.setValue(date);
        startPicker.setDayCellFactory(datePicker -> new DateCell() {

            /**
             * disables weekends in date picker
             * @param date date
             * @param empty editable
             */
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });
        endPicker.setValue(date);
        endPicker.setDayCellFactory(datePicker -> new DateCell() {
            /**
             * disables weekends in date picker
             * @param date date
             * @param empty editable
             */
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY);
            }
        });

        while(start.isBefore(end.plusSeconds(1)))
        {
            startTimeComboBox.getItems().add(start);
            endTimeComboBox.getItems().add(start);
            start = start.plusMinutes(15);
            end = start.plusMinutes(15);
        }
        startTimeComboBox.getSelectionModel().select(LocalTime.of(8,0));
        endTimeComboBox.getSelectionModel().select(LocalTime.of(9,0));

    }


}
