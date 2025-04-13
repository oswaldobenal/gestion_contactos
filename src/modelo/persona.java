package modelo;

public class persona {
    private String nombre, telefono, email, categoria;
    private boolean favorito;
    
    public persona() {
        this.nombre = "";
        this.telefono = "";
        this.email = "";
        this.categoria = "";
        this.favorito = false;
    }
    
    public persona(String nombre, String telefono, String email, String categoria, boolean favorito) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.categoria = categoria;
        this.favorito = favorito;
    }
    
    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public boolean isFavorito() { return favorito; }
    public void setFavorito(boolean favorito) { this.favorito = favorito; }
    
    public String datosContacto() {
        return String.format("%s;%s;%s;%s;%s", nombre, telefono, email, categoria, favorito);
    }
    
    public String formatoLista() {
        return String.format("%-40s%-40s%-40s%-40s", nombre, telefono, email, categoria);
    }
}