package com.mycompany.qproperty.model;

import com.mycompany.qproperty.dao.Database;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: PropertyModel.java 
 * Date :6/7/2024 
 * Purpose : Describes the methods of PropertyModel. 
 * Here Database connection is instantiated by calling database class and acts as a middle man between the application and the database.
 * All the query required of Property view are describe here
 * This class interacts with database to do a function such as search Property and add new Property
 * *
 */
public class PropertyModel {

    private Connection connection = null;
    private PreparedStatement addNewProperty = null;
    private PreparedStatement deleteProperty = null;
    private PreparedStatement updateProperty = null;
    private PreparedStatement selectAllProperty = null;
    private PreparedStatement searchPropertyByAddress = null;

    public PropertyModel() {
        try {
            connection = Database.getConnection();

            addNewProperty = connection.prepareStatement(
                    "INSERT INTO property (address, description, build_year, managing_agent, property_type, customer_id) "
                    + "VALUES (?, ?, ?, ?, ?, ?)");
            selectAllProperty = connection.prepareStatement(
                    "SELECT * FROM property");   
            searchPropertyByAddress = connection.prepareStatement(
                    "SELECT * FROM property WHERE address LIKE ?");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public boolean addProperty(String address, String description, Date builtYear, String managingAgent, String propertyType, int customerId) {
        try {
            addNewProperty.setString(1, address);
            addNewProperty.setString(2, description);
            addNewProperty.setDate(3, builtYear);
            addNewProperty.setString(4, managingAgent);
            addNewProperty.setString(5, propertyType);
            addNewProperty.setInt(6, customerId);

            int rowsAffected = addNewProperty.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    public Property searchProperty(String address) {
        Property results = null;
        ResultSet resultSet = null;

        try {
            searchPropertyByAddress.setString(1, "%" + address + "%");
            resultSet = searchPropertyByAddress.executeQuery(); //execute query
            //iterates until there is no data
            while (resultSet.next()) {
                results = (new Property(
                        resultSet.getString("address"),
                        resultSet.getString("description"),
                        resultSet.getDate("build_year"),
                        resultSet.getString("managing_agent"),
                        resultSet.getString("property_type"),
                        resultSet.getInt("customer_id")
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
            }
        }

        return results;
    }

    public ArrayList<Property> getAllProperties() {
        ArrayList<Property> results = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectAllProperty.executeQuery();

            while (resultSet.next()) {
                results.add(new Property(
                        resultSet.getString("address"),
                        resultSet.getString("description"),
                        resultSet.getDate("build_year"),
                        resultSet.getString("managing_agent"),
                        resultSet.getString("property_type"),
                        resultSet.getInt("customer_id")
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
    public ArrayList<String> populateAddress() {
          ArrayList<String> result = new ArrayList<>();
        try (
             
             ResultSet resultSet = selectAllProperty.executeQuery()) {

            // Iterate over the result set and add customer names as menu items
            while (resultSet.next()) {
                String propertyAddress = resultSet.getString("address");
                result.add(propertyAddress);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    // Other methods for updating and deleting properties can be added here
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
