package com.reservas.view;

import com.reservas.model.Reserva;
import java.util.List;

/**
 * Vista para la gestión de reservas en la interfaz de consola.
 */
public class ReservaView {

    /**
     * Muestra el menú principal de opciones para las reservas.
     */
    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE RESERVAS");
        System.out.println("1. Alta reserva");
        System.out.println("2. Ver reserva");
        System.out.println("3. Modificar reserva");
        System.out.println("4. Eliminar reserva");
        System.out.println("5. Listar todas");
        System.out.println("0. Volver");
    }

    /**
     * Muestra los detalles de una reserva específica.
     * @param reserva la reserva a mostrar.
     */
    public void mostrarReserva(Reserva reserva) {
        ConsolaView.mostrarTitulo("DATOS DE LA RESERVA");
        System.out.println("Recurso:      " + reserva.getIdRecurso());
        System.out.println("Nº reserva:   " + reserva.getIdReservaLocal());
        System.out.println("Usuario:      " + reserva.getIdUsuario());
        System.out.println("Fecha:        " + (reserva.getFecha() != null ? reserva.getFecha() : "N/A"));
        System.out.println("Hora inicio:  " + (reserva.getHoraInicio() != null ? reserva.getHoraInicio() : "N/A"));
        System.out.println("Hora fin:     " + (reserva.getHoraFin() != null ? reserva.getHoraFin() : "N/A"));
        System.out.println("Coste:        " + (reserva.getCoste() != null ? String.format("%.2f €", reserva.getCoste()) : "N/A"));
        System.out.println("Plazas:       " + (reserva.getNumeroPlazas() != null ? reserva.getNumeroPlazas() : "N/A"));
        System.out.println("Motivo:       " + (reserva.getMotivo() != null ? reserva.getMotivo() : ""));
        System.out.println("Observaciones:" + (reserva.getObservaciones() != null ? reserva.getObservaciones() : ""));
        ConsolaView.pausar("Pulse intro para continuar...");
    }

    /**
     * Muestra un listado de reservas.
     * @param reservas la lista de reservas a mostrar.
     */
    public void listarReservas(List<Reserva> reservas) {
        ConsolaView.mostrarTitulo("LISTADO DE RESERVAS");
        if (reservas.isEmpty()) {
            System.out.println("No hay reservas registradas.");
        } else {
            System.out.printf("%-10s %-10s %-10s %-12s %-12s %-12s %-8s%n",
                    "Recurso", "Nº Res.", "Usuario", "Fecha", "H. Inicio", "H. Fin", "Coste");
            System.out.println("-".repeat(80));
            for (Reserva r : reservas) {
                System.out.printf("%-10d %-10d %-10d %-12s %-12s %-12s %-8s%n",
                        r.getIdRecurso(), r.getIdReservaLocal(), r.getIdUsuario(),
                        r.getFecha() != null ? r.getFecha().toString() : "N/A",
                        r.getHoraInicio() != null ? r.getHoraInicio().toString() : "N/A",
                        r.getHoraFin() != null ? r.getHoraFin().toString() : "N/A",
                        r.getCoste() != null ? String.format("%.2f", r.getCoste()) : "N/A");
            }
        }
        ConsolaView.pausar("Pulse intro para continuar...");
    }
}
