package com.closure13k.aaronfmpt1.logic.employee;

import com.closure13k.aaronfmpt1.logic.InputController;
import com.closure13k.aaronfmpt1.logic.OutputController;

import java.time.LocalDate;

/**
 * Encargada de la recogida y validación de los datos de un empleado para los procesos de creación y actualización.
 */
public class EmployeeHandler {

    private static EmployeeHandler instance;

    private final OutputController messages = OutputController.getInstance();
    private final InputController inputCon = InputController.getInstance();
    private final EmployeeValidator employeeValidator = EmployeeValidator.getInstance();


    public static EmployeeHandler getInstance() {
        if (instance == null) {
            instance = new EmployeeHandler();
        }
        return instance;
    }

    private EmployeeHandler() {
        if (instance != null) {
            throw new IllegalStateException("EmployeeHandler ya ha sido instanciado.");
        }
    }


    public boolean populateEmployeeName(Employee employee) {
        String name = inputCon.requestString("Ingrese el nombre del empleado: ");
        if (employeeValidator.isValidName(name)) {
            employee.setName(name);
        } else {
            messages.print("Nombre inválido.");
            return false;
        }
        return true;
    }

    public boolean populateEmployeeSurname(Employee employee) {
        String surname = inputCon.requestString("Ingrese el apellido del empleado: ");
        if (employeeValidator.isValidSurname(surname)) {
            employee.setSurname(surname);
        } else {
            messages.print("Apellido inválido.");
            return false;
        }
        return true;
    }

    public boolean populateEmployeeNif(Employee employee) {
        String nif = inputCon.requestString("Ingrese el NIF del empleado: ");
        if (employeeValidator.isValidNif(nif)) {
            employee.setNif(nif);
        } else {
            messages.print("NIF inválido.");
            return false;
        }
        return true;
    }

    public boolean populateEmployeeRole(Employee employee) {
        String role = inputCon.requestString("Ingrese el cargo del empleado: ");
        if (employeeValidator.isValidRole(role)) {
            employee.setRole(role);
        } else {
            messages.print("Cargo inválido.");
            return false;
        }
        return true;
    }

    public boolean populateEmployeeSalary(Employee employee) {
        double salary = inputCon.requestDouble("Ingrese el salario del empleado: ");
        if (employeeValidator.isValidSalary(salary)) {
            employee.setSalary(salary);
        } else {
            messages.print("Salario inválido.");
            return false;
        }
        return true;
    }

    public boolean populateEmployeeHireDate(Employee employee) {
        LocalDate hireDate = inputCon.requestDate(
                "Ingrese la fecha de contratación del empleado (dd/MM/yyyy): ",
                EmployeeValidator.DATE_FORMAT);
        if (employeeValidator.isValidHireDate(hireDate)) {
            employee.setHireDate(hireDate);
        } else {
            messages.print("Fecha inválida.");
            return false;
        }
        return true;
    }

}
