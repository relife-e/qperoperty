
package com.mycompany.qproperty.model;

/**
 *
 * @author Anmol Saru Magar & Roshan Phakami PunMagar 
 * File Name: RepairJob.java 
 * Date :6/7/2024 
 * Purpose : Describes the attributes of RepairJob. It also support get() and set() methods along with toString()
 *
 * *
 */
import java.util.Date;

public class RepairJob {
    private int jobID;
    private String description;
    private Date bookingDate;
    private Date completionDate;
    private double charge;
    private String serviceStaff;
    private String jobType;
    private String address; 

    public RepairJob() {}

    public RepairJob(int id, String adr, String description, Date bookingDate, Date completionDate, double charge, String serviceStaff, String jobType) {
        this.address = adr;
        this.jobID = id;
        this.description = description;
        this.bookingDate = bookingDate;
        this.completionDate = completionDate;
        this.charge = charge;
        this.serviceStaff = serviceStaff;
        this.jobType = jobType;
    }

    // Getters and setters

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getServiceStaff() {
        return serviceStaff;
    }

    public void setServiceStaff(String serviceStaff) {
        this.serviceStaff = serviceStaff;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    @Override
    public String toString() {
    return "Repair Job ID: " + jobID + "\n" +
            "Description: " + description + "\n" +
            "Booking Date: " + bookingDate + "\n" +
            "Completion Date: " + completionDate + "\n" +
            "Charge: " + charge + "\n" +
            "Service Staff: " + serviceStaff + "\n" +
            "Job Type: " + jobType + "\n" +
            "Address: " + address + "\n" +  "\n";
}
}
