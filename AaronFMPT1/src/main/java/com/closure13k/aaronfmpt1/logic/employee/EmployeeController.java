package com.closure13k.aaronfmpt1.logic.employee;

import com.closure13k.aaronfmpt1.logic.OutputController;
import com.closure13k.aaronfmpt1.logic.exceptions.EmployeeException;
import com.closure13k.aaronfmpt1.persistence.PersistenceController;
import com.closure13k.aaronfmpt1.persistence.exceptions.PreexistingEntityException;

import java.util.List;

public class EmployeeController {
    private static EmployeeController instance;
    private final PersistenceController pController = new PersistenceController();

    private final OutputController messages = OutputController.getInstance();

    /**
     * Obtiene la instancia de EmployeeController.
     */
    public static EmployeeController getInstance() {
        if (instance == null) {
            instance = new EmployeeController();
        }
        return instance;
    }

    /**
     * Constructor privado para el patrón Singleton.
     */
    private EmployeeController() {
        if (instance != null) {
            throw new IllegalStateException("EmployeeController ya ha sido instanciado.");
        }
    }

    /**
     * Elimina al empleado con el id especificado.
     */
    public void deleteEmployee(Employee employee) throws EmployeeException {
        try {
            pController.deleteEmployee(employee);
        } catch (RuntimeException e) {
            throw new EmployeeException("Hubo un problema al eliminar al empleado.\n" + e.getMessage());
        }
    }

    /**
     * Muestra a todos los empleados activos.
     */
    public void listAllActiveEmployees() throws EmployeeException {
        List<Employee> employees = pController.listAllActiveEmployees();
        if (employees.isEmpty()) {
            throw new EmployeeException(EmployeeException.NO_EMPLOYEES);
        }
        for (Employee e : employees) {
            System.out.println(e.getFormattedDetails());
        }
    }

    public void listAllActiveEmployeesByRole(String role) throws EmployeeException {
        List<Employee> employees = pController.listAllActiveEmployeesByRole(role);
        if (employees.isEmpty()) {
            throw new EmployeeException(EmployeeException.NOT_FOUND_BY_ROLE);
        }
        for (Employee e : employees) {
            System.out.println(e.getFormattedDetails());
        }
    }


    /**
     * Actualiza al empleado.
     */
    public void updateEmployee(Employee employee) throws EmployeeException {
        try {
            pController.updateEmployee(employee);
        } catch (Exception e) {
            throw new EmployeeException("Hubo un problema al actualizar al empleado.\n" + e.getMessage());
        }
    }

    public Employee findEmployeeById(int id) throws EmployeeException {

        Employee employee = pController.findEmployeeById(id);
        if (employee == null) {
            throw new EmployeeException(EmployeeException.NOT_FOUND);
        }

        return employee;
    }


    public Employee findEmployeeByNif(String nif) throws EmployeeException {

        Employee employee = pController.findEmployeeByNif(nif);
        if (employee == null) {
            throw new EmployeeException(EmployeeException.NOT_FOUND);
        }

        return employee;
    }

    public void batchCreateEmployees(List<Employee> employees) throws EmployeeException {
        try {
            pController.batchCreateEmployees(employees);
        } catch (Exception e) {
            throw new EmployeeException("Hubo un problema al crear a los empleados.\n" + e.getMessage());
        }
    }
}
