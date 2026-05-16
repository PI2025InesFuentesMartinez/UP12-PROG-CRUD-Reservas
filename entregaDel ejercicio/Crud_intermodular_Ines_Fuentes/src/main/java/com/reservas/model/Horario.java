package com.reservas.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

/**
 * Entidad que define un bloque de tiempo de disponibilidad en la semana.
 */
public class Horario {

    public static final List<String> DIAS_VALIDOS = Arrays.asList(
            "Lunes", "Martes", "Miércoles", "Jueves",
            "Viernes", "Sábado", "Domingo");

    private int idHorario;
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Horario() {
    }

    public Horario(
            String diaSemana,
            LocalTime horaInicio,
            LocalTime horaFin) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Horario(
            int idHorario,
            String diaSemana,
            LocalTime horaInicio,
            LocalTime horaFin) {
        this(diaSemana, horaInicio, horaFin);
        this.idHorario = idHorario;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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

    @Override
    public String toString() {
        return "Horario{" + idHorario + " - " + diaSemana + " " + horaInicio + "-" + horaFin + "}";
    }
}
