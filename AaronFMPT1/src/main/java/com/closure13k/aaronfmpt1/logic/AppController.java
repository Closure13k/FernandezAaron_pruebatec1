package com.closure13k.aaronfmpt1.logic;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.logic.employee.EmployeeController;
import com.closure13k.aaronfmpt1.logic.employee.EmployeeDetailController;
import com.closure13k.aaronfmpt1.logic.employee.EmployeeValidationException;

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
            switch (option) {
                case 1 -> createEmployee();
                case 2 -> deleteEmployee();
                case 3 -> empController.listAllActiveEmployees();
                case 4 -> listEmployeesByRole();
                case 5 -> updateEmployee();
                case -1 -> messages.print("¡Hasta luego!");
                default -> messages.invalidOption();
            }
        } while (option != -1);
    }

    private void createEmployee() {
        //!TODO: Implementar creado por lotes.

        Employee employee = new Employee();
        int attempts = 3;
        do {
            try {
                if (attempts < 3) {
                    messages.print("Por favor, intente de nuevo. Le quedan " + attempts + " intentos.");
                }

                populateAllDetails(employee);

                empController.createEmployee(employee);
                messages.print("Empleado creado con éxito:\n" + employee.getFormattedDetails());
                return;
            } catch (EmployeeValidationException e) {
                messages.print(e.getMessage());
                attempts--;
            }
        } while (attempts > 0);
        messages.print("Demasiados intentos fallidos. Regresando al menú principal.");
    }

    private void populateAllDetails(Employee employee) throws EmployeeValidationException {
        empDetails.populateEmployeeName(employee);
        empDetails.populateEmployeeSurname(employee);
        empDetails.populateEmployeeNif(employee);
        empDetails.populateEmployeeRole(employee);
        empDetails.populateEmployeeSalary(employee);
        empDetails.populateEmployeeHireDate(employee);
    }

    private void updateEmployee() {
        messages.print("¿Desea buscar al empleado por id o por NIF?");
        int option = inputs.requestInt("1. Id\n2. NIF\nOpción: ");
        Employee employee;
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

    private void updateMenu(Employee employee) {
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

    private void deleteEmployee() {
        //Solicitud búsqueda por id o nif
        //Quiere buscar por id o nif?
        messages.print("¿Desea buscar al empleado por id o por NIF?");
        int option = inputs.requestInt("1. Id\n2. NIF\nOpción: ");
        Employee employee;
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
        inputs.requestString(employee.getFormattedDetails()
                + "\nPresione s para confirmar la eliminación o cualquier otra tecla para cancelar.");

        empController.deleteEmployee(employee);
    }

    private void listEmployeesByRole() {
        String role = inputs.requestString("Ingrese el cargo del empleado: ");
        if (role.isBlank()) {
            messages.print("El cargo no puede estar vacío.");
            return;
        }
        empController.listAllActiveEmployeesByRole(role);
    }

}
