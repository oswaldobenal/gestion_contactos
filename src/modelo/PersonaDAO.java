package modelo;

import java.sql.*;
import java.util.*;

public class PersonaDAO {
    // Configuración de la base de datos
    private static final String JDBC_URL = "jdbc:sqlite:contactos.db";
    
    // Constructor
    public PersonaDAO() {
        inicializarBaseDatos();
    }
    
    // Inicializar la base de datos
    private void inicializarBaseDatos() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            // Crear tabla si no existe
            Statement stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS personas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT NOT NULL," +
                    "telefono TEXT," +
                    "email TEXT," +
                    "categoria TEXT," +
                    "favorito INTEGER DEFAULT 0)";
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
    
    // Insertar un nuevo contacto
    public boolean insertar(Persona persona) {
        String sql = "INSERT INTO personas (nombre, telefono, email, categoria, favorito) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getTelefono());
            pstmt.setString(3, persona.getEmail());
            pstmt.setString(4, persona.getCategoria());
            pstmt.setInt(5, persona.isFavorito() ? 1 : 0);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar contacto: " + e.getMessage());
            return false;
        }
    }
    
    // Actualizar un contacto existente
    public boolean actualizar(Persona persona) {
        String sql = "UPDATE personas SET nombre = ?, telefono = ?, email = ?, categoria = ?, favorito = ? WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getTelefono());
            pstmt.setString(3, persona.getEmail());
            pstmt.setString(4, persona.getCategoria());
            pstmt.setInt(5, persona.isFavorito() ? 1 : 0);
            pstmt.setInt(6, persona.getId());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar contacto: " + e.getMessage());
            return false;
        }
    }
    
    // Eliminar un contacto
    public boolean eliminar(int id) {
        String sql = "DELETE FROM personas WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar contacto: " + e.getMessage());
            return false;
        }
    }
    
    // Obtener un contacto por su ID
    public Persona obtenerPorId(int id) {
        String sql = "SELECT * FROM personas WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Persona persona = new Persona();
                persona.setId(rs.getInt("id"));
                persona.setNombre(rs.getString("nombre"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setEmail(rs.getString("email"));
                persona.setCategoria(rs.getString("categoria"));
                persona.setFavorito(rs.getInt("favorito") == 1);
                return persona;
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener contacto: " + e.getMessage());
        }
        
        return null;
    }
    
    // Obtener todos los contactos
    public List<Persona> obtenerTodos() {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM personas";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setId(rs.getInt("id"));
                persona.setNombre(rs.getString("nombre"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setEmail(rs.getString("email"));
                persona.setCategoria(rs.getString("categoria"));
                persona.setFavorito(rs.getInt("favorito") == 1);
                lista.add(persona);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los contactos: " + e.getMessage());
        }
        
        return lista;
    }
    
    // Buscar contactos por diferentes criterios
    public List<Persona> buscar(String termino) {
        List<Persona> lista = new ArrayList<>();
        String sql = "SELECT * FROM personas WHERE nombre LIKE ? OR telefono LIKE ? OR email LIKE ? OR categoria LIKE ?";
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String busqueda = "%" + termino + "%";
            pstmt.setString(1, busqueda);
            pstmt.setString(2, busqueda);
            pstmt.setString(3, busqueda);
            pstmt.setString(4, busqueda);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Persona persona = new Persona();
                persona.setId(rs.getInt("id"));
                persona.setNombre(rs.getString("nombre"));
                persona.setTelefono(rs.getString("telefono"));
                persona.setEmail(rs.getString("email"));
                persona.setCategoria(rs.getString("categoria"));
                persona.setFavorito(rs.getInt("favorito") == 1);
                lista.add(persona);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar contactos: " + e.getMessage());
        }
        
        return lista;
    }
    
    // Obtener estadísticas de contactos
    public Map<String, Integer> obtenerEstadisticas() {
        Map<String, Integer> estadisticas = new HashMap<>();
        
        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            // Total de contactos
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as total FROM personas")) {
                if (rs.next()) {
                    estadisticas.put("total", rs.getInt("total"));
                }
            }
            
            // Contactos favoritos
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT COUNT(*) as favoritos FROM personas WHERE favorito = 1")) {
                if (rs.next()) {
                    estadisticas.put("favoritos", rs.getInt("favoritos"));
                }
            }
            
            // Contactos por categoría
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT categoria, COUNT(*) as cantidad FROM personas GROUP BY categoria")) {
                while (rs.next()) {
                    String categoria = rs.getString("categoria");
                    if (categoria == null || categoria.isEmpty()) {
                        categoria = "sin_categoria";
                    }
                    estadisticas.put(categoria, rs.getInt("cantidad"));
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener estadísticas: " + e.getMessage());
        }
        
        return estadisticas;
    }
}