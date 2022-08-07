package controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import utilities.Alerts;
import utilities.SceneSwitcher;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static utilities.Alerts.confirmAlert;
import static utilities.Alerts.errorAlert;

/**
 * Main controller for adding/editing appointments and customers
 */
public class MainScreen implements Initializable {

    Parent scene;
    Stage stage;

    @FXML private TableView<Appointments> apptTable;
    @FXML private TableColumn<Appointments, Integer> apptAppointmentIdCol;
    @FXML private TableColumn<Appointments, Integer> apptCustomerIdCol;
    @FXML private TableColumn<Appointments, LocalDateTime> apptEndTimeCol;
    @FXML private TableColumn<Appointments, LocalDateTime> apptStartTimeCol;
    @FXML private TableColumn<Appointments, String> apptTitleCol;
    @FXML private TableColumn<Appointments, Integer> apptUserIdCol;

    @FXML private TableColumn<Appointments, String> apptTypeCol;
    @FXML private TableColumn<Appointments, String> apptDescriptionCol;
    @FXML private TableColumn<Appointments, String> apptContactCol;
    @FXML private TableColumn<Appointments, String> apptLocationCol;

    @FXML private TableColumn<Customers, Integer> custAddIdCol;
    @FXML private TableColumn<Customers, Integer> custCustIdCol;
    @FXML private TableColumn<Customers, String> custCustNameCol;
    @FXML private TableColumn<Customers, String> custPhoneCol;
    @FXML private TableColumn<Customers, String> custPostalCodeCol;
    @FXML private TableColumn<Customers, String> custStateCol;
    @FXML private TableView<Customers> customerTableView;

    @FXML private RadioButton weekRadioButton;
    @FXML private RadioButton monthRadioButton;
    @FXML private RadioButton allRadioButton;


    private static Appointments selectedAppointment;
    private static Customers customerToDelete;

    private static Customers selectedCustomer;

    /**
     * Initializes tableviews
     * @param url pointer
     * @param resourceBundle Localtime/date
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        apptTable.setItems(Appointments.getAllAppointments());
        apptTypeCol.setCellValueFactory(new PropertyValueFactory("type"));
        apptDescriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        apptAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptStartTimeCol.setCellValueFactory(new PropertyValueFactory("start"));
        apptEndTimeCol.setCellValueFactory(new PropertyValueFactory("end"));
        apptContactCol.setCellValueFactory(new PropertyValueFactory("apptContactID"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory("location"));
        apptUserIdCol.setCellValueFactory(new PropertyValueFactory("apptUserID"));
        apptCustomerIdCol.setCellValueFactory(new PropertyValueFactory("apptCustomerID"));

        customerTableView.setItems(Customers.getAllCustomers());
        custAddIdCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custCustNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        custPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));


    }

    /**
     * @return selected appointment from table
     */
    public static Appointments appointmentToModify() {
        return selectedAppointment;
    }

