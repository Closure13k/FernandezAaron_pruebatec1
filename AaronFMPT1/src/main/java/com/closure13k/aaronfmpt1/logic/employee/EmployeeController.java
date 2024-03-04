package com.closure13k.aaronfmpt1.logic.employee;

import com.closure13k.aaronfmpt1.persistence.PersistenceController;

import java.util.List;

public class EmployeeController {
    private static EmployeeController instance;
    private final PersistenceController pController = new PersistenceController();

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
     * Valida y crea al empleado.
     */
    public void createEmployee(Employee employee) {
        pController.createEmployee(employee);
    }

    /**
     * Elimina al empleado con el id especificado.
     */
    public void deleteEmployee(Employee employee) {
        pController.deleteEmployee(employee);
    }

    /**
     * Muestra a todos los empleados activos.
     */
    public void listAllActiveEmployees() {
        List<Employee> employees = pController.listAllActiveEmployees();
        for (Employee e : employees) {
            System.out.println(e.getFormattedDetails());
        }
    }

    public void listAllActiveEmployeesByRole(String role) {
        List<Employee> employees = pController.listAllActiveEmployeesByRole(role);
        for (Employee e : employees) {
            System.out.println(e.getFormattedDetails());
        }
    }


    /**
     * Actualiza al empleado.
     */
    public void updateEmployee(Employee employee) {
        pController.updateEmployee(employee);
    }

    //!TODO: Los find son para actualizar, no para mostrar.
    //!TODO: Manejar null-check.
    public Employee findEmployeeById(int id) {
        return pController.findEmployeeById(id);
    }


    public Employee findEmployeeByNif(String nif) {
        return pController.findEmployeeByNif(nif);
    }
}
