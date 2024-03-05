package com.closure13k.aaronfmpt1.logic.employee;

import com.closure13k.aaronfmpt1.logic.InputController;
import com.closure13k.aaronfmpt1.logic.exceptions.EmployeeValidationException;

import java.time.LocalDate;

import static com.closure13k.aaronfmpt1.logic.exceptions.EmployeeValidationException.INVALID_HIRE_DATE;
import static com.closure13k.aaronfmpt1.logic.exceptions.EmployeeValidationException.INVALID_NIF;
import static com.closure13k.aaronfmpt1.logic.exceptions.EmployeeValidationException.INVALID_ROLE;
import static com.closure13k.aaronfmpt1.logic.exceptions.EmployeeValidationException.INVALID_SALARY;
import static com.closure13k.aaronfmpt1.logic.exceptions.EmployeeValidationException.INVALID_SURNAME;


/**
 * Clase encargada de la recepción y validación de los datos de un empleado para los procesos de
 * creación y actualización.
 */
public class EmployeeDetailController {

    // Singleton
    private static EmployeeDetailController instance;

    private final InputController inputController = InputController.getInstance();
    private final EmployeeValidator employeeValidator = EmployeeValidator.getInstance();


    /**
     * Devuelve la instancia de la clase.
     */
    public static EmployeeDetailController getInstance() {
        if (instance == null) {
            instance = new EmployeeDetailController();
        }
        return instance;
    }

    private EmployeeDetailController() {
        if (instance != null) {
            throw new IllegalStateException("EmployeeHandler ya ha sido instanciado.");
        }
    }


    /**
     * Solicita y registra el nombre de un empleado.<p>
     * Validado por {@link EmployeeValidator#isValidName(String)}
     *
     * @param employee Objeto empleado al que se le asignará el nombre.
     * @throws EmployeeValidationException Si el nombre introducido no es válido (vacío).
     */
    public void populateEmployeeName(Employee employee) throws EmployeeValidationException {
        String name = inputController.requestString("Ingrese el nombre del empleado: ");
        if (!employeeValidator.isValidName(name)) {
            throw new EmployeeValidationException(EmployeeValidationException.INVALID_NAME);
        }
        employee.setName(name);
    }

    /**
     * Solicita y registra el apellido de un empleado.
     * Validado por {@link EmployeeValidator#isValidSurname(String)}
     *
     * @param employee Objeto empleado al que se le asignará el apellido.
     * @throws EmployeeValidationException Si el apellido introducido no es válido (vacío).
     */

    public void populateEmployeeSurname(Employee employee) throws EmployeeValidationException {
        String surname = inputController.requestString("Ingrese el apellido del empleado: ");
        if (!employeeValidator.isValidSurname(surname)) {
            throw new EmployeeValidationException(INVALID_SURNAME);
        }
        employee.setSurname(surname);
    }

    /**
     * Solicita y registra el NIF de un empleado.
     * Validado por {@link EmployeeValidator#isValidNif(String)}
     *
     * @param employee Objeto empleado al que se le asignará el NIF.
     * @throws EmployeeValidationException Si el NIF introducido no es válido.
     */
    public void populateEmployeeNif(Employee employee) throws EmployeeValidationException {
        String nif = inputController.requestString("Ingrese el NIF del empleado: ");
        if (!employeeValidator.isValidNif(nif)) {
            throw new EmployeeValidationException(INVALID_NIF);
        }
        employee.setNif(nif);
    }

    /**
     * Solicita y registra el cargo de un empleado.
     * Validado por {@link EmployeeValidator#isValidRole(String)}
     *
     * @param employee Objeto empleado al que se le asignará el cargo.
     * @throws EmployeeValidationException Si el cargo introducido no es válido (vacío).
     */
    public void populateEmployeeRole(Employee employee) throws EmployeeValidationException {
        String role = inputController.requestString("Ingrese el cargo del empleado: ");
        if (!employeeValidator.isValidRole(role)) {
            throw new EmployeeValidationException(INVALID_ROLE);
        }
        employee.setRole(role);
    }

    /**
     * Solicita y registra el salario de un empleado.
     * Validado por {@link EmployeeValidator#isValidSalary(double)}
     *
     * @param employee Objeto empleado al que se le asignará el salario.
     * @throws EmployeeValidationException Si el salario introducido no es válido (menor o igual a 0).
     */
    public void populateEmployeeSalary(Employee employee) throws EmployeeValidationException {
        double salary = inputController.requestDouble("Ingrese el salario del empleado: ");
        if (!employeeValidator.isValidSalary(salary)) {
            throw new EmployeeValidationException(INVALID_SALARY);
        }
        employee.setSalary(salary);
    }

    /**
     * Solicita y registra la fecha de contratación de un empleado.
     * Validado por {@link EmployeeValidator#isValidHireDate(LocalDate)}
     *
     * @param employee Objeto empleado al que se le asignará la fecha de contratación.
     * @throws EmployeeValidationException Si la fecha introducida no es válida (vacía o no sigue el formato dd/MM/yyyy).
     */
    public void populateEmployeeHireDate(Employee employee) throws EmployeeValidationException {
        LocalDate hireDate = inputController.requestDate(
                "Ingrese la fecha de contratación del empleado (dd/MM/yyyy): ",
                EmployeeValidator.DATE_FORMAT);
        if (!employeeValidator.isValidHireDate(hireDate)) {
            throw new EmployeeValidationException(INVALID_HIRE_DATE);
        }
        employee.setHireDate(hireDate);
    }

}
