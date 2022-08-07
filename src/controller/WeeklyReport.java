package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import utilities.SceneSwitcher;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller for displaying report based on week
 */
public class WeeklyReport implements Initializable {

    @FXML private TableView<Appointments> weeklyTable;
    @FXML private TableColumn<Appointments, Integer> weeklyAppointmentIdCol;
    @FXML private TableColumn<Appointments, Integer> weeklyCustomerIdCol;
    @FXML private TableColumn<Appointments, LocalDateTime> weeklyEndTimeCol;
    @FXML private TableColumn<Appointments, LocalDateTime> weeklyStartTimeCol;
    @FXML private TableColumn<Appointments, String> weeklyTitleCol;
    @FXML private TableColumn<Appointments, Integer> weeklyUserIdCol;

    @FXML private TableColumn<Appointments, String> weeklyTypeCol;
    @FXML private TableColumn<Appointments, String> weeklyDescriptionCol;
    @FXML private TableColumn<Appointments, String> weeklyContactCol;
    @FXML private TableColumn<Appointments, String> weeklyLocationCol;

    /**
     * Initializes table
     * @param url pointer
     * @param resourceBundle table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        weeklyTable.setItems(Appointments.getWeeklyAppointments());
        weeklyTypeCol.setCellValueFactory(new PropertyValueFactory("type"));
        weeklyDescriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        weeklyAppointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        weeklyTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        weeklyStartTimeCol.setCellValueFactory(new PropertyValueFactory("start"));
        weeklyEndTimeCol.setCellValueFactory(new PropertyValueFactory("end"));
        weeklyContactCol.setCellValueFactory(new PropertyValueFactory("apptContactID"));
        weeklyLocationCol.setCellValueFactory(new PropertyValueFactory("location"));
        weeklyUserIdCol.setCellValueFactory(new PropertyValueFactory("apptUserID"));
        weeklyCustomerIdCol.setCellValueFactory(new PropertyValueFactory("apptCustomerID"));

    }

    /**
     * switches to main screen on exit
     * @param event exit button
     * @throws IOException if there is an IO error
     */
    @FXML
    void onExitButton(ActionEvent event) throws IOException {

        SceneSwitcher.switchToMain(event);

    }

}
