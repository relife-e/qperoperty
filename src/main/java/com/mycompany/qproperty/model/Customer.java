
package com.mycompany.qproperty.model;


/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: Customer.java 
 * Date :6/7/2024 
 * Purpose : Describes the attributes of Customer. It also support get() and set() methods along with toString()
 *
 * *
 */
public class Customer {

    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public Customer() {
    }

    public Customer(int customerId, String firstName, String lastName, String address, String phone) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "firstName=" + firstName + '\n'
                + "lastName=" + lastName + '\n'
                + "phoneNumber=" + phone + '\n'
                + "address=" + address + '\n'
                ;
    }

}
