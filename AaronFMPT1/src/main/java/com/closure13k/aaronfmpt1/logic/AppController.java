package com.closure13k.aaronfmpt1.logic;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.logic.employee.EmployeeController;
import com.closure13k.aaronfmpt1.logic.employee.EmployeeHandler;

import static com.closure13k.aaronfmpt1.logic.OutputController.MAIN_MENU;

public final class AppController {
    private final OutputController messages = OutputController.getInstance();
    private final EmployeeController employeeCon = EmployeeController.getInstance();
    private final InputController inputCon = InputController.getInstance();
    private final EmployeeHandler employeeHandler = EmployeeHandler.getInstance();

    public void beginApp() {
        messages.welcome();
        int option;
        do {
            messages.print(MAIN_MENU);
            option = inputCon.requestInt("Ingrese su opción: ");
            switch (option) {
                case 1 -> createEmployee();
                case 2 -> deleteEmployee();
                case 3 -> employeeCon.listAllActiveEmployees();
                case 4 -> listEmployeesByRole();
                case 5 -> updateEmployee();
                case -1 -> messages.print("¡Hasta luego!");
                default -> messages.invalidOption();
            }
        } while (option != -1);
    }

    private void createEmployee() {
        Employee employee = new Employee();
        int attempts = 3;
        do {
            if (attempts < 3) {
                messages.print("Por favor, intente de nuevo. Le quedan " + attempts + " intentos.");
            }
            if (!employeeHandler.populateEmployeeName(employee)) {
                attempts--;
                continue;
            }
            if (!employeeHandler.populateEmployeeSurname(employee)) {
                attempts--;
                continue;
            }
            if (!employeeHandler.populateEmployeeNif(employee)) {
                attempts--;
                continue;
            }
            if (!employeeHandler.populateEmployeeRole(employee)) {
                attempts--;
                continue;
            }
            if (!employeeHandler.populateEmployeeSalary(employee)) {
                attempts--;
                continue;
            }
            if (!employeeHandler.populateEmployeeHireDate(employee)) {
                attempts--;
                continue;
            }
            employeeCon.createEmployee(employee);
            messages.print("Empleado creado con éxito:\n" + employee.getFormattedDetails());
            return;
        } while (attempts > 0);
        messages.print("Demasiados intentos fallidos. Regresando al menú principal.");
    }

    private void updateEmployee() {
        messages.print("¿Desea buscar al empleado por id o por NIF?");
        int option = inputCon.requestInt("1. Id\n2. NIF\nOpción: ");
        Employee employee;
        switch (option) {
            case 1 -> {
                int id = inputCon.requestInt("Ingrese el id del empleado: ");
                employee = employeeCon.findEmployeeById(id);
            }
            case 2 -> {
                String nif = inputCon.requestString("Ingrese el NIF del empleado: ");
                employee = employeeCon.findEmployeeByNif(nif);
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
            int field = inputCon.requestInt("Opción: ");
            switch (field) {
                case 1 -> {
                    if (!employeeHandler.populateEmployeeName(employee)) attempts--;
                }
                case 2 -> {
                    if (!employeeHandler.populateEmployeeSurname(employee)) attempts--;
                }
                case 3 -> {
                    if (!employeeHandler.populateEmployeeRole(employee)) attempts--;
                }
                case 4 -> {
                    if (!employeeHandler.populateEmployeeSalary(employee)) attempts--;
                }
                case 5 -> {
                    if (!employeeHandler.populateEmployeeHireDate(employee)) attempts--;
                }
                case 0 -> {
                    employeeCon.updateEmployee(employee);
                    return;
                }
                case -1 -> {
                    messages.print("Cambios cancelados.");
                    return;
                }
                default -> messages.invalidOption();
            }

        }
    }

    private void deleteEmployee() {
        //Solicitud búsqueda por id o nif
        //Quiere buscar por id o nif?
        messages.print("¿Desea buscar al empleado por id o por NIF?");
        int option = inputCon.requestInt("1. Id\n2. NIF\nOpción: ");
        Employee employee;
        switch (option) {
            case 1 -> {
                int id = inputCon.requestInt("Ingrese el id del empleado: ");
                employee = employeeCon.findEmployeeById(id);
            }
            case 2 -> {
                String nif = inputCon.requestString("Ingrese el NIF del empleado: ");
                employee = employeeCon.findEmployeeByNif(nif);
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
        inputCon.requestString(employee.getFormattedDetails()
                + "\nPresione s para confirmar la eliminación o cualquier otra tecla para cancelar.");

        employeeCon.deleteEmployee(employee);
    }

    private void listEmployeesByRole() {
        String role = inputCon.requestString("Ingrese el cargo del empleado: ");
        employeeCon.listAllActiveEmployeesByRole(role);
    }

}
