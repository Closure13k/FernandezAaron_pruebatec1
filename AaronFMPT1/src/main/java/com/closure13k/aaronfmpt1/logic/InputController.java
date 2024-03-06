package com.closure13k.aaronfmpt1.logic;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
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

    /**
     * Solicita un entero al usuario.
     *
     * @param prompt Mensaje a mostrar al usuario.
     * @return El entero introducido por el usuario.
     */
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

    /**
     * Solicita una cadena de texto al usuario.
     *
     * @param prompt Mensaje a mostrar al usuario.
     * @return La cadena de texto introducida por el usuario.
     */
    public String requestString(String prompt) {
        messages.print(prompt);
        return input.nextLine();
    }

    /**
     * Solicita un número decimal al usuario.
     *
     * @param prompt Mensaje a mostrar al usuario.
     * @return El número decimal introducido por el usuario. -1 si no pasase una primera validación de tipo.
     */
    public double requestDouble(String prompt) {
        messages.print(prompt);
        try {
            return Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            messages.print("El valor debe ser un número.");
            return -1;
        }
    }

    /**
     * Solicita una fecha al usuario.
     *
     * @param prompt Mensaje a mostrar al usuario.
     * @param format Formato de fecha a solicitar.
     * @return La fecha introducida por el usuario. null si no pasase la validación de formato y fecha real.
     */
    public LocalDate requestDate(String prompt, String format) {
        messages.print(prompt);
        try {
            return LocalDate.parse(
                    input.nextLine(),
                    DateTimeFormatter.ofPattern(format)
                            .withResolverStyle(ResolverStyle.STRICT)
            );
        } catch (DateTimeParseException e) {
            messages.print("Fecha inválida. Debe seguir el formato: " + format + " y ser una fecha real.");
            return null;
        }
    }

    /**
     * Liberar recursos del scanner.
     */
    public void close() {
        input.close();
    }
}
