package com.reservas.controller;

import com.reservas.dao.UsuarioDAO;
import com.reservas.model.Usuario;
import com.reservas.view.ConsolaView;
import com.reservas.view.UsuarioView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Controlador encargado de la lógica de negocio relacionada con los usuarios.
 */
public class UsuarioController {

    private final UsuarioView view;
    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.view = new UsuarioView();
        this.usuarioDAO = UsuarioDAO.getInstance();
    }

    public void iniciar() {
        int opcion;
        do {
            view.mostrarMenu();
            opcion = ConsolaView.leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1 -> altaUsuario();
                    case 2 -> verUsuario();
                    case 3 -> modificarUsuario();
                    case 4 -> eliminarUsuario();
                    case 5 -> listarUsuarios();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
                if (opcion != 0 && opcion > 0 && opcion <= 5) {
                    ConsolaView.pausar("Pulse intro para continuar...");
                }
            } catch (SQLException e) {
                ConsolaView.mostrar("Error en la operación de usuarios: " + e.getMessage());
                ConsolaView.pausar("Pulse intro para continuar...");
            }
        } while (opcion != 0);
    }

    private void altaUsuario() throws SQLException {
        ConsolaView.mostrarTitulo("ALTA DE USUARIO");
        String nombre = ConsolaView.leerTexto("Nombre: ");
        String correo = ConsolaView.leerTexto("Correo: ");
        String contrasena = ConsolaView.leerTexto("Contraseña: ");

        LocalDate fechaNacimiento = null;
        boolean fechaValida = false;
        while (!fechaValida) {
            String fechaStr = ConsolaView.leerTexto("Fecha de nacimiento (dd/MM/yyyy): ");
            if (fechaStr.isEmpty()) {
                fechaValida = true; // Opcional
            } else {
                try {
                    fechaNacimiento = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    fechaValida = true;
                } catch (DateTimeParseException e) {
                    ConsolaView.mostrar("Formato de fecha incorrecto. Intente de nuevo.");
                }
            }
        }

        String tipo = ConsolaView.leerTexto("Tipo (Administrador/Normal) [Normal]: ");
        if (tipo.isEmpty()) {
            tipo = Usuario.TIPO_NORMAL;
        }

        Usuario usuario = new Usuario(correo, contrasena, nombre, fechaNacimiento, tipo);
        int id = usuarioDAO.create(usuario);

        ConsolaView.mostrar(id > 0
                ? "Usuario creado correctamente con ID " + usuario.getIdUsuario()
                : "No se ha podido crear el usuario.");
    }

    private void verUsuario() throws SQLException {
        int id = ConsolaView.leerEntero("ID del usuario: ");
        Usuario usuario = usuarioDAO.read(id);

        if (usuario == null) {
            ConsolaView.mostrar("No existe un usuario con el ID " + id);
            return;
        }
        view.mostrarUsuario(usuario);
    }

    private void modificarUsuario() throws SQLException {
        int id = ConsolaView.leerEntero("ID del usuario a modificar: ");
        Usuario usuario = usuarioDAO.read(id);

        if (usuario == null) {
            ConsolaView.mostrar("No existe un usuario con el ID " + id);
            return;
        }

        String nombre = ConsolaView.leerTexto("Nuevo nombre [" + usuario.getNombre() + "]: ");
        String correo = ConsolaView.leerTexto("Nuevo correo [" + usuario.getCorreoElectronico() + "]: ");
        String contrasena = ConsolaView.leerTexto("Nueva contraseña [***]: ");

        String fechaActual = usuario.getFechaNacimiento() != null
                ? usuario.getFechaNacimiento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "N/A";
        String fechaStr = ConsolaView.leerTexto("Nueva fecha nacimiento (dd/MM/yyyy) [" + fechaActual + "]: ");

        String tipo = ConsolaView.leerTexto("Nuevo tipo [" + usuario.getTipoUsuario() + "]: ");

        if (!nombre.isEmpty())
            usuario.setNombre(nombre);
        if (!correo.isEmpty())
            usuario.setCorreoElectronico(correo);
        if (!contrasena.isEmpty())
            usuario.setContrasena(contrasena);

        if (!fechaStr.isEmpty()) {
            try {
                usuario.setFechaNacimiento(LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            } catch (DateTimeParseException e) {
                ConsolaView.mostrar("Formato de fecha incorrecto. No se actualizó la fecha.");
            }
        }

        if (!tipo.isEmpty())
            usuario.setTipoUsuario(tipo);

        boolean actualizado = usuarioDAO.update(usuario);
        ConsolaView.mostrar(actualizado
                ? "Usuario actualizado correctamente."
                : "No se ha podido actualizar el usuario.");
    }

    private void eliminarUsuario() throws SQLException {
        int id = ConsolaView.leerEntero("ID del usuario a eliminar: ");
        Usuario usuario = usuarioDAO.read(id);

        if (usuario == null) {
            ConsolaView.mostrar("No existe un usuario con el ID " + id);
            return;
        }

        if (!ConsolaView.leerSiNo("¿Seguro que quieres eliminar el usuario '" + usuario.getNombre() + "'?")) {
            ConsolaView.mostrar("Operación cancelada.");
            return;
        }

        boolean borrado = usuarioDAO.delete(id);
        ConsolaView.mostrar(borrado
                ? "Usuario eliminado correctamente."
                : "No se ha podido eliminar el usuario.");
    }

    private void listarUsuarios() throws SQLException {
        List<Usuario> usuarios = usuarioDAO.listAll();
        view.listarUsuarios(usuarios);
    }
}
