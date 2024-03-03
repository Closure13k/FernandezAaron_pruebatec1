package com.closure13k.aaronfmpt1.logic;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.logic.employee.EmployeeController;

import static com.closure13k.aaronfmpt1.logic.OutputController.MAIN_MENU;

public final class AppController {
    private final InputController inputs = InputController.getInstance();
    private final EmployeeValidator employeeValidator = EmployeeValidator.getInstance();
    private final OutputController messages = OutputController.getInstance();
    private final EmployeeController employeeCon = EmployeeController.getInstance();

    public void beginApp() {
        int option;

        messages.welcome();
        do {
            messages.print(MAIN_MENU);
            option = inputs.readInt();
            switch (option) {
                case 1 -> {
                    Employee employee = new Employee();
                    if (employeeValidator.isValidEmployeeInput(employee)) {
                        employeeCon.createEmployee(employee);
                    }
                }
                case 2 -> {
                    messages.print("Introduzca el id del empleado a eliminar:");
                    int employeeId = inputs.readInt();
                    if (employeeId > 0) {
                        Employee employee = employeeCon.findEmployeeById(employeeId);
                        if (employee == null) {
                            messages.print("Empleado no encontrado.");
                            break;
                        }

                        messages.print(employee.getFormattedDetails());
                        messages.print(("¿Está seguro de que desea eliminar el empleado?\n" +
                                "S para confirmar, cualquier otra tecla para cancelar."));
                        String confirmation = inputs.readString();

                        if (confirmation.toLowerCase().contains("s")) {
                            employeeCon.deleteEmployee(employee);
                            messages.print("Empleado eliminado.");
                        } else {
                            messages.print("Eliminación cancelada.");
                        }
                    } else {
                        messages.print("Dato inválido.");
                    }
                }
                case 3 -> employeeCon.listAllActiveEmployees();
                case 4 -> {
                    messages.print("Introduzca el cargo:");
                    String role = inputs.readString();
                    if (role.isBlank()) {
                        messages.print("El cargo no puede estar vacío.");
                        break;
                    }
                    employeeCon.listAllActiveEmployeesByRole(role);
                }
                case 5 -> {
                    messages.print("Introduzca el id del empleado a actualizar:");
                    int employeeId = inputs.readInt();
                    Employee employee = employeeCon.findEmployeeById(employeeId);
                    if (employee != null) {
                        int updateOption, maxUpdateRotations = 15;
                        do {
                            updateOption = performUpdateOptions(employee);
                            maxUpdateRotations--;
                        } while (updateOption != 0 && updateOption != -1 && maxUpdateRotations > 0);
                    } else {
                        messages.print("Empleado no encontrado.");
                    }


                }
                case -1 -> messages.print("¡Hasta luego!");
                default -> messages.invalidOption();
            }
        } while (option != -1);
    }

    private int performUpdateOptions(Employee employee) {
        int updateOption;
        messages.updateMenuPrompt(employee.getFormattedDetails());
        updateOption = inputs.readInt();
        switch (updateOption) {
            case 1 -> {
                employeeValidator.requestAndValidateName(employee);
            }
            case 2 -> {
                employeeValidator.requestAndValidateSurname(employee);
            }
            case 3 -> {
                employeeValidator.requestAndValidateRole(employee);
            }
            case 4 -> {
                employeeValidator.requestAndValidateSalary(employee);
            }
            case 5 -> {
                employeeValidator.requestAndValidateHireDate(employee);
            }
            case 0 -> {
                employeeCon.updateEmployee(employee);
                messages.print("Cambios guardados para %s (%s)".formatted(employee.getName(), employee.getNif()));
            }

            case -1 -> {
                messages.print("Cambios cancelados.");
            }
            default -> messages.invalidOption();
        }
        return updateOption;
    }
}
