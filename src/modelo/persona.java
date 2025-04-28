package modelo;

public class Persona {
    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private String categoria;
    private boolean favorito;

    // Constructor vacío
    public Persona() {
    }

    // Constructor con todos los campos
    public Persona(int id, String nombre, String telefono, String email, String categoria, boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.categoria = categoria;
        this.favorito = favorito;
    }

    // Constructor sin id para nuevos contactos
    public Persona(String nombre, String telefono, String email, String categoria, boolean favorito) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.categoria = categoria;
        this.favorito = favorito;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", categoria='" + categoria + '\'' +
                ", favorito=" + favorito +
                '}';
    }
}