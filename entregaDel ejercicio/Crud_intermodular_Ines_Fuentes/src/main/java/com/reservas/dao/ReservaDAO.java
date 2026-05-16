package com.reservas.dao;

import com.reservas.model.Reserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de acceso a datos (CRUD) para la entidad Reserva.
 */
public class ReservaDAO {

    private static final String INSERT_QUERY = "INSERT INTO RESERVA (id_recurso, id_reserva_local, id_usuario, fecha, hora_inicio, hora_fin, coste, numero_plazas, motivo, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT id_recurso, id_reserva_local, id_usuario, fecha, hora_inicio, hora_fin, coste, numero_plazas, motivo, observaciones FROM RESERVA WHERE id_recurso = ? AND id_reserva_local = ?";
    private static final String SELECT_ALL = "SELECT id_recurso, id_reserva_local, id_usuario, fecha, hora_inicio, hora_fin, coste, numero_plazas, motivo, observaciones FROM RESERVA ORDER BY id_recurso, id_reserva_local";
    private static final String SELECT_MAX_LOCAL = "SELECT COALESCE(MAX(id_reserva_local), 0) FROM RESERVA WHERE id_recurso = ?";
    private static final String UPDATE_QUERY = "UPDATE RESERVA SET id_usuario = ?, fecha = ?, hora_inicio = ?, hora_fin = ?, coste = ?, numero_plazas = ?, motivo = ?, observaciones = ? WHERE id_recurso = ? AND id_reserva_local = ?";
    private static final String DELETE_QUERY = "DELETE FROM RESERVA WHERE id_recurso = ? AND id_reserva_local = ?";

    private static volatile ReservaDAO instance;

    private ReservaDAO() {}

    /**
     * Obtiene la instancia única de ReservaDAO (Patrón Singleton).
     * @return la instancia de ReservaDAO.
     */
    public static ReservaDAO getInstance() {
        if (instance == null) {
            synchronized (ReservaDAO.class) {
                if (instance == null) {
                    instance = new ReservaDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Crea un nuevo registro de reserva en la base de datos.
     * @param reserva el objeto Reserva a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean create(Reserva reserva) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
             
            preparedStatement.setInt(1, reserva.getIdRecurso());
            preparedStatement.setInt(2, reserva.getIdReservaLocal());
            preparedStatement.setInt(3, reserva.getIdUsuario());
            
            preparedStatement.setDate(4, reserva.getFecha() != null ? Date.valueOf(reserva.getFecha()) : null);
            preparedStatement.setTime(5, reserva.getHoraInicio() != null ? Time.valueOf(reserva.getHoraInicio()) : null);
            preparedStatement.setTime(6, reserva.getHoraFin() != null ? Time.valueOf(reserva.getHoraFin()) : null);
            
            if (reserva.getCoste() != null) {
                preparedStatement.setDouble(7, reserva.getCoste());
            } else {
                preparedStatement.setNull(7, Types.DOUBLE);
            }
            
            if (reserva.getNumeroPlazas() != null) {
                preparedStatement.setInt(8, reserva.getNumeroPlazas());
            } else {
                preparedStatement.setNull(8, Types.INTEGER);
            }
            
            preparedStatement.setString(9, reserva.getMotivo());
            preparedStatement.setString(10, reserva.getObservaciones());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Lee una reserva de la base de datos mediante el ID del recurso y su ID local de reserva.
     * @param idRecurso el ID del recurso reservado.
     * @param idReservaLocal el ID local de la reserva dentro del recurso.
     * @return el objeto Reserva si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public Reserva read(int idRecurso, int idReservaLocal) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
             
            preparedStatement.setInt(1, idRecurso);
            preparedStatement.setInt(2, idReservaLocal);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToReserva(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Actualiza los datos de una reserva existente en la base de datos.
     * @param reserva el objeto Reserva con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean update(Reserva reserva) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
             
            preparedStatement.setInt(1, reserva.getIdUsuario());
            preparedStatement.setDate(2, reserva.getFecha() != null ? Date.valueOf(reserva.getFecha()) : null);
            preparedStatement.setTime(3, reserva.getHoraInicio() != null ? Time.valueOf(reserva.getHoraInicio()) : null);
            preparedStatement.setTime(4, reserva.getHoraFin() != null ? Time.valueOf(reserva.getHoraFin()) : null);
            
            if (reserva.getCoste() != null) {
                preparedStatement.setDouble(5, reserva.getCoste());
            } else {
                preparedStatement.setNull(5, Types.DOUBLE);
            }
            
            if (reserva.getNumeroPlazas() != null) {
                preparedStatement.setInt(6, reserva.getNumeroPlazas());
            } else {
                preparedStatement.setNull(6, Types.INTEGER);
            }
            
            preparedStatement.setString(7, reserva.getMotivo());
            preparedStatement.setString(8, reserva.getObservaciones());
            preparedStatement.setInt(9, reserva.getIdRecurso());
            preparedStatement.setInt(10, reserva.getIdReservaLocal());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina una reserva de la base de datos.
     * @param idRecurso el ID del recurso de la reserva.
     * @param idReservaLocal el ID local de la reserva a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean delete(int idRecurso, int idReservaLocal) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
             
            preparedStatement.setInt(1, idRecurso);
            preparedStatement.setInt(2, idReservaLocal);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene el siguiente ID local disponible para una reserva de un recurso específico.
     * @param idRecurso el ID del recurso.
     * @return el próximo ID local de reserva disponible.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public int nextIdReservaLocal(int idRecurso) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MAX_LOCAL)) {

            preparedStatement.setInt(1, idRecurso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) + 1;
                }
            }
        }
        return 1;
    }

    /**
     * Obtiene una lista con todas las reservas registradas en la base de datos.
     * @return una lista de objetos Reserva.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public List<Reserva> listAll() throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                lista.add(resultSetToReserva(resultSet));
            }
        }
        return lista;
    }

    private Reserva resultSetToReserva(ResultSet resultSet) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdRecurso(resultSet.getInt("id_recurso"));
        reserva.setIdReservaLocal(resultSet.getInt("id_reserva_local"));
        reserva.setIdUsuario(resultSet.getInt("id_usuario"));
        
        Date sqlFecha = resultSet.getDate("fecha");
        if (sqlFecha != null) {
            reserva.setFecha(sqlFecha.toLocalDate());
        }
        
        Time sqlHoraInicio = resultSet.getTime("hora_inicio");
        if (sqlHoraInicio != null) {
            reserva.setHoraInicio(sqlHoraInicio.toLocalTime());
        }
        
        Time sqlHoraFin = resultSet.getTime("hora_fin");
        if (sqlHoraFin != null) {
            reserva.setHoraFin(sqlHoraFin.toLocalTime());
        }
        
        double costeVal = resultSet.getDouble("coste");
        if (!resultSet.wasNull()) {
            reserva.setCoste(costeVal);
        }
        
        int plazasVal = resultSet.getInt("numero_plazas");
        if (!resultSet.wasNull()) {
            reserva.setNumeroPlazas(plazasVal);
        }
        
        reserva.setMotivo(resultSet.getString("motivo"));
        reserva.setObservaciones(resultSet.getString("observaciones"));
        
        return reserva;
    }
}
