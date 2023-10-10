package edu.umg.domain.user;

import edu.umg.datos.Conexion;

import java.sql.Connection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    //Sentencias SQL
    //Recuperar un usuario por su ID
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM usuario WHERE id = ?";
    //Recuperar todos los usuarios
    private static final String SQL_SELECT = "SELECT * FROM usuario";
    //Insertar un usuario
    private static final String SQL_INSERT = "INSERT INTO usuario(username, password) VALUES(?, ?)";
    //Actualizar un usuario
    private static final String SQL_UPDATE = "UPDATE usuario SET username = ?, password = ? WHERE id = ?";
    //Eliminar un usuario
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE id = ?";
    //Validar un usuario por su username y password
    private static final String SQL_SELECT_BY_USER_PASS = "SELECT * FROM usuario WHERE username = ? AND password = ?";

//Mensaje de notificación para la conexión exitosa
private static final String SUCCESSFUL_CONNECTION_MESSAGE = "Conexión exitosa a la base de datos.";

    private Connection conexionTransaccional;

    public UsuarioDAO() {
    }

    //Constructor para utilizar una conexión transaccional
    public UsuarioDAO(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }


    //Funcion para encriptar la contraseña
    //Esta función toma una contraseña como entrada, calcula su hash utilizando el algoritmo SHA-512
    // y devuelve el hash en forma de cadena hexadecimal. Esto es útil para almacenar contraseñas de
    // manera segura en una base de datos, ya que el hash es irreversible y difícil de descifrar.
    // Cuando un usuario ingresa su contraseña, la contraseña ingresada se encripta de la misma manera
    // y se compara con el hash almacenado en la base de datos para verificar su autenticidad.
    private String encriptarContraseña(String contraseña) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(contraseña.getBytes());
            byte[] mb = md.digest();
            String encriptado = "";
            for (int i = 0; i < mb.length; i++) {
                encriptado += Integer.toHexString(mb[i]);
            }
            return encriptado;
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }

    //Validar el usuario por medio de su username y password
    public boolean validarUsuario(String username, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean usuarioValido = false;

        try {
            conn = obtenerConexion();
            stmt = conn.prepareStatement(SQL_SELECT_BY_USER_PASS);
            stmt.setString(1, username);
            stmt.setString(2, encriptarContraseña(password));
            rs = stmt.executeQuery();
            while (rs.next()) {
                usuarioValido = true;
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            cerrarConexion(conn);
        }
        return usuarioValido;
    }

    //Metodo para obtener la conexion
    private Connection obtenerConexion() throws SQLException {
        Connection conn = null;
        if (this.conexionTransaccional != null) {
            conn = this.conexionTransaccional;
        } else {
            conn = Conexion.getConnection();
        }
        return conn;
    }

    //Metodo para cerrar la conexion
    private void cerrarConexion(Connection conn) throws SQLException {
        if (this.conexionTransaccional == null) {
            conn.close();
        }
    }
}