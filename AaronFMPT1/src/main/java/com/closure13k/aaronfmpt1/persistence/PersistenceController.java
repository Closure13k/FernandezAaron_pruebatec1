package com.closure13k.aaronfmpt1.persistence;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.persistence.exceptions.NonexistentEntityException;

import javax.persistence.NoResultException;
import java.util.List;

public class PersistenceController {
    EmployeeJpaController empJpaCont = new EmployeeJpaController();

    public void createEmployee(Employee employee) {
        empJpaCont.create(employee);
    }

    public Employee findEmployeeById(int id) {
        return empJpaCont.findEmployee(id);
    }

    public void deleteEmployee(Employee employee) {
        try {
            employee.setActive(false);
            empJpaCont.edit(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Employee> listAllActiveEmployees() {
        return empJpaCont.findActiveEmployeeEntities();
    }


    public List<Employee> listAllActiveEmployeesByRole(String role) {
        return empJpaCont.findActiveEmployeeEntitiesByRole(role);
    }

    public void updateEmployee(Employee employee) {
        try {
            empJpaCont.edit(employee);
        } catch (Exception e) {
            throw new RuntimeException(e); //!TODO: Custom exception
        }
    }


}
