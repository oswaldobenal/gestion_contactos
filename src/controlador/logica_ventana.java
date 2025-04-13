package controlador;

import modelo.persona;
import modelo.personaDAO;
import vista.ventana;
import vista.ContactoTableModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class logica_ventana implements ActionListener, DocumentListener {
    private ventana delegado;
    private List<persona> contactos;
    private ContactoTableModel tableModel;
    private TableRowSorter<ContactoTableModel> sorter;
    private personaDAO dao;

    public logica_ventana(ventana delegado) {
        this.delegado = delegado;
        this.dao = new personaDAO(new persona());
        configurarEventos();
        cargarContactosAsync();
    }

    private void configurarEventos() {
        delegado.btn_add.addActionListener(this);
        delegado.btn_modificar.addActionListener(this);
        delegado.btn_eliminar.addActionListener(this);
        
        // Menú contextual
        delegado.tablaContactos.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    JPopupMenu menu = new JPopupMenu();
                    JMenuItem exportarItem = new JMenuItem("Exportar CSV");
                    exportarItem.addActionListener(ev -> exportarCSV());
                    menu.add(exportarItem);
                    menu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // Atajo de teclado
        KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_DOWN_MASK);
        delegado.tablaContactos.getInputMap().put(ks, "exportar");
        delegado.tablaContactos.getActionMap().put("exportar", new AbstractAction() {
            public void actionPerformed(ActionEvent e) { exportarCSV(); }
        });
    }

    private void cargarContactosAsync() {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                contactos = dao.leerArchivo();
                for (int i = 0; i < contactos.size(); i++) {
                    publish((i * 100) / contactos.size());
                    Thread.sleep(20);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                delegado.progressBar.setValue(chunks.getLast());
            }

            @Override
            protected void done() {
                tableModel = new ContactoTableModel(contactos);
                delegado.tablaContactos.setModel(tableModel);
                sorter = new TableRowSorter<>(tableModel);
                delegado.tablaContactos.setRowSorter(sorter);
                delegado.progressBar.setVisible(false);
                actualizarEstadisticas();
            }
        };
        delegado.progressBar.setVisible(true);
        worker.execute();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delegado.btn_add) {
            agregarContacto();
        } else if (e.getSource() == delegado.btn_eliminar) {
            eliminarContacto();
        } else if (e.getSource() == delegado.btn_modificar) {
            modificarContacto();
        }
    }

    private void agregarContacto() {
        String nombre = delegado.txt_nombres.getText();
        String telefono = delegado.txt_telefono.getText();
        String email = delegado.txt_email.getText();
        String categoria = (String) delegado.cmb_categoria.getSelectedItem();
        boolean favorito = delegado.chb_favorito.isSelected();

        if (!nombre.isEmpty() && !telefono.isEmpty() && !email.isEmpty()) {
            persona nuevo = new persona(nombre, telefono, email, categoria, favorito);
            new personaDAO(nuevo).escribirArchivo();
            contactos.add(nuevo);
            tableModel.actualizarDatos(contactos);
            limpiarCampos();
        }
    }


    private void exportarCSV() {
        try {
            dao.exportarCSV(contactos);
            JOptionPane.showMessageDialog(delegado, "Exportación exitosa!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(delegado, "Error: " + ex.getMessage());
        }
    }

    private void actualizarEstadisticas() {
        long favoritos = contactos.stream().filter(persona::isFavorito).count();
        JPanel statsPanel = (JPanel) delegado.tabbedPane.getComponentAt(1);
        ((JLabel) statsPanel.getComponent(1)).setText("Total contactos: " + contactos.size());
        ((JLabel) statsPanel.getComponent(2)).setText("Favoritos: " + favoritos);
    }
    
    private void eliminarContacto() {
        int fila = delegado.tablaContactos.getSelectedRow();
        if (fila != -1) {
            // Convertir el índice de la vista al modelo
            int modelIndex = delegado.tablaContactos.convertRowIndexToModel(fila);
            contactos.remove(modelIndex);
            
            try {
                // Actualizar el archivo completo
                dao.actualizarContactos(contactos);
                // Actualizar la tabla
                tableModel.actualizarDatos(contactos);
                // Actualizar estadísticas
                actualizarEstadisticas();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(delegado, "Error al eliminar: " + ex.getMessage());
            }
        }
    }

    private void modificarContacto() {
        int fila = delegado.tablaContactos.getSelectedRow();
        if (fila != -1) {
            // Convertir el índice de la vista al modelo
            int modelIndex = delegado.tablaContactos.convertRowIndexToModel(fila);
            persona seleccionado = contactos.get(modelIndex);
            
            // Actualizar datos
            seleccionado.setNombre(delegado.txt_nombres.getText());
            seleccionado.setTelefono(delegado.txt_telefono.getText());
            seleccionado.setEmail(delegado.txt_email.getText());
            seleccionado.setCategoria((String) delegado.cmb_categoria.getSelectedItem());
            seleccionado.setFavorito(delegado.chb_favorito.isSelected());
            
            try {
                // Actualizar el archivo completo
                dao.actualizarContactos(contactos);
                // Actualizar la tabla
                tableModel.fireTableRowsUpdated(modelIndex, modelIndex);
                // Actualizar estadísticas
                actualizarEstadisticas();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(delegado, "Error al modificar: " + ex.getMessage());
            }
        }
    }

    private void limpiarCampos() {
        delegado.txt_nombres.setText("");
        delegado.txt_telefono.setText("");
        delegado.txt_email.setText("");
        delegado.cmb_categoria.setSelectedIndex(0);
        delegado.chb_favorito.setSelected(false);
    }

    // Implementación DocumentListener
    @Override public void insertUpdate(DocumentEvent e) { filtrar(); }
    @Override public void removeUpdate(DocumentEvent e) { filtrar(); }
    @Override public void changedUpdate(DocumentEvent e) { filtrar(); }

    private void filtrar() {
        String texto = delegado.txt_buscar.getText();
        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0));
    }
}