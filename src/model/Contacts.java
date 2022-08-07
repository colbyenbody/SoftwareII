package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Contact class is used to retrieve and present contact information pulled from the database
 */
public class Contacts {
    /**
     * contains contact information
     */
        private int contactID;
        private String contactName;
        static ObservableList<Contacts> contactList = FXCollections.observableArrayList();

        /**
         * stores contact object information
         * @param contactID Contact ID
         * @param contactName Contact Name

         */
        public Contacts (int contactID, String contactName) {
            this.contactID = contactID;
            this.contactName = contactName;
        }

        /**
         * Gets contact id
         * @return Returns contact id
         */
        public int getContactID() {
            return contactID;
        }

        /**
         * Sets contact id
         * @param contactID contact id
         */
        public void setContactID(int contactID) {contactID = contactID; }

        /**
         * Gets contact name
         * @return Returns contact name
         */
        public String getContactName() {
            return contactName;
        }

        /**
         * Sets contact name
         * @param contactName contact name
         */
        public void setContactName(String contactName) {
            contactName = contactName;
        }


    /**
     * Stores all contact information in an observable list
     * @throws SQLException if there is an SQL error
     */
    public static void setContacts() throws SQLException {
        Statement stmt = DBConnect.getConnection().createStatement();
        String setContactList = "SELECT * FROM contacts";
        ResultSet rs = stmt.executeQuery(setContactList);
        while (rs.next()) {
            int contactID = rs.getInt("Contact_ID");
            String contactName = rs.getString("Contact_Name");

            Contacts getContacts = new Contacts(contactID, contactName);
            contactList.add(getContacts);
        }
    }

    /**
     * gets a list of all contacts
     * @return contact list
     */
    public static ObservableList<Contacts> getAllContacts()
    {
        return contactList;
    }

    /**
     * returns contact id via contact name
     * @param name contact name
     * @return contact id
     * @throws SQLException if there is an SQL error
     */
    public int getContactIDByName(String name) throws SQLException {
        int contactID = 0;
        PreparedStatement statement = DBConnect.getConnection().prepareStatement("SELECT Contact_ID FROM contacts WHERE Contact_Name = ?");
        statement.setString(1, name);
        ResultSet resultSet = statement.executeQuery(String.valueOf(statement));
        while (resultSet.next()) {
            contactID = resultSet.getInt("Contact_ID");
        }
        return contactID;
    }

    /**
     * returns contact name via contact id
     * @param ID contact id
     * @return contact name
     * @throws SQLException if there is an SQL error
     * @throws ClassNotFoundException if there is a class error
     */
    public String getContactNameByID(int ID) throws SQLException, ClassNotFoundException {
        PreparedStatement statement = DBConnect.getConnection().prepareStatement("SELECT Contact_Name FROM contacts WHERE Contact_ID = ?");
        statement.setInt(1, ID);
        ResultSet resultSet = statement.executeQuery(String.valueOf(statement));
        while (resultSet.next()) {
            contactName = resultSet.getString("Contact_Name");
        }
        return contactName;
    }

    /**
     * to string override to display the country name
     * @return contact name
     */
        @Override
        public String toString() {
            return contactName;
        }
    }

