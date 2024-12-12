package com.mycompany.qproperty.controller;

import com.mycompany.qproperty.App;
import com.mycompany.qproperty.model.User;
import com.mycompany.qproperty.model.UserModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar File Name:
 * RegisterController.java 
 * Date :6/7/2024 
 * Purpose : Describes the function ofRegister window. Describes the data flow of between user and model which
 * allows user to register their account
 *
 * *
 */
public class RegisterController implements Initializable {

    @FXML
    private TextField email;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField passWord;

    @FXML
    private TextField userName;
    
    //initializing the UserModel class
    UserModel model = new UserModel();
    ArrayList<User> users;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void register() {
        String fname = fName.getText();
        String lname = lName.getText();
        String pass = passWord.getText();
        String uName = userName.getText();
        String e = email.getText();
        //validating if the fields are empty or not
        if (fname.isEmpty() || lname.isEmpty() || pass.isEmpty() || uName.isEmpty() || e.isEmpty()) {
            showAlert("Warning", "Please enter all details.");
            return;
        }
        model.addUser(fname, lname, uName, pass, e);
        fName.clear();
        lName.clear();
        passWord.clear();
        userName.clear();
        email.clear();
        showAlert("Success", "User registered successfully.");
    }

    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void backBtn() throws IOException {
        App.setRoot("Login");

    }

    @FXML
    private void goCustomerView() throws IOException {
        App.setRoot("Customer");
    }

    @FXML
    private void goPropertyView() throws IOException {
        App.setRoot("Properties");
    }

    @FXML
    private void goRepairJobsView() throws IOException {
        App.setRoot("RepairJobs");
    }

    @FXML
    private void goReportView() throws IOException {
        App.setRoot("ManagerReport");
    }

    @FXML
    private void back() throws IOException {
        App.setRoot("Main");
    }

}
