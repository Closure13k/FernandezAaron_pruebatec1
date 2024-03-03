package com.closure13k.aaronfmpt1.logic;

import com.closure13k.aaronfmpt1.logic.employee.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Clase encargada de validar la entrada de datos del usuario y modificar los atributos de un objeto Employee.
 * Depende de la clase OutputController para imprimir mensajes en la consola y de la clase InputController para recibir
 * la entrada del usuario.<p>
 * Utilizada por los métodos de creación y actualización.
 */
public final class EmployeeValidator {
    //!TODO: Refactorizar para separar la lógica de validación de la lógica de entrada.
    private static EmployeeValidator instance;
    private final OutputController messages = OutputController.getInstance();
    private final InputController inputs = InputController.getInstance();

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
        return requestAndValidateName(employee) && requestAndValidateSurname(employee)
                && requestAndValidateNif(employee) && requestAndValidateRole(employee)
                && requestAndValidateSalary(employee) && requestAndValidateHireDate(employee);
    }

    public boolean requestAndValidateName(Employee employee) {
        messages.print("Introduce el nombre del empleado:");
        String name = inputs.readString();
        if (name.isBlank()) {
            messages.print("El nombre no puede estar vacío.");
            return false;
        }
        employee.setName(name);
        return true;
    }

    public boolean requestAndValidateSurname(Employee employee) {
        messages.print("Introduce el apellido del empleado:");
        String surname = inputs.readString();
        if (surname.isBlank()) {
            messages.print("El apellido no puede estar vacío.");
            return false;
        }
        employee.setSurname(surname);
        return true;
    }

    public boolean requestAndValidateNif(Employee employee) {
        messages.print("Introduce el NIF del empleado:");
        String nif = inputs.readString();
        if (!nif.matches("[0-9]{8}[A-Za-z]")) {
            messages.print("El NIF debe tener 8 dígitos y una letra.");
            return false;
        }
        employee.setNif(nif);
        return true;
    }

    public boolean requestAndValidateRole(Employee employee) {
        messages.print("Introduce el cargo del empleado:");
        String role = inputs.readString();
        if (role.isBlank()) {
            messages.print("El cargo no puede estar vacío.");
            return false;
        }
        employee.setRole(role);
        return true;
    }

    public boolean requestAndValidateSalary(Employee employee) {
        messages.print("Introduce el salario del empleado:");
        try {
            double salary = Double.parseDouble(inputs.readString());
            if (salary <= 0) {
                messages.print("El salario no puede ser menor o igual a 0.");
                return false;
            }
            employee.setSalary(salary);
        } catch (NumberFormatException e) {
            messages.print("El salario debe ser un número.");
            return false;
        }
        return true;
    }

    public boolean requestAndValidateHireDate(Employee employee) {
        messages.print("Introduce la fecha de contratación del empleado (DD-MM-AAAA):");
        try {
            LocalDate hireDate = LocalDate.parse(inputs.readString(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            employee.setHireDate(hireDate);
        } catch (DateTimeParseException e) {
            messages.print("La fecha no es válida.");
            return false;
        }
        return true;
    }
}

