
package com.mycompany.qproperty.model;

import java.sql.Date;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: Property.java 
 * Date :6/7/2024 
 * Purpose : Describes the attributes of Property. It also support get() and set() methods along with toString()
 *
 * *
 */
public class Property {

    private String address;
    private String description;
    private Date builtYear;
    private String managingAgent;
    private String propertyType;
    private int customerID; // Foreign key to associate with Customer

    public Property() {
    }

    public Property(String address, String description, Date builtYear, String managingAgent, String propertyType, int customerID) {
        this.address = address;
        this.description = description;
        this.builtYear = builtYear;
        this.managingAgent = managingAgent;
        this.propertyType = propertyType;
        this.customerID = customerID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBuiltYear() {
        return builtYear;
    }

    public void setBuiltYear(Date builtYear) {
        this.builtYear = builtYear;
    }

    public String getManagingAgent() {
        return managingAgent;
    }

    public void setManagingAgent(String managingAgent) {
        this.managingAgent = managingAgent;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "address='" + address + '\'' + '\n'
                + "description='" + description + '\'' + '\n'
                + "builtYear=" + builtYear + '\n'
                + "managingAgent='" + managingAgent + '\'' + '\n'
                + "propertyType='" + propertyType + '\'' + '\n'
                + '\n';
    }

}
