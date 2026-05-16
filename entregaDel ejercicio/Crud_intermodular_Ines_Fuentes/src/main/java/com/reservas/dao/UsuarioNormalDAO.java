package com.reservas.dao;

import com.reservas.model.UsuarioNormal;
import java.sql.*;

/**
 * Clase que gestiona las operaciones de acceso a datos (CRUD) para la entidad UsuarioNormal.
 */
public class UsuarioNormalDAO {

    private static final String INSERT_QUERY = "INSERT INTO USUARIONORMAL (id_usuario, direccion, telefono_movil, fotografia) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT id_usuario, direccion, telefono_movil, fotografia FROM USUARIONORMAL WHERE id_usuario = ?";
    private static final String UPDATE_QUERY = "UPDATE USUARIONORMAL SET direccion = ?, telefono_movil = ?, fotografia = ? WHERE id_usuario = ?";
    private static final String DELETE_QUERY = "DELETE FROM USUARIONORMAL WHERE id_usuario = ?";

    private static volatile UsuarioNormalDAO instance;

    private UsuarioNormalDAO() {}

    /**
     * Obtiene la instancia única de UsuarioNormalDAO (Patrón Singleton).
     * @return la instancia de UsuarioNormalDAO.
     */
    public static UsuarioNormalDAO getInstance() {
        if (instance == null) {
            synchronized (UsuarioNormalDAO.class) {
                if (instance == null) {
                    instance = new UsuarioNormalDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Crea un nuevo registro de usuario normal en la base de datos.
     * @param usuarioNormal el objeto UsuarioNormal a insertar.
     * @return true si la inserción fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean create(UsuarioNormal usuarioNormal) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
             
            preparedStatement.setInt(1, usuarioNormal.getIdUsuario());
            preparedStatement.setString(2, usuarioNormal.getDireccion());
            preparedStatement.setString(3, usuarioNormal.getTelefonoMovil());
            preparedStatement.setString(4, usuarioNormal.getFotografia());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Lee un usuario normal de la base de datos mediante su ID.
     * @param idUsuarioNormal el ID del usuario normal a buscar.
     * @return el objeto UsuarioNormal si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public UsuarioNormal read(int idUsuarioNormal) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
             
            preparedStatement.setInt(1, idUsuarioNormal);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToUsuarioNormal(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Actualiza los datos de un usuario normal existente en la base de datos.
     * @param usuarioNormal el objeto UsuarioNormal con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean update(UsuarioNormal usuarioNormal) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
             
            preparedStatement.setString(1, usuarioNormal.getDireccion());
            preparedStatement.setString(2, usuarioNormal.getTelefonoMovil());
            preparedStatement.setString(3, usuarioNormal.getFotografia());
            preparedStatement.setInt(4, usuarioNormal.getIdUsuario());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un usuario normal de la base de datos.
     * @param idUsuarioNormal el ID del usuario normal a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean delete(int idUsuarioNormal) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
             
            preparedStatement.setInt(1, idUsuarioNormal);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    private UsuarioNormal resultSetToUsuarioNormal(ResultSet resultSet) throws SQLException {
        UsuarioNormal usuarioNormal = new UsuarioNormal();
        usuarioNormal.setIdUsuario(resultSet.getInt("id_usuario"));
        usuarioNormal.setDireccion(resultSet.getString("direccion"));
        usuarioNormal.setTelefonoMovil(resultSet.getString("telefono_movil"));
        usuarioNormal.setFotografia(resultSet.getString("fotografia"));
        return usuarioNormal;
    }
}
