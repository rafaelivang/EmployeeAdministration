/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package contollers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import models.Employee;
import models.EmployeeFactory;
import models.EmployeeTypes;
import utils.EmployeeAdministrationConstants;

/**
 *
 * @author ivgarci
 */
public class EmployeeManager {
    private static EmployeeManager emInstance = new EmployeeManager();
    private Map<Integer,Employee> employees = null;
    private Map<Integer,Employee> inactiveEmployees = null;
    
    private EmployeeManager() {
        this.employees = new HashMap<>();
        this.inactiveEmployees = new HashMap<>();
        
        ingestExternalData();
    }
    
    private void ingestExternalData() {
        try {
            BufferedReader bufferedFileReader = Files.newBufferedReader(Paths.get("C:\\Users\\ivgarci\\EmployeeAdministration\\src\\java\\externalData\\Employees"));
            JsonReader jsonReader = Json.createReader(bufferedFileReader);
            JsonObject employeesData = jsonReader.readObject();
            
            JsonArray fileEmployees = employeesData.getJsonArray("employees");
            for (int i = 0; i < fileEmployees.size(); i++) {
                JsonObject jo = fileEmployees.getJsonObject(i);
                createEmployee(jo);
            }
        } catch (FileNotFoundException fne) {
            Logger.getLogger(EmployeeManager.class.getName()).log(Level.SEVERE, null, fne);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Singleton implementation for EmployeeManager. Implemented as there is only one instance of EmployeeManager required.
     * @return EmployeeManager instance
     */
    public static EmployeeManager getInstance() {
        if (emInstance == null) {
            emInstance = new EmployeeManager();
        }
        return emInstance;
    }
    
    public Collection<Employee> getAllEmployees() {
        return this.employees.values();
    }
    
    public Employee getEmployee(int id) {
        return this.employees.get(id);
    }
    
    public boolean createEmployee(JsonObject employeeData) {
        Employee newEmployee = EmployeeFactory.createNewEmployee(EmployeeTypes.FULL_TIME_EMPLOYEE);
        
        try {
            newEmployee.setFirstName(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_FIRST_NAME));
            newEmployee.setLastName(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_LAST_NAME));
            newEmployee.setMiddleInitial(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_MIDDLE_INITIAL));
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date dateOfBirth = df.parse(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_DATE_OF_BIRTH));
            Date dateOfEmployment = df.parse(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_DATE_OF_EMPLOYMENT));
            if (dateOfBirth.after(dateOfEmployment)) {
                Logger.getGlobal().log(Level.WARNING, "Employee has not born yet! dateOfBirth: {0} dateOfEmployment: {1}", new Object[]{dateOfBirth, dateOfEmployment});
                return false;
            }
            newEmployee.setDateOfBirth(dateOfBirth);
            newEmployee.setDateOfEmployment(dateOfEmployment);
        } catch (ParseException ex) {
            // not created
            return false;
        }
        
        this.employees.put(newEmployee.getId(), newEmployee);
        return true;
    }
    
    public boolean updateEmployee(JsonObject employeeData) {
        int id = employeeData.getInt(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_ID);
        
        try {
            this.employees.get(id).setFirstName(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_FIRST_NAME));
            this.employees.get(id).setMiddleInitial(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_MIDDLE_INITIAL));
            this.employees.get(id).setLastName(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_LAST_NAME));
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date dateOfBirth = df.parse(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_DATE_OF_BIRTH));
            Date dateOfEmployment = df.parse(employeeData.getString(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_DATE_OF_EMPLOYMENT));
            if (dateOfBirth.after(dateOfEmployment)) {
                Logger.getGlobal().log(Level.WARNING, "Employee has not born yet! dateOfBirth: {0} dateOfEmployment: {1}", new Object[]{dateOfBirth, dateOfEmployment});
                return false;
            }
            this.employees.get(id).setDateOfBirth(dateOfBirth);
            this.employees.get(id).setDateOfEmployment(dateOfEmployment);
        } catch (ParseException ex) {
            // not updated
            return false;
        }
        
        return true;
    }
    
    public boolean deleteEmployee(int id) {
        Employee employee = this.employees.get(id);
        if (employee == null) {
            return false;
        }
        
        employee.setStatus(false);
        this.inactiveEmployees.put(id, employee);
        this.employees.remove(id);
        return true;
    }
    
}
