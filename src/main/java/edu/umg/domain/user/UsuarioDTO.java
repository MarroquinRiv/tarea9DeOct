package edu.umg.domain.user;

public class UsuarioDTO {

    private String nombreUsuario;
    private String contraseña;

    public UsuarioDTO() {
    }

    //Se genera el constructor con los parametros de la clase y getters y setters por defecto

    public UsuarioDTO(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    //Metodo toString para representacion textual del objeto
    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }

}
