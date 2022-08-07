package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Contact class is used to retrieve and present appointment information pulled from the database
 */
public class Appointments {

    private int appointmentID;
    private int apptCustomerID;
    private int apptUserID;
    private int apptContactID;
    private String description;
    private String location;
    private String title;
    private String type;
    private String start;
    private String end;
    private String contactName;
    private static ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointments> fifminAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointments> weeklyAppointments = FXCollections.observableArrayList();
    private static ObservableList<Appointments> monthlyAppointments = FXCollections.observableArrayList();


    /**
     * stores Appointment object information
     * @param appointmentID appointment id
     * @param apptCustomerID customer id
     * @param apptUserID user id
     * @param apptContactID contact id
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start date
     * @param end end date
     */
    public Appointments(int appointmentID, int apptCustomerID, int apptUserID, int apptContactID, String title, String description, String location, String type, String start, String end) {
        this.appointmentID = appointmentID;
        this.apptCustomerID = apptCustomerID;
        this.apptUserID = apptUserID;
        this.apptContactID = apptContactID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
    }

    /**
     * Gets the CustomerID
     * @return apptCustomerID
     */
    public int getApptCustomerID() {
        return apptCustomerID;
    }
    /**
     * Sets the appt customerId
     * @param apptCustomerID customer id
     */
    public void setApptCustomerID(int apptCustomerID) {
        this.apptCustomerID = apptCustomerID;
    }

    /**
     * gets the user id for appointment
     * @return user id
     */
    public int getApptUserID() {
        return apptUserID;
    }

    /**
     * sets the user id
     * @param apptUserID user id
     */
    public void setApptUserID(int apptUserID) {
        this.apptUserID = apptUserID;
    }

    /**
     * gets the contact id
     * @return contact id
     */
    public int getApptContactID() {
        return apptContactID;
    }

    /**
     * sets the contact id
     * @param apptContactID contact id
     */
    public void setApptContactId(int apptContactID) {
        this.apptContactID = apptContactID;
    }

    /**
     * gets the appointment id
     * @return appointment id
     */
    public int getAppointmentId() {
        return appointmentID;
    }

    /**
     * sets the appointment id
     * @param appointmentId appointment id
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentID = appointmentID;
    }

    /**
     * gets the title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * sets the title
     * @param title title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * gets the description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the  description
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * gets the location
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * sets the location
     * @param location lcoation
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * gets the type
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * sets the appointment type
     * @param type type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * gets the start time
     * @return start time
     */
    public String getStart() {
        return start;
    }

    /**
     * gets the end time
     * @return end time
     */
    public String getEnd() {
        return end;
    }

    /**
     * sets the end time
     * @param end end time
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * gets the contact name
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * sets the  contact name
     * @param contactName contact name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Returns all appointments from observable list
     * @return all appointments
     */
    public static ObservableList<Appointments> getAllAppointments()
    {
        return allAppointments;
    }

    /**
     * Returns list of appointments within 15 minutes
     * @return fifminAppointments
     */
    public static ObservableList<Appointments> fifminlist()
    {
        return fifminAppointments;
    }

    /**
     * Returns weekly appointments from observable list
     * @return weeklyAppointments
     */
    public static ObservableList<Appointments> getWeeklyAppointments()
    {
        return weeklyAppointments;
    }

    /**
     * Returns monthly appointments from observable list
     * @return monthlyAppointments
     */
    public static ObservableList<Appointments> getMonthlyAppointments()
    {
        return monthlyAppointments;
    }


