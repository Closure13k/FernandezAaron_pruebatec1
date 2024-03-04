package com.closure13k.aaronfmpt1.logic.employee;

import java.time.LocalDate;

/**
 * Encargada de la validación de datos de un empleado.
 */
public final class EmployeeValidator {
    // Expresión regular básica para validar el NIF.
    public static final String NIF_REGEX = "[0-9]{8}[A-Za-z]";

    // Formato de fecha para la validación de la fecha de contratación.
    public static final String DATE_FORMAT = "dd/MM/yyyy";

    // Instancia única de la clase.
    private static EmployeeValidator instance;

    /**
     * Devuelve la instancia única de la clase.
     */
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


    /**
     * Valida el nombre de un empleado.
     * Usado por {@link EmployeeDetailController#populateEmployeeName(Employee)} y
     *
     * @param name Nombre a validar.
     * @return true si el nombre no está vacío, false si lo está.
     */
    boolean isValidName(String name) {
        return !name.isBlank();
    }

    /**
     * Valida el apellido de un empleado.
     * Usado por {@link EmployeeDetailController#populateEmployeeSurname(Employee)} y
     *
     * @param surname Apellido a validar.
     * @return true si el apellido no está vacío, false si lo está.
     */

    boolean isValidSurname(String surname) {
        return !surname.isBlank();
    }


    /**
     * Valida el rol de un empleado.
     * Usado por {@link EmployeeDetailController#populateEmployeeRole(Employee)} y
     *
     * @param role Rol a validar.
     * @return true si el rol no está vacío, false si lo está.
     */
    boolean isValidRole(String role) {
        return !role.isBlank();
    }


    /**
     * Valida el salario de un empleado.
     * Usado por {@link EmployeeDetailController#populateEmployeeSalary(Employee)} y
     *
     * @param salary Salario a validar.
     * @return true si el salario es válido, false si no.
     */
    boolean isValidSalary(double salary) {
        return salary > 0;
    }


    /**
     * Valida el NIF de un empleado.
     * Usado por {@link EmployeeDetailController#populateEmployeeNif(Employee)} y
     *
     * @param nif NIF a validar.
     * @return true si el NIF es válido, false si no.
     */
    boolean isValidNif(String nif) {
        return nif.matches(NIF_REGEX);
    }


    /**
     * Valida la fecha de contratación de un empleado.
     * Usado por {@link EmployeeDetailController#populateEmployeeHireDate(Employee)} y
     *
     * @param hireDate Fecha de contratación a validar.
     * @return true si la fecha es válida, false si no.
     */
    boolean isValidHireDate(LocalDate hireDate) {
        return hireDate != null;
    }
}
