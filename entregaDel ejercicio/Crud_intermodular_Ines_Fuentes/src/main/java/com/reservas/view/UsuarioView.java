package com.reservas.view;

import com.reservas.model.Usuario;
import java.util.List;

/**
 * Vista para la gestión de usuarios en la interfaz de consola.
 */
public class UsuarioView {

    /**
     * Muestra el menú principal de opciones para los usuarios.
     */
    public void mostrarMenu() {
        ConsolaView.limpiarPantalla();
        ConsolaView.mostrarTitulo("GESTIÓN DE USUARIOS");
        System.out.println("1. Alta usuario");
        System.out.println("2. Ver usuario");
        System.out.println("3. Modificar usuario");
        System.out.println("4. Eliminar usuario");
        System.out.println("5. Listar todos");
        System.out.println("0. Volver");
    }

    /**
     * Muestra los detalles de un usuario específico.
     * @param usuario el usuario a mostrar.
     */
    public void mostrarUsuario(Usuario usuario) {
        ConsolaView.mostrarTitulo("DATOS DEL USUARIO");
        System.out.println("ID:          " + usuario.getIdUsuario());
        System.out.println("Nombre:      " + usuario.getNombre());
        System.out.println("Correo:      " + usuario.getCorreoElectronico());
        System.out.println("Nacimiento:  " + (usuario.getFechaNacimiento() != null ? usuario.getFechaNacimiento() : "N/A"));
        System.out.println("Tipo:        " + usuario.getTipoUsuario());
        ConsolaView.pausar("Pulse intro para continuar...");
    }

    /**
     * Muestra un listado de usuarios.
     * @param usuarios la lista de usuarios a mostrar.
     */
    public void listarUsuarios(List<Usuario> usuarios) {
        ConsolaView.mostrarTitulo("LISTADO DE USUARIOS");
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados.");
        } else {
            System.out.printf("%-5s %-25s %-30s %-12s %-15s%n", "ID", "Nombre", "Correo", "Fecha Nac.", "Tipo");
            System.out.println("-".repeat(92));
            for (Usuario u : usuarios) {
                System.out.printf("%-5d %-25s %-30s %-12s %-15s%n",
                        u.getIdUsuario(), u.getNombre(), u.getCorreoElectronico(),
                        u.getFechaNacimiento() != null ? u.getFechaNacimiento().toString() : "N/A",
                        u.getTipoUsuario());
            }
        }
        ConsolaView.pausar("Pulse intro para continuar...");
    }
}
