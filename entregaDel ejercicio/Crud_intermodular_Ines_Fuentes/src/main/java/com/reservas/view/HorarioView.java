package com.reservas.view;

import com.reservas.model.Horario;
import java.util.List;

/**
 * Vista para la gestión de horarios en la interfaz de consola.
 */
public class HorarioView {

    /**
     * Muestra el menú principal de opciones para los horarios.
     */
    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE HORARIOS");
        System.out.println("1. Alta horario");
        System.out.println("2. Ver horario");
        System.out.println("3. Modificar horario");
        System.out.println("4. Eliminar horario");
        System.out.println("5. Listar todos");
        System.out.println("0. Volver");
    }

    /**
     * Muestra los detalles de un horario específico.
     * @param horario el horario a mostrar.
     */
    public void mostrarHorario(Horario horario) {
        ConsolaView.mostrarTitulo("DATOS DEL HORARIO");
        System.out.println("ID:          " + horario.getIdHorario());
        System.out.println("Día semana:  " + horario.getDiaSemana());
        System.out.println("Hora inicio: " + (horario.getHoraInicio() != null ? horario.getHoraInicio() : "N/A"));
        System.out.println("Hora fin:    " + (horario.getHoraFin() != null ? horario.getHoraFin() : "N/A"));
        ConsolaView.pausar("Pulse intro para continuar...");
    }

    /**
     * Muestra un listado de horarios.
     * @param horarios la lista de horarios a mostrar.
     */
    public void listarHorarios(List<Horario> horarios) {
        ConsolaView.mostrarTitulo("LISTADO DE HORARIOS");
        if (horarios.isEmpty()) {
            System.out.println("No hay horarios registrados.");
        } else {
            System.out.printf("%-5s %-15s %-15s %-15s%n", "ID", "Día", "Hora Inicio", "Hora Fin");
            System.out.println("-".repeat(55));
            for (Horario h : horarios) {
                System.out.printf("%-5d %-15s %-15s %-15s%n",
                        h.getIdHorario(), h.getDiaSemana(),
                        h.getHoraInicio() != null ? h.getHoraInicio().toString() : "N/A",
                        h.getHoraFin() != null ? h.getHoraFin().toString() : "N/A");
            }
        }
        ConsolaView.pausar("Pulse intro para continuar...");
    }
}
