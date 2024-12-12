package com.mycompany.qproperty.controller;

import com.mycompany.qproperty.App;
import com.mycompany.qproperty.IExitable;
import com.mycompany.qproperty.model.Customer;
import com.mycompany.qproperty.model.CustomerModel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: CustomerController.java 
 * Date :6/7/2024 
 * Purpose : Describes the function of customer window Describes the data flow of between user and model which
 * allows user to add new customer, update customer details search customer
 * details and display all the customer details
 *
 * *
 */
public class CustomerController implements Initializable, IExitable {

//initialzing interfaces
    @FXML
    private TextField customerId;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField address;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextArea propertyList;

    //initalizing CustomerModel class
    private CustomerModel customerModel;
    //CustomerModel model = new CustomerModel();
    ArrayList<Customer> customer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //creating new CustomerModel() object
        customerModel = new CustomerModel();
    }

    @FXML
    private void addNewCustomer() {
        String fName = firstName.getText();
        String lName = lastName.getText();
        String addr = address.getText();
        String phone = phoneNumber.getText();
        //checking if the fields are empty
        if (fName.isEmpty() || lName.isEmpty() || addr.isEmpty() || phone.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }

        int result = customerModel.addCustomer(fName, lName, phone, addr);
        if (result > 0) {
            showAlert("Success", "Customer added successfully.");
            // Clear input fields
            firstName.clear();
            lastName.clear();
            address.clear();
            phoneNumber.clear();
        } else {
            showAlert("Error", "Failed to add customer.");
        }
    }

    @FXML
    private void search() {
        propertyList.clear();
        String lName = lastName.getText(); // You can modify this to use phone number or any other criteria
        String phnNum = phoneNumber.getText();
        //checking is the field are empty
        if (lName.isEmpty() && phnNum.isEmpty()) {
            showAlert("Error", "Please Enter last name or phone number");
            return; // Add return to exit the method early
        }

        Customer customer = customerModel.getCustomerByString(lName, phnNum);

        if (customer != null) {
            propertyList.setText(customer.toString());
        } else {
            showAlert("Customer Not Found", "No customer found with the provided information.");
        }
    }

    @FXML
    private void updateCustomer() {
        //validating customer Id if it is integer
        int custId;
        try {
            custId = Integer.parseInt(customerId.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Customer ID must be a valid integer.");
            return;
        }
        String fName = firstName.getText();
        String lName = lastName.getText(); // You can modify this to use phone number or any other criteria
        String phnNum = phoneNumber.getText();
        String addr = address.getText();
        //validating is the fields are empty or not
        if (fName.isEmpty() || lName.isEmpty() || phnNum.isEmpty() || addr.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }
        boolean result = customerModel.updateCustomer(custId, fName, lName, addr, phnNum);
        if (result) {
            showAlert("Success", "Customer updated successfully.");
        } else {
            showAlert("Failure", "");
        }

    }

    @FXML
    private void getAllCustomer() {
        propertyList.clear();
        StringBuilder customerInfo = new StringBuilder();

        ArrayList<Customer> customerList = customerModel.getAllCustomers();
        for (Customer customer : customerList) {
            // Append each customer information to the StringBuilder
            customerInfo.append(customer.toString()).append("\n");
        }

        // Set the concatenated customer information to the propertyList
        propertyList.setText(customerInfo.toString());
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

    @FXML
    private void exitButtonClick(ActionEvent event) {
        this.exitClick(); //call Iexitable interface
    }

    @FXML
    private void about() throws IOException {
        App.setRoot("About");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
