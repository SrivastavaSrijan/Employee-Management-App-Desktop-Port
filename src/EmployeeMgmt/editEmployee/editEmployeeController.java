package EmployeeMgmt.editEmployee;

import EmployeeMgmt.MainController;
import EmployeeMgmt.displayBook.displayBookController;
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

public class editEmployeeController extends MainController {
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
    displayBookController.Employee employee;

    public editEmployeeController(displayBookController.Employee emp) {
        this.employee = emp;
    }

    public void initialize() {
        super.initialize();
        showRelevantDetails(employee);

    }

    @FXML
    public void showRelevantDetails(displayBookController.Employee employee) {
        idField.setText(employee.getId().toString());
        idField.setDisableAnimation(true);
        fNameField.setText(employee.getfName());
        lNameField.setText(employee.getlName());
        rankField.setText(employee.getRank());
        fieldField.setText(employee.getField());
        titleField.setText(employee.getPosition());
        addressField.setText(employee.getAddress());
        dojField.setText(employee.getDoj());
        uniField.setText(employee.getUni());
        salaryField.setText(employee.getSalary());
    }

    @FXML
    private void showRandoms(MouseEvent event) {
        showRandomPeeps();
    }

    private void showRandomPeeps() {
        Faker faker = new Faker();
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
    public void editEmployeeAction(MouseEvent event) {
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

        String set =
                "          first_name='" + fName + "'"
                        + "          ,last_name='" + lName + "'"
                        + "          ,rank='" + rank + "'"
                        + "          ,address='" + address + "'"
                        + "          ,doj='" + doj + "'"
                        + "          ,uni='" + uni + "'"
                        + "          ,field='" + field + "'"
                        + "          ,position='" + title + "'"
                        + "          ,salary='" + salary + "'";
        String qu = "UPDATE EMPLOYEE SET " + set + " WHERE id=" + ID;
        //System.out.println(qu);
        if (handler.editEmp(qu)) {
            Alert al = new Alert(Alert.AlertType.INFORMATION);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Entry edited!");
            al.setContentText("Successfully edited details for Employee " + fName);
            al.showAndWait();
            super.close(event);
        } else {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Entry not edited");
            al.setContentText("Editing Employee " + fName + " failed.");
            al.showAndWait();
        }
    }

    @FXML
    public void cancel(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
