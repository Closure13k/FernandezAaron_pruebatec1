package com.closure13k.aaronfmpt1.logic;

import com.closure13k.aaronfmpt1.logic.employee.Employee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Clase encargada de validar la entrada de datos.
 */
public final class InputController {
    private static InputController instance;
    private Scanner input;

    public static InputController getInstance() {
        if (instance == null) {
            instance = new InputController();
        }
        return instance;
    }

    private InputController() {
        if (instance != null) {
            throw new IllegalStateException("InputController ya ha sido instanciado.");
        }
        this.input = new Scanner(System.in);
    }

    public int readInt() {
        int i;
        try {
            i = input.nextInt();
            input.nextLine(); // Consume el \n para frenar el scanner.
            return i;
        } catch (Exception e) {
            input.nextLine(); // Consume el \n para frenar el scanner.
            i = -2;
        }
        return i;
    }

    public String readString() {
        return input.nextLine();
    }
}