/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Date;

/**
 *
 * @author ivgarci
 */
public class FullTimeEmployee implements Employee {
    // Unique identifier for an employee
    private final int id;
    
    // Employees first name
    private String firstName = "";
    
    // Employees middle initial
    private String middleInitial = "";
    
    //Employeed last name
    private String lastName = "";
    
    // Employee birthday and year
    private Date dateOfBirth = new Date();
    
     // Employee start date
    private Date dateOfEmployment = new Date();
    
    // ACTIVE or INACTIVE
    private boolean status;
    
    public FullTimeEmployee(int id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * @return the firstName
     */
    @Override
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the middleInitial
     */
    @Override
    public String getMiddleInitial() {
        return middleInitial;
    }

    /**
     * @param middleInitial the middleInitial to set
     */
    @Override
    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    /**
     * @return the lastName
     */
    @Override
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the dateOfBirth
     */
    @Override
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    @Override
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * @return the dateOfEmployment
     */
    @Override
    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    /**
     * @param dateOfEmployment the dateOfEmployment to set
     */
    @Override
    public void setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    /**
     * @return the status
     */
    @Override
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

}