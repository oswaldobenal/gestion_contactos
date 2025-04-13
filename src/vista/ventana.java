package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ventana extends JFrame {
    public JTabbedPane tabbedPane;
    public JTable tablaContactos;
    public JProgressBar progressBar;
    public JTextField txt_nombres, txt_telefono, txt_email, txt_buscar;
    public JComboBox<String> cmb_categoria;
    public JCheckBox chb_favorito;
    public JButton btn_add, btn_modificar, btn_eliminar;
    public JLabel lblTotal, lblFavoritos;

    public ventana() {
        configurarVentana();
        crearComponentes();
    }

    private void configurarVentana() {
        setTitle("Gestión de Contactos Mejorada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
    }

    private void crearComponentes() {
        // Panel principal con pestañas
        tabbedPane = new JTabbedPane();
        
        // Barra de progreso
        progressBar = new JProgressBar();
        progressBar.setVisible(false);

        // Panel de Contactos
        JPanel panelContactos = new JPanel(new BorderLayout());
        
        // Panel de formulario
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        txt_nombres = new JTextField();
        txt_telefono = new JTextField();
        txt_email = new JTextField();
        txt_buscar = new JTextField();
        cmb_categoria = new JComboBox<>(new String[]{"Familia", "Amigos", "Trabajo"});
        chb_favorito = new JCheckBox("Favorito");
        
        formPanel.add(new JLabel("Nombre:"));
        formPanel.add(txt_nombres);
        formPanel.add(new JLabel("Teléfono:"));
        formPanel.add(txt_telefono);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(txt_email);
        formPanel.add(new JLabel("Categoría:"));
        formPanel.add(cmb_categoria);
        formPanel.add(new JLabel("Favorito:"));
        formPanel.add(chb_favorito);
        
        // Panel de botones
        JPanel btnPanel = new JPanel(new FlowLayout());
        btn_add = new JButton("Agregar");
        btn_modificar = new JButton("Modificar");
        btn_eliminar = new JButton("Eliminar");
        btnPanel.add(btn_add);
        btnPanel.add(btn_modificar);
        btnPanel.add(btn_eliminar);
        
        // Panel de búsqueda
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.add(new JLabel("Buscar:"), BorderLayout.WEST);
        searchPanel.add(txt_buscar, BorderLayout.CENTER);
        
        // Tabla de contactos
        tablaContactos = new JTable();
        JScrollPane scrollPane = new JScrollPane(tablaContactos);
        
        // Ensamblar panel de contactos
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(btnPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        
        panelContactos.add(topPanel, BorderLayout.NORTH);
        panelContactos.add(scrollPane, BorderLayout.CENTER);

        // Panel de Estadísticas
        JPanel panelEstadisticas = new JPanel(new GridLayout(3, 1, 10, 10));
        panelEstadisticas.setBorder(new EmptyBorder(20, 20, 20, 20));
        lblTotal = new JLabel("Total contactos: 0", SwingConstants.CENTER);
        lblFavoritos = new JLabel("Favoritos: 0", SwingConstants.CENTER);
        
        panelEstadisticas.add(new JLabel("ESTADÍSTICAS", SwingConstants.CENTER));
        panelEstadisticas.add(lblTotal);
        panelEstadisticas.add(lblFavoritos);

        // Agregar pestañas
        tabbedPane.addTab("Contactos", panelContactos);
        tabbedPane.addTab("Estadísticas", panelEstadisticas);

        // Configurar contenido principal
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        getContentPane().add(progressBar, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ventana frame = new ventana();
                new controlador.logica_ventana(frame); // Conectar con el controlador
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}