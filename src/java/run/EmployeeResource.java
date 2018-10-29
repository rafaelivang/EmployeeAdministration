/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Contributor(s):
 * 
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 * 
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package run;

import contollers.EmployeeManager;
import java.io.StringReader;
import java.util.Collection;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import models.Employee;
import utils.EmployeeAdministrationConstants;
import utils.EmployeeAdministrationUtils;

@Stateless
@Path("")
public class EmployeeResource {
    
    /**
     * Get all employees
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employees")
    public Response getEmployees() {
        try {
            EmployeeManager manager = EmployeeManager.getInstance();
            Collection<Employee> employees = manager.getAllEmployees();
            JsonArrayBuilder employeesJsonBuilder = Json.createArrayBuilder();
            employees.forEach((employee) -> {
                employeesJsonBuilder
                        .add(Json.createObjectBuilder()
                            .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_ID, employee.getId())
                            .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_FIRST_NAME, EmployeeAdministrationUtils.validateAndProcessString(employee.getFirstName()))
                            .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_MIDDLE_INITIAL, EmployeeAdministrationUtils.validateAndProcessString(employee.getMiddleInitial()))
                            .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_LAST_NAME, EmployeeAdministrationUtils.validateAndProcessString(employee.getLastName()))
                            .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_DATE_OF_EMPLOYMENT, EmployeeAdministrationUtils.validateAndProcessDate(employee.getDateOfEmployment()))
                            .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_DATE_OF_BIRTH, EmployeeAdministrationUtils.validateAndProcessDate(employee.getDateOfBirth()))
                        );
            });
            JsonObject employeeJson = Json.createObjectBuilder().add("employees", employeesJsonBuilder).build();
            return Response.status(Response.Status.OK).entity(Objects.toString(employeeJson.toString())).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get employees by an ID
     * @param id
     * @return 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/employee")
    public Response getEmployeeById(@QueryParam("id") String id) {
        try {
            EmployeeManager manager = EmployeeManager.getInstance();
            Employee employee = manager.getEmployee(Integer.parseInt(id));
            
            JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
            if (employee == null) {
                return Response.status(Response.Status.OK).entity(Objects.toString(jsonBuilder.build().toString())).build();
            }
            
            JsonObject employeeJson = jsonBuilder
                        .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_ID, employee.getId())
                        .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_FIRST_NAME, EmployeeAdministrationUtils.validateAndProcessString(employee.getFirstName()))
                        .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_MIDDLE_INITIAL, EmployeeAdministrationUtils.validateAndProcessString(employee.getMiddleInitial()))
                        .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_LAST_NAME, EmployeeAdministrationUtils.validateAndProcessString(employee.getLastName()))
                        .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_DATE_OF_EMPLOYMENT, EmployeeAdministrationUtils.validateAndProcessDate(employee.getDateOfEmployment()))
                        .add(EmployeeAdministrationConstants.EMPLOYEE_PROPERTY_DATE_OF_BIRTH, EmployeeAdministrationUtils.validateAndProcessDate(employee.getDateOfBirth())).build();
            return Response.status(Response.Status.OK).entity(Objects.toString(employeeJson.toString())).build();
        } catch(NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Creates a new employee
     * @param employee 
     * @return  
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Path("/createEmployee")
    public Response createEmployee(String employee) {
        try {
            EmployeeManager manager = EmployeeManager.getInstance();
            JsonReader jsonReader = Json.createReader(new StringReader(employee));
            JsonObject employeeData = jsonReader.readObject();
            if (manager.createEmployee(employeeData)) {
                return Response.status(Response.Status.CREATED).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(EmployeeAdministrationConstants.INVALID_PARAMETERS_MESSAGE).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Updates existing employee
     * @param employee 
     * @return  
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    @Path("/updateEmployee")
    public Response updateEmployee(String employee) {
        try {
            EmployeeManager manager = EmployeeManager.getInstance();
            JsonReader jsonReader = Json.createReader(new StringReader(employee));
            JsonObject employeeData = jsonReader.readObject();
            if (manager.updateEmployee(employeeData)) {
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).entity(EmployeeAdministrationConstants.INVALID_PARAMETERS_MESSAGE).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Delete employee
     * @param id
     * @return  
     */
    @DELETE
    @Consumes(MediaType.TEXT_HTML)
    @Path("/deleteEmployee")
    public Response deleteEmployee(@QueryParam("id") String id) {
        try {
            EmployeeManager manager = EmployeeManager.getInstance();
            if (manager.deleteEmployee(Integer.parseInt(id))) {
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch(NumberFormatException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }   
}