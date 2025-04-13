package vista;

import modelo.persona;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ContactoTableModel extends AbstractTableModel {
    private final String[] columnas = {"Nombre", "Teléfono", "Email", "Categoría", "Favorito"};
    private List<persona> contactos;

    public ContactoTableModel(List<persona> contactos) {
        this.contactos = contactos;
    }

    @Override
    public int getRowCount() { return contactos.size(); }

    @Override
    public int getColumnCount() { return columnas.length; }

    @Override
    public Object getValueAt(int row, int col) {
        persona p = contactos.get(row);
        switch (col) {
            case 0: return p.getNombre();
            case 1: return p.getTelefono();
            case 2: return p.getEmail();
            case 3: return p.getCategoria();
            case 4: return p.isFavorito();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) { return columnas[column]; }

    @Override
    public Class<?> getColumnClass(int column) {
        return column == 4 ? Boolean.class : String.class;
    }
    
    public void actualizarDatos(List<persona> nuevosContactos) {
        this.contactos = nuevosContactos;
        fireTableDataChanged();
    }
}