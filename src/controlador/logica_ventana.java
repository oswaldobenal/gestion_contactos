package controlador;

import modelo.Persona;
import modelo.PersonaDAO;
import vista.ventana;

import javax.swing.JOptionPane;
import java.util.List;
import java.util.Map;
import util.I18nManager;

public class logica_ventana {
    private final ventana vista;
    private final PersonaDAO personaDAO;
    private final I18nManager i18n;
    
    public logica_ventana(ventana vista) {
        this.vista = vista;
        this.personaDAO = new PersonaDAO();
        this.i18n = I18nManager.getInstance();
    }
    
    public void cargarContactos() {
        List<Persona> contactos = personaDAO.obtenerTodos();
        vista.actualizarTablaContactos(contactos);
    }
    
    public void agregarContacto(Persona persona) {
        boolean resultado = personaDAO.insertar(persona);
        if (resultado) {
            vista.mostrarMensaje(
                i18n.getString("message.contact.added"),
                i18n.getString("dialog.title.success"),
                JOptionPane.INFORMATION_MESSAGE
            );
            cargarContactos();
        } else {
            vista.mostrarMensaje(
                i18n.getString("message.error.add"),
                i18n.getString("dialog.title.error"),
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void modificarContacto(Persona persona) {
        boolean resultado = personaDAO.actualizar(persona);
        if (resultado) {
            vista.mostrarMensaje(
                i18n.getString("message.contact.updated"),
                i18n.getString("dialog.title.success"),
                JOptionPane.INFORMATION_MESSAGE
            );
            cargarContactos();
        } else {
            vista.mostrarMensaje(
                i18n.getString("message.error.update"),
                i18n.getString("dialog.title.error"),
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void eliminarContacto(int id) {
        boolean resultado = personaDAO.eliminar(id);
        if (resultado) {
            vista.mostrarMensaje(
                i18n.getString("message.contact.deleted"),
                i18n.getString("dialog.title.success"),
                JOptionPane.INFORMATION_MESSAGE
            );
            cargarContactos();
        } else {
            vista.mostrarMensaje(
                i18n.getString("message.error.delete"),
                i18n.getString("dialog.title.error"),
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void buscarContactos(String termino) {
        if (termino == null || termino.trim().isEmpty()) {
            cargarContactos();
        } else {
            List<Persona> resultados = personaDAO.buscar(termino);
            vista.actualizarTablaContactos(resultados);
        }
    }
    
    public Map<String, Integer> obtenerEstadisticas() {
        return personaDAO.obtenerEstadisticas();
    }
}