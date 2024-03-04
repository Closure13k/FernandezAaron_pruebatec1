package com.closure13k.aaronfmpt1.logic.employee;

import java.time.LocalDate;

/**
 * Encargada de la validación de datos de un empleado.
 */
public final class EmployeeValidator {
    public static final String NIF_REGEX = "[0-9]{8}[A-Za-z]";
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    private static EmployeeValidator instance;

    public static EmployeeValidator getInstance() {
        if (instance == null) {
            instance = new EmployeeValidator();
        }
        return instance;
    }

    private EmployeeValidator() {
        if (instance != null) {
            throw new IllegalStateException("InputValidator ya ha sido instanciado.");
        }
    }

    public boolean isValidEmployeeInput(Employee employee) {
        return isValidName(employee.getName())
                && isValidSurname(employee.getSurname())
                && isValidNif(employee.getNif())
                && isValidRole(employee.getRole())
                && isValidSalary(employee.getSalary())
                && isValidHireDate(employee.getHireDate());
    }

    public boolean isValidName(String name) {
        return !name.isBlank();
    }

    public boolean isValidSurname(String surname) {
        return !surname.isBlank();
    }

    public boolean isValidRole(String role) {
        return !role.isBlank();
    }

    public boolean isValidSalary(double salary) {
        return salary > 0;
    }

    public boolean isValidNif(String nif) {
        return nif.matches(NIF_REGEX);
    }

    public boolean isValidHireDate(LocalDate hireDate) {
        return hireDate != null;
    }
}
