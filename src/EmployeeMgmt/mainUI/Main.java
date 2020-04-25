package EmployeeMgmt.mainUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/EmployeeMgmt/loginUI/loginUI.fxml"));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        Scene primaryScene = new Scene(root);
        Image icon = new Image(getClass().getResourceAsStream("/EmployeeMgmt/resources/png.png"));
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(primaryScene);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
