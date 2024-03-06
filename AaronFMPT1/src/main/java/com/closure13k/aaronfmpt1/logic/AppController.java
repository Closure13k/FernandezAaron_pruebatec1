package com.closure13k.aaronfmpt1.logic;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.logic.employee.EmployeeController;
import com.closure13k.aaronfmpt1.logic.employee.EmployeeDetailController;
import com.closure13k.aaronfmpt1.logic.employee.exceptions.EmployeeException;
import com.closure13k.aaronfmpt1.logic.employee.exceptions.EmployeeValidationException;

import java.util.ArrayList;
import java.util.List;

import static com.closure13k.aaronfmpt1.logic.OutputController.MAIN_MENU;

public final class AppController {
    private final InputController inputs = InputController.getInstance();
    private final OutputController messages = OutputController.getInstance();
    private final EmployeeController empController = EmployeeController.getInstance();
    private final EmployeeDetailController empDetails = EmployeeDetailController.getInstance();

    public void beginApp() {
        messages.welcome();
        int option;
        do {
            messages.print(MAIN_MENU);
            option = inputs.requestInt("Ingrese su opción: ");
            try {
                switch (option) {
                    case 1 -> createEmployeeBatch();
                    case 2 -> deleteEmployee();
                    case 3 -> empController.listAllActiveEmployees();
                    case 4 -> listEmployeesByRole();
                    case 5 -> updateEmployee();
                    case -1 -> messages.print("¡Hasta luego!");
                    default -> messages.invalidOption();
                }
            } catch (EmployeeException | EmployeeValidationException e) {
                messages.print(e.getMessage());
            }
        } while (option != -1);

        inputs.close();
    }

    /**
     * Inicia el proceso de creación de empleados.
     * Se pueden crear hasta 10 empleados en un mismo lote.
     *
     * @throws EmployeeException Si no se pueden crear los empleados.
     */
    private void createEmployeeBatch() throws EmployeeException {
        List<Employee> employees = new ArrayList<>();
        String option = "s";
        String confirmSave;
        int batchSize = 10;

        while (option.equalsIgnoreCase("s") && employees.size() < batchSize) {
            if (!employees.isEmpty()) {
                option = inputs.requestString("¿Desea agregar otro empleado?\n(Escriba 'S' o cualquier otra tecla para no): ");
                if (!option.equalsIgnoreCase("s")) {
                    break;
                }
            }

            Employee employee = new Employee();
            int attempts = 3;
            do {
                try {
                    if (attempts < 3) {
                        messages.print("Por favor, intente de nuevo. Le quedan " + attempts + " intentos.");
                    }

                    populateAllDetails(employee);

                    messages.print("Empleado añadido a lote:\n" + employee.getFormattedDetails());
                    break;
                } catch (EmployeeValidationException e) {
                    messages.print(e.getMessage());
                    attempts--;
                }
            } while (attempts > 0);
            if (attempts == 0) {
                messages.print("Demasiados intentos fallidos.");
                break;
            }
            employees.add(employee);
        }

        if (employees.isEmpty()) {
            messages.print("No se han añadido empleados al lote.");
            return;
        }
        //Mostramos el lote.
        messages.print("Empleados a guardar:");
        for (Employee e : employees) {
            messages.print(e.getFormattedDetails());
        }

        confirmSave = inputs.requestString("¿Desea guardar los empleados?\n(Escriba 'S' o cualquier otra tecla para no): ");
        if (confirmSave.equalsIgnoreCase("s")) {
            //Llamada al controlador para crear los empleados.
            empController.batchCreateEmployees(employees);
            messages.print("Registro terminado.");
        } else {
            messages.print("Creación de empleados cancelada.");
        }
    }

    /**
     * Inicia el proceso de eliminación de un empleado.
     *
     * @throws EmployeeException Si no se puede eliminar al empleado.
     */
    private void deleteEmployee() throws EmployeeException {
        Employee employee;

        messages.print("¿Desea buscar al empleado por id o por NIF?");
        int option = inputs.requestInt("1. Id\n2. NIF\nOpción: ");

        switch (option) {
            case 1 -> {
                int id = inputs.requestInt("Ingrese el id del empleado: ");
                employee = empController.findEmployeeById(id);
            }
            case 2 -> {
                String nif = inputs.requestString("Ingrese el NIF del empleado: ");
                employee = empController.findEmployeeByNif(nif);
            }
            default -> {
                messages.invalidOption();
                return;
            }
        }

        if (employee == null) {
            messages.print("Empleado no encontrado.");
            return;
        }

        String confirmDelete = inputs.requestString(employee.getFormattedDetails()
                + "\nPresione 'S' para confirmar la eliminación o cualquier otra tecla para cancelar.");
        if (confirmDelete.equalsIgnoreCase("s")) {
            empController.deleteEmployee(employee);
            messages.print("Empleado eliminado.");
        } else {
            messages.print("Eliminación cancelada.");

        }


    }


    /**
     * Inicia el proceso de actualización de un empleado.
     *
     * @throws EmployeeException Si no se puede actualizar al empleado.
     */
    private void updateEmployee() throws EmployeeException {
        Employee employee;

        messages.print("¿Desea buscar al empleado por id o por NIF?");
        int option = inputs.requestInt("1. Id\n2. NIF\nOpción: ");

        switch (option) {
            case 1 -> {
                int id = inputs.requestInt("Ingrese el id del empleado: ");
                employee = empController.findEmployeeById(id);
            }
            case 2 -> {
                String nif = inputs.requestString("Ingrese el NIF del empleado: ");
                employee = empController.findEmployeeByNif(nif);
            }
            default -> {
                messages.invalidOption();
                return;
            }
        }

        if (employee == null) {
            messages.print("Empleado no encontrado.");
            return;
        }

        updateMenu(employee);
    }

    /**
     * Muestra el menú de actualización para escoger qué campo actualizar.
     *
     * @param employee El empleado a actualizar.
     */
    private void updateMenu(Employee employee) throws EmployeeException {
        int attempts = 3;
        while (attempts > 0) {
            messages.print("¿Qué campo desea actualizar?");
            messages.updateMenuPrompt(employee.getFormattedDetails());
            int field = inputs.requestInt("Opción: ");
            try {
                switch (field) {
                    case 1 -> empDetails.populateEmployeeName(employee);
                    case 2 -> empDetails.populateEmployeeSurname(employee);
                    case 3 -> empDetails.populateEmployeeRole(employee);
                    case 4 -> empDetails.populateEmployeeSalary(employee);
                    case 5 -> empDetails.populateEmployeeHireDate(employee);
                    case 0 -> {
                        empController.updateEmployee(employee);
                        return;
                    }
                    case -1 -> {
                        messages.print("Cambios cancelados.");
                        return;
                    }
                    default -> messages.invalidOption();
                }
            } catch (EmployeeValidationException e) {
                messages.print(e.getMessage());
                attempts--;
            }
        }
    }


    /**
     * Inicia el proceso de listar empleados por rol.
     *
     * @throws EmployeeException Si no hay empleados con el rol especificado.
     */
    private void listEmployeesByRole() throws EmployeeException {
        String role = inputs.requestString("Ingrese el cargo del empleado: ");
        if (role.isBlank()) {
            messages.print("El cargo no puede estar vacío.");
            return;
        }
        empController.listAllActiveEmployeesByRole(role);
    }

    /**
     * Llena todos los detalles del empleado.
     *
     * @param employee El empleado a llenar.
     * @throws EmployeeValidationException Si algún detalle no pasa la validación.
     */
    private void populateAllDetails(Employee employee) throws EmployeeValidationException {
        empDetails.populateEmployeeName(employee);
        empDetails.populateEmployeeSurname(employee);
        empDetails.populateEmployeeNif(employee);
        empDetails.populateEmployeeRole(employee);
        empDetails.populateEmployeeSalary(employee);
        empDetails.populateEmployeeHireDate(employee);
    }
}
