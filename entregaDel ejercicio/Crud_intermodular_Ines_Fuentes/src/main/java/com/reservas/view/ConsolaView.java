package com.reservas.view;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Clase utilitaria para manejar la entrada y salida de datos por consola.
 */
public class ConsolaView {

    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Lee una cadena de texto desde la consola.
     * @param mensaje el mensaje a mostrar al usuario.
     * @return el texto introducido por el usuario.
     */
    public static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    /**
     * Lee un número entero desde la consola, validando la entrada.
     * @param mensaje el mensaje a mostrar al usuario.
     * @return el número entero introducido.
     */
    public static int leerEntero(String mensaje) {
        boolean valido = false;
        int numero = 0;

        while (!valido) {
            try {
                System.out.print(mensaje);
                numero = Integer.parseInt(scanner.nextLine().trim());
                valido = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: debes introducir un número entero.");
            }
        }
        return numero;
    }

    /**
     * Lee una respuesta afirmativa o negativa ("s" o "n") desde la consola.
     * @param mensaje el mensaje a mostrar al usuario.
     * @return true si la respuesta es afirmativa, false en caso contrario.
     */
    public static boolean leerSiNo(String mensaje) {
        String respuesta = leerTexto(mensaje + " (s/n): ");
        return respuesta.equalsIgnoreCase("s")
                || respuesta.equalsIgnoreCase("si")
                || respuesta.equalsIgnoreCase("sí");
    }

    /**
     * Lee una fecha desde la consola con el formato dd/MM/yyyy.
     * @param mensaje el mensaje a mostrar al usuario.
     * @return la fecha introducida válida.
     */
    public static LocalDate leerFecha(String mensaje) {
        boolean valido = false;
        LocalDate fecha = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (!valido) {
            try {
                String input = leerTexto(mensaje + " (dd/MM/yyyy): ");
                fecha = LocalDate.parse(input, formatter);
                valido = true;
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato de fecha incorrecto. Usa dd/MM/yyyy.");
            }
        }
        return fecha;
    }

    /**
     * Lee una hora desde la consola con el formato HH:mm.
     * @param mensaje el mensaje a mostrar al usuario.
     * @return la hora introducida válida.
     */
    public static LocalTime leerHora(String mensaje) {
        boolean valido = false;
        LocalTime hora = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (!valido) {
            try {
                String input = leerTexto(mensaje + " (HH:mm): ");
                hora = LocalTime.parse(input, formatter);
                valido = true;
            } catch (DateTimeParseException e) {
                System.out.println("Error: formato de hora incorrecto. Usa HH:mm.");
            }
        }
        return hora;
    }

    /**
     * Muestra un mensaje en la consola.
     * @param mensaje el mensaje a mostrar.
     */
    public static void mostrar(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Imprime un salto de línea en la consola.
     */
    public static void saltoLinea() {
        System.out.println();
    }

    /**
     * Pausa la ejecución hasta que el usuario pulsa intro.
     * @param mensaje el mensaje a mostrar antes de pausar.
     */
    public static void pausar(String mensaje) {
        System.out.println();
        System.out.print(mensaje);
        scanner.nextLine();
    }

    /**
     * Muestra un título centrado y formateado en la consola.
     * @param titulo el texto del título a mostrar.
     */
    public static void mostrarTitulo(String titulo) {
        int ancho = 48;
        String linea = "=".repeat(ancho);

        int espacios = (ancho - titulo.length()) / 2;
        String tituloCentrado = " ".repeat(Math.max(0, espacios)) + titulo;

        System.out.println();
        System.out.println(linea);
        System.out.println(tituloCentrado);
        System.out.println(linea);
    }

    /**
     * Limpia la pantalla de la consola.
     */
    public static void limpiarPantalla() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
