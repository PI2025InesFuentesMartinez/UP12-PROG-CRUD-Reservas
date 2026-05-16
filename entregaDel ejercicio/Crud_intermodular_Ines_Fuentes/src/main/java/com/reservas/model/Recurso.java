package com.reservas.model;

/**
 * Representa un recurso físico o lógico que puede ser reservado en el sistema.
 */
public class Recurso {

    private int idRecurso;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private int capacidad;

    public Recurso() {
    }

    public Recurso(
            String nombre,
            String descripcion,
            String ubicacion,
            int capacidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.capacidad = capacidad;
    }

    public Recurso(
            int idRecurso,
            String nombre,
            String descripcion,
            String ubicacion,
            int capacidad) {
        this(nombre, descripcion, ubicacion, capacidad);
        this.idRecurso = idRecurso;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    @Override
    public String toString() {
        return "Recurso{" + idRecurso + " - " + nombre
                + " (" + capacidad + " plazas) en " + ubicacion + "}";
    }
}
