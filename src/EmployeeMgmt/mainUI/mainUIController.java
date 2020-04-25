package EmployeeMgmt.mainUI;

import EmployeeMgmt.MainController;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class mainUIController extends MainController {

    @FXML
    private JFXButton addButton;
    @FXML
    private JFXButton FAQButt;
    @FXML
    private FontAwesomeIconView FAQIcon;
    @FXML
    private StackPane stackPane;

    @FXML
    private void loadAddEmployee(MouseEvent event) {
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.setIconified(true);
        loadWindow("/EmployeeMgmt/addEmployee/addEmployee.fxml", thisStage);
    }

    @FXML
    private void loadDisplayEmployee(MouseEvent event) {
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.setIconified(true);
        loadWindow("/EmployeeMgmt/displayBook/displayBook.fxml", thisStage);
    }

    @FXML
    private void doEdit(MouseEvent event) {
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        thisStage.setIconified(true);
        loadWindow("/EmployeeMgmt/editEmployee/editEmployee.fxml", thisStage);
    }

    @FXML
    public void showFAQ(ActionEvent event) {
        Alert al = new Alert(Alert.AlertType.INFORMATION);
        al.initStyle(StageStyle.UNDECORATED);
        al.setContentText("Created by Srijan Srivastava \nMore available at srivastavasrijan.github.io");
        al.setHeaderText("About me!");
        al.showAndWait();


    }
}
