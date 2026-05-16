package com.reservas.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Entidad que representa la reserva de un recurso por parte de un usuario en un horario determinado.
 */
public class Reserva {

    private int idRecurso;
    private int idReservaLocal;
    private int idUsuario;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Double coste;
    private Integer numeroPlazas;
    private String motivo;
    private String observaciones;

    public Reserva() {
    }

    public Reserva(
            int idRecurso,
            int idUsuario,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            Double coste,
            Integer numeroPlazas,
            String motivo,
            String observaciones) {
        this.idRecurso = idRecurso;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.coste = coste;
        this.numeroPlazas = numeroPlazas;
        this.motivo = motivo;
        this.observaciones = observaciones;
    }

    public Reserva(
            int idRecurso,
            int idReservaLocal,
            int idUsuario,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            Double coste,
            Integer numeroPlazas,
            String motivo,
            String observaciones) {
        this(idRecurso, idUsuario, fecha, horaInicio, horaFin, coste, numeroPlazas,
                motivo, observaciones);
        this.idReservaLocal = idReservaLocal;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public int getIdReservaLocal() {
        return idReservaLocal;
    }

    public void setIdReservaLocal(int idReservaLocal) {
        this.idReservaLocal = idReservaLocal;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public Integer getNumeroPlazas() {
        return numeroPlazas;
    }

    public void setNumeroPlazas(Integer numeroPlazas) {
        this.numeroPlazas = numeroPlazas;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Reserva{recurso=" + idRecurso + "/" + idReservaLocal
                + ", usuario=" + idUsuario
                + ", " + fecha + " " + horaInicio + "-" + horaFin
                + ", coste=" + coste + ", plazas=" + numeroPlazas
                + ", motivo='" + motivo + "'}";
    }
}