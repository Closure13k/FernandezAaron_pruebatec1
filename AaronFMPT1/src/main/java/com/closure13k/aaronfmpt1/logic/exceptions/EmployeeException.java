package com.closure13k.aaronfmpt1.logic.exceptions;

public class EmployeeException extends Exception {

    public static final String NOT_FOUND = "Empleado no encontrado.";
    public static final String NOT_FOUND_BY_ROLE = "No hay empleados con ese rol.";
    public static final String NO_EMPLOYEES = "No se encontraron empleados.";

    public EmployeeException(String message) {
        super(message);
    }


}
