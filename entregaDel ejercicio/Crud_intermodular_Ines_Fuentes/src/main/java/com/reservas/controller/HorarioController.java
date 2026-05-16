package com.reservas.controller;

import com.reservas.dao.HorarioDAO;
import com.reservas.model.Horario;
import com.reservas.view.ConsolaView;
import com.reservas.view.HorarioView;

import java.sql.SQLException;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador para la gestión de horarios, actuando como intermediario entre la vista y el DAO.
 */
public class HorarioController {

    private final HorarioDAO horarioDAO;
    private final HorarioView view;

    public HorarioController() {
        this.horarioDAO = HorarioDAO.getInstance();
        this.view       = new HorarioView();
    }

    /**
     * Inicia el submenú de gestión de horarios y procesa las acciones del usuario.
     */
    public void iniciar() {
        int opcion;

        do {
            view.mostrarMenu();
            opcion = ConsolaView.leerEntero("Opción: ");

            try {
                switch (opcion) {
                    case 1 -> altaHorario();
                    case 2 -> verHorario();
                    case 3 -> modificarHorario();
                    case 4 -> eliminarHorario();
                    case 5 -> listarHorarios();
                    case 0 -> ConsolaView.mostrar("Volviendo...");
                    default -> ConsolaView.mostrar("Opción no válida.");
                }
            } catch (SQLException e) {
                ConsolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void altaHorario() throws SQLException {
        ConsolaView.mostrarTitulo("ALTA DE HORARIO");

        ConsolaView.mostrar("Días válidos: " + String.join(", ", Horario.DIAS_VALIDOS));
        String dia = pedirDiaValido();

        LocalTime horaInicio = ConsolaView.leerHora("Hora de inicio");
        LocalTime horaFin    = ConsolaView.leerHora("Hora de fin");

        if (!horaFin.isAfter(horaInicio)) {
            ConsolaView.mostrar("La hora de fin debe ser posterior a la de inicio. Operación cancelada.");
            return;
        }

        Horario horario = new Horario(dia, horaInicio, horaFin);
        int id = horarioDAO.create(horario);

        ConsolaView.mostrar(id > 0
                ? "Horario creado correctamente con ID " + horario.getIdHorario()
                : "No se ha podido crear el horario.");
    }

    private void verHorario() throws SQLException {
        int id = ConsolaView.leerEntero("ID del horario: ");
        Horario horario = horarioDAO.read(id);

        if (horario == null) {
            ConsolaView.mostrar("No existe un horario con el ID " + id);
            return;
        }
        view.mostrarHorario(horario);
    }

    private void modificarHorario() throws SQLException {
        int id = ConsolaView.leerEntero("ID del horario a modificar: ");
        Horario horario = horarioDAO.read(id);

        if (horario == null) {
            ConsolaView.mostrar("No existe un horario con el ID " + id);
            return;
        }

        ConsolaView.mostrar("Días válidos: " + String.join(", ", Horario.DIAS_VALIDOS));
        String dia        = pedirDiaValido();
        LocalTime horaIni = ConsolaView.leerHora("Nueva hora de inicio");
        LocalTime horaFin = ConsolaView.leerHora("Nueva hora de fin");

        if (!horaFin.isAfter(horaIni)) {
            ConsolaView.mostrar("La hora de fin debe ser posterior a la de inicio. Operación cancelada.");
            return;
        }

        horario.setDiaSemana(dia);
        horario.setHoraInicio(horaIni);
        horario.setHoraFin(horaFin);

        boolean actualizado = horarioDAO.update(horario);
        ConsolaView.mostrar(actualizado
                ? "Horario actualizado correctamente."
                : "No se ha podido actualizar el horario.");
    }

    private void eliminarHorario() throws SQLException {
        int id = ConsolaView.leerEntero("ID del horario a eliminar: ");
        Horario horario = horarioDAO.read(id);

        if (horario == null) {
            ConsolaView.mostrar("No existe un horario con el ID " + id);
            return;
        }

        if (!ConsolaView.leerSiNo("¿Seguro que quieres eliminar el horario "
                + horario.getDiaSemana() + " " + horario.getHoraInicio() + "-" + horario.getHoraFin() + "?")) {
            ConsolaView.mostrar("Operación cancelada.");
            return;
        }

        boolean borrado = horarioDAO.delete(id);
        ConsolaView.mostrar(borrado
                ? "Horario eliminado correctamente."
                : "No se ha podido eliminar el horario.");
    }

    private void listarHorarios() throws SQLException {
        List<Horario> horarios = horarioDAO.listAll();
        view.listarHorarios(horarios);
    }

    private String pedirDiaValido() {
        String dia;
        do {
            dia = ConsolaView.leerTexto("Día de la semana: ");
            if (!dia.isEmpty()) {
                dia = dia.substring(0, 1).toUpperCase() + dia.substring(1).toLowerCase();
            }
            if (!Horario.DIAS_VALIDOS.contains(dia)) {
                ConsolaView.mostrar("Día no válido. Introduce uno de: " + String.join(", ", Horario.DIAS_VALIDOS));
            }
        } while (!Horario.DIAS_VALIDOS.contains(dia));
        return dia;
    }
}
