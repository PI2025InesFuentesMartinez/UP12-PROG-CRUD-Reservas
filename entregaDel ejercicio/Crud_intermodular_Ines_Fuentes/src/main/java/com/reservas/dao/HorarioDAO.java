package com.reservas.dao;

import com.reservas.model.Horario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de acceso a datos (CRUD) para la entidad Horario.
 */
public class HorarioDAO {

    private static final String INSERT_QUERY = "INSERT INTO HORARIO (dia_semana, hora_inicio, hora_fin) VALUES (?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT id_horario, dia_semana, hora_inicio, hora_fin FROM HORARIO WHERE id_horario = ?";
    private static final String SELECT_ALL = "SELECT id_horario, dia_semana, hora_inicio, hora_fin FROM HORARIO ORDER BY dia_semana, hora_inicio";
    private static final String UPDATE_QUERY = "UPDATE HORARIO SET dia_semana = ?, hora_inicio = ?, hora_fin = ? WHERE id_horario = ?";
    private static final String DELETE_QUERY = "DELETE FROM HORARIO WHERE id_horario = ?";

    private static volatile HorarioDAO instance;

    private HorarioDAO() {}

    /**
     * Obtiene la instancia única de HorarioDAO (Patrón Singleton).
     * @return la instancia de HorarioDAO.
     */
    public static HorarioDAO getInstance() {
        if (instance == null) {
            synchronized (HorarioDAO.class) {
                if (instance == null) {
                    instance = new HorarioDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Crea un nuevo registro de horario en la base de datos.
     * @param horario el objeto Horario a insertar.
     * @return el ID del horario creado, o 0 si falló.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public int create(Horario horario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
             
            preparedStatement.setString(1, horario.getDiaSemana());
            preparedStatement.setTime(2, Time.valueOf(horario.getHoraInicio()));
            preparedStatement.setTime(3, Time.valueOf(horario.getHoraFin()));
            
            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int id = keys.getInt(1);
                        horario.setIdHorario(id);
                        return id;
                    }
                }
            }
            return 0;
        }
    }

    /**
     * Lee un horario de la base de datos mediante su ID.
     * @param idHorario el ID del horario a buscar.
     * @return el objeto Horario si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public Horario read(int idHorario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
             
            preparedStatement.setInt(1, idHorario);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToHorario(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Actualiza los datos de un horario existente en la base de datos.
     * @param horario el objeto Horario con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean update(Horario horario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
             
            preparedStatement.setString(1, horario.getDiaSemana());
            preparedStatement.setTime(2, Time.valueOf(horario.getHoraInicio()));
            preparedStatement.setTime(3, Time.valueOf(horario.getHoraFin()));
            preparedStatement.setInt(4, horario.getIdHorario());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un horario de la base de datos.
     * @param idHorario el ID del horario a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean delete(int idHorario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
             
            preparedStatement.setInt(1, idHorario);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene una lista con todos los horarios registrados en la base de datos.
     * @return una lista de objetos Horario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public List<Horario> listAll() throws SQLException {
        List<Horario> lista = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                lista.add(resultSetToHorario(resultSet));
            }
        }
        return lista;
    }

    private Horario resultSetToHorario(ResultSet resultSet) throws SQLException {
        Horario horario = new Horario();
        horario.setIdHorario(resultSet.getInt("id_horario"));
        horario.setDiaSemana(resultSet.getString("dia_semana"));
        
        Time sqlHoraInicio = resultSet.getTime("hora_inicio");
        if (sqlHoraInicio != null) {
            horario.setHoraInicio(sqlHoraInicio.toLocalTime());
        }
        
        Time sqlHoraFin = resultSet.getTime("hora_fin");
        if (sqlHoraFin != null) {
            horario.setHoraFin(sqlHoraFin.toLocalTime());
        }
        
        return horario;
    }
}
