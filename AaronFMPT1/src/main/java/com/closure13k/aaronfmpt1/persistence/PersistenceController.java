package com.closure13k.aaronfmpt1.persistence;

import com.closure13k.aaronfmpt1.logic.employee.Employee;

public class PersistenceController {
    EmployeeJpaController empJpaCont = new EmployeeJpaController();

    public void createEmployee(Employee employee) {
        empJpaCont.create(employee);
    }

    public void deleteEmployee(int id) {
        try {
            Employee employee = empJpaCont.findEmployee(id);
            employee.setActive(false);
            empJpaCont.edit(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateEmployee(Employee employee) {
        try {
            empJpaCont.edit(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
