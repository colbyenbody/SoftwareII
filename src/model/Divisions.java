package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DBConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Contact class is used to retrieve and present division information pulled from the database
 */
public class Divisions {

    private int divisionId;
    private String divisionState;
    private int divCountryID;
    private static ObservableList<Divisions> allDivisions = FXCollections.observableArrayList();

    /**
     * Constructor used to define the division object
     * @param divisionId division id
     * @param divisionState state
     * @param divCountryID country id
     */
    public Divisions(int divisionId, String divisionState, int divCountryID) {
        this.divisionId = divisionId;
        this.divisionState = divisionState;
        this.divCountryID = divCountryID;

    }

    /**
     * Constructor used to retrieve the division id based on the state selected
     * @param state state
     * @return division id
     * @throws SQLException if there is an sql error
     */
    public static int getCurrentDivisionId(String state) throws SQLException {
        Statement stmt = DBConnect.getConnection().createStatement();
        String divisionQuery = "SELECT * FROM client_schedule.first_level_divisions WHERE Division='"+state+"'";
        ResultSet rs = stmt.executeQuery(divisionQuery);
        if(rs.next())
        {
            int divisionId = rs.getInt(1);
            return divisionId;
        }
        return 0;
    }

    /**
     * gets division id
     * @return division
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * sets division id
     * @param divisionId division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * getse state
     * @return state
     */
    public String getDivisionState() {
        return divisionState;
    }

    /**
     * sets state
     * @param divisionState state
     */
    public void setDivisionState(String divisionState) {
        this.divisionState = divisionState;
    }

    /**
     * gets country id
     * @return country id
     */
    public int getDivCountryID() {
        return divCountryID;
    }

    /**
     * sets country id
     * @param divCountryID country id
     */
    public void setDivCountryID(int divCountryID) {
        this.divCountryID = divCountryID;
    }

    /**
     * setAllDivisions is used to set the complete list of divisions from the database
     * @throws SQLException if there is an SQL error
     */
    public static void setAllDivisions() throws SQLException
    {
        Statement stmt = DBConnect.getConnection().createStatement();
        String divisionQuery = "SELECT * FROM client_schedule.first_level_divisions";

        ResultSet rs = stmt.executeQuery(divisionQuery);

        while(rs.next())
        {
            int divisionId = rs.getInt("Division_ID");
            String divisionState = rs.getString("Division");
            int divCountryID = rs.getInt("Country_ID");

            Divisions newDivision = new Divisions(divisionId, divisionState, divCountryID);
            allDivisions.add(newDivision);
        }
    }

    /**
     * getAllDivisions is used to get the local list of all divisions
     * @return all divisions
     */
    public static ObservableList<Divisions> getAllDivisions(){
        return allDivisions;
    }

    /**
     * countrySelectionStateReturn is used to query the database for all states related to the country Id
     * @param id country id
     * @return state list
     * @throws SQLException if there is an SQL error
     */
    public static ObservableList<Divisions> countrySelectionStateReturn(int id) throws SQLException {

        ObservableList<Divisions> stateList = FXCollections.observableArrayList();

        Statement statement = DBConnect.getConnection().createStatement();
        String setAppointmentList = "SELECT * FROM client_schedule.first_level_divisions WHERE Country_ID="+id+"";
        ResultSet rs = statement.executeQuery(setAppointmentList);

        while (rs.next())
        {
            int divisionId = rs.getInt("Division_ID");
            String state = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
            Divisions division = new Divisions(divisionId, state, countryId);
            stateList.add(division);
        }
        return stateList;
    }


    /**
     * to String override used to present the divisionState in the comboBox list
     * @return state
     */
    @Override
    public String toString(){
        return(divisionState);
    }
}
