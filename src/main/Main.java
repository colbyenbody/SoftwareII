package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import utilities.DBConnect;

import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;

/** This class creates main application*/
public class Main extends Application {

    Locale locale = Locale.getDefault();

    /**
     * loads login screen
     * @param stage login screen
     * @throws Exception if there is an error
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 310, 370));
        stage.show();
    }


    /** Connects/disconnects to database, loads all models
     * @param args arguments
     * @throws Exception if there is an error
     * */
    public static void main(String[] args) throws Exception {
        //Locale.setDefault(new Locale("fr", "FR"));
        DBConnect.connect();
        Appointments.fifMinChecker();
        Divisions.setAllDivisions();
        Countries.setAllCountries();
        Users.setAllUsers();
        Contacts.setContacts();
        Appointments.setAllAppointments();
        Customers.setAllCustomers();
        launch(args);
        DBConnect.closeConnection();
    }
}