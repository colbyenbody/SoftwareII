package model;


/**
 * Contact class is used to retrieve and present report information pulled from the database
 */
public class Reports {
    int date;
    String type;
    String location;
    int contactID;
    String title;
    String description;
    int total;

    /**
     * @param date month and year
     * @param type type and contact
     * @param total total
     * @param description description
     * @param title title
     * @param location location
     * @param contactID contact id
     */

    public Reports(int date, String type, String location, int contactID, String description, String title, int total) {
        this.date = date;
        this.type = type;
        this.total = total;
        this.contactID = contactID;
        this.title = title;
        this.description = description;
        this.location = location;
    }

    /**
     * gets date
     * @return date
     */
    public int getDate() {return date;}

    /**
     * sets the date
     * @param date date
     */
    public void setDate(int date) {this.date = date;}

    /**
     * gets contact id
     * @return contactID
     */
    public int getContactID() {return contactID;}
    /**
     * sets contact id
     * @param contactID contact id
     */
    public void setContactID(int contactID) {this.contactID = contactID;}

    /**
     * gets location
     * @return location
     */
    public String getLocation() {return location;}
    /**
     * sets location
     * @param location location
     */
    public void setLocation(String location) {this.location = location;}

    /**
     * gets description
     * @return description
     */
    public String getDescription() {return description;}
    /**
     * sets description
     * @param description description
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * gets title
     * @return title
     */
    public String getTitle() {return title;}
    /**
     * sets title
     * @param title title
     */
    public void setTitle(String title) {this.title = title;}

    /**
     * gets type
     * @return type
     */
    public String getType() {return type;}
    /**
     * sets type
     * @param type tyoe
     */
    public void setType(String type) {this.type = type;}

    /**
     * gets total
     * @return total
     */
    public int getTotal() {return total;}
    /**
     * sets total
     * @param total total
     */
    public void setTotal(int total) {this.total = total;}
}

