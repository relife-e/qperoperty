package com.mycompany.qproperty.controller;

import com.mycompany.qproperty.App;
import com.mycompany.qproperty.IExitable;
import com.mycompany.qproperty.model.Customer;
import com.mycompany.qproperty.model.CustomerModel;
import com.mycompany.qproperty.model.Property;
import com.mycompany.qproperty.model.PropertyModel;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: PropertiesController.java 
 * Date :6/7/2024 
 * Purpose : Describes the function of Properties window. Describes the data flow of between user and model which
 * allows user to add or search for specific properties
 *
 * *
 */
public class PropertiesController implements Initializable, IExitable {


    @FXML
    private TextField addSearch;

    @FXML
    private TextField address;

    @FXML
    private MenuButton assignCustomer;

    @FXML
    private DatePicker buildYear;

    @FXML
    private TextField description;


    @FXML
    private MenuButton managingAgent;


    @FXML
    private TextField propertyId;

    @FXML
    private MenuButton propertyType;

    @FXML
    private TextArea resultList;

    //initializing model clasess
    private CustomerModel customerModel;
    private PropertyModel propertyModel;
    //initializing string
    private String selectedCustomer;
    private String selectedManagingAgent;
    private String selectedPropertyType;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        customerModel = new CustomerModel(); // Initialize your CustomerModel here
        propertyModel = new PropertyModel(); // Initialize your PropertyModel here
        // Clearing menu items
        assignCustomer.getItems().clear();
        managingAgent.getItems().clear();
        propertyType.getItems().clear();
        //calling methods to populate menu button
        populateAssignCustomerMenu();
        populateManagingAgentMenu();
        populatePropertyTypeMenu();

    }

    private void populateAssignCustomerMenu() {
        //customer is populated from database
        ArrayList<Integer> customerIds = customerModel.populateCustomer();
        for (Integer id : customerIds) {
            MenuItem menuItem = new MenuItem(id.toString());
            assignCustomer.getItems().add(menuItem);
            menuItem.setOnAction(event -> {
                selectedCustomer = menuItem.getText();
                assignCustomer.setText(selectedCustomer); // Update the text of the MenuButton to show the selected customer
            });
        }
    }

    private void populateManagingAgentMenu() {
        //managing agents are hardcoded
        ArrayList<String> managingAgents = new ArrayList<>(Arrays.asList("Agent A", "Agent B", "Agent C"));
        for (String agent : managingAgents) {
            MenuItem menuItem = new MenuItem(agent);
            managingAgent.getItems().add(menuItem);
            menuItem.setOnAction(event -> {
                selectedManagingAgent = menuItem.getText();
                managingAgent.setText(selectedManagingAgent); // Update the text of the MenuButton to show the selected managing agent
            });
        }
    }

    private void populatePropertyTypeMenu() {
        // property types are hardcoded
        ArrayList<String> propertyTypes = new ArrayList<>(Arrays.asList("Residential", "Commercial", "Industrial"));
        for (String type : propertyTypes) {
            MenuItem menuItem = new MenuItem(type);
            propertyType.getItems().add(menuItem);
            menuItem.setOnAction(event -> {
                selectedPropertyType = menuItem.getText();
                propertyType.setText(selectedPropertyType); // Update the text of the MenuButton to show the selected property type
            });
        }
    }

    @FXML
    private void addProperty(ActionEvent event) {
        String addr = address.getText();
        String desc = description.getText();
        LocalDate dateBuild = buildYear.getValue();
        
        String customerId = selectedCustomer;
        String managingAgentName = selectedManagingAgent;
        String propertyTypeName = selectedPropertyType;
        //checking if the fields are empty
        if (addr.isEmpty() || desc.isEmpty() || dateBuild == null || customerId == null || managingAgentName.isEmpty() || propertyTypeName.isEmpty()) {
            showAlert("Error", "Please fill in all fields.");
            return;
        }
        Date date = Date.valueOf(dateBuild);
        boolean result = propertyModel.addProperty(addr, desc, date, managingAgentName, propertyTypeName, Integer.parseInt(customerId));
        if (result) {
            showAlert("Success", "Property added successfully.");
            // Clear input fields
            address.clear();
            description.clear();
            buildYear.setValue(null);
            assignCustomer.setText("Select Customer");
            managingAgent.setText("Select Managing Agent");
            propertyType.setText("Select Property Type");
        } else {
            showAlert("Error", "Failed to add property.");
        }
    }

   

    @FXML
    private void search() {

        String addr = addSearch.getText();
        //checking if the fields are empty
        if (addr.isEmpty()) {
            showAlert("Error", "Please enter property address to search for property");
            return; // Add return to exit the method early
        }

        Property p = propertyModel.searchProperty(addr);

        if (p != null) {
            resultList.setText(p.toString());
        } else {
            showAlert("Property Not Found", "No Property found with the provided information.");
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
