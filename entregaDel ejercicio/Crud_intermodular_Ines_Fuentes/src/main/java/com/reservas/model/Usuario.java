package com.reservas.model;

import java.time.LocalDate;

/**
 * Clase base que define a un usuario del sistema de reservas.
 */
public class Usuario {

    public static final String TIPO_ADMIN = "Administrador";
    public static final String TIPO_NORMAL = "Normal";

    private int idUsuario;
    private String correoElectronico;
    private String contrasena;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String tipoUsuario;

    public Usuario() {
    }

    public Usuario(
            String correoElectronico,
            String contrasena,
            String nombre,
            LocalDate fechaNacimiento,
            String tipoUsuario) {
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuario(
            int idUsuario,
            String correoElectronico,
            String contrasena,
            String nombre,
            LocalDate fechaNacimiento,
            String tipoUsuario) {
        this(correoElectronico, contrasena, nombre, fechaNacimiento, tipoUsuario);
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario
                + ", correo='" + correoElectronico + '\''
                + ", nombre='" + nombre + '\''
                + ", fechaNacimiento=" + fechaNacimiento
                + ", tipo='" + tipoUsuario + "'}";
    }
}
