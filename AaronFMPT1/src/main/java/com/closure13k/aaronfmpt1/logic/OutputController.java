package com.closure13k.aaronfmpt1.logic;

import java.util.List;

/**
 * Clase encargada de imprimir información en la consola.
 */
public final class OutputController {
    private static final String ASCII_ART = """
             ______     ______     ______     ______     __   __     ______   __    __     ______   ______ \s
            /\\  __ \\   /\\  __ \\   /\\  == \\   /\\  __ \\   /\\ "-.\\ \\   /\\  ___\\ /\\ "-./  \\   /\\  == \\ /\\__  _\\\s
            \\ \\  __ \\  \\ \\  __ \\  \\ \\  __<   \\ \\ \\/\\ \\  \\ \\ \\-.  \\  \\ \\  __\\ \\ \\ \\-./\\ \\  \\ \\  _-/ \\/_/\\ \\/\s
             \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_\\ \\_\\  \\ \\_____\\  \\ \\_\\\\"\\_\\  \\ \\_\\    \\ \\_\\ \\ \\_\\  \\ \\_\\      \\ \\_\\\s
              \\/_/\\/_/   \\/_/\\/_/   \\/_/ /_/   \\/_____/   \\/_/ \\/_/   \\/_/     \\/_/  \\/_/   \\/_/       \\/_/\s
                                                                                                           \s
            """;
    private static final String WELCOME_MESSAGE = """
             ----------------------------------
            | Prueba Técnica 1 - Java Básico   |
            | Hack A Boss - Softtek            |
            | Aaron Manuel Fernández Mourelle  |
             ----------------------------------
            """;

    public static final String MAIN_MENU = """
             -----------------------------------------
            | Elija una acción:
            | 1. Crear empleado
            | 2. Eliminar empleado (búsqueda por id o nif)
            | 3. Listar a todos los empleados
            | 4. Listar, por cargo, a todos los empleados
            | 5. Actualizar empleado
            | -1. Salir
             -----------------------------------------
            """;
    private static final String UPDATE_MENU = """
             -----------------------------------------
            | Elija una acción para el empleado:
            | 1. Modificar nombre
            | 2. Modificar apellido
            | 3. Modificar cargo
            | 4. Modificar salario
            | 5. Modificar fecha de contratación
            | 0. Guardar cambios y regresar
            | -1. Cancelar y regresar
             -----------------------------------------
            """;

    private static final String INVALID_OPTION = "Opción no válida. Inténtelo de nuevo.";
    public static OutputController instance;

    public static OutputController getInstance() {
        if (instance == null) {
            instance = new OutputController();
        }
        return instance;
    }

    public void print(String message) {
        System.out.println(message);
    }

    public void welcome() {
        print(ASCII_ART + WELCOME_MESSAGE);
    }

    public void invalidOption() {
        print(INVALID_OPTION);
    }

    public void printErrors(List<String> errors) {
        for (String error : errors) {
            print(error);
        }
    }

    public void updateMenuPrompt(String details) {
        print(details + UPDATE_MENU);
    }

    public void deletePrompt(String employeeDetails) {
        print(employeeDetails + "\n¿Está seguro de que quiere eliminarlo? (s/n)");

    }

}
