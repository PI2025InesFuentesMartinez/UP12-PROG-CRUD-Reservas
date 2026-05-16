package com.reservas.dao;

import com.reservas.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona las operaciones de acceso a datos (CRUD) para la entidad base Usuario.
 */
public class UsuarioDAO {

    private static final String INSERT_QUERY = "INSERT INTO USUARIO (correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID = "SELECT id_usuario, correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario FROM USUARIO WHERE id_usuario = ?";
    private static final String SELECT_ALL = "SELECT id_usuario, correo_electronico, contrasena, nombre, fecha_nacimiento, tipo_usuario FROM USUARIO ORDER BY nombre";
    private static final String UPDATE_QUERY = "UPDATE USUARIO SET correo_electronico = ?, contrasena = ?, nombre = ?, fecha_nacimiento = ?, tipo_usuario = ? WHERE id_usuario = ?";
    private static final String DELETE_QUERY = "DELETE FROM USUARIO WHERE id_usuario = ?";

    private static volatile UsuarioDAO instance;

    private UsuarioDAO() {}

    /**
     * Obtiene la instancia única de UsuarioDAO (Patrón Singleton).
     * @return la instancia de UsuarioDAO.
     */
    public static UsuarioDAO getInstance() {
        if (instance == null) {
            synchronized (UsuarioDAO.class) {
                if (instance == null) {
                    instance = new UsuarioDAO();
                }
            }
        }
        return instance;
    }

    /**
     * Crea un nuevo registro de usuario en la base de datos.
     * @param usuario el objeto Usuario a insertar.
     * @return el ID del usuario creado, o 0 si falló.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public int create(Usuario usuario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
             
            preparedStatement.setString(1, usuario.getCorreoElectronico());
            preparedStatement.setString(2, usuario.getContrasena());
            preparedStatement.setString(3, usuario.getNombre());
            preparedStatement.setDate(4, Date.valueOf(usuario.getFechaNacimiento()));
            preparedStatement.setString(5, usuario.getTipoUsuario());
            
            int filasInsertadas = preparedStatement.executeUpdate();
            if (filasInsertadas > 0) {
                try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                    if (keys.next()) {
                        int id = keys.getInt(1);
                        usuario.setIdUsuario(id);
                        return id;
                    }
                }
            }
            return 0;
        }
    }

    /**
     * Lee un usuario de la base de datos mediante su ID.
     * @param idUsuario el ID del usuario a buscar.
     * @return el objeto Usuario si se encuentra, null en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public Usuario read(int idUsuario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID)) {
             
            preparedStatement.setInt(1, idUsuario);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSetToUsuario(resultSet);
                }
            }
        }
        return null;
    }

    /**
     * Actualiza los datos de un usuario existente en la base de datos.
     * @param usuario el objeto Usuario con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean update(Usuario usuario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
             
            preparedStatement.setString(1, usuario.getCorreoElectronico());
            preparedStatement.setString(2, usuario.getContrasena());
            preparedStatement.setString(3, usuario.getNombre());
            preparedStatement.setDate(4, Date.valueOf(usuario.getFechaNacimiento()));
            preparedStatement.setString(5, usuario.getTipoUsuario());
            preparedStatement.setInt(6, usuario.getIdUsuario());
            
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Elimina un usuario de la base de datos.
     * @param idUsuario el ID del usuario a eliminar.
     * @return true si la eliminación fue exitosa, false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public boolean delete(int idUsuario) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
             
            preparedStatement.setInt(1, idUsuario);
            return preparedStatement.executeUpdate() > 0;
        }
    }

    /**
     * Obtiene una lista con todos los usuarios registrados en la base de datos.
     * @return una lista de objetos Usuario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public List<Usuario> listAll() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                lista.add(resultSetToUsuario(resultSet));
            }
        }
        return lista;
    }

    private Usuario resultSetToUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(resultSet.getInt("id_usuario"));
        usuario.setCorreoElectronico(resultSet.getString("correo_electronico"));
        usuario.setContrasena(resultSet.getString("contrasena"));
        usuario.setNombre(resultSet.getString("nombre"));
        
        Date sqlDate = resultSet.getDate("fecha_nacimiento");
        if (sqlDate != null) {
            usuario.setFechaNacimiento(sqlDate.toLocalDate());
        }
        
        usuario.setTipoUsuario(resultSet.getString("tipo_usuario"));
        return usuario;
    }
}
