package EmployeeMgmt.databaseHandler;

import EmployeeMgmt.MainController;
import EmployeeMgmt.displayBook.displayBookController;

import javax.swing.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseHandler {
    private static DatabaseHandler handler = null;
    private static final String DB_URL = "jdbc:derby:database;create=true";

    private static Connection conn = null;
    private static Statement stmt = null;

    public DatabaseHandler() {
        createConnection();
        setupEmployeeTable();
        setupUserTable();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null)
            handler = new DatabaseHandler();
        return handler;

    }

    void createConnection() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").getDeclaredConstructor().newInstance();
            conn = DriverManager.getConnection(DB_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setupEmployeeTable() {
        String TABLE_NAME = "EMPLOYEE";
        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME + " already exists. Ready for go.");
            } else {
                String createEmp = ("CREATE TABLE " + TABLE_NAME + "("
                        + "         id INTEGER primary key,\n"
                        + "          first_name varchar(500),\n"
                        + "          last_name varchar(500),\n"
                        + "          rank varchar(500),\n"
                        + "          address varchar(500),\n"
                        + "          doj varchar(500),\n"
                        + "          uni varchar(500),\n"
                        + "          field varchar(500),\n"
                        + "          position varchar(500),\n"
                        + "          salary varchar(500)"
                        + " )");

                stmt.execute(createEmp);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "....setupDatabases");
        }

    }


    void setupUserTable() {
        String TABLE_NAME2 = "USERS";

        try {
            stmt = conn.createStatement();
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME2.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table " + TABLE_NAME2 + " already exists. Ready for go.");
            } else {
                String createUsers = ("CREATE TABLE " + TABLE_NAME2 + "("
                        + "         users varchar(200) primary key,\n"
                        + "          pswd varchar(500)"
                        + " )");
                stmt.execute(createUsers);
                String qu = "INSERT INTO USERS VALUES (" +
                        "'" + "a" + "',"
                        + "'" + MainController.sha256("a") + "')";
                handler = getInstance();
                handler.execAction(qu);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage() + "....setupDatabases");
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Exception at execQuery:DataHandler!" + e.getLocalizedMessage());
            return null;
        }
        return result;
    }

    public boolean execAction(String query) {
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), " Error.", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean deleteEmp(displayBookController.Employee employee) {
        try {
            String qu = "DELETE FROM EMPLOYEE WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(qu);
            preparedStatement.setString(1, employee.getId().toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

    }

    public boolean editEmp(String qu) {
        try {

            PreparedStatement preparedStatement = conn.prepareStatement(qu);
            //preparedStatement.setString(1, employee.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

    }

}
