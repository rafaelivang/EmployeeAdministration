/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Date;

/**
 *
 * @author ivgarci
 */
public class EmployeeAdministrationUtils {
    
    public static String validateAndProcessString(String value) {
        return value == null ? "null" : value;
    }
    
    public static String validateAndProcessDate(Date date) {
        return date == null ? "null" : date.toString();
    }
    
}
