package com.mycompany.qproperty.model;

import com.mycompany.qproperty.dao.Database;
import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: RepairJobsModel.java 
 * Date :6/7/2024 
 * Purpose : Describes the methods of RepairJobsModel. 
 * Here Database connection is instantiated by calling database class and acts as a middle man between the application and the database.
 * All the query required of RepairJobs view are describe here
 * This class interacts with database to do a function such as search bookings, add new bookings, Cancel bookings, update bookings and view bookings
 * *
 */
public class RepairJobsModel {

    private Connection connection = null;
    private PreparedStatement addNewRepairJob = null;
    private PreparedStatement deleteRepairJob = null;
    private PreparedStatement updateRepairJob = null;
    private PreparedStatement selectAllRepairJobs = null;
    private PreparedStatement searchRepairJobByDescription = null;
    private PreparedStatement completedJobs = null;

    public RepairJobsModel() {
        try {
            connection = Database.getConnection();

            selectAllRepairJobs = connection.prepareStatement("SELECT * FROM repairs");
            completedJobs = connection.prepareStatement("SELECT * FROM repairs WHERE completed_date IS NOT NULL");
            addNewRepairJob = connection.prepareStatement(
                    "INSERT INTO repairs (property_address, description, booking_date, completion_date, charge, service_staff, job_type) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)");
            deleteRepairJob = connection.prepareStatement("DELETE FROM repairs WHERE repairs_id = ?");
            updateRepairJob = connection.prepareStatement(
                    "UPDATE repairs SET  completion_date = ? " +
                            "WHERE repairs_id = ?");            
            searchRepairJobByDescription = connection.prepareStatement(
                    "SELECT * FROM repairs WHERE repairs_id = ?");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public boolean addRepairJob(String address, String description, Date bookingDate, Date completionDate, double charge, String serviceStaff, String jobType) {
    try {
        // Set parameters for the prepared statement
        addNewRepairJob.setString(1, address);
        addNewRepairJob.setString(2, description);
        addNewRepairJob.setDate(3, bookingDate);
        addNewRepairJob.setDate(4, completionDate);
        addNewRepairJob.setDouble(5, charge);
        addNewRepairJob.setString(6, serviceStaff);
        addNewRepairJob.setString(7, jobType);

        // Execute the update
        int rowsAffected = addNewRepairJob.executeUpdate();

        // Return true if the update was successful
        return rowsAffected > 0;
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
        return false;
    }
}

public boolean deleteRepairJob(int jobId) {
    try {
        // Set parameter for the prepared statement
        deleteRepairJob.setInt(1, jobId);

        // Execute the update
        int rowsAffected = deleteRepairJob.executeUpdate();

        // Return true if the update was successful
        return rowsAffected > 0;
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
        return false;
    }
}

public ArrayList<Integer> compeletedJobs() {
    ArrayList<Integer> result = new ArrayList<>();
    try {
        ResultSet resultSet = null;

        // Execute the query to retrieve completed jobs
        resultSet = completedJobs.executeQuery();

        // Iterate over the result set and add charges to the result list
        while (resultSet.next()) {
            result.add(resultSet.getInt("charges"));
        }
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
    }
    return result;
}

public boolean updateRepairJob(int jobId, Date completionDate) {
    try {
        // Set parameters for the prepared statement
        updateRepairJob.setDate(1, completionDate);
        updateRepairJob.setInt(2, jobId);

        // Execute the update
        int rowsAffected = updateRepairJob.executeUpdate();

        // Return true if the update was successful
        return rowsAffected > 0;
    } catch (SQLException sqlException) {
        sqlException.printStackTrace();
        return false;
    }
}

public RepairJob searchRepairJob(int id) {
    RepairJob results = null;
    ResultSet resultSet = null;

    try {
        // Set parameter for the prepared statement
        searchRepairJobByDescription.setInt(1, id);
        resultSet = searchRepairJobByDescription.executeQuery();

        // Iterate over the result set and create a RepairJob object
        while (resultSet.next()) {
            results = (new RepairJob(
                    resultSet.getInt("repairs_id"),
                    resultSet.getString("property_address"),
                    resultSet.getString("description"),
                    resultSet.getDate("booking_date"),
                    resultSet.getDate("completion_date"),
                    resultSet.getDouble("charge"),
                    resultSet.getString("service_staff"),
                    resultSet.getString("job_type")
            ));
        }
    } catch (SQLException sqlException) {
        // Print stack trace if an exception occurs
        sqlException.printStackTrace();
    } finally {
        try {
            // Close the result set if not null
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    return results;
}

    
    public ArrayList<RepairJob> getAllJobs() {
        ArrayList<RepairJob> results = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectAllRepairJobs.executeQuery();

            while (resultSet.next()) {
                results.add(new RepairJob(
                        resultSet.getInt("repairs_id"),
                        resultSet.getString("property_address"),
                        resultSet.getString("description"),
                        resultSet.getDate("booking_date"),
                        resultSet.getDate("completion_date"),
                        resultSet.getDouble("charge"),
                        resultSet.getString("service_staff"),
                        resultSet.getString("job_type")
                ));
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                close();
            }
        }

        return results;
    }
    // Other methods for updating and deleting repair jobs can be added here

    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
