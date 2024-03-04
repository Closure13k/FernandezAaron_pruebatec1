package com.closure13k.aaronfmpt1.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public final class InputController {
    private static InputController instance;
    private Scanner input;

    private final OutputController messages = OutputController.getInstance();

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

    public int requestInt(String prompt) {
        messages.print(prompt);
        int i;
        try {
            i = input.nextInt();
        } catch (Exception e) {
            i = -2;
        }
        input.nextLine(); // Consume el \n para frenar el scanner.
        return i;
    }

    public String requestString(String prompt) {
        messages.print(prompt);
        return input.nextLine();
    }

    public double requestDouble(String prompt) {
        messages.print(prompt);
        try {
            return Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            messages.print("El valor debe ser un número.");
            return -1;
        }
    }

    public LocalDate requestDate(String prompt, String format) {
        messages.print(prompt);
        try {
            return LocalDate.parse(input.nextLine(), DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e) {
            messages.print("Fecha inválida. Debe seguir el formato: " + format);
            return null;
        }
    }

    public void close() {
        input.close();
    }
}
