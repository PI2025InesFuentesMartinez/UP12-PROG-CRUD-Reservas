package com.reservas.app;

/**
 * Clase principal que inicia la aplicación de gestión de reservas.
 */
public class Main {
    /**
     * Método de entrada de la aplicación.
     * @param args argumentos de línea de comandos.
     */
    public static void main(String[] args) {
        com.reservas.controller.AppController appController = new com.reservas.controller.AppController();
        appController.iniciar();
    }
}
