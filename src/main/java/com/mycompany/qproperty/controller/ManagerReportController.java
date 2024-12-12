
package com.mycompany.qproperty.controller;

import com.mycompany.qproperty.App;
import com.mycompany.qproperty.IExitable;
import com.mycompany.qproperty.model.ReportModel;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import javafx.scene.control.TextField;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: ManagerReportController.java 
 * Date :6/7/2024 
 * Purpose : Describes the function of Manager Report window. Describes the data flow of between user and model which
 * allows user to view bar graph, minimum, maximum and average charges
 *
 * *
 */
public class ManagerReportController implements Initializable, IExitable {



    @FXML
    private TextField avg;

    @FXML
    private BarChart<?, ?> barGraph;

    @FXML
    private TextField max;

    @FXML
    private TextField min;


    /**
     * Initializes the controller class.
     */
    ReportModel reportModel = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        reportModel = new ReportModel();

    }

    @FXML
    private void viewBtn(){
    ArrayList<Double> data = reportModel.compeletedJobs();
    
    XYChart.Series series1 = new XYChart.Series();
    series1.setName("Completed Jobs");
    
    // Add data points with incremental x-values
    for(int i = 0; i < data.size(); i++){
        series1.getData().add(new XYChart.Data(String.valueOf(i + 1), data.get(i)));
    }
    
    double avgValue = reportModel.getAvg();
    avg.setText(Double.toString(avgValue));
    
    double maxValue = reportModel.getMax();
    max.setText(Double.toString(maxValue));
    
    double minValue = reportModel.getMin();
    min.setText(Double.toString(minValue));
    
    barGraph.getData().clear(); // Clearing existing data
    barGraph.getData().add(series1); // Adding the series to the BarChart
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
}
