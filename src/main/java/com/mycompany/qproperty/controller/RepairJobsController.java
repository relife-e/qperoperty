package com.mycompany.qproperty.controller;

import com.mycompany.qproperty.App;
import com.mycompany.qproperty.IExitable;
import com.mycompany.qproperty.model.PropertyModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import com.mycompany.qproperty.model.RepairJobsModel;
import com.mycompany.qproperty.model.RepairJob;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.event.ActionEvent;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: RepairJobsController.java 
 * Date :6/7/2024 
 * Purpose : Describes the function of Repair Job window. Describes the data flow of between user and model which
 * allows user to cancel, add, view all, update and search for bookings
 *
 * *
 */
public class RepairJobsController implements Initializable, IExitable {



    @FXML
    private DatePicker bookingDate;

    @FXML
    private TextField charge;

    @FXML
    private DatePicker completionDate;


    @FXML
    private TextField description;


    @FXML
    private TextField jobId;

    @FXML
    private MenuButton jobType;

    @FXML
    private TextField repairId;


    @FXML
    private TextArea resultList;


    @FXML
    private MenuButton staffName;

    @FXML
    private MenuButton addressMenu;

    //initalzing Model classes and Strings for menu button
    private RepairJobsModel repairJobsModel;
    private PropertyModel propertyModel;
    private String selectedJobType;
    private String selectedStaffName;
    private String selectedAddress;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        repairJobsModel = new RepairJobsModel();
        propertyModel = new PropertyModel();
        //clearing menu button
        addressMenu.getItems().clear();
        jobType.getItems().clear();
        staffName.getItems().clear();
        //intiantiating methods to populate data
        populateJobTypeMenu();
        populateStaffNameMenu();
        populateAssignAddressMenu();

        addressMenu.getItems().forEach(item -> item.setOnAction(event -> {
            selectedAddress = item.getText();
            addressMenu.setText(selectedAddress); // Update the text of the MenuButton to show the selected customer
        }));

        jobType.getItems().forEach(item -> item.setOnAction(event -> {
            selectedJobType = item.getText();
            jobType.setText(selectedJobType); // Update the text of the MenuButton to show the selected property type
        }));

        staffName.getItems().forEach(item -> item.setOnAction(event -> {
            selectedStaffName = item.getText();
            staffName.setText(selectedStaffName); // Update the text of the MenuButton to show the selected staff name
        }));
    }

    private void populateJobTypeMenu() {
        //property types are hardcoded
        ArrayList<String> jobTypes = new ArrayList<>(Arrays.asList("Electrical", "Plumbing", "Structural", "Cleaning", "Gardening", "Pest Control"));
        for (String type : jobTypes) {
            MenuItem menuItem = new MenuItem(type);
            jobType.getItems().add(menuItem);
        }
    }

    private void populateStaffNameMenu() {
        //staff names are hardcoded 
        ArrayList<String> staffNames = new ArrayList<>(Arrays.asList("Xing Xu", "Jane Smith", "Alice Johnson", "Bob Brown"));
        for (String name : staffNames) {
            MenuItem menuItem = new MenuItem(name);
            staffName.getItems().add(menuItem);
        }
    }

    private void populateAssignAddressMenu() {
        //address is populated from the database
        ArrayList<String> address = propertyModel.populateAddress();
        for (String adr : address) {
            MenuItem menuItem = new MenuItem(adr);
            addressMenu.getItems().add(menuItem);
        }
    }

    @FXML
    private void addRepairJob(ActionEvent event) {
        String desc = description.getText();
        LocalDate bookingDateValue = bookingDate.getValue();
        LocalDate completionDateValue = completionDate.getValue();
        String chargeText = charge.getText();
        //checking if the fields are empty
        if (desc.isEmpty() || bookingDateValue == null || completionDateValue == null || chargeText.isEmpty() || selectedStaffName == null || selectedJobType == null) {
            showAlert("Error", "Please fill in all fields.");
            return; // Exit the method if any field is empty
        }

        double chargeValue = 0.0; // Default value in case parsing fails
        
        //validating integer
        try {
            chargeValue = Double.parseDouble(chargeText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid charge value. Please enter a valid number.");
            return; // Exit the method if charge value is invalid
        }

        Date bookingDateSql = java.sql.Date.valueOf(bookingDateValue);
        Date completionDateSql = java.sql.Date.valueOf(completionDateValue);

        boolean result = repairJobsModel.addRepairJob(selectedAddress, desc, bookingDateSql, completionDateSql, chargeValue, selectedStaffName, selectedJobType);

        if (result) {
            showAlert("Success", "Repair job added successfully.");
            // Clear input fields
            description.clear();
            bookingDate.setValue(null);
            completionDate.setValue(null);
            charge.clear();
            staffName.setText("Select Staff Name");
            jobType.setText("Select Job Type");
        } else {
            showAlert("Error", "Failed to add repair job.");
        }
    }

    @FXML
    private void getAllRepairJobs() {
        StringBuilder propertyInfo = new StringBuilder();

        ArrayList<RepairJob> jobs = repairJobsModel.getAllJobs();
        for (RepairJob p : jobs) {
            // Append each customer information to the StringBuilder
            propertyInfo.append(p.toString()).append("\n");
        }

        // Set the concatenated customer information to the propertyList
        resultList.setText(propertyInfo.toString());
    }

    @FXML
    private void search() {

        String jobIdText = jobId.getText();

        // Check if jobIdText is empty
        if (jobIdText.isEmpty()) {
            showAlert("Error", "Job ID cannot be empty.");
            return;
        }
        //validating integer
        int jId;
        try {
            jId = Integer.parseInt(jobIdText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Job ID. Please enter a valid number.");
            return;
        }

        RepairJob p = repairJobsModel.searchRepairJob(jId);

        if (p != null) {
            resultList.setText(p.toString());
        } else {
            showAlert("Repair Jobs Not Found", "No repair Jobs found with the provided information.");
        }
    }

    @FXML
    private void deleteJobs() {
         String jobIdText = jobId.getText();
         //checking if the field is empty
         if (jobIdText.isEmpty()) {
            showAlert("Error", "Job ID cannot be empty.");
            return;
        }
          int jId;
          //validating integer
        try {
            jId = Integer.parseInt(jobIdText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Job ID. Please enter a valid number.");
            return;
        }
        boolean check = repairJobsModel.deleteRepairJob(jId);

        if (check) {
            showAlert("Sucessfull", "Booking cancled");
        } else {
            showAlert("Repair Id Not Found", "Please Enter correct repair ID");
        }
    }

    @FXML
    private void updateBookings() {
        String jobIdText = jobId.getText();

        LocalDate completionDateValue = completionDate.getValue();
        //validating if the fields are empty
        if (completionDateValue == null || repairId.getText().isEmpty()) {
            showAlert("Error", "Please fill in compltion date and job id.");
            return;
        }
        Date completionDateSql = Date.valueOf(completionDateValue);
        int jId;
        try {
            jId = Integer.parseInt(jobIdText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid Job ID. Please enter a valid number.");
            return;
        }

        boolean result = repairJobsModel.updateRepairJob(jId, completionDateSql);

        if (result) {
            showAlert("Success", "Repair job added successfully.");
            // Clear input fields
            repairId.clear();
            completionDate.setValue(null);

        } else {
            showAlert("Error", "Failed to add repair job.");
        }

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
    private void exitButtonClick(ActionEvent event) {
        this.exitClick(); //call Iexitable interface
    }

    @FXML
    private void about() throws IOException {
        App.setRoot("About");

    }

    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
