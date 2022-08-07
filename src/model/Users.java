package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DBConnect;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Contact class is used to retrieve and present user information pulled from the database
 */
public class Users {
    private int userID;
    private String username;
    private String password;
    private static ObservableList<Users> allUsers = FXCollections.observableArrayList();

    /**
     * @param userID User ID
     * @param username User Name
     * @param password Password
     */
    public Users(int userID, String username, String password) {
        this.userID = userID;
        this.username = username;
        this.password = password;
    }


    /**
     * @param user_name User Name
     * @param user_id User ID
     */
    public Users(String user_name, int user_id) {
    }


    /**
     * gets user id
     * @return user ID
     */
    public  int getUserID(){return userID;}
    /**
     * setUserID is used to set the User ID
     * @param userID user id
     */
    public void setUserID(int userID){this.userID = userID;}
    /**
     * gets username
     * @return username
     */
    public  String getUsername(){return username;}
    /**
     * sets username
     * @param username username
     */
    public void setUsername(String username){this.username = username;}
    /**
     * getPassword is used to retrieve the password
     * @return password
     */
    public String getPassword(){return password;}
    /**
     * sets password
     * @param password password
     */
    public void setPassword(String password){this.password = password;}

    /**
     * gets all users from observable list
     * @return all users
     */
    public static ObservableList<Users> getAllUsers()
    {
        return allUsers;
    }

    /**
     * This method retrieves a list of users from the Database
     * @throws SQLException if there is an SQL error
     * @throws Exception if there is an error
     */
    public static void setAllUsers () throws SQLException, Exception{

        Statement stmt = DBConnect.getConnection().createStatement();
        String userQuery = "select * from users";
        ResultSet rs = stmt.executeQuery(userQuery);

        while(rs.next()){
            int userID = rs.getInt("User_ID");
            String user = rs.getString("User_Name");
            String pass = rs.getString("Password");

            Users userResult = new Users(userID, user, pass);

            allUsers.add(userResult);

        }

}
    /**
     * gets currently logged in username
     * @return username
     * @throws SQLException if there is an SQL error
     */
    public static String getLoggedName () throws SQLException{
        String user = " ";
        String sql = "SELECT DISTINCT User_Name FROM users";
        PreparedStatement ps = DBConnect.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            user = rs.getString("User_Name");
        }
        return user;
    }

    /**
     * returns a date instant to localDate
     * @param dateToConvert date
     * @return convert date
     */
    public static LocalDate convertToLocalDateViaInstant(Date dateToConvert)
    {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * @return username string
     */
    @Override
    public String toString() {
        return username;
    }
}
