package com.reservas.dao;

import com.reservas.model.Administrador;
import java.sql.*;

/**
 * Clase que gestiona las operaciones de acceso a datos (CRUD) para la entidad Administrador.
 */
public class AdministradorDAO {

    private static final String INSERT_QUERY = "INSERT INTO ADMINISTRADOR (id_usuario, telefono_guardia) VALUES (?, ?)";
    private static final String SELECT_BY_ID = "SELECT id_usuario, telefono_guardia FROM ADMINISTRADOR WHERE id_usuario = ?";
    private static final String UPDATE_QUERY = "UPDATE ADMINISTRADOR SET telefono_guardia = ? WHERE id_usuario = ?";
    private static final String DELETE_QUERY = "DELETE FROM ADMINISTRADOR WHERE id_usuario = ?";

    private static volatile AdministradorDAO instance;

    private AdministradorDAO() {}

    /**
     * Obtiene la instancia única de AdministradorDAO (Patrón Singleton).
     * @return la instancia de AdministradorDAO.
     */
    public static AdministradorDAO getInstance() {
        if (instance == null) {
            synchronized (AdministradorDAO.class) {
                if (instance == null) {
                    instance = new AdministradorDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Crea un nuevo registro de administrador en la base de datos.
     * @param administrador el objeto Administrador a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean create(Administrador administrador) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
             
            preparedStatement.setInt(1, administrador.getIdUsuario());
            preparedStatement.setString(2, administrador.getTelefonoGuardia());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Lee un administrador de la base de datos mediante su ID.
     * @param idAdministrador el ID del administrador a buscar.
     * @return el objeto Administrador si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public Administrador read(int idAdministrador) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
             
            preparedStatement.setInt(1, idAdministrador);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToAdministrador(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Actualiza los datos de un administrador existente en la base de datos.
     * @param administrador el objeto Administrador con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean update(Administrador administrador) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
             
            preparedStatement.setString(1, administrador.getTelefonoGuardia());
            preparedStatement.setInt(2, administrador.getIdUsuario());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un administrador de la base de datos.
     * @param idAdministrador el ID del administrador a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean delete(int idAdministrador) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
             
            preparedStatement.setInt(1, idAdministrador);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    private Administrador resultSetToAdministrador(ResultSet resultSet) throws SQLException {
        Administrador admin = new Administrador();
        admin.setIdUsuario(resultSet.getInt("id_usuario"));
        admin.setTelefonoGuardia(resultSet.getString("telefono_guardia"));
        return admin;
    }
}