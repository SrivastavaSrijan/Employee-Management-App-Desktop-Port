module com.SrijanSrivastava {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.fontawesomefx.controls;
    requires de.jensd.fx.fontawesomefx.fontawesome;
    requires de.jensd.fx.fontawesomefx.commons;
    requires com.jfoenix;
    requires java.xml;
    requires org.apache.derby.engine;
    requires org.apache.derby.client;
    requires org.apache.derby.commons;
    requires org.apache.derby.tools;
    requires java.sql;
    requires java.desktop;
    requires javafaker;
    requires org.yaml.snakeyaml;
    requires org.apache.commons.lang3;
    opens EmployeeMgmt.displayBook to javafx.fxml;
    opens EmployeeMgmt.addEmployee to javafx.fxml;
    opens EmployeeMgmt.mainUI to javafx.fxml;
    opens EmployeeMgmt.loginUI to javafx.fxml;
    opens EmployeeMgmt.editEmployee to javafx.fxml;
    opens EmployeeMgmt to javafx.fxml;

    exports EmployeeMgmt.mainUI;
    exports EmployeeMgmt.addEmployee;
    exports EmployeeMgmt.displayBook;
    exports EmployeeMgmt.loginUI;
    exports EmployeeMgmt.editEmployee;
    exports EmployeeMgmt;


}