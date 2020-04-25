package EmployeeMgmt;

import EmployeeMgmt.databaseHandler.DatabaseHandler;
import EmployeeMgmt.displayBook.displayBookController;
import EmployeeMgmt.editEmployee.editEmployeeController;
import EmployeeMgmt.mainUI.mainUIController;
import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.security.MessageDigest.getInstance;


public class MainController {

    public DatabaseHandler handler;
    @FXML
    public JFXCheckBox checkBox;
    @FXML
    public AnchorPane maxPane;

    @FXML
    public void initialize() {
        handler = DatabaseHandler.getInstance();
        checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                if (maxPane.getStylesheets().size() > 1)
                    maxPane.getStylesheets().clear();
                maxPane.getStylesheets().add(getClass().getResource("/EmployeeMgmt/doDark.css").toExternalForm());
            } else {
                if (maxPane.getStylesheets().size() > 1)
                    maxPane.getStylesheets().clear();
                maxPane.getStylesheets().add(getClass().getResource("/EmployeeMgmt/unDark.css").toExternalForm());
            }
        });
        FadeIn();
    }

    @FXML
    public void min(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    public void max(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (!stage.isAlwaysOnTop())
            stage.setAlwaysOnTop(true);
        else
            stage.setAlwaysOnTop(false);
    }

    @FXML
    public void close(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FadeOut();
        stage.close();
    }

    public void loadWindow(String loc, Stage MainStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            Image icon = new Image(getClass().getResourceAsStream("/EmployeeMgmt/resources/png.png"));
            stage.getIcons().add(icon);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            MainStage.setIconified(false);
            //TODO EmployeeMgmt.setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(mainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadWindowandControl(String loc, Stage MainStage, displayBookController.Employee emp) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(loc));
            editEmployeeController newContoller = new editEmployeeController(emp);
            loader.setController(newContoller);
            Parent parent = loader.load();
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            Image icon = new Image(getClass().getResourceAsStream("/EmployeeMgmt/resources/png.png"));
            stage.getIcons().add(icon);
            stage.setScene(new Scene(parent));
            stage.showAndWait();
            MainStage.setIconified(false);
            //TODO EmployeeMgmt.setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(mainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void FadeOut() {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(maxPane);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }

    public void FadeIn() {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(500));
        fade.setNode(maxPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


}
