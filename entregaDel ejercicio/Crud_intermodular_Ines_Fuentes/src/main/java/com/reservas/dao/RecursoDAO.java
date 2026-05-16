package com.reservas.dao;

import com.reservas.model.Recurso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de acceso a datos (CRUD) para la entidad Recurso.
 */
public class RecursoDAO {

    private static final String INSERT_QUERY = "INSERT INTO RECURSO (nombre, descripcion, ubicacion, capacidad) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT id_recurso, nombre, descripcion, ubicacion, capacidad FROM RECURSO WHERE id_recurso = ?";
    private static final String SELECT_ALL = "SELECT id_recurso, nombre, descripcion, ubicacion, capacidad FROM RECURSO ORDER BY nombre";
    private static final String UPDATE_QUERY = "UPDATE RECURSO SET nombre = ?, descripcion = ?, ubicacion = ?, capacidad = ? WHERE id_recurso = ?";
    private static final String DELETE_QUERY = "DELETE FROM RECURSO WHERE id_recurso = ?";

    private static volatile RecursoDAO instance;

    private RecursoDAO() {}

    /**
     * Obtiene la instancia única de RecursoDAO (Patrón Singleton).
     * @return la instancia de RecursoDAO.
     */
    public static RecursoDAO getInstance() {
        if (instance == null) {
            synchronized (RecursoDAO.class) {
                if (instance == null) {
                    instance = new RecursoDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Crea un nuevo registro de recurso en la base de datos.
     * @param recurso el objeto Recurso a insertar.
     * @return el ID del recurso creado, o 0 si falló.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public int create(Recurso recurso) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
             
            preparedStatement.setString(1, recurso.getNombre());
            preparedStatement.setString(2, recurso.getDescripcion());
            preparedStatement.setString(3, recurso.getUbicacion());
            preparedStatement.setInt(4, recurso.getCapacidad());
            
            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int id = keys.getInt(1);
                        recurso.setIdRecurso(id);
                        return id;
                    }
                }
            }
            return 0;
        }
    }

    /**
     * Lee un recurso de la base de datos mediante su ID.
     * @param idRecurso el ID del recurso a buscar.
     * @return el objeto Recurso si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public Recurso read(int idRecurso) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
             
            preparedStatement.setInt(1, idRecurso);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToRecurso(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Actualiza los datos de un recurso existente en la base de datos.
     * @param recurso el objeto Recurso con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean update(Recurso recurso) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
             
            preparedStatement.setString(1, recurso.getNombre());
            preparedStatement.setString(2, recurso.getDescripcion());
            preparedStatement.setString(3, recurso.getUbicacion());
            preparedStatement.setInt(4, recurso.getCapacidad());
            preparedStatement.setInt(5, recurso.getIdRecurso());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un recurso de la base de datos.
     * @param idRecurso el ID del recurso a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean delete(int idRecurso) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
             
            preparedStatement.setInt(1, idRecurso);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene una lista con todos los recursos registrados en la base de datos.
     * @return una lista de objetos Recurso.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public List<Recurso> listAll() throws SQLException {
        List<Recurso> lista = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                lista.add(resultSetToRecurso(resultSet));
            }
        }
        return lista;
    }

    private Recurso resultSetToRecurso(ResultSet resultSet) throws SQLException {
        Recurso recurso = new Recurso();
        recurso.setIdRecurso(resultSet.getInt("id_recurso"));
        recurso.setNombre(resultSet.getString("nombre"));
        recurso.setDescripcion(resultSet.getString("descripcion"));
        recurso.setUbicacion(resultSet.getString("ubicacion"));
        recurso.setCapacidad(resultSet.getInt("capacidad"));
        return recurso;
    }
}
