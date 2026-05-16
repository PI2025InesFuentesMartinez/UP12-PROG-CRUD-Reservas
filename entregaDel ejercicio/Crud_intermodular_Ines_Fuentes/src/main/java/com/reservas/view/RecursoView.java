package com.reservas.view;

import com.reservas.model.DisponibleEn;
import com.reservas.model.Recurso;
import java.util.List;

/**
 * Vista para la gestión de recursos en la interfaz de consola.
 */
public class RecursoView {

    /**
     * Muestra el menú principal de opciones para los recursos.
     */
    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE RECURSOS");
        System.out.println("1. Alta recurso");
        System.out.println("2. Ver recurso");
        System.out.println("3. Modificar recurso");
        System.out.println("4. Eliminar recurso");
        System.out.println("5. Listar todos");
        System.out.println("6. Ver horarios del recurso");
        System.out.println("7. Asignar horario a recurso");
        System.out.println("8. Desasignar horario de recurso");
        System.out.println("0. Volver");
    }

    /**
     * Muestra los detalles de un recurso específico.
     * @param recurso el recurso a mostrar.
     */
    public void mostrarRecurso(Recurso recurso) {
        ConsolaView.mostrarTitulo("DATOS DEL RECURSO");
        System.out.println("ID:          " + recurso.getIdRecurso());
        System.out.println("Nombre:      " + recurso.getNombre());
        System.out.println("Descripción: " + recurso.getDescripcion());
        System.out.println("Ubicación:   " + recurso.getUbicacion());
        System.out.println("Capacidad:   " + recurso.getCapacidad());
        ConsolaView.pausar("Pulse intro para continuar...");
    }

    /**
     * Muestra un listado de recursos.
     * @param recursos la lista de recursos a mostrar.
     */
    public void listarRecursos(List<Recurso> recursos) {
        ConsolaView.mostrarTitulo("LISTADO DE RECURSOS");
        if (recursos.isEmpty()) {
            System.out.println("No hay recursos registrados.");
        } else {
            System.out.printf("%-5s %-25s %-20s %-10s%n", "ID", "Nombre", "Ubicación", "Capacidad");
            System.out.println("-".repeat(65));
            for (Recurso r : recursos) {
                System.out.printf("%-5d %-25s %-20s %-10d%n",
                        r.getIdRecurso(), r.getNombre(), r.getUbicacion(), r.getCapacidad());
            }
        }
        ConsolaView.pausar("Pulse intro para continuar...");
    }

    /**
     * Muestra las disponibilidades (horarios) de un recurso.
     * @param idRecurso el identificador del recurso.
     * @param disponibilidades la lista de disponibilidades asignadas al recurso.
     */
    public void listarDisponibilidades(int idRecurso, List<DisponibleEn> disponibilidades) {
        ConsolaView.mostrarTitulo("HORARIOS DEL RECURSO " + idRecurso);
        if (disponibilidades.isEmpty()) {
            System.out.println("Este recurso no tiene horarios asignados.");
        } else {
            System.out.printf("%-15s %-15s%n", "ID Recurso", "ID Horario");
            System.out.println("-".repeat(32));
            for (DisponibleEn d : disponibilidades) {
                System.out.printf("%-15d %-15d%n", d.getIdRecurso(), d.getIdHorario());
            }
        }
        ConsolaView.pausar("Pulse intro para continuar...");
    }
}
