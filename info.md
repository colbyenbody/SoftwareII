Scheduling Application - C195 Performance Evaluation

    Author: Colby Enbody
    Email: cenbod3@wgu.edu
    Submission Date: 3/20/2022

###Development Environment:

    IDE: IntelliJ IDEA 2021.3 (Community Edition) Build #IC-213.5744.223, built on November 26, 2021,  Runtime version: 11.0.13+7-b1751.19 amd64
    JDK: 17.0.2
    JavaFX SDK: openjfx-17.0.2
    JDBC: mysql_connector_java_8.0.28

###Purpose of application

    A multi-lingual GUI for managing appointments with customers via a database

###Additional Report: 

    The third report shows total appointments for each country

###Lambda examples:

    Line 66 of AreaReport controller
    Line 65 of ContactReport controller

###Javadoc

    Javadoc index.html is under c195ce/javadoc

###Directions to Run:

    Open the folder in IntelliJ idea and update the path for your JavaFX SDK, JDK, and JDBC.
    Add javafx lib to project structure
    Add mysql-connector to project structure
    Update PATH_TO_FX under path variable settings
    under run, edit configuration and add --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics to VM options
