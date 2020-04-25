package EmployeeMgmt.loginUI;

import EmployeeMgmt.MainController;
import EmployeeMgmt.displayBook.displayBookController;
import EmployeeMgmt.mainUI.mainUIController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


public class loginUIController extends MainController {
    @FXML
    private AnchorPane maxPane;
    @FXML
    private JFXButton LoginButton;
    @FXML
    private JFXButton ForgotPassButton;
    @FXML
    private JFXTextField UsernameField;
    @FXML
    private JFXPasswordField PasswordField;
    @FXML
    private JFXTextField NewUsernameField;
    @FXML
    private JFXPasswordField NewPasswordField;
    @FXML
    private FontAwesomeIconView PasswordIcon;
    @FXML
    private FontAwesomeIconView UserIcon;
    @FXML
    private JFXButton save;
    ObservableList<User> list = FXCollections.observableArrayList();


    private class User {
        private String user;
        private String pswd;

        public User(String user, String psswd) {
            this.user = user;
            this.pswd = psswd;
        }

        public String getUser() {
            return user;
        }

        public String getPswd() {
            return pswd;
        }
    }

    public void loadWindow(String loc, Stage MainStage) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            Image icon = new Image(getClass().getResourceAsStream("/EmployeeMgmt/resources/png.png"));
            stage.getIcons().add(icon);
            stage.setScene(new Scene(parent));
            stage.show();
            //TODO EmployeeMgmt.setStageIcon(stage);
        } catch (IOException ex) {
            Logger.getLogger(mainUIController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void loadData() {
        list.clear();
        String qu = "SELECT * FROM USERS";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                String userx = rs.getString("users");
                String pswdx = rs.getString("pswd");
                list.add(new User(userx, pswdx));
            }
        } catch (SQLException e) {
            Logger.getLogger(displayBookController.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public String setPassword(String password) {
        if (password.length() < 16) {
            return sha256(password);
        } else
            return password;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        loadData();
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        String uEnter = UsernameField.getText();
        String pEnter = sha256(PasswordField.getText());
        Optional<User> thisUser =
                list.stream().filter(User -> User.getUser().equals(uEnter)).findFirst();

        if (thisUser.isPresent() && !uEnter.isEmpty() && !pEnter.isEmpty()) {
            if (!(setPassword(thisUser.get().getPswd()).equals(pEnter))) {
                Alert al = new Alert(Alert.AlertType.ERROR);
                al.initStyle(StageStyle.UTILITY);
                al.setHeaderText("Password incorrect");
                al.setContentText("Password incorrect for user " + uEnter + " \nPlease try again.");
                al.showAndWait();
                PasswordField.setUnFocusColor(Color.RED);
                PasswordField.setFocusColor(Color.RED);
            } else {
                super.FadeOut();
                thisStage.close();
                loadWindow("/EmployeeMgmt/mainUI/main.fxml", thisStage);
            }
        } else if (!uEnter.isEmpty() && !pEnter.isEmpty()) {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION, "Add " + uEnter + " ?", ButtonType.YES, ButtonType.NO);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("User not found!");
            al.setContentText(uEnter + " does not exist." + "\nWould you like to create a user with this name?");
            al.showAndWait();
            if (al.getResult() == ButtonType.YES) {
                new FadeTransition();
                thisStage.close();
                loadWindow("newUser.fxml", thisStage);
            }
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Empty values!");
            al.setContentText("You've entered empty values. Please try again using valid values");
            al.showAndWait();
            PasswordField.setUnFocusColor(Color.RED);
            PasswordField.setFocusColor(Color.RED);
            UsernameField.setUnFocusColor(Color.RED);
            UsernameField.setFocusColor(Color.RED);
        }
    }

    @FXML
    private void handleHint(ActionEvent event) {
//todo

    }

    private void addUser(String user, String pswd) {
        loadData();
        pswd = setPassword(pswd);
        String qu = "INSERT INTO USERS VALUES (" +
                "'" + user + "'," +
                "'" + pswd + "'" +
                ")";
        if (!handler.execAction(qu)) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Entry not added");
            al.setContentText("Adding user " + user + " failed.");
            al.showAndWait();
        } else {
            Alert al = new Alert(Alert.AlertType.INFORMATION, "New User", new ButtonType("Proceed"));
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("User successfully added!");
            al.setContentText("User " + user + " was added to our records.");
            al.showAndWait();

        }
    }

    @FXML
    private void handleSave(ActionEvent event) {
        loadData();
        String newUser = NewUsernameField.getText();
        String newPassword = NewPasswordField.getText();
        Stage thisStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (!newUser.isEmpty() && !newPassword.isEmpty()) {
            setPassword("newPassword");
            addUser(newUser, newPassword);
            FadeOut();
            thisStage.close();
            loadWindow("loginUI.fxml", thisStage);
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Empty values!");
            al.setContentText("You've entered empty values. Please try again using valid values");
            al.showAndWait();
            NewUsernameField.setUnFocusColor(Color.RED);
            NewUsernameField.setFocusColor(Color.RED);
            NewPasswordField.setUnFocusColor(Color.RED);
            NewPasswordField.setFocusColor(Color.RED);
        }
    }

    @FXML
    private void doHint(MouseEvent event) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.initStyle(StageStyle.UTILITY);
        a.setContentText("The default values are 'a' and 'a' respectively, which will be changed in future updates.");
        a.setHeaderText("Admin Defaults");
        PauseTransition delay = new PauseTransition(Duration.seconds(1));
        delay.setOnFinished(e -> a.hide());
        a.show();
        loadData();
        delay.play();
    }
}
