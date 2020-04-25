package EmployeeMgmt.displayBook;

import EmployeeMgmt.MainController;
import EmployeeMgmt.databaseHandler.DatabaseHandler;
import com.github.javafaker.Faker;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class displayBookController extends MainController {
    ObservableList<Employee> list = FXCollections.observableArrayList();

    @FXML
    private TableView<Employee> tableView;
    @FXML
    private TableColumn<Employee, String> IDCol;
    @FXML
    private TableColumn<Employee, String> fNameCol;
    @FXML
    private TableColumn<Employee, String> lNameCol;
    @FXML
    private TableColumn<Employee, String> rankCol;
    @FXML
    private TableColumn<Employee, String> addressCol;
    @FXML
    private TableColumn<Employee, String> dojCol;
    @FXML
    private TableColumn<Employee, String> uniCol;
    @FXML
    private TableColumn<Employee, String> fieldCol;
    @FXML
    private TableColumn<Employee, String> posCol;
    @FXML
    private TableColumn<Employee, String> salaryCol;
    @FXML
    private JFXTextField filterField;
    @FXML
    private ContextMenu myMenu;

    @FXML
    public void initialize() {
        super.initialize();
        loadData();
        initColumn();
    }

    void initColumn() {
        IDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fNameCol.setCellValueFactory(new PropertyValueFactory<>("fName"));
        lNameCol.setCellValueFactory(new PropertyValueFactory<>("lName"));
        rankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        dojCol.setCellValueFactory(new PropertyValueFactory<>("doj"));
        uniCol.setCellValueFactory(new PropertyValueFactory<>("uni"));
        fieldCol.setCellValueFactory(new PropertyValueFactory<>("field"));
        posCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
    }

    void loadData() {
        list.clear();
        String qu = "SELECT * FROM EMPLOYEE";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                Integer IDx = rs.getInt("id");
                String fNamex = rs.getString("first_name");
                String lNamex = rs.getString("last_name");
                String rankx = rs.getString("rank");
                String addressx = rs.getString("address");
                String dojx = rs.getString("doj");
                String unix = rs.getString("uni");
                String fieldx = rs.getString("field");
                String positionx = rs.getString("position");
                String salaryx = rs.getString("salary");
                //System.out.println(IDx+" "+fNamex+" "+lNamex+" "+mobNumberx+" "+salaryx+" ");
                list.add(new Employee(IDx, fNamex, lNamex, rankx, addressx, dojx, unix, fieldx, positionx, salaryx));
            }
        } catch (SQLException e) {
            Logger.getLogger(displayBookController.class.getName()).log(Level.SEVERE, null, e);
        }

        FilteredList<Employee> filteredData = new FilteredList<>(list, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                if (employee.getfName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (employee.getlName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (employee.getId().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (employee.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (employee.getDoj().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (employee.getField().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (employee.getPosition().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (employee.getRank().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (employee.getUni().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                } else if (String.valueOf(employee.getSalary()).contains(lowerCaseFilter))
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Employee> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);
    }

    @FXML
    public void addRandomPeeps(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Faker faker = new Faker();
        handler = DatabaseHandler.getInstance();
        String quStart = "INSERT INTO EMPLOYEE VALUES ";
        int lim = 10;
        String fullQ = new String();
        for (int i = 0; i < lim; i++) {
            String fakeID = faker.phoneNumber().subscriberNumber();
            String fakeFirst = faker.funnyName().name();
            String fakeLast = faker.name().lastName();
            String numString = Integer.valueOf(faker.number().numberBetween(1, 10) * 10000).toString();
            String fakeSalary = numString;
            String fakeAddress = faker.address().streetAddress();
            String fakeTitle = faker.job().title();
            String fakeField = faker.job().field();
            String fakeRank = faker.leagueOfLegends().rank();
            String fakedoj = faker.business().creditCardExpiry();
            String fakeUni = faker.university().name();
            String qu =
                    "(" + fakeID + "," +
                            "'" + fakeFirst + "'," +
                            "'" + fakeLast + "'," +
                            "'" + fakeRank + "'," +
                            "'" + fakeAddress + "'," +
                            "'" + fakedoj + "'," +
                            "'" + fakeUni + "'," +
                            "'" + fakeField + "'," +
                            "'" + fakeTitle + "'," +
                            "'" + fakeSalary + "'";
            if (i != lim - 1) {
                qu = qu.concat("),");
            } else {
                qu = qu.concat(")");
            }
            fullQ = fullQ.concat(qu);
        }
        fullQ = quStart + fullQ;
        Alert al2 = new Alert(Alert.AlertType.INFORMATION);
        al2.initStyle(StageStyle.UTILITY);
        if (handler.execAction(fullQ)) {
            al2.setContentText("Successfully inserted " + lim + " employees.");
            al2.setHeaderText("Done!");
        } else {
            al2.setContentText("Inserted some employees; some threw errors. ");
            al2.setHeaderText("You win some, you lose some");
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished(e -> al2.hide());
        al2.show();
        loadData();
        delay.play();
    }


    public static class Employee {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty fName;
        private final SimpleStringProperty lName;
        private final SimpleStringProperty rank;
        private final SimpleStringProperty address;
        private final SimpleStringProperty doj;
        private final SimpleStringProperty uni;
        private final SimpleStringProperty field;
        private final SimpleStringProperty position;

        private final SimpleStringProperty salary;

        public Employee(Integer id, String fName, String lName, String rank, String address, String doj, String uni, String field, String position, String salary) {
            this.id = new SimpleIntegerProperty(id);
            this.fName = new SimpleStringProperty(fName);
            this.lName = new SimpleStringProperty(lName);
            this.rank = new SimpleStringProperty(rank);
            this.address = new SimpleStringProperty(address);
            this.doj = new SimpleStringProperty(doj);
            this.field = new SimpleStringProperty(field);
            this.uni = new SimpleStringProperty(uni);
            this.position = new SimpleStringProperty(position);
            this.salary = new SimpleStringProperty(salary);
        }


        public String getfName() {
            return fName.get();
        }

        public SimpleStringProperty fNameProperty() {
            return fName;
        }

        public String getlName() {
            return lName.get();
        }

        public SimpleStringProperty lNameProperty() {
            return lName;
        }

        public String getRank() {
            return rank.get();
        }

        public SimpleStringProperty rankProperty() {
            return rank;
        }

        public String getAddress() {
            return address.get();
        }

        public SimpleStringProperty addressProperty() {
            return address;
        }

        public String getDoj() {
            return doj.get();
        }

        public SimpleStringProperty dojProperty() {
            return doj;
        }

        public String getUni() {
            return uni.get();
        }

        public SimpleStringProperty uniProperty() {
            return uni;
        }

        public String getField() {
            return field.get();
        }

        public SimpleStringProperty fieldProperty() {
            return field;
        }

        public String getPosition() {
            return position.get();
        }

        public SimpleStringProperty positionProperty() {
            return position;
        }

        public String getSalary() {
            return salary.get();
        }

        public SimpleStringProperty salaryProperty() {
            return salary;
        }


        public Integer getId() {
            return id.get();
        }


    }

    @FXML
    public void doDelete(ActionEvent event) {
        Employee selectedEmp = tableView.getSelectionModel().getSelectedItem();
        if (selectedEmp == null) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("No employee selected!");
            al.setContentText("Please select an employee to delete");
            al.showAndWait();
        } else {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION, "Delete?", ButtonType.YES, ButtonType.NO);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Delete?");
            al.setContentText("Employee " + selectedEmp.getfName() + " will be deleted." + "\nWould you like to proceed?");
            al.showAndWait();
            if (al.getResult() == ButtonType.YES) {
                Boolean result = handler.deleteEmp(selectedEmp);
                if (result) {
                    Alert al2 = new Alert(Alert.AlertType.INFORMATION);
                    al2.initStyle(StageStyle.UTILITY);
                    al2.setContentText("Successfully deleted employee " + selectedEmp.getfName());
                    al2.setHeaderText("Done!");
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(e -> al2.hide());
                    al2.show();
                    loadData();
                    delay.play();
                } else {
                    Alert a11 = new Alert(Alert.AlertType.ERROR);
                    a11.initStyle(StageStyle.UTILITY);
                    a11.setContentText("Could not delete employee " + selectedEmp.getfName());
                    a11.setHeaderText("Failed!");
                    PauseTransition delay = new PauseTransition(Duration.seconds(1));
                    delay.setOnFinished(e -> a11.hide());
                    a11.show();
                    delay.play();
                }
            }
        }

    }

    @FXML
    public void doUpdate(ActionEvent event) {
        Employee selectedEmp = tableView.getSelectionModel().getSelectedItem();
        Stage thisStage = (Stage) tableView.getScene().getWindow();
        if (selectedEmp == null) {
            Alert al = new Alert(Alert.AlertType.ERROR);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("No employee selected!");
            al.setContentText("Please select an employee to delete");
            al.showAndWait();
        } else {
            Alert al = new Alert(Alert.AlertType.CONFIRMATION, "Delete?", ButtonType.YES, ButtonType.NO);
            al.initStyle(StageStyle.UTILITY);
            al.setHeaderText("Update?");
            al.setContentText("Update details for employee " + selectedEmp.getfName() + " ?");
            al.showAndWait();
            if (al.getResult() == ButtonType.YES) {
                loadWindowandControl("/EmployeeMgmt/editEmployee/editEmployee.fxml", thisStage, selectedEmp);
                loadData();
            }
        }

    }
}