    /**
     * Checks if an appointment is selected, if valid deletes
     * @param actionEvent delete appointment button selected
     * @throws IOException if there is an IO error
     * @throws SQLException if there is an sql error
     * @throws ClassNotFoundException if there is a class error
     */
    public void onDeleteAppointment(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        selectedAppointment = apptTable.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            errorAlert("Please select an appointment to delete");
        } else {
            Appointments.deleteSingleAppointment(selectedAppointment.getAppointmentId());
            String id = String.valueOf(selectedAppointment.getAppointmentId());
            String type = selectedAppointment.getType();
            confirmAlert("Appointment ID: " + id + " Type: " + type + "is being deleted");
            SceneSwitcher.switchToMain(actionEvent);
            Appointments.clearAllAppointments();
            Appointments.setAllAppointments();
        }


    }


    /**
     * Checks if customer is selected and deletes if valid
     * @param event button
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if there is a class error
     */
    @FXML
    void onDeleteCustomer(ActionEvent event) throws SQLException, ClassNotFoundException {
        customerToDelete = customerTableView.getSelectionModel().getSelectedItem();
        if(customerToDelete == null)
        {
            Alerts.delCustomerMissing();
        }
        else{
            int Id = customerToDelete.getCustomerId();
            String name = customerToDelete.getCustomerName();
            boolean appointment = Customers.searchForAppointments(Id);
            if (appointment)
            {
                Alerts.delCustomerAppointmentsExist();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will permanently delete the selected customer, do you want to continue?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    Customers.deleteCustomer(Id);
                    Customers.clearAllCustomers();
                    Customers.setAllCustomers();
                }
            }
        }


    }

    /**
     * onActionAddCustomerBtn switches to AddCustomer screen
     * @param event button
     * @throws IOException if there is an IO error
     */
    @FXML
    void onActionAddCustomerBtn(ActionEvent event) throws IOException {

        SceneSwitcher.switchToAddCustomer(event);
    }

    /**
     * onActionAddAppointmentButton switches to AddAppointment screen
     * @param event button
     * @throws IOException if there is an IO error
     */
    @FXML
    void onActionAddAppointmentButton(ActionEvent event) throws IOException {

        SceneSwitcher.switchToAddAppointment(event);
    }

    /**
     * onActionWeeklyReportButton switches to weekly report screen
     * @param event button
     * @throws IOException if there is an IO error
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if there is a class error
     */
    @FXML
    void onActionWeeklyRadioButton(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        monthRadioButton.setSelected(false);
        allRadioButton.setSelected(false);
        apptTable.getItems().clear();
        Appointments.weeklyReport();
        apptTable.setItems(Appointments.getWeeklyAppointments());


    }

    /**
     * onActionMonthlyRadioButton switches to weekly report screen
     * @param event button
     * @throws IOException if there is an IO error
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if there is a class error
     */
    @FXML
    void onActionMonthlyRadioButton(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        weekRadioButton.setSelected(false);
        allRadioButton.setSelected(false);
        apptTable.getItems().clear();
        Appointments.monthlyReport();
        apptTable.setItems(Appointments.getMonthlyAppointments());
    }
    /**
     * onActionAllRadioButton switches to weekly report screen
     * @param event button
     * @throws IOException if there is an IO error
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if there is a class error
     */
    @FXML
    void onActionAllRadioButton(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        monthRadioButton.setSelected(false);
        weekRadioButton.setSelected(false);
        apptTable.getItems().clear();
        Appointments.setAllAppointments();
        apptTable.setItems(Appointments.getAllAppointments());



    }


    /**
     * onActionMonthlyReportButton switches to monthly report screen
     * @param event button
     * @throws IOException if there is an IO error
     */
    @FXML
    void onActionMonthlyReportButton(ActionEvent event) throws IOException {

        SceneSwitcher.switchToMonthlyReport(event);
    }

    /**
     * onActionContactReportButton switches to contact report screen
     * @param event button
     * @throws IOException if there is an IO error
     */
    @FXML
    void onActionContactReportButton(ActionEvent event) throws IOException {

        SceneSwitcher.switchToContactReport(event);
    }

    /**
     * onActionMonthlyAreaButton switches to area report screen
     * @param event button
     * @throws IOException if there is an IO error
     */
    @FXML
    void onActionAreaReportButton(ActionEvent event) throws IOException {

        SceneSwitcher.switchToAreaReport(event);
    }

    /**
     * appointments to modify
     */
    private static Appointments appointmentToModify;

    /**
     * Switches to modify appointment screen if a valid appointment is selected
     * @param event
     * @throws IOException if there is an IO error
     * @throws ParseException if there is a parse error
     */
    @FXML
    void onActionModifyAppointmentBtn(ActionEvent event) throws IOException, ParseException {
        appointmentToModify = apptTable.getSelectionModel().getSelectedItem();

        if(appointmentToModify == null)
        {
            errorAlert("Please select an appointment to edit");
            return;
        }
        else
        {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();

            ModifyAppointment MAController = loader.getController();
            MAController.sendAppointment(appointmentToModify);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }


    }

    /**
     * checks whether customer is selected, if valid goes to edit customer
     * @param actionEvent edit customer button
     * @throws IOException if there is an I/O error
     */
    public void onModifyCustomer(ActionEvent actionEvent) throws IOException {
        selectedCustomer = customerTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            errorAlert("You must select a customer to edit");

        } else {
            confirmAlert("You are about to edit " + selectedCustomer.getCustomerName() + "\n press OK to continue");
            Stage modifyCustomerStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Parent scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ModifyCustomer.fxml")));
            modifyCustomerStage.setScene(new Scene(scene));
            modifyCustomerStage.show();



        }
    }

    /**
     * @return returns selected customer
     */
    public static Customers customerToModify() {
        return selectedCustomer;
    }

    /**
     * onExitButton displays exit alert
     */
    @FXML
    public void onExitButton(){
        Alerts.onExit();
    }

}


