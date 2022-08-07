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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
/**
 * Controller for updating existing appointment
 */
public class ModifyAppointment implements Initializable {

    private ObservableList<Users> comboUsers = Users.getAllUsers();
    private ObservableList<Contacts> comboContacts = Contacts.getAllContacts();
    private ObservableList<Customers> comboCustomers = Customers.getAllCustomers();


    @FXML private TextField modifyTitleTextField;
    @FXML private TextField modifyDescriptionTextField;
    @FXML private TextField modifyTypeTextField;
    @FXML private TextField modifyLocationTextField;
    @FXML private TextField modifyAppointmentIDTextField;

    @FXML private ComboBox<Customers> modifyCustomersComboBox;
    @FXML private ComboBox<Contacts> modifyContactsComboBox;
    @FXML private ComboBox<Users> modifyUserComboBox;


    @FXML private DatePicker startPicker;
    @FXML private DatePicker endPicker;
    @FXML private ComboBox<LocalTime> startTimeComboBox;
    @FXML private ComboBox<LocalTime> endTimeComboBox;

    private final ObservableList<Customers> customers = Customers.getAllCustomers();



    /**
     * loads information to be modified
     * @param appointment appointment information
     * @throws ParseException if error while parsing
     */
    public void sendAppointment(Appointments appointment) throws ParseException {
        String start = appointment.getStart();
        String end = appointment.getEnd();

        Date startParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start);
        Date endParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startLocalParse = LocalDateTime.parse(start, formatter);
        LocalTime startLocalTimeParse = startLocalParse.toLocalTime();
        LocalDateTime endLocalParse = LocalDateTime.parse(end, formatter);
        LocalTime endLocalTimeParse = endLocalParse.toLocalTime();

        LocalDate date = Users.convertToLocalDateViaInstant(startParse);
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
        modifyAppointmentIDTextField.setText(String.valueOf(appointment.getAppointmentId()));
        modifyContactsComboBox.getSelectionModel().select(appointment.getApptContactID() - 1);

        for(Customers customers : customers){
            if (customers.getCustomerId() != appointment.getApptCustomerID()) continue;
            modifyCustomersComboBox.setValue(customers);
        }
        modifyCustomersComboBox.setItems(customers);

        modifyDescriptionTextField.setText(String.valueOf(appointment.getDescription()));
        modifyLocationTextField.setText(String.valueOf(appointment.getDescription()));
        modifyTitleTextField.setText(String.valueOf(appointment.getTitle()));
        modifyTypeTextField.setText(String.valueOf(appointment.getType()));
        modifyUserComboBox.getSelectionModel().select(appointment.getApptUserID() - 1);

        LocalDate endDate = Users.convertToLocalDateViaInstant(endParse);
        endPicker.setValue(endDate);
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
        startTimeComboBox.getSelectionModel().select(startLocalTimeParse);
        endTimeComboBox.getSelectionModel().select(endLocalTimeParse);

    }



    /**
     * switches to main screen on exit
     * @param event exit button
     * @throws IOException if there is an error
     */
    @FXML
    void onExitButton (ActionEvent event) throws IOException {

        SceneSwitcher.switchToMain(event);

    }

    /**
     * updates appointment information in database
     * @param event updates appointment
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if there is a class error
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

        int appointmentId = Integer.parseInt(modifyAppointmentIDTextField.getText());
        String title =  modifyTitleTextField.getText();
        String description = modifyDescriptionTextField.getText();
        String location = modifyLocationTextField.getText();
        String type = modifyTypeTextField.getText();
        String start = startTime;
        String utcStart = timeConverter.toUTC(start);
        String end = endTime;
        String utcEnd = timeConverter.toUTC(end);
        int customerId = modifyCustomersComboBox.getValue().getCustomerId();
        int userId = modifyUserComboBox.getValue().getUserID();
        int contactId = modifyContactsComboBox.getValue().getContactID();

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
        else if(Appointments.checkForOverlapModify(utcStart, utcEnd, customerId))
        {
            Alerts.errorAlert("Another appointment exists for this time, please pick a different time");
        }
        else
        {
            Appointments.updateAppointment(appointmentId,title,description,location,type,utcStart,utcEnd,customerId,userId,contactId);
            Alerts.appointmentUpdated();

        }
    }


    /**
     * Initializes datepickers and comboboxes
     * @param url pointer
     * @param resourceBundle Localtime/date
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        modifyCustomersComboBox.setItems(comboCustomers);

        modifyUserComboBox.setItems(comboUsers);
        modifyContactsComboBox.setItems(comboContacts);


        LocalTime start = LocalTime.of(5,0 );
        LocalTime end = LocalTime.of(18,0);

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