    /**
     * loads all appointments to list
     * @throws SQLException if there is an sql error
     * @throws ClassNotFoundException if class error
     */
    public static void setAllAppointments() throws SQLException, ClassNotFoundException {

        Statement stmt = DBConnect.getConnection().createStatement();
        String appointmentQuery = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID FROM appointments";
        ResultSet rs = stmt.executeQuery(appointmentQuery);


        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String convertedStart = utilities.timeConverter.toLocal(start);
            String end = rs.getString("End");
            String convertedEnd = utilities.timeConverter.toLocal(end);
            int apptCustomerID = rs.getInt("Customer_ID");
            int apptUserID = rs.getInt("User_ID");
            int apptContactID = rs.getInt("Contact_ID");

            Appointments apptList = new Appointments(appointmentID, apptCustomerID, apptUserID, apptContactID, title, description, location, type, convertedStart, convertedEnd);

            allAppointments.add(apptList);

        }

    }
    /**
     * clears all appointment for observable list
     */
    public static void clearAllAppointments(){allAppointments.clear();}

    /**
     * loads all appointments within 15 minutes to list
     * @throws SQLException if there is an sql error
     * @throws ClassNotFoundException if class error
     */
    public static void fifMinChecker() throws SQLException, ClassNotFoundException {

        Statement stmt = DBConnect.getConnection().createStatement();
        String appointmentQuery = "SELECT * from appointments WHERE Start BETWEEN NOW() AND DATE_ADD(NOW(), INTERVAL 15 MINUTE)";
        ResultSet rs = stmt.executeQuery(appointmentQuery);


        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String convertedStart = utilities.timeConverter.toLocal(start);
            String end = rs.getString("End");
            String convertedEnd = utilities.timeConverter.toLocal(end);
            int apptCustomerID = rs.getInt("Customer_ID");
            int apptUserID = rs.getInt("User_ID");
            int apptContactID = rs.getInt("Contact_ID");

            Appointments apptFifList = new Appointments(appointmentID, apptCustomerID, apptUserID, apptContactID, title, description, location, type, convertedStart, convertedEnd);

            fifminAppointments.add(apptFifList);

        }

    }
    /**
     * loads all appointments within a week to list
     * @throws SQLException if there is an sql error
     * @throws ClassNotFoundException if class error
     */
    public static void weeklyReport() throws SQLException, ClassNotFoundException {

        Statement stmt = DBConnect.getConnection().createStatement();
        String appointmentQuery = "select * from appointments where Start between now() and (select adddate(now(), interval 7 day))";
        ResultSet rs = stmt.executeQuery(appointmentQuery);


        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String convertedStart = utilities.timeConverter.toLocal(start);
            String end = rs.getString("End");
            String convertedEnd = utilities.timeConverter.toLocal(end);
            int apptCustomerID = rs.getInt("Customer_ID");
            int apptUserID = rs.getInt("User_ID");
            int apptContactID = rs.getInt("Contact_ID");

            Appointments weeklyAppointmentList = new Appointments(appointmentID, apptCustomerID, apptUserID, apptContactID, title, description, location, type, convertedStart, convertedEnd);

            weeklyAppointments.add(weeklyAppointmentList);

        }

    }
    /**
     * loads all appointments within a month to list
     * @throws SQLException if there is an sql error
     * @throws ClassNotFoundException if class error
     */
    public static void monthlyReport() throws SQLException, ClassNotFoundException {

        Statement stmt = DBConnect.getConnection().createStatement();
        String appointmentQuery = "select * from appointments where month(Start)=month(now())";
        ResultSet rs = stmt.executeQuery(appointmentQuery);


        while (rs.next()) {
            int appointmentID = rs.getInt("Appointment_ID");
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            String start = rs.getString("Start");
            String convertedStart = utilities.timeConverter.toLocal(start);
            String end = rs.getString("End");
            String convertedEnd = utilities.timeConverter.toLocal(end);
            int apptCustomerID = rs.getInt("Customer_ID");
            int apptUserID = rs.getInt("User_ID");
            int apptContactID = rs.getInt("Contact_ID");

            Appointments weeklyAppointmentList = new Appointments(appointmentID, apptCustomerID, apptUserID, apptContactID, title, description, location, type, convertedStart, convertedEnd);

            monthlyAppointments.add(weeklyAppointmentList);

        }

    }

    /**
     * creates a list that groups appointments by type and month
     * @return monthly list
     * @throws SQLException if there is an SQL error.
     * @throws Exception if there is an SQL error. */

    public static ObservableList<Reports> monthlyTypeReport() throws SQLException, Exception {
        ObservableList<Reports> monthlyList = FXCollections.observableArrayList();
        Statement stmt = DBConnect.getConnection().createStatement();
        String monthlytypereport = "Select month(start) as month, type, Location, Contact_ID, Description, Title, count(*) as total from appointments GROUP BY month";
        ResultSet rs = stmt.executeQuery(monthlytypereport);
        while (rs.next()) {
            Reports row = new Reports (rs.getInt("month"),rs.getString("type"),rs.getString("location"),rs.getInt("Contact_ID"), rs.getString("description"), rs.getString("title"), rs.getInt("total"));
            monthlyList.add(row);
        }
        return monthlyList;
    }

    /**
     * creates a list that groups appointments by type and month
     * @return monthly list
     * @throws SQLException if there is an SQL error.
     * @throws Exception if there is an SQL error. */

    public static ObservableList<Reports> typeTypeReport() throws SQLException, Exception {
        ObservableList<Reports> typeList = FXCollections.observableArrayList();
        Statement stmt = DBConnect.getConnection().createStatement();
        String monthlytypereport = "Select month(start) as month, type, Location, Contact_ID, Description, Title, count(*) as total from appointments GROUP BY type";
        ResultSet rs = stmt.executeQuery(monthlytypereport);
        while (rs.next()) {
            Reports row = new Reports (rs.getInt("month"),rs.getString("type"),rs.getString("location"),rs.getInt("Contact_ID"), rs.getString("description"), rs.getString("title"), rs.getInt("total"));
            typeList.add(row);
        }
        return typeList;
    }



    /**
     * adds a new appointment to database, updates appointments
     * @param appointmentId appointment id
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start  start
     * @param end end
     * @param customerId customer id
     * @param userId user id
     * @param contactId contact id
     * @throws SQLException if there is an sql error
     * @throws ClassNotFoundException if class error
     */
    public static void addAppointment(int appointmentId, String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId) throws SQLException, ClassNotFoundException {
        Statement stmt = DBConnect.getConnection().createStatement();
        String aptUpdate = "INSERT INTO client_schedule.appointments VALUES("+appointmentId+", '"+title+"', '"+description+"', '"+location+"', '"+type+"', '"+start+"', '"+end+"', NOW(), 'script', NOW(), 'script', "+customerId+", "+userId+", "+contactId+")";
        stmt.executeUpdate(aptUpdate);
        allAppointments.clear();
        setAllAppointments();

    }

    /**
     * uses appointment id to delete single appointment
     * @param id appointment id
     * */
    public static void deleteSingleAppointment(int id) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID = " + id;
            PreparedStatement ps = DBConnect.getConnection().prepareStatement(sql);
            int n = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * checks for conflicting appointments
     * @param time start time
     * @param endTime end time
     * @param id customer id
     * @return overlap
     * @throws SQLException if there is an sql error
     */
    public static boolean checkForOverlap(String time, String endTime, int id) throws SQLException {
        boolean overlap = false;
        Statement stmt = DBConnect.getConnection().createStatement();
        String conflict =  "SELECT * FROM client_schedule.appointments WHERE Customer_ID="+id+" AND (('"+time+"' BETWEEN Start AND End) OR ('"+endTime+"' BETWEEN Start AND End)) OR (( Start BETWEEN '"+time+"' AND '"+endTime+"') OR ( End BETWEEN '"+time+"' AND '"+endTime+"'))";
        ResultSet rs = stmt.executeQuery(conflict);

        if(rs.next()) {
            overlap = true;
        }

        return  overlap;
    }

    /**
     * checks for conflicting appointments when modifying customer
     * @param time start time
     * @param endTime end time
     * @param id customer id
     * @return overlap
     * @throws SQLException if there is an sql error
     */
    public static boolean checkForOverlapModify(String time, String endTime, int id) throws SQLException {
        boolean overlap = false;
        Statement stmt = DBConnect.getConnection().createStatement();
        String conflict =  "SELECT * FROM client_schedule.appointments WHERE Customer_ID<>"+id+" AND (('"+time+"' BETWEEN Start AND End))";
        ResultSet rs = stmt.executeQuery(conflict);

        if(rs.next()) {
            overlap = true;
        }

        return  overlap;
    }

    /**
     * updates existing appointment in database
     * @param appointmentId appointment id
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start
     * @param end end
     * @param customerId customer id
     * @param userId user id
     * @param contactId contact id
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if class error
     */
    public static void updateAppointment(int appointmentId, String title, String description, String location, String type, String start, String end, int customerId, int userId, int contactId) throws SQLException, ClassNotFoundException {
        Statement stmt = DBConnect.getConnection().createStatement();
        String aptUpdate = "UPDATE client_schedule.appointments SET Title='"+title+"', Description='"+description+"', Location='"+location+"', Type='"+type+"', Start='"+start+"', End='"+end+"', Customer_ID="+customerId+", User_ID="+userId+", Contact_ID="+contactId+" WHERE Appointment_ID="+appointmentId+"";
        stmt.executeUpdate(aptUpdate);
        allAppointments.clear();
        setAllAppointments();
        System.out.println("Appointment updated");

    }
}


