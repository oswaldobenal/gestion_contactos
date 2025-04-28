package vista;

import modelo.Persona;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ContactoTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Nombre", "Teléfono", "Email", "Categoría", "Favorito"};
    private List<Persona> contactos;
    
    public ContactoTableModel(List<Persona> contactos) {
        this.contactos = contactos;
    }
    
    @Override
    public int getRowCount() {
        return contactos.size();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Persona persona = contactos.get(rowIndex);
        
        return switch (columnIndex) {
            case 0 -> persona.getId();
            case 1 -> persona.getNombre();
            case 2 -> persona.getTelefono();
            case 3 -> persona.getEmail();
            case 4 -> persona.getCategoria();
            case 5 -> persona.isFavorito();
            default -> null;
        };
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 5) {
            return Boolean.class;
        }
        return String.class;
    }
    
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // Solo permitimos editar la columna de favoritos directamente
        return columnIndex == 5;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        if (columnIndex == 5 && value instanceof Boolean) {
            Persona persona = contactos.get(rowIndex);
            persona.setFavorito((Boolean) value);
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }
    
    public void actualizarDatos(List<Persona> nuevaLista) {
        this.contactos = nuevaLista;
        fireTableDataChanged();
    }
    
    public Persona getPersonaAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < contactos.size()) {
            return contactos.get(rowIndex);
        }
        return null;
    }
}