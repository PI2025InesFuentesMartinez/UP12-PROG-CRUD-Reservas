package com.reservas.controller;

import com.reservas.dao.RecursoDAO;
import com.reservas.dao.ReservaDAO;
import com.reservas.dao.UsuarioDAO;
import com.reservas.model.Recurso;
import com.reservas.model.Reserva;
import com.reservas.model.Usuario;
import com.reservas.view.ConsolaView;
import com.reservas.view.ReservaView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Controlador para la gestión de reservas, manejando la lógica de negocio y las operaciones de la base de datos.
 */
public class ReservaController {

    private final ReservaDAO reservaDAO;
    private final RecursoDAO recursoDAO;
    private final UsuarioDAO usuarioDAO;
    private final ReservaView view;

    public ReservaController() {
        this.reservaDAO = ReservaDAO.getInstance();
        this.recursoDAO = RecursoDAO.getInstance();
        this.usuarioDAO = UsuarioDAO.getInstance();
        this.view       = new ReservaView();
    }

    /**
     * Inicia el submenú de gestión de reservas y procesa las acciones del usuario.
     */
    public void iniciar() {
        int opcion;

        do {
            view.mostrarMenu();
            opcion = ConsolaView.leerEntero("Opción: ");

            try {
                switch (opcion) {
                    case 1 -> altaReserva();
                    case 2 -> verReserva();
                    case 3 -> modificarReserva();
                    case 4 -> eliminarReserva();
                    case 5 -> listarReservas();
                    case 0 -> ConsolaView.mostrar("Volviendo...");
                    default -> ConsolaView.mostrar("Opción no válida.");
                }
            } catch (SQLException e) {
                ConsolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void altaReserva() throws SQLException {
        ConsolaView.mostrarTitulo("ALTA DE RESERVA");

        int idRecurso = ConsolaView.leerEntero("ID del recurso: ");
        Recurso recurso = recursoDAO.read(idRecurso);
        if (recurso == null) {
            ConsolaView.mostrar("No existe un recurso con el ID " + idRecurso);
            return;
        }

        int idUsuario = ConsolaView.leerEntero("ID del usuario: ");
        Usuario usuario = usuarioDAO.read(idUsuario);
        if (usuario == null) {
            ConsolaView.mostrar("No existe un usuario con el ID " + idUsuario);
            return;
        }

        LocalDate fecha      = ConsolaView.leerFecha("Fecha de la reserva");
        LocalTime horaInicio = ConsolaView.leerHora("Hora de inicio");
        LocalTime horaFin    = ConsolaView.leerHora("Hora de fin");

        if (!horaFin.isAfter(horaInicio)) {
            ConsolaView.mostrar("La hora de fin debe ser posterior a la de inicio. Operación cancelada.");
            return;
        }

        String costeStr = ConsolaView.leerTexto("Coste (deja en blanco si no aplica): ");
        Double coste = costeStr.isEmpty() ? null : Double.parseDouble(costeStr.replace(",", "."));

        String plazasStr = ConsolaView.leerTexto("Número de plazas (deja en blanco si no aplica): ");
        Integer plazas = plazasStr.isEmpty() ? null : Integer.parseInt(plazasStr);

        String motivo        = ConsolaView.leerTexto("Motivo: ");
        String observaciones = ConsolaView.leerTexto("Observaciones (opcional): ");

        int idReservaLocal = reservaDAO.nextIdReservaLocal(idRecurso);

        Reserva reserva = new Reserva(idRecurso, idReservaLocal, idUsuario,
                fecha, horaInicio, horaFin, coste, plazas, motivo, observaciones);

        boolean creada = reservaDAO.create(reserva);
        ConsolaView.mostrar(creada
                ? "Reserva creada correctamente (recurso=" + idRecurso + ", nº=" + idReservaLocal + ")"
                : "No se ha podido crear la reserva.");
    }

    private void verReserva() throws SQLException {
        int idRecurso      = ConsolaView.leerEntero("ID del recurso: ");
        int idReservaLocal = ConsolaView.leerEntero("Número de reserva: ");

        Reserva reserva = reservaDAO.read(idRecurso, idReservaLocal);
        if (reserva == null) {
            ConsolaView.mostrar("No existe la reserva indicada.");
            return;
        }
        view.mostrarReserva(reserva);
    }

    private void modificarReserva() throws SQLException {
        int idRecurso      = ConsolaView.leerEntero("ID del recurso: ");
        int idReservaLocal = ConsolaView.leerEntero("Número de reserva: ");

        Reserva reserva = reservaDAO.read(idRecurso, idReservaLocal);
        if (reserva == null) {
            ConsolaView.mostrar("No existe la reserva indicada.");
            return;
        }

        int idUsuario = ConsolaView.leerEntero("ID del usuario [" + reserva.getIdUsuario() + "]: ");
        if (idUsuario > 0 && usuarioDAO.read(idUsuario) != null) {
            reserva.setIdUsuario(idUsuario);
        } else {
            ConsolaView.mostrar("Usuario no encontrado, se conserva el actual.");
        }

        LocalDate fecha      = ConsolaView.leerFecha("Nueva fecha");
        LocalTime horaInicio = ConsolaView.leerHora("Nueva hora de inicio");
        LocalTime horaFin    = ConsolaView.leerHora("Nueva hora de fin");

        if (!horaFin.isAfter(horaInicio)) {
            ConsolaView.mostrar("La hora de fin debe ser posterior a la de inicio. Operación cancelada.");
            return;
        }

        String costeStr      = ConsolaView.leerTexto("Coste (deja en blanco para mantener): ");
        String plazasStr     = ConsolaView.leerTexto("Número de plazas (deja en blanco para mantener): ");
        String motivo        = ConsolaView.leerTexto("Motivo: ");
        String observaciones = ConsolaView.leerTexto("Observaciones: ");

        reserva.setFecha(fecha);
        reserva.setHoraInicio(horaInicio);
        reserva.setHoraFin(horaFin);
        if (!costeStr.isEmpty()) reserva.setCoste(Double.parseDouble(costeStr.replace(",", ".")));
        if (!plazasStr.isEmpty()) reserva.setNumeroPlazas(Integer.parseInt(plazasStr));
        if (!motivo.isEmpty()) reserva.setMotivo(motivo);
        if (!observaciones.isEmpty()) reserva.setObservaciones(observaciones);

        boolean actualizado = reservaDAO.update(reserva);
        ConsolaView.mostrar(actualizado
                ? "Reserva actualizada correctamente."
                : "No se ha podido actualizar la reserva.");
    }

    private void eliminarReserva() throws SQLException {
        int idRecurso      = ConsolaView.leerEntero("ID del recurso: ");
        int idReservaLocal = ConsolaView.leerEntero("Número de reserva: ");

        Reserva reserva = reservaDAO.read(idRecurso, idReservaLocal);
        if (reserva == null) {
            ConsolaView.mostrar("No existe la reserva indicada.");
            return;
        }

        if (!ConsolaView.leerSiNo("¿Seguro que quieres eliminar la reserva " + idReservaLocal
                + " del recurso " + idRecurso + "?")) {
            ConsolaView.mostrar("Operación cancelada.");
            return;
        }

        boolean borrada = reservaDAO.delete(idRecurso, idReservaLocal);
        ConsolaView.mostrar(borrada
                ? "Reserva eliminada correctamente."
                : "No se ha podido eliminar la reserva.");
    }

    private void listarReservas() throws SQLException {
        List<Reserva> reservas = reservaDAO.listAll();
        view.listarReservas(reservas);
    }
}
