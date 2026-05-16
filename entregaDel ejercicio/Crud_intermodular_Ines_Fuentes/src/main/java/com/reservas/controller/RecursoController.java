package com.reservas.controller;

import com.reservas.dao.DisponibleEnDAO;
import com.reservas.dao.HorarioDAO;
import com.reservas.dao.RecursoDAO;
import com.reservas.model.DisponibleEn;
import com.reservas.model.Horario;
import com.reservas.model.Recurso;
import com.reservas.view.ConsolaView;
import com.reservas.view.RecursoView;

import java.sql.SQLException;
import java.util.List;

/**
 * Controlador para la gestión de recursos, coordinando las acciones entre la vista y el modelo de datos.
 */
public class RecursoController {

    private final RecursoDAO recursoDAO;
    private final HorarioDAO horarioDAO;
    private final DisponibleEnDAO disponibleEnDAO;
    private final RecursoView view;

    public RecursoController() {
        this.recursoDAO      = RecursoDAO.getInstance();
        this.horarioDAO      = HorarioDAO.getInstance();
        this.disponibleEnDAO = DisponibleEnDAO.getInstance();
        this.view            = new RecursoView();
    }

    /**
     * Inicia el submenú de gestión de recursos y procesa las acciones del usuario.
     */
    public void iniciar() {
        int opcion;

        do {
            view.mostrarMenu();
            opcion = ConsolaView.leerEntero("Opción: ");

            try {
                switch (opcion) {
                    case 1 -> altaRecurso();
                    case 2 -> verRecurso();
                    case 3 -> modificarRecurso();
                    case 4 -> eliminarRecurso();
                    case 5 -> listarRecursos();
                    case 6 -> verHorariosRecurso();
                    case 7 -> asignarHorario();
                    case 8 -> desasignarHorario();
                    case 0 -> ConsolaView.mostrar("Volviendo...");
                    default -> ConsolaView.mostrar("Opción no válida.");
                }
            } catch (SQLException e) {
                ConsolaView.mostrar("Error de base de datos: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private void altaRecurso() throws SQLException {
        ConsolaView.mostrarTitulo("ALTA DE RECURSO");
        String nombre      = ConsolaView.leerTexto("Nombre: ");
        String descripcion = ConsolaView.leerTexto("Descripción: ");
        String ubicacion   = ConsolaView.leerTexto("Ubicación: ");
        int capacidad      = ConsolaView.leerEntero("Capacidad: ");

        Recurso recurso = new Recurso(nombre, descripcion, ubicacion, capacidad);
        int id = recursoDAO.create(recurso);

        ConsolaView.mostrar(id > 0
                ? "Recurso creado correctamente con ID " + recurso.getIdRecurso()
                : "No se ha podido crear el recurso.");
    }

    private void verRecurso() throws SQLException {
        int id = ConsolaView.leerEntero("ID del recurso: ");
        Recurso recurso = recursoDAO.read(id);

        if (recurso == null) {
            ConsolaView.mostrar("No existe un recurso con el ID " + id);
            return;
        }
        view.mostrarRecurso(recurso);
    }

    private void modificarRecurso() throws SQLException {
        int id = ConsolaView.leerEntero("ID del recurso a modificar: ");
        Recurso recurso = recursoDAO.read(id);

        if (recurso == null) {
            ConsolaView.mostrar("No existe un recurso con el ID " + id);
            return;
        }

        String nombre      = ConsolaView.leerTexto("Nuevo nombre [" + recurso.getNombre() + "]: ");
        String descripcion = ConsolaView.leerTexto("Nueva descripción [" + recurso.getDescripcion() + "]: ");
        String ubicacion   = ConsolaView.leerTexto("Nueva ubicación [" + recurso.getUbicacion() + "]: ");
        int capacidad      = ConsolaView.leerEntero("Nueva capacidad [" + recurso.getCapacidad() + "]: ");

        if (!nombre.isEmpty())      recurso.setNombre(nombre);
        if (!descripcion.isEmpty()) recurso.setDescripcion(descripcion);
        if (!ubicacion.isEmpty())   recurso.setUbicacion(ubicacion);
        if (capacidad > 0)          recurso.setCapacidad(capacidad);

        boolean actualizado = recursoDAO.update(recurso);
        ConsolaView.mostrar(actualizado
                ? "Recurso actualizado correctamente."
                : "No se ha podido actualizar el recurso.");
    }

    private void eliminarRecurso() throws SQLException {
        int id = ConsolaView.leerEntero("ID del recurso a eliminar: ");
        Recurso recurso = recursoDAO.read(id);

        if (recurso == null) {
            ConsolaView.mostrar("No existe un recurso con el ID " + id);
            return;
        }

        if (!ConsolaView.leerSiNo("¿Seguro que quieres eliminar el recurso '" + recurso.getNombre() + "'?")) {
            ConsolaView.mostrar("Operación cancelada.");
            return;
        }

        boolean borrado = recursoDAO.delete(id);
        ConsolaView.mostrar(borrado
                ? "Recurso eliminado correctamente."
                : "No se ha podido eliminar el recurso.");
    }

    private void listarRecursos() throws SQLException {
        List<Recurso> recursos = recursoDAO.listAll();
        view.listarRecursos(recursos);
    }

    private void verHorariosRecurso() throws SQLException {
        int idRecurso = ConsolaView.leerEntero("ID del recurso: ");

        if (recursoDAO.read(idRecurso) == null) {
            ConsolaView.mostrar("No existe un recurso con el ID " + idRecurso);
            return;
        }

        List<DisponibleEn> disponibilidades = disponibleEnDAO.listByRecurso(idRecurso);
        view.listarDisponibilidades(idRecurso, disponibilidades);
    }

    private void asignarHorario() throws SQLException {
        int idRecurso = ConsolaView.leerEntero("ID del recurso: ");

        if (recursoDAO.read(idRecurso) == null) {
            ConsolaView.mostrar("No existe un recurso con el ID " + idRecurso);
            return;
        }

        int idHorario = ConsolaView.leerEntero("ID del horario a asignar: ");
        Horario horario = horarioDAO.read(idHorario);

        if (horario == null) {
            ConsolaView.mostrar("No existe un horario con el ID " + idHorario);
            return;
        }

        if (disponibleEnDAO.read(idRecurso, idHorario) != null) {
            ConsolaView.mostrar("El horario " + idHorario + " ya está asignado a este recurso.");
            return;
        }

        DisponibleEn relacion = new DisponibleEn(idRecurso, idHorario);
        boolean asignado = disponibleEnDAO.create(relacion);
        ConsolaView.mostrar(asignado
                ? "Horario asignado correctamente al recurso."
                : "No se ha podido asignar el horario.");
    }

    private void desasignarHorario() throws SQLException {
        int idRecurso = ConsolaView.leerEntero("ID del recurso: ");
        int idHorario = ConsolaView.leerEntero("ID del horario a desasignar: ");

        if (disponibleEnDAO.read(idRecurso, idHorario) == null) {
            ConsolaView.mostrar("Ese horario no está asignado a ese recurso.");
            return;
        }

        if (!ConsolaView.leerSiNo("¿Confirmar desasignación del horario " + idHorario
                + " del recurso " + idRecurso + "?")) {
            ConsolaView.mostrar("Operación cancelada.");
            return;
        }

        boolean eliminado = disponibleEnDAO.delete(idRecurso, idHorario);
        ConsolaView.mostrar(eliminado
                ? "Horario desasignado correctamente."
                : "No se ha podido desasignar el horario.");
    }
}
