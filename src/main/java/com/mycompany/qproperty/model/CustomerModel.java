package com.mycompany.qproperty.model;

import com.mycompany.qproperty.dao.Database;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: CustomerModel.java 
 * Date :6/7/2024 
 * Purpose : Describes the methods of CustomerModel. 
 * Here Database connection instantiated by calling database class and acts as a middle man between the application and the database.
 * All the query required of customer view are describe here
 * This class interacts with database to do a function such as search customer, add new customer, update customer and display all customer
 * *
 */
public class CustomerModel {

    private Connection connection = null;
    private PreparedStatement selectAllCustomer = null;
    private PreparedStatement selectCustomerByString = null;
    private PreparedStatement updateCustomer = null;
    private PreparedStatement addNewCustomer = null;
    private PreparedStatement selectCustomerById = null;

    public CustomerModel() {
        try {
            connection = Database.getConnection();

            // create query that selects all entries in the table
            selectAllCustomer = connection.prepareStatement("SELECT * FROM customer");

            // create query that selects entries with a specific id
            selectCustomerByString = connection.prepareStatement("SELECT * FROM customer WHERE last_name = ? or phone_number = ? ");

            selectCustomerById = connection.prepareStatement("SELECT * FROM customer WHERE customer_id = ?");
            // create query that deletes entries with a specific id
            updateCustomer = connection.prepareStatement("UPDATE FROM customer WHERE customer_id = ?");

            // create insert that adds a new entry into the database
            addNewCustomer = connection.prepareStatement(
                    "INSERT INTO customer (first_name, last_name, phone_number, address) VALUES (?, ?, ?, ?)");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }

    public boolean updateCustomer(int customerId, String firstName, String lastName, String address, String phoneNumber) {
        try {
            // Check if the customer with the given ID exists
            if (customerExists(customerId)) {
                // Retrieve the existing data for the customer
                Customer existingCustomer = getCustomerById(customerId);

                // Prepare the SQL statement
                updateCustomer.setString(1, (firstName != null) ? firstName : existingCustomer.getFirstName());
                updateCustomer.setString(2, (lastName != null) ? lastName : existingCustomer.getLastName());
                updateCustomer.setString(3, (address != null) ? address : existingCustomer.getAddress());
                updateCustomer.setString(4, (phoneNumber != null) ? phoneNumber : existingCustomer.getPhone());
                updateCustomer.setInt(5, customerId);

                // Execute the update
                int rowsAffected = updateCustomer.executeUpdate();

                // Check if the update was successful
                return rowsAffected > 0;
            } else {
                System.out.println("Customer with ID " + customerId + " does not exist.");
                return false;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    private boolean customerExists(int customerId) {
        try {
            // Check if the customer exists in the database
            selectCustomerById.setInt(1, customerId);
            ResultSet resultSet = selectCustomerById.executeQuery();
            return resultSet.next(); // Returns true if a matching customer is found
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }
    }

    private Customer getCustomerById(int customerId) {
        try {
            // Prepare the SQL statement to retrieve customer data by ID
            selectCustomerById.setInt(1, customerId);
            ResultSet resultSet = selectCustomerById.executeQuery();

            // Check if the result set has data
            if (resultSet.next()) {
                // Create a new Customer object with the retrieved data
                return new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number")
                );
            } else {
                return null; // No customer found with the given ID
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> results = new ArrayList<>();
        ResultSet resultSet = null;

        try {
            // executeQuery returns ResultSet containing matching entries
            resultSet = selectAllCustomer.executeQuery();

            while (resultSet.next()) {
                results.add(new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number")
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

    // select customer by ID
    public Customer getCustomerByString(String lastName, String phoneNum) {
        Customer customer = null;
        ResultSet resultSet = null;

        try {
            // Set the parameter value for phone number or address
            selectCustomerByString.setString(1, lastName);
            selectCustomerByString.setString(2, phoneNum);

            // Execute the query
            resultSet = selectCustomerByString.executeQuery();

            if (resultSet.next()) {
                customer = new Customer(
                        resultSet.getInt("customer_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("address"),
                        resultSet.getString("phone_number")
                );
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
        System.out.println();
        return customer;
    }

    // add a new customer
    public int addCustomer(String firstName, String lastName, String phoneNumber, String address) {
        int result = 0;

        try {
            addNewCustomer.setString(1, firstName);
            addNewCustomer.setString(2, lastName);
            addNewCustomer.setString(3, phoneNumber);
            addNewCustomer.setString(4, address);

            // insert the new entry; returns # of rows updated
            result = addNewCustomer.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            close();
        }

        return result;
    }
      public ArrayList<Integer> populateCustomer() {
          ArrayList<Integer> result = new ArrayList<>();
        try (
             
             ResultSet resultSet = selectAllCustomer.executeQuery()) {

            // Iterate over the result set and add customer names as menu items
            while (resultSet.next()) {
                Integer customerName = resultSet.getInt("customer_id");
                result.add(customerName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // close the database connection
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
