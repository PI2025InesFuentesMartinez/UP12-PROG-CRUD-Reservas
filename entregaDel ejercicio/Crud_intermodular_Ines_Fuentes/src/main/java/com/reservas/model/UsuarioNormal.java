package com.reservas.model;

import java.time.LocalDate;

/**
 * Representa a un usuario estándar del sistema de reservas.
 */
public class UsuarioNormal extends Usuario {

    private String direccion;
    private String telefonoMovil;
    private String fotografia;

    public UsuarioNormal() {
        super();
        setTipoUsuario(TIPO_NORMAL);
    }

    public UsuarioNormal(
            String correoElectronico,
            String contrasena,
            String nombre,
            LocalDate fechaNacimiento,
            String direccion,
            String telefonoMovil,
            String fotografia) {
        super(correoElectronico, contrasena, nombre, fechaNacimiento, TIPO_NORMAL);
        this.direccion = direccion;
        this.telefonoMovil = telefonoMovil;
        this.fotografia = fotografia;
    }

    public UsuarioNormal(
            int idUsuario,
            String correoElectronico,
            String contrasena,
            String nombre,
            LocalDate fechaNacimiento,
            String direccion,
            String telefonoMovil,
            String fotografia) {
        super(idUsuario, correoElectronico, contrasena, nombre, fechaNacimiento, TIPO_NORMAL);
        this.direccion = direccion;
        this.telefonoMovil = telefonoMovil;
        this.fotografia = fotografia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefonoMovil() {
        return telefonoMovil;
    }

    public void setTelefonoMovil(String telefonoMovil) {
        this.telefonoMovil = telefonoMovil;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    @Override
    public String toString() {
        return "UsuarioNormal{id=" + getIdUsuario()
                + ", correo='" + getCorreoElectronico() + '\''
                + ", nombre='" + getNombre() + '\''
                + ", direccion='" + direccion + '\''
                + ", tlf='" + telefonoMovil + '\''
                + ", foto='" + fotografia + "'}";
    }
}
