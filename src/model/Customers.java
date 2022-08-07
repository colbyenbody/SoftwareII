package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import utilities.*;

/**
 * Contact class is used to retrieve and present customer information pulled from the database
 */
public class Customers {
    private int customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phone;
    private int countryId;
    private String state;
    private int divisionId;
    private static final ObservableList<Customers> allCustomers = FXCollections.observableArrayList();

    /**
     * Customers constructor used to define the customer object that can be constructed.
     * @param customerId customer id
     * @param customerName customer name
     * @param address address
     * @param postalCode postal code
     * @param phone phone number
     * @param countryId country id
     * @param state state
     * @param divisionId division id
     */
    public Customers(int customerId, String customerName, String address, String postalCode, String phone, int countryId, String state, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.countryId = countryId;
        this.state = state;
        this.divisionId = divisionId;
    }

    /**
     * gets the division id
     * @return division id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * gets customer id
     * @return customer id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * gets the customer name
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * gets address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * gets postal code
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * gets phone number
     * @return phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * sets customer id
     * @param customerId customer id
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * sets customer name
     * @param customerName customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * sets customer address
     * @param address address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * sets postal code
     * @param postalCode postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * sets phone number
     * @param phone phone number
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * gets country id
     * @return country id
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * sets country id
     * @param countryId country id
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * gets state
     * @return state
     */
    public String getState() {
        return state;
    }

    /**
     * sets state
     * @param state state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * sets division id
     * @param divisionId division id
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * stores all customer information in an observable list
     * @throws SQLException if there is an sql error
     * @throws ClassNotFoundException if there is a class error
     */
    public static void setAllCustomers() throws SQLException, ClassNotFoundException {
        Statement stmt = DBConnect.getConnection().createStatement();
        String customerQuery = "SELECT * FROM client_schedule.customers INNER JOIN client_schedule.first_level_divisions ON customers.Division_ID=first_level_divisions.Division_ID;";

        ResultSet rs = stmt.executeQuery(customerQuery);

        while(rs.next())
        {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String postalCode = rs.getString("Postal_Code");
            String phone = rs.getString("Phone");
            int countryId = rs.getInt("Country_ID");
            String state = rs.getString("Division");
            int divisionId = rs.getInt("Division_ID");

            Customers cust = new Customers(customerId, customerName, address, postalCode, phone, countryId, state, divisionId);
            allCustomers.add(cust);

        }

    }

    /**
     * gets list of all customers
     * @return all customers
     */
    public static ObservableList<Customers> getAllCustomers()
    {
        return allCustomers;
    }

    /**
     * clears list of all customers
     */
    public static void clearAllCustomers()
    {
        allCustomers.clear();
    }

    /**
     * deletes single customer from database
     * @param customerId customer id
     * @return true if customer is deleted
     */
    public static boolean deleteCustomer(int customerId) {
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID = " + customerId;
            PreparedStatement ps = DBConnect.getConnection().prepareStatement(sql);

            int n = ps.executeUpdate();
            if (n == 1) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * adds a new customer to database
     * @param customerId customer id
     * @param customerName customer name
     * @param address address
     * @param postalCode postal code
     * @param phone phone number
     * @param countryId country id
     * @param state state
     * @param divisionId division
     * @throws SQLException if there is an SQL error
     */
    public static void addCustomer(int customerId, String customerName, String address, String postalCode, String phone, int countryId, String state, int divisionId) throws SQLException
    {
        Statement stmt = DBConnect.getConnection().createStatement();
        String addCustomer = "INSERT INTO customers VALUES("+customerId+", '"+customerName+"', '"+address+"', '"+postalCode+"', '"+phone+"', NOW(), 'script', NOW(), 'script', "+divisionId+")";
        stmt.executeUpdate(addCustomer);
        Customers addcustomer = new Customers(customerId, customerName, address, postalCode, phone, countryId, state, divisionId);
        allCustomers.add(addcustomer);
        System.out.println("Customer has been added");

    }

    /**
     * uses customer id to search appoinments
     * @param customerId customer id
     * @return true or false
     * @throws SQLException if there is an SQL error
     */
    public static boolean searchForAppointments(int customerId) throws SQLException {

        Statement stmt = DBConnect.getConnection().createStatement();
        String appointmentQuery = "SELECT * FROM client_schedule.appointments WHERE Customer_ID="+customerId+"";
        ResultSet rs = stmt.executeQuery(appointmentQuery);

        if(rs.next())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * to string override used to display the customer name
     * @return customer name
     */
    @Override
    public String toString(){
        return(customerName);
    }

}
