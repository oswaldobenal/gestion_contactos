package vista;

import controlador.logica_ventana;
import modelo.Persona;
import util.EstiloManager;
import util.I18nManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ventana extends JFrame {
    // Administradores
    private final EstiloManager estiloManager;
    private final I18nManager i18n;
    
    // Controlador
    private logica_ventana controlador;
    
    // Componentes UI
    private JTabbedPane tabbedPane;
    private JPanel panelContactos;
    private JPanel panelEstadisticas;
    
    // Formulario
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JComboBox<String> cmbCategoria;
    private JCheckBox chkFavorito;
    
    // Botones
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    
    // Búsqueda
    private JTextField txtBuscar;
    
    // Tabla
    private JTable tablaContactos;
    private ContactoTableModel modeloTabla;
    
    // Panel de idioma
    private JButton btnIdioma;
    
    // Barra de progreso
    private JProgressBar barraProgreso;
    
    // Estado de formulario
    private int idContactoSeleccionado = -1;
    
    public ventana() {
        estiloManager = EstiloManager.getInstance();
        i18n = I18nManager.getInstance();
        
        // Configurar ventana
        configurarVentana();
        
        // Inicializar componentes
        inicializarComponentes();
        
        // Configurar controlador
        controlador = new logica_ventana(this);
        
        // Cargar datos
        controlador.cargarContactos();
    }
    
    private void configurarVentana() {
        // Configuración básica de la ventana
        setTitle(i18n.getString("app.title"));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        estiloManager.aplicarEstiloGeneral();
        
        // Configurar el layout principal
        setLayout(new BorderLayout());
        
        // Panel principal con margen
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelPrincipal.setBackground(estiloManager.getColorFondo());
        add(panelPrincipal, BorderLayout.CENTER);
        
        // Crear panel de pestañas
        tabbedPane = new JTabbedPane();
        panelPrincipal.add(tabbedPane, BorderLayout.CENTER);
        
        // Panel de contactos
        panelContactos = new JPanel(new BorderLayout());
        panelContactos.setBackground(estiloManager.getColorFondo());
        
        // Panel de estadísticas
        panelEstadisticas = new JPanel(new BorderLayout());
        panelEstadisticas.setBackground(estiloManager.getColorFondo());
        
        // Añadir pestañas
        tabbedPane.addTab(i18n.getString("tab.contacts"), panelContactos);
        tabbedPane.addTab(i18n.getString("tab.statistics"), panelEstadisticas);
        
        // Configurar apariencia de pestañas
        tabbedPane.setBackground(estiloManager.getColorPrimario());
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(estiloManager.getFuenteRegular());
        
        // Panel de estado e idioma
        JPanel panelEstado = new JPanel(new BorderLayout());
        panelEstado.setBackground(estiloManager.getColorFondo());
        panelPrincipal.add(panelEstado, BorderLayout.SOUTH);
        
        // Barra de progreso
        barraProgreso = new JProgressBar(0, 100);
        barraProgreso.setValue(50);
        panelEstado.add(barraProgreso, BorderLayout.CENTER);
        
        // Botón de idioma
        btnIdioma = new JButton("Idioma: " + i18n.getLanguageDisplay());
        btnIdioma.addActionListener(e -> cambiarIdioma());
        panelEstado.add(btnIdioma, BorderLayout.EAST);
    }
    
    private void inicializarComponentes() {
        // Panel de contactos
        inicializarPanelContactos();
        
        // Panel de estadísticas
        inicializarPanelEstadisticas();
    }
    
    private void inicializarPanelContactos() {
        // Panel superior: formulario y búsqueda
        JPanel panelSuperior = new JPanel(new BorderLayout(10, 0));
        panelSuperior.setBackground(estiloManager.getColorFondo());
        panelSuperior.setBorder(new EmptyBorder(0, 0, 10, 0));
        
        // Panel del formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createLineBorder(estiloManager.getColorBorde()));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.setBackground(Color.WHITE);
        panelBusqueda.setBorder(BorderFactory.createLineBorder(estiloManager.getColorBorde()));
        
        // Configurar el panel superior
        panelSuperior.add(panelFormulario, BorderLayout.WEST);
        panelSuperior.add(panelBusqueda, BorderLayout.CENTER);
        
        // Configurar componentes del formulario
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Nombre
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(new JLabel(i18n.getString("form.name")), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        estiloManager.aplicarEstiloCampoTexto(txtNombre);
        panelFormulario.add(txtNombre, gbc);
        
        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel(i18n.getString("form.phone")), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtTelefono = new JTextField(20);
        estiloManager.aplicarEstiloCampoTexto(txtTelefono);
        panelFormulario.add(txtTelefono, gbc);
        
        // Email
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel(i18n.getString("form.email")), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        estiloManager.aplicarEstiloCampoTexto(txtEmail);
        panelFormulario.add(txtEmail, gbc);
        
        // Categoría
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel(i18n.getString("form.category")), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        String[] categorias = {"Personal", "Trabajo", "Familia", "Amigos", "Otros"};
        cmbCategoria = new JComboBox<>(categorias);
        cmbCategoria.setFont(estiloManager.getFuenteRegular());
        panelFormulario.add(cmbCategoria, gbc);
        
        // Favorito
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        panelFormulario.add(new JLabel(i18n.getString("form.favorite")), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        chkFavorito = new JCheckBox();
        panelFormulario.add(chkFavorito, gbc);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotones.setBackground(Color.WHITE);
        panelBotones.setBorder(BorderFactory.createLineBorder(estiloManager.getColorBorde()));
        
        // Botones
        btnAgregar = new JButton(i18n.getString("button.add"));
        estiloManager.aplicarEstiloBotonPrimario(btnAgregar);
        btnAgregar.addActionListener(e -> agregarContacto());
        panelBotones.add(btnAgregar);
        
        btnModificar = new JButton(i18n.getString("button.edit"));
        estiloManager.aplicarEstiloBotonSecundario(btnModificar);
        btnModificar.addActionListener(e -> modificarContacto());
        btnModificar.setEnabled(false);
        panelBotones.add(btnModificar);
        
        btnEliminar = new JButton(i18n.getString("button.delete"));
        estiloManager.aplicarEstiloBotonError(btnEliminar);
        btnEliminar.addActionListener(e -> eliminarContacto());
        btnEliminar.setEnabled(false);
        panelBotones.add(btnEliminar);
        
        // Añadir panel de botones debajo del formulario
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelFormulario.add(panelBotones, gbc);
        
        // Ajustar tamaño del panel de formulario
        panelFormulario.setPreferredSize(new Dimension(350, 250));
        
        // Panel de búsqueda
        JPanel panelBusquedaInterior = new JPanel(new BorderLayout(5, 0));
        panelBusquedaInterior.setBackground(Color.WHITE);
        panelBusquedaInterior.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JLabel lblBuscar = new JLabel(i18n.getString("search.label"));
        lblBuscar.setFont(estiloManager.getFuenteRegular());
        panelBusquedaInterior.add(lblBuscar, BorderLayout.WEST);
        
        txtBuscar = new JTextField();
        estiloManager.aplicarEstiloCampoTexto(txtBuscar);
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                buscarContactos();
            }
        });
        panelBusquedaInterior.add(txtBuscar, BorderLayout.CENTER);
        
        panelBusqueda.add(panelBusquedaInterior, BorderLayout.CENTER);
        
        // Añadir panel superior al panel de contactos
        panelContactos.add(panelSuperior, BorderLayout.NORTH);
        
        // Tabla de contactos
        modeloTabla = new ContactoTableModel(new ArrayList<>());
        tablaContactos = new JTable(modeloTabla);
        estiloManager.aplicarEstiloTabla(tablaContactos);
        
        // Configurar renderizadores específicos para columnas
        configurarRenderizadoresTabla();
        
        // Añadir la tabla en un scroll pane
        JScrollPane scrollPane = new JScrollPane(tablaContactos);
        scrollPane.setBorder(BorderFactory.createLineBorder(estiloManager.getColorBorde()));
        
        // Añadir listener de selección para la tabla
        tablaContactos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int filaSeleccionada = tablaContactos.getSelectedRow();
                if (filaSeleccionada >= 0) {
                    cargarContactoSeleccionado(filaSeleccionada);
                    btnModificar.setEnabled(true);
                    btnEliminar.setEnabled(true);
                } else {
                    limpiarFormulario();
                    btnModificar.setEnabled(false);
                    btnEliminar.setEnabled(false);
                }
            }
        });
        
        // Añadir el ScrollPane al panel de contactos
        panelContactos.add(scrollPane, BorderLayout.CENTER);
    }
    
    private void inicializarPanelEstadisticas() {
        // Esta función se implementará en futuras versiones
        JPanel panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createLineBorder(estiloManager.getColorBorde()));
        
        JLabel labelEnDesarrollo = new JLabel(i18n.getString("statistics.developing"), JLabel.CENTER);
        labelEnDesarrollo.setFont(estiloManager.getFuenteTitulo());
        labelEnDesarrollo.setForeground(estiloManager.getColorTexto());
        
        panelContenido.add(labelEnDesarrollo, BorderLayout.CENTER);
        
        // Añadir el panel al panel de estadísticas
        panelEstadisticas.add(panelContenido, BorderLayout.CENTER);
    }
    
    private void configurarRenderizadoresTabla() {
        // Renderizador para columna favorito
        tablaContactos.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JCheckBox checkBox = new JCheckBox();
                checkBox.setSelected((Boolean) value);
                checkBox.setHorizontalAlignment(JLabel.CENTER);
                
                if (isSelected) {
                    checkBox.setBackground(estiloManager.getColorPrimario());
                } else {
                    if (row % 2 == 0) {
                        checkBox.setBackground(Color.WHITE);
                    } else {
                        checkBox.setBackground(estiloManager.getColorTabla());
                    }
                }
                
                return checkBox;
            }
        });
        
        // Ocultar la columna ID
        tablaContactos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaContactos.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaContactos.getColumnModel().getColumn(0).setWidth(0);
        
        // Ajustar tamaños de columnas
        tablaContactos.getColumnModel().getColumn(1).setPreferredWidth(150); // Nombre
        tablaContactos.getColumnModel().getColumn(2).setPreferredWidth(100); // Teléfono
        tablaContactos.getColumnModel().getColumn(3).setPreferredWidth(150); // Email
        tablaContactos.getColumnModel().getColumn(4).setPreferredWidth(100); // Categoría
        tablaContactos.getColumnModel().getColumn(5).setPreferredWidth(60);  // Favorito
    }
    
    // Métodos de acción
    private void agregarContacto() {
        if (validarFormulario()) {
            Persona nuevaPersona = obtenerPersonaDesdeFormulario();
            controlador.agregarContacto(nuevaPersona);
            limpiarFormulario();
        }
    }
    
    private void modificarContacto() {
        if (idContactoSeleccionado >= 0 && validarFormulario()) {
            Persona personaModificada = obtenerPersonaDesdeFormulario();
            personaModificada.setId(idContactoSeleccionado);
            controlador.modificarContacto(personaModificada);
            limpiarFormulario();
        }
    }
    
    private void eliminarContacto() {
        if (idContactoSeleccionado >= 0) {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    i18n.getString("dialog.confirm.delete"),
                    i18n.getString("dialog.title.confirm"),
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            
            if (opcion == JOptionPane.YES_OPTION) {
                controlador.eliminarContacto(idContactoSeleccionado);
                limpiarFormulario();
            }
        }
    }
    
    private void buscarContactos() {
        String termino = txtBuscar.getText().trim();
        controlador.buscarContactos(termino);
    }
    
    private void cambiarIdioma() {
        // Mostrar diálogo de selección de idioma
        String[] opciones = {"Español", "English", "Français"};
        int seleccion = JOptionPane.showOptionDialog(
                this,
                i18n.getString("dialog.language.select"),
                i18n.getString("dialog.title.language"),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );
        
        // Cambiar idioma según selección
        switch (seleccion) {
            case 0 -> i18n.setLocale(I18nManager.LOCALE_ES);
            case 1 -> i18n.setLocale(I18nManager.LOCALE_EN);
            case 2 -> i18n.setLocale(I18nManager.LOCALE_FR);
            default -> { return; } // No cambiar si se cancela
        }
        
        // Actualizar interfaz con nuevo idioma
        actualizarTextos();
    }
    
    private void actualizarTextos() {
        // Actualizar título de la ventana
        setTitle(i18n.getString("app.title"));
        
        // Actualizar pestañas
        tabbedPane.setTitleAt(0, i18n.getString("tab.contacts"));
        tabbedPane.setTitleAt(1, i18n.getString("tab.statistics"));
        
        // Actualizar botón de idioma
        btnIdioma.setText("Idioma: " + i18n.getLanguageDisplay());
        
        // Actualizar textos del formulario
        SwingUtilities.invokeLater(() -> {
            // Actualizar labels y botones
            Component[] componentes = panelContactos.getComponents();
            actualizarLabelsRecursivamente(componentes);
            
            // Actualizar tabla
            modeloTabla.fireTableStructureChanged();
            configurarRenderizadoresTabla();
            
            // Revalidar y repintar
            revalidate();
            repaint();
        });
    }
    
    private void actualizarLabelsRecursivamente(Component[] componentes) {
        for (Component comp : componentes) {
            if (comp instanceof JLabel label) {
                String texto = label.getText();
                // Intentar buscar la clave correspondiente
                if (texto != null && texto.startsWith("form.") || texto.startsWith("button.") || texto.startsWith("search.")) {
                    label.setText(i18n.getString(texto));
                }
            } else if (comp instanceof JButton button) {
                String texto = button.getText();
                if (texto != null && texto.startsWith("button.")) {
                    button.setText(i18n.getString(texto));
                }
            } else if (comp instanceof Container container) {
                actualizarLabelsRecursivamente(container.getComponents());
            }
        }
    }
    
    private void cargarContactoSeleccionado(int filaSeleccionada) {
        Persona personaSeleccionada = modeloTabla.getPersonaAt(filaSeleccionada);
        if (personaSeleccionada != null) {
            idContactoSeleccionado = personaSeleccionada.getId();
            txtNombre.setText(personaSeleccionada.getNombre());
            txtTelefono.setText(personaSeleccionada.getTelefono());
            txtEmail.setText(personaSeleccionada.getEmail());
            cmbCategoria.setSelectedItem(personaSeleccionada.getCategoria());
            chkFavorito.setSelected(personaSeleccionada.isFavorito());
        }
    }
    
    private boolean validarFormulario() {
        // Validar que al menos el nombre no esté vacío
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    i18n.getString("validation.name.required"),
                    i18n.getString("dialog.title.error"),
                    JOptionPane.ERROR_MESSAGE
            );
            txtNombre.requestFocus();
            return false;
        }
        return true;
    }
    
    private Persona obtenerPersonaDesdeFormulario() {
        String nombre = txtNombre.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String email = txtEmail.getText().trim();
        String categoria = cmbCategoria.getSelectedItem().toString();
        boolean favorito = chkFavorito.isSelected();
        
        return new Persona(nombre, telefono, email, categoria, favorito);
    }
    
    private void limpiarFormulario() {
        idContactoSeleccionado = -1;
        txtNombre.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        cmbCategoria.setSelectedIndex(0);
        chkFavorito.setSelected(false);
        
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);
        
        txtNombre.requestFocus();
    }
    
    // Métodos para comunicación con el controlador
    public void actualizarTablaContactos(List<Persona> contactos) {
        modeloTabla.actualizarDatos(contactos);
        actualizarBarraProgreso(contactos.size());
    }
    
    public void actualizarBarraProgreso(int cantidadContactos) {
        // Actualizar la barra de progreso según la cantidad de contactos
        // Por ejemplo, podemos ajustar el máximo a 100 y actualizar según el número de contactos
        int maximo = 100;
        int valor = Math.min(cantidadContactos * 10, maximo);
        
        barraProgreso.setMaximum(maximo);
        barraProgreso.setValue(valor);
    }
    
    public void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensaje, titulo, tipo);
    }
    
    // Método principal para pruebas
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ventana app = new ventana();
            app.setVisible(true);
        });
    }
}