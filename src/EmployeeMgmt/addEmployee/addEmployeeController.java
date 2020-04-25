package EmployeeMgmt.addEmployee;

import EmployeeMgmt.MainController;
import com.github.javafaker.Faker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class addEmployeeController extends MainController {
    @FXML
    private JFXTextField idField;
    @FXML
    private JFXTextField fNameField;
    @FXML
    private JFXTextField lNameField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXTextField rankField;
    @FXML
    private JFXTextField dojField;
    @FXML
    private JFXTextField uniField;
    @FXML
    private JFXTextField fieldField;
    @FXML
    private JFXTextField titleField;
    @FXML
    private JFXTextField salaryField;

    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private AnchorPane maxPane;

    @FXML
    public void initialize() {
        super.initialize();
        showRandomPeeps();
    }

    @FXML
    private void showRandoms(MouseEvent event) {
        showRandomPeeps();
    }

    private void showRandomPeeps() {
        Faker faker = new Faker();
        String fakeID = faker.phoneNumber().subscriberNumber();
        String fakeFirst = faker.name().firstName();
        String fakeLast = faker.funnyName().name();
        //int y = ((int)(Math.random()*((100-1)+1))+1)*10000;
        String numString = Integer.valueOf(faker.number().numberBetween(1, 10) * 10000).toString();
        String fakeSalary = numString;
        String fakeAddress = faker.address().streetAddress();
        String fakeTitle = faker.job().title();
        String fakeField = faker.job().field();
        String fakeRank = faker.leagueOfLegends().rank();
        String fakedoj = faker.business().creditCardExpiry();
        String fakeUni = faker.university().name();
        idField.setText(fakeID);
        fNameField.setText(fakeFirst);
        lNameField.setText(fakeLast);
        rankField.setText(fakeRank);
        fieldField.setText(fakeField);
        titleField.setText(fakeTitle);
        addressField.setText(fakeAddress);
        dojField.setText(fakedoj);
        uniField.setText(fakeUni);
        salaryField.setText(fakeSalary);
    }

    public static String getRandomIntegerBetweenRange(int min, int max) {
        int x = (int) (Math.random() * ((max - min) + 1)) + min;
        return Integer.toString(x);
    }

    @FXML
    public void addEmployeeAction(MouseEvent event) {
        String ID = idField.getText();
        String fName = fNameField.getText();
        String lName = lNameField.getText();
        String rank = rankField.getText();
        String field = fieldField.getText();
        String title = titleField.getText();
        String address = addressField.getText();
        String doj = dojField.getText();
        String uni = uniField.getText();
        String salary = salaryField.getText();
        if (ID.isEmpty() || fName.isEmpty() || lName.isEmpty() || rank.isEmpty() ||
                field.isEmpty() || title.isEmpty() || address.isEmpty() || doj.isEmpty() || uni.isEmpty() || salary.isEmpty()) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Empty values!");
            al.setContentText("Please fill in all the fields!");
            al.showAndWait();
            return;
        }
        String qu = "INSERT INTO EMPLOYEE VALUES (" +
                "" + ID + "," +
                "'" + fName + "'," +
                "'" + lName + "'," +
                "'" + rank + "'," +
                "'" + address + "'," +
                "'" + doj + "'," +
                "'" + uni + "'," +
                "'" + field + "'," +
                "'" + title + "'," +
                "'" + salary + "'" +
                ")";

        if (handler.execAction(qu)) {

            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Entry added");
            al.setContentText("Successfully added Employee " + fName);
            al.showAndWait();

        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Entry not added");
            al.setContentText("Adding Employee " + fName + " failed.");
            al.showAndWait();
        }
    }

    @FXML
    public void cancel(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
