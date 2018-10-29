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
public interface Employee {

    /**
     * @return the id
     */
    public int getId();

    /**
     * @return the firstName
     */
    public String getFirstName();

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName);

    /**
     * @return the middleInitial
     */
    public String getMiddleInitial();

    /**
     * @param middleInitial the middleInitial to set
     */
    public void setMiddleInitial(String middleInitial);

    /**
     * @return the lastName
     */
    public String getLastName();

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName);

    /**
     * @return the dateOfBirth
     */
    public Date getDateOfBirth();

    /**
     * @param dateOfBirth the dateOfBirth to set
     */
    public void setDateOfBirth(Date dateOfBirth);

    /**
     * @return the dateOfEmployment
     */
    public Date getDateOfEmployment();

    /**
     * @param dateOfEmployment the dateOfEmployment to set
     */
    public void setDateOfEmployment(Date dateOfEmployment);

    /**
     * @return the status
     */
    public boolean isStatus();

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status);
    
}
