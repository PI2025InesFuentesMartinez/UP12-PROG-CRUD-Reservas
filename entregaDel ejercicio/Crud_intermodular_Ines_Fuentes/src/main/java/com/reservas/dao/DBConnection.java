package com.reservas.dao;

import java.sql.*;

/**
 * Clase utilitaria para gestionar la conexión a la base de datos (Patrón Singleton).
 */
public final class DBConnection {
    private static final String URL = "jdbc:mariadb://localhost:3306/sistema_reservas";
    private static final String USERNAME = "reservas_app";
    private static final String PASSWORD = "reservas_app";
    private static Connection connection;

    private DBConnection() {
    }

    /**
     * Obtiene la conexión activa a la base de datos. Si no existe, la crea.
     * @return la conexión a la base de datos.
     * @throws SQLException si ocurre un error al intentar establecer la conexión.
     */
    public static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()) {
            synchronized (DBConnection.class) {
                if (connection == null || connection.isClosed()) {
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                }
            }
        }
        return connection;
    }

    /**
     * Cierra la conexión activa a la base de datos, si existe.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.err.println("No se ha podido cerrar la conexión: " + e.getMessage());
            }
        }
    }
}