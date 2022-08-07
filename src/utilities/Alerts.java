package utilities;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;

/**Alerts class used to display alerts for all errors*/
public class Alerts {

    /**
     * alert with dialog
     * @param header error header
     * @param info error info
     * @param title error title
     */
    public static void errorDialog(String info, String header, String title) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(info);
        alert.setHeaderText(header);
        alert.showAndWait();

    }

    /**
     * exit alert
     */
    public static void onExit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * error alert
     * @param context context of alert
     */
    public static void errorAlert(String context) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(context);
        Optional<ButtonType> result = alert.showAndWait();
        result.get();
    }

    /**
     * confirm alert
     * @param context context of alert
     */
    public static void confirmAlert(String context) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText(context);
        Optional<ButtonType> result = alert.showAndWait();
        result.get();
    }

    /**
     * customerMissing alert
     */
    public static void customerMissing() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("No Customer Selected");
        alert.setContentText("Please select the customer to modify");
        alert.showAndWait();
    }

    /**
     * delCustomerMissing alert
     */
    public static void delCustomerMissing() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("No Customer Selected");
        alert.setContentText("Please select the customer to be deleted");
        alert.showAndWait();
    }

    /**
     * delCustomerAppointmentsExist alert
     */
    public static void delCustomerAppointmentsExist() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Customer not deleted");
        alert.setContentText("Customer has existing appointments, please delete any appointments first");
        alert.showAndWait();
    }

    /**
     * customerAdded alert
     */
    public static void customerAdded() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Customer Added");
        alert.setContentText("Customer added to database");
        alert.showAndWait();
    }

    /**
     * appointmentAdded alert
     */
    public static void appointmentAdded() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Appointment Added");
        alert.setContentText("Appointment added to database");
        alert.showAndWait();
    }

    /**
     * appointmentUpdated alert
     */
    public static void appointmentUpdated() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("Appointment Updated");
        alert.setContentText("The appointment information has been updated");
        alert.showAndWait();
    }



    /**
     * customerNotAdded alert
     */
    public static void customerNotAdded() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("");
        alert.setHeaderText("Customer not added");
        alert.setContentText("Check fields and try again");
        alert.showAndWait();
    }

    /**
     * start2 displays error
     * @param s alert
     */
    public static void start2 (Stage s) {
        // set title for the stage
        s.setTitle("creating alerts");

        // create a button
        Button b = new Button("Confirmation alert");
        Button b1 = new Button("error alert");
        Button b2 = new Button("Information alert");
        Button b3 = new Button("Warning alert");

        // create a tile pane
        TilePane r = new TilePane();

        // create a alert
        Alert a = new Alert(Alert.AlertType.NONE);

        // action event
        var event = new
                EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent event) {

                    }

                    public void handle(ActionEvent e) {
                        // set alert type
                        a.setAlertType(Alert.AlertType.CONFIRMATION);

                        // show the dialog
                        a.show();
                    }
                };

        // action event
        var event1 = new
                EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent event) {

                    }

                    {
                        // set alert type
                        a.setAlertType(Alert.AlertType.ERROR);

                        // show the dialog
                        a.show();
                    }
                };

        // action event
        var event2 = new
                EventHandler<javafx.event.ActionEvent>() {
                    @Override
                    public void handle(javafx.event.ActionEvent event) {

                    }

                    {
                        // set alert type
                        a.setAlertType(Alert.AlertType.WARNING);

                        // show the dialog
                        a.show();
                    }
                };

        // when button is pressed
        b.setOnAction(event);
        b1.setOnAction(event1);
        b2.setOnAction(event2);

        // add button
        r.getChildren().add(b);
        r.getChildren().add(b1);
        r.getChildren().add(b2);
        r.getChildren().add(b3);

        // create a scene
        Scene sc = new Scene(r, 200, 200);

        // set the scene
        s.setScene(sc);

        s.show();
    }

    }

