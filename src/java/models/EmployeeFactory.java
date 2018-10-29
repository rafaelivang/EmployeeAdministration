package models;

/**
 * EmployeeFactory creates employees without exposing the creation logic.
 * @author ivgarci
 */
public class EmployeeFactory {
    private static int counter = 0;
    
    public static Employee createNewEmployee(EmployeeTypes employeeType) {
        if (EmployeeTypes.FULL_TIME_EMPLOYEE.equals(employeeType)) {
            return new FullTimeEmployee(counter++);
        }
        return null;
    }
}