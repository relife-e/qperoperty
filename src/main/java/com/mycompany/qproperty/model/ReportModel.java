
package com.mycompany.qproperty.model;

import com.mycompany.qproperty.dao.Database;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar File Name:
 * ReportModel.java Date :6/7/2024 Purpose : Describes the methods of
 * ReportModel. Here Database connection is instantiated by calling database class and acts as a middle man
 * between the application and the database. All the query required of Report
 * view are describe here This class interacts with database to calculate
 * minimum, maximum and average charges Furthermore it also returns *
 */
public class ReportModel {


    private ArrayList<Double> result = new ArrayList<>();
    private Connection connection = null;

    private PreparedStatement completedJobs = null;

    public ReportModel() {

        try {
            connection = Database.getConnection();

            completedJobs = connection.prepareStatement("SELECT * FROM repairs WHERE completion_date IS NOT NULL");

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public ArrayList<Double> compeletedJobs() {
       result = new ArrayList<>();
        try {
            ResultSet resultSet = null;

            // Execute the query to retrieve completed jobs
            resultSet = completedJobs.executeQuery();

            // Iterate over the result set and add charges to the result list
            while (resultSet.next()) {
                result.add(resultSet.getDouble("charge"));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return result;
    }

    //get average method
    public double getAvg() {
        double total = 0;
        int count = 0;
        for (double i : result) {
            total = i + total;
            count++;
        }
        double avg = total / count;
        return avg;
    }

    //get maximum method
    public double getMax() {
        double min = 0;
        double max = 0;
        for (double i : result) {
            min = i;
            if (min > max) {
                max = min;
            }

        }

        return max;
    }

// Get minimum method
    public double getMin() {
        double min = Double.MAX_VALUE; // Initialize min to the maximum possible value

        for (double i : result) {
            if (i < min) {
                min = i; // Update min if the current element is smaller
            }
        }

        return min;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
