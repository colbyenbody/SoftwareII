package utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Handles switching between screens
 */
public class SceneSwitcher {

    static Parent scene;
    static Stage stage;

    /**
     * Switches user from current screen to MainScreen
     * @param event changing screens
     * @throws IOException if there is an IO Error
     */
    public static void switchToMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/View/MainScreen.fxml")));
        stage.setScene(new Scene(scene, 1000, 600));
        stage.setTitle("Main Screen");
        stage.show();
    }

    /**
     * switchToAddCustomer sends user from current screen to Add Customer screen
     * @param event changing screens
     * @throws IOException if there is an IO error
     */
    public static void switchToAddCustomer(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/View/AddCustomer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * switchToAddCustomer sends user from current screen to Add Customer screen
     * @param event changing screens
     * @throws IOException if there is an IO error
     */
    public static void switchToAddAppointment(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/View/AddAppointment.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * switchToAddCustomer sends user from current screen to monthly report screen
     * @param event changing screens
     * @throws IOException if there is an IO error
     */
    public static void switchToMonthlyReport(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/View/MonthlyReport.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * switchToWeeklyReport sends user from current screen to weekly report screen
     * @param event changing screens
     * @throws IOException if there is an IO error
     */
    public static void switchToWeeklyReport(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/View/WeeklyReport.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * switchToContactReport sends user from current screen to contact report screen
     * @param event changing screens
     * @throws IOException if there is an IO error
     */
    public static void switchToContactReport (ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/View/ContactReport.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * switchToAreaReport sends user from current screen to area report screen
     * @param event changing screens
     * @throws IOException if there is an IO error
     */
    public static void switchToAreaReport (ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(SceneSwitcher.class.getResource("/View/AreaReport.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

}
