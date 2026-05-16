package com.reservas.controller;

import com.reservas.dao.DBConnection;
import com.reservas.view.ConsolaView;

/**
 * Controlador principal que gestiona el menú general y la navegación de la aplicación.
 */
public class AppController {

    private UsuarioController usuarioController;
    private RecursoController recursoController;
    private HorarioController horarioController;
    private ReservaController reservaController;

    /**
     * Inicia la ejecución de la aplicación, mostrando el menú principal y procesando la entrada del usuario.
     */
    public void iniciar() {
        int opcion;
        try {
            usuarioController  = new UsuarioController();
            recursoController  = new RecursoController();
            horarioController  = new HorarioController();
            reservaController  = new ReservaController();

            do {
                mostrarMenuPrincipal();
                opcion = ConsolaView.leerEntero("Opción: ");

                switch (opcion) {
                    case 1 -> usuarioController.iniciar();
                    case 2 -> recursoController.iniciar();
                    case 3 -> horarioController.iniciar();
                    case 4 -> reservaController.iniciar();
                    case 0 -> ConsolaView.mostrar("Gracias por usar la aplicación.\nHasta pronto...");
                    default -> ConsolaView.mostrar("Opción no válida.");
                }
            } while (opcion != 0);

        } catch (Exception e) {
            ConsolaView.mostrarTitulo("ERROR EN LA APLICACIÓN");
            ConsolaView.mostrar("Error: " + e.getMessage());
            ConsolaView.pausar("Pulse intro para terminar...");
        } finally {
            DBConnection.closeConnection();
        }
    }

    private void mostrarMenuPrincipal() {
        ConsolaView.saltoLinea();
        ConsolaView.mostrarTitulo("SISTEMA DE RESERVAS - APLICACIÓN DE CONSOLA");
        ConsolaView.mostrar("1. Gestionar usuarios");
        ConsolaView.mostrar("2. Gestionar recursos");
        ConsolaView.mostrar("3. Gestionar horarios");
        ConsolaView.mostrar("4. Gestionar reservas");
        ConsolaView.mostrar("0. Salir");
    }
}
