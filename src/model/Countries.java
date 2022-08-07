package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DBConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

    /**
     * Countries class is used to retrieve and present Country information pulled from the database
     */
    public class Countries {

        private int countryId;
        private String country;
        private static ObservableList<model.Countries> allCountries = FXCollections.observableArrayList();

        /**
         * stores country object information
         * @param countryId country id
         * @param country country
         */
        public Countries(int countryId, String country) {
            this.countryId = countryId;
            this.country = country;
        }

        /**
         * gets the country id
         * @return country id
         */
        public int getCountryId() {
            return countryId;
        }

        /**
         * sets the country id
         * @param countryId country id
         */
        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }

        /**
         * gets the country name
         * @return country
         */
        public String getCountry() {
            return country;
        }

        /**
         * sets the Country Name
         * @param country country
         */
        public void setCountry(String country) {
            this.country = country;
        }

        /**
         * Stores all country information in an observable list
         * @throws SQLException if there is an sql error
         */
        public static void setAllCountries() throws SQLException
        {
            Statement stmt = DBConnect.getConnection().createStatement();
            String countryQuery = "SELECT * FROM client_schedule.countries";

            ResultSet rs = stmt.executeQuery(countryQuery);

            while(rs.next())
            {
                int countryId = rs.getInt("Country_ID");
                String country = rs.getString("Country");

                model.Countries newCountry = new model.Countries(countryId, country);
                allCountries.add(newCountry);
            }
        }

        /**
         * returns all countries
         * @return all countries
         */
        public static ObservableList<model.Countries> getAllCountries(){

            return allCountries;
        }

        /**
         * to string override displays country name
         * @return country
         */
        @Override
        public String toString(){
            return(country);
        }
    }

