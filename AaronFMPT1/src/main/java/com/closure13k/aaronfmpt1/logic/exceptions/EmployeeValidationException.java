package com.closure13k.aaronfmpt1.logic.exceptions;

public class EmployeeValidationException extends IllegalArgumentException {
    public static final String INVALID_NAME = "El nombre no puede estar vacío.";
    public static final String INVALID_SURNAME = "El apellido no puede estar vacío.";
    public static final String INVALID_ROLE = "El rol no puede estar vacío.";
    public static final String INVALID_NIF = "El NIF no es válido. Debe tener 8 dígitos seguidos de una letra.";
    public static final String INVALID_SALARY = "El salario debe ser mayor que 0.";
    public static final String INVALID_HIRE_DATE = "La fecha de contratación no puede estar vacía o no sigue el formato dd/MM/yyyy.";


    public EmployeeValidationException(String message) {
        super(message);
    }
}
