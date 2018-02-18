package Admin;

import dbUtil.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController  implements Initializable{
    @FXML
    private TextField ids;
    @FXML
    private TextField fname;
    @FXML
    private TextField lname;
    @FXML
    private TextField emails;
    @FXML
    private DatePicker dobs;
    @FXML
    private TableView<StudentData> studenttable;

    @FXML
    private TableColumn<StudentData, String> idcolum;
    @FXML
    private TableColumn<StudentData, String> firstnamecolum;
    @FXML
    private TableColumn<StudentData, String> lastnamecolum;
    @FXML
    private TableColumn<StudentData, String> emailcolum;
    @FXML
    private TableColumn<StudentData, String> dobcolum;

    private dbConnection db;
    private ObservableList<StudentData> data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.db = new dbConnection();

    }

    @FXML
    private void loadStudentData(ActionEvent event){
        try {
            Connection conn = dbConnection.getConnection();
            this.data = FXCollections.observableArrayList();
            String sql = "select * from user";
            ResultSet rs = conn.createStatement().executeQuery(sql);

            while (rs.next()) {
                this.data.add(new StudentData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

        } catch (SQLException e) {
            System.err.println("Error" + e);
            e.printStackTrace();
        }

        this.idcolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("ID"));
        this.firstnamecolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("firstName"));
        this.lastnamecolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("lastName"));
        this.emailcolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("email"));
        this.dobcolum.setCellValueFactory(new PropertyValueFactory<StudentData, String>("DOB"));

        this.studenttable.setItems(null);
        this.studenttable.setItems(this.data);

    }

    @FXML
    private void addStudent(ActionEvent event) {
        String sqlInsert = "insert into user(id,firstName,lastName,email,DOB) values (?,?,?,?,?)";

        try {
            Connection conn = dbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlInsert);
            stmt.setString(1, this.ids.getText());
            stmt.setString(2, this.fname.getText());
            stmt.setString(3, this.lname.getText());
            stmt.setString(4, this.emails.getText());
            stmt.setString(5, this.dobs.getEditor().getText());

            stmt.execute();
            stmt.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private  void clearFields(ActionEvent event){
        this.ids.setText("");
        this.fname.setText("");
        this.lname.setText("");
        this.emails.setText("");
        this.dobs.setValue(null);
    }
}//class
