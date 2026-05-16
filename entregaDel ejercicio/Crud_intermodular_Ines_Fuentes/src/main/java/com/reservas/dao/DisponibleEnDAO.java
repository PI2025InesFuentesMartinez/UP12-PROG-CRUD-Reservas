package com.reservas.dao;

import com.reservas.model.DisponibleEn;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de acceso a datos para la relación DisponibleEn.
 */
public class DisponibleEnDAO {

    private static final String INSERT_QUERY = "INSERT INTO DISPONIBLEEN (id_recurso, id_horario) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT id_recurso, id_horario FROM DISPONIBLEEN WHERE id_recurso = ? AND id_horario = ?";
    private static final String SELECT_BY_RECURSO = "SELECT id_recurso, id_horario FROM DISPONIBLEEN WHERE id_recurso = ? ORDER BY id_horario";
    private static final String DELETE_QUERY = "DELETE FROM DISPONIBLEEN WHERE id_recurso = ? AND id_horario = ?";

    private static volatile DisponibleEnDAO instance;

    private DisponibleEnDAO() {}

    /**
     * Obtiene la instancia única de DisponibleEnDAO.
     * @return la instancia de DisponibleEnDAO.
     */
    public static DisponibleEnDAO getInstance() {
        if (instance == null) {
            synchronized (DisponibleEnDAO.class) {
                if (instance == null) {
                    instance = new DisponibleEnDAO();
                }
            }
        }
        return instance;
    }
    
    

    /**
     * Crea una nueva relación de disponibilidad en la base de datos.
     * @param disponibleEn el objeto DisponibleEn a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean create(DisponibleEn disponibleEn) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
             
            preparedStatement.setInt(1, disponibleEn.getIdRecurso());
            preparedStatement.setInt(2, disponibleEn.getIdHorario());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Lee una relación de disponibilidad de la base de datos.
     * @param idRecurso el ID del recurso.
     * @param idHorario el ID del horario.
     * @return el objeto DisponibleEn si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public DisponibleEn read(int idRecurso, int idHorario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
             
            preparedStatement.setInt(1, idRecurso);
            preparedStatement.setInt(2, idHorario);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToDisponibleEn(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Elimina una relación de disponibilidad de la base de datos.
     * @param idRecurso el ID del recurso a desasignar.
     * @param idHorario el ID del horario a desasignar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean delete(int idRecurso, int idHorario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
             
            preparedStatement.setInt(1, idRecurso);
            preparedStatement.setInt(2, idHorario);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene la lista de disponibilidades asignadas a un recurso específico.
     * @param idRecurso el ID del recurso.
     * @return una lista de objetos DisponibleEn.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public List<DisponibleEn> listByRecurso(int idRecurso) throws SQLException {
        List<DisponibleEn> lista = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_RECURSO)) {

            preparedStatement.setInt(1, idRecurso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    lista.add(resultSetToDisponibleEn(resultSet));
                }
            }
        }
        return lista;
    }

    private DisponibleEn resultSetToDisponibleEn(ResultSet resultSet) throws SQLException {
        return new DisponibleEn(
            resultSet.getInt("id_recurso"),
            resultSet.getInt("id_horario")
        );
    }
}
