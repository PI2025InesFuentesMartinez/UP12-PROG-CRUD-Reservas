package com.reservas.model;

import java.time.LocalDate;

/**
 * Representa a un usuario administrador del sistema, con privilegios adicionales.
 */
public class Administrador extends Usuario {

    private String telefonoGuardia;

    public Administrador() {
        super();
        setTipoUsuario(TIPO_ADMIN);
    }

    public Administrador(
            String correoElectronico,
            String contrasena,
            String nombre,
            LocalDate fechaNacimiento,
            String telefonoGuardia) {
        super(correoElectronico, contrasena, nombre, fechaNacimiento, TIPO_ADMIN);
        this.telefonoGuardia = telefonoGuardia;
    }

    public Administrador(
            int idUsuario,
            String correoElectronico,
            String contrasena,
            String nombre,
            LocalDate fechaNacimiento,
            String telefonoGuardia) {
        super(idUsuario, correoElectronico, contrasena, nombre, fechaNacimiento, TIPO_ADMIN);
        this.telefonoGuardia = telefonoGuardia;
    }

    public String getTelefonoGuardia() {
        return telefonoGuardia;
    }

    public void setTelefonoGuardia(String telefonoGuardia) {
        this.telefonoGuardia = telefonoGuardia;
    }

    @Override
    public String toString() {
        return "Administrador{id=" + getIdUsuario()
                + ", correo='" + getCorreoElectronico() + '\''
                + ", nombre='" + getNombre() + '\''
                + ", fechaNacimiento=" + getFechaNacimiento()
                + ", telefonoGuardia='" + telefonoGuardia + "'}";
    }
}
