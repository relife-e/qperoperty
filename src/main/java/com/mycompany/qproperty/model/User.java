
package com.mycompany.qproperty.model;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: User.java 
 * Date :6/7/2024 
 * Purpose : Describes the attributes of User. It also support get() and set() methods along with toString()
 *
 * *
 */

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;

    public User(String firstName, String lastName, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
