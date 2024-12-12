package com.mycompany.qproperty.model;

import com.mycompany.qproperty.dao.Database;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar File Name:
 * ReportModel.java Date :6/7/2024 
 * Purpose : Describes the methods of ReportModel.
 * Here Database connection is instantiated by calling database class and acts as a middle man
 * between the application and the database. All the query required of user view are describe here 
 * This class interacts with database to authenticate the user login process
 * furthermore this class also encrypts and decrypts the password
 */
public class UserModel {

    private Connection connection = null;
    private PreparedStatement addNewUser = null;
    private PreparedStatement checkUser = null;
    private PreparedStatement authenticateUserStmt = null;

    public UserModel() {
        try {
            connection = Database.getConnection();
            checkUser = connection.prepareStatement("SELECT * FROM user WHERE username = ? OR email = ?");
            addNewUser = connection.prepareStatement(
                    "INSERT INTO user (first_name, last_name, username, password, email) " +
                            "VALUES (?, ?, ?, ?, ?)");
            authenticateUserStmt = connection.prepareStatement("SELECT password FROM user WHERE email = ? OR username = ?");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            System.exit(1);
        }
    }
    
    public void addUser(String fName, String lName, String userName, String password, String email){
        try {
            // Hash the password before storing
            String hashedPassword = hashPassword(password);
            
            addNewUser.setString(1, fName);
            addNewUser.setString(2, lName);
            addNewUser.setString(3, userName);
            addNewUser.setString(4, hashedPassword);
            addNewUser.setString(5, email);
            
            int rowsAffected = addNewUser.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public boolean checkUserExists(String userName, String email) {
        try {
            checkUser.setString(1, userName);
            checkUser.setString(2, email);
            
            ResultSet resultSet = checkUser.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }
    
    public boolean authenticateUser(String identifier, String password) {
        try {
            authenticateUserStmt.setString(1, identifier);
            authenticateUserStmt.setString(2, identifier);
            
            ResultSet resultSet = authenticateUserStmt.executeQuery();
            
            if (resultSet.next()) {
                String hashedPasswordFromDB = resultSet.getString("password");
                String hashedPassword = hashPassword(password);
                
                return hashedPasswordFromDB.equals(hashedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //methods used to hash password
    private String hashPassword(String password) {
    try {
        // Obtain the SHA-1 message digest instance
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        
        byte[] hashedBytes = md.digest(password.getBytes());
        
        //initialize string builder variable
        StringBuilder sb = new StringBuilder();
        for (byte hashedByte : hashedBytes) {
            // Convert each byte to its hexadecimal representation and append to the string builder
            sb.append(Integer.toString((hashedByte & 0xff) + 0x100, 16).substring(1));
        }
        
        // Returnhashed password
        return sb.toString();
    } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return null;
    }
}
    
    // Close the connection when done to avoid resource leaks
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
