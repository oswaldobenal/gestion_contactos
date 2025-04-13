package modelo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class personaDAO {
    private File archivo;
    private persona persona;
    
    public personaDAO(persona persona) {
        this.persona = persona;
        prepararArchivo();
    }
    
    private void prepararArchivo() {
        archivo = new File("contactos.csv");
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                escribir("Nombre;Telefono;Email;Categoria;Favorito");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private void escribir(String texto) {
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write(texto + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean escribirArchivo() {
        escribir(persona.datosContacto());
        return true;
    }
    
    public List<persona> leerArchivo() throws IOException {
        List<persona> contactos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = reader.readLine()) != null) {
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] datos = linea.split(";");
                persona p = new persona(
                    datos[0], datos[1], datos[2], datos[3], Boolean.parseBoolean(datos[4])
                );
                contactos.add(p);
            }
        }
        return contactos;
    }
    
    public void exportarCSV(List<persona> contactos) throws IOException {
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("Nombre;Telefono;Email;Categoria;Favorito\n");
            for (persona p : contactos) {
                writer.write(p.datosContacto() + "\n");
            }
        }
    }
    
    public void actualizarContactos(List<persona> contactos) throws IOException {
        // Borramos el archivo existente
        archivo.delete();
        
        // Volvemos a crear el archivo
        prepararArchivo();
        
        // Escribimos todos los contactos actualizados
        try (FileWriter writer = new FileWriter(archivo, true)) {
            writer.write("Nombre;Telefono;Email;Categoria;Favorito\n");
            for (persona p : contactos) {
                writer.write(p.datosContacto() + "\n");
            }
        }
    }
}