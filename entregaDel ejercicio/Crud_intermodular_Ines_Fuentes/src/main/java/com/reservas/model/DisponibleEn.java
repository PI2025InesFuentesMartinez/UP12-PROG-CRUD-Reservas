package com.reservas.model;

/**
 * Representa la relación de disponibilidad entre un recurso y un horario específico.
 */
public class DisponibleEn {

    private int idRecurso;
    private int idHorario;

    public DisponibleEn(
            int idRecurso,
            int idHorario) {
        this.idRecurso = idRecurso;
        this.idHorario = idHorario;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int idRecurso) {
        this.idRecurso = idRecurso;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    @Override
    public String toString() {
        return "DisponibleEn{recurso=" + idRecurso + ", horario=" + idHorario + "}";
    }
}
