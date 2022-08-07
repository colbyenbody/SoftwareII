package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointments;
import model.Reports;
import utilities.SceneSwitcher;

import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;

/**
 * Controller for displaying report based on month
 */
public class MonthlyReport implements Initializable {

    @FXML private TableView<Object> monthlyTable;
    @FXML private TableColumn<Reports, String> typeCol;
    @FXML private TableColumn<Reports, Month> monthCol;
    @FXML private TableColumn<Reports, Integer> totalCol;
    @FXML private TableColumn<Reports, String> titleCol;
    @FXML private TableColumn<Reports, String> locationCol;
    @FXML private TableColumn<Reports, String> descriptionCol;
    @FXML private TableColumn<Reports, Integer> contactIDCol;

    @FXML private TableView<Object> typeTable;
    @FXML private TableColumn<Reports, String> typeTypeCol;
    @FXML private TableColumn<Reports, Month> typeMonthCol;
    @FXML private TableColumn<Reports, Integer> typeTotalCol;
    @FXML private TableColumn<Reports, String> typeTitleCol;
    @FXML private TableColumn<Reports, String> typeLocationCol;
    @FXML private TableColumn<Reports, String> typeDescriptionCol;
    @FXML private TableColumn<Reports, Integer> typeContactIDCol;



    private final ObservableList<Object> typeMonth = FXCollections.observableArrayList();
    private final ObservableList<Object> typeType = FXCollections.observableArrayList();


    /**
     * Initializes table and comboboxes
     * @param url pointer
     * @param resourceBundle table
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadMonthTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            loadTypeTable();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * loads information to each report
     * @throws Exception if there is an error
     */
    public void loadInformation() throws  Exception{

        typeMonth.addAll(Appointments.monthlyTypeReport());
        typeType.addAll(Appointments.typeTypeReport());

    }


    /**
     * loads the report based on month
     * @throws Exception if there is an error
     */
    public void loadMonthTable () throws Exception {
        loadInformation();
        monthlyTable.setItems(typeMonth);
        typeCol.setCellValueFactory(new PropertyValueFactory("type"));
        monthCol.setCellValueFactory(new PropertyValueFactory("date"));
        totalCol.setCellValueFactory(new PropertyValueFactory("total"));
        titleCol.setCellValueFactory(new PropertyValueFactory("title"));
        locationCol.setCellValueFactory(new PropertyValueFactory("location"));
        contactIDCol.setCellValueFactory(new PropertyValueFactory("contactID"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
    }

    /**
     * loads the report based on month
     * @throws Exception if there is an error
     */
    public void loadTypeTable () throws Exception {
        typeTable.setItems(typeType);
        typeTypeCol.setCellValueFactory(new PropertyValueFactory("type"));
        typeMonthCol.setCellValueFactory(new PropertyValueFactory("date"));
        typeTotalCol.setCellValueFactory(new PropertyValueFactory("total"));
        typeTitleCol.setCellValueFactory(new PropertyValueFactory("title"));
        typeLocationCol.setCellValueFactory(new PropertyValueFactory("location"));
        typeContactIDCol.setCellValueFactory(new PropertyValueFactory("contactID"));
        typeDescriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
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
