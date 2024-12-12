
package com.mycompany.qproperty.controller;

import com.mycompany.qproperty.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: MainController.java 
 * Date :6/7/2024 
 * Purpose : Describes the function of Main window. Describes the data flow of between user and model which
 * go selected view such as customer, repair jobs, property and manager report view
 *
 * *
 */
public class MainController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void goCustomerView() throws IOException{
        App.setRoot("Customer");
    }
    @FXML
    private void goPropertyView() throws IOException{
        App.setRoot("Properties");
    }
    @FXML
    private void goRepairJobsView() throws IOException{
        App.setRoot("RepairJobs");
    }
    @FXML
    private void goReportView() throws IOException{
        App.setRoot("ManagerReport");
    }
    @FXML
    private void back() throws IOException{
        App.setRoot("Login");
    }
    
}
