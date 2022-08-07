package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import model.Appointments;
import utilities.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
/**
 * Controller for handling login authentication
 */
public class LoginScreen implements Initializable {

    @FXML private Label lblErrors;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Button exitButton;
    @FXML private Label zoneidlabel;
    @FXML private Label logintitle;
    @FXML private Label usernamelabel;
    @FXML private Label passwordlabel;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * switches to main screen if login successful, logs username, shows if appointments is within 15 min
     * @param event login
     * @throws IOException if there is an error
     * @throws SQLException if there is an sql error
     */
    @FXML
    public void onLoginButton (ActionEvent event) throws SQLException, IOException {

        if (event.getSource() == btnLogin) {
            System.out.println("login pressed");

            if (logIn().equals("Success")) {
                try {
                    Logger.auditLogin(txtUsername.getText(), true);
                    SceneSwitcher.switchToMain(event);
                    showAppointmentAlert();
                } catch (IOException | ClassNotFoundException ex) {
                    System.err.println(ex.getMessage());
                }

            }
            else {
                Logger.auditLogin(txtUsername.getText(), false);
            }

        }
    }
    Locale currentLocale = Locale.getDefault();

    /**
     * Initializes login screen
     * @param url pointer
     * @param rb resource bundle
     */
    public void initialize(URL url, ResourceBundle rb) {

        ZoneId zoneId = ZoneId.systemDefault();
        zoneidlabel.setText(String.valueOf(zoneId));
        if(currentLocale.getLanguage().equals("fr"))
        {
            logintitle.setText("Connexion");
            btnLogin.setText("Connexion");
            exitButton.setText("Annuler");
            usernamelabel.setText("Nom d'utilisateur");
            passwordlabel.setText("Mot de passe");
        }
        // Checks connection to database, displays error if no connection
        try {
            con = DBConnect.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (con == null) {
            lblErrors.setTextFill(Color.RED);
            if (currentLocale.getLanguage().equals("fr")){
                lblErrors.setText("base de données non connectée");
            }
            else {
                lblErrors.setText("Database not connected");
            }
        } else {
            lblErrors.setTextFill(Color.GREEN);
            if (currentLocale.getLanguage().equals("fr")){
                lblErrors.setText("base de données connectée");
            }
            else {
                lblErrors.setText("Database connected");
            }
        }

    }

    /**
     * login checks password/username translates based on language
     * @throws SQLException if there is an SQL error
     * @return status whether login is successful or not
     */
    private String logIn() throws SQLException {
        con = DBConnect.getConnection();
        String status = "Success";
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if(username.isEmpty() || password.isEmpty()) {
            if (currentLocale.getLanguage().equals("fr")){
                setLblError(Color.RED, "informations d'identification vides");
            }
            else {
                setLblError(Color.RED, "Empty credentials");
            }
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM users Where User_Name = ? and Password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    if (currentLocale.getLanguage().equals("fr")){
                        setLblError(Color.RED, "Entrez le nom d'utilisateur/mot de passe correct");
                    }
                    else {
                        setLblError(Color.RED, "Enter Correct Username/Password");
                    }
                    status = "Error";
                } else {
                    if (currentLocale.getLanguage().equals("fr")){
                        setLblError(Color.RED, "Connexion réussie");
                    }
                    else {
                        setLblError(Color.GREEN, "Login Successful");
                    }

                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    /**
     * login checks password/username translates based on language
     * @throws SQLException if there is an SQL error
     * @return status whether login is successful or not
     */
    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }

    /**
     * checks if there is an appointment within 15 minutes of logon
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if there is a class error
     */
    private void showAppointmentAlert() throws ClassNotFoundException, SQLException {

        String apptStr = "";
        ObservableList<Appointments> apptList = Appointments.fifminlist();

        if (!apptList.isEmpty()) {
            for (int i = 0; i < apptList.size(); i++) {
                int apptId = apptList.get(i).getApptCustomerID();
                String start = apptList.get(i).getStart();

                apptStr += "Appointment ID: " + apptId + ", Start: " + start + "\n";
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, apptStr, ButtonType.OK);
            alert.setHeaderText("Alert: You have an appointment within 15 minutes");
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No upcoming appointments", ButtonType.OK);
            alert.showAndWait().filter(response -> response == ButtonType.OK);
        }



    }

    /**
     * onExitButton displays exit alert
     */
    @FXML
    public void onExitButton(){
        Alerts.onExit();
    }

}
