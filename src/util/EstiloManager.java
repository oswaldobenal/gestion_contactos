package util;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class EstiloManager {
    private static EstiloManager instance;
    
    // Colores de la aplicación
    private final Color colorPrimario = new Color(52, 152, 219); // #3498db - Azul
    private final Color colorSecundario = new Color(46, 204, 113); // #2ecc71 - Verde
    private final Color colorError = new Color(231, 76, 60); // #e74c3c - Rojo
    private final Color colorFondo = new Color(245, 247, 250); // #f5f7fa - Gris claro
    private final Color colorTexto = new Color(44, 62, 80); // #2c3e50 - Azul oscuro
    private final Color colorTabla = new Color(236, 240, 241); // #ecf0f1 - Gris muy claro
    private final Color colorBorde = new Color(189, 195, 199); // #bdc3c7 - Gris medio
    
    // Fuente personalizada
    private Font fuenteRegular;
    private Font fuenteTitulo;
    private Font fuenteBoton;
    
    private EstiloManager() {
        cargarFuentes();
    }
    
    public static EstiloManager getInstance() {
        if (instance == null) {
            instance = new EstiloManager();
        }
        return instance;
    }
    
    private void cargarFuentes() {
        try {
            // Intentar cargar la fuente desde recursos
            InputStream is = getClass().getResourceAsStream("/recursos/fuentes/roboto/Roboto-Regular.ttf");
            if (is != null) {
                fuenteRegular = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(fuenteRegular);
                
                // Crear variantes de la fuente
                fuenteTitulo = fuenteRegular.deriveFont(Font.BOLD, 14f);
                fuenteBoton = fuenteRegular.deriveFont(12f);
                fuenteRegular = fuenteRegular.deriveFont(12f);
            } else {
                // Si no se puede cargar, usar fuente del sistema
                fuenteRegular = new Font("Arial", Font.PLAIN, 12);
                fuenteTitulo = new Font("Arial", Font.BOLD, 14);
                fuenteBoton = new Font("Arial", Font.PLAIN, 12);
            }
        } catch (FontFormatException | IOException e) {
            System.err.println("Error al cargar la fuente: " + e.getMessage());
            // Si hay error, usar fuente del sistema
            fuenteRegular = new Font("Arial", Font.PLAIN, 12);
            fuenteTitulo = new Font("Arial", Font.BOLD, 14);
            fuenteBoton = new Font("Arial", Font.PLAIN, 12);
        }
    }
    
    public void aplicarEstiloGeneral() {
        // Establecer Look and Feel básico
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al establecer Look and Feel: " + e.getMessage());
        }
        
        // Configurar componentes
        UIManager.put("Panel.background", colorFondo);
        UIManager.put("Label.font", fuenteRegular);
        UIManager.put("Label.foreground", colorTexto);
        UIManager.put("TextField.font", fuenteRegular);
        UIManager.put("TextField.border", BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(colorBorde),
        BorderFactory.createEmptyBorder(2, 5, 2, 5)));
        UIManager.put("Button.font", fuenteBoton);
        UIManager.put("CheckBox.font", fuenteRegular);
        UIManager.put("ComboBox.font", fuenteRegular);
        UIManager.put("Table.font", fuenteRegular);
        UIManager.put("TableHeader.font", fuenteTitulo);
        UIManager.put("TabbedPane.font", fuenteRegular);
    }
    
    // Getters para acceder a los colores y fuentes
    public Color getColorPrimario() {
        return colorPrimario;
    }
    
    public Color getColorSecundario() {
        return colorSecundario;
    }
    
    public Color getColorError() {
        return colorError;
    }
    
    public Color getColorFondo() {
        return colorFondo;
    }
    
    public Color getColorTexto() {
        return colorTexto;
    }
    
    public Color getColorTabla() {
        return colorTabla;
    }
    
    public Color getColorBorde() {
        return colorBorde;
    }
    
    public Font getFuenteRegular() {
        return fuenteRegular;
    }
    
    public Font getFuenteTitulo() {
        return fuenteTitulo;
    }
    
    public Font getFuenteBoton() {
        return fuenteBoton;
    }
    
    // Métodos para estilizar componentes específicos
    public void aplicarEstiloBotonPrimario(JButton boton) {
        boton.setBackground(colorPrimario);
        boton.setForeground(Color.WHITE);
        boton.setFont(fuenteBoton);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    
    public void aplicarEstiloBotonSecundario(JButton boton) {
        boton.setBackground(colorSecundario);
        boton.setForeground(Color.WHITE);
        boton.setFont(fuenteBoton);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    
    public void aplicarEstiloBotonError(JButton boton) {
        boton.setBackground(colorError);
        boton.setForeground(Color.WHITE);
        boton.setFont(fuenteBoton);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
    }
    
    public void aplicarEstiloCampoTexto(JTextField campo) {
        campo.setFont(fuenteRegular);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(colorBorde),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)));
    }
    
    public void aplicarEstiloTabla(JTable tabla) {
        tabla.setFont(fuenteRegular);
        tabla.getTableHeader().setFont(fuenteTitulo);
        tabla.getTableHeader().setBackground(colorPrimario);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setRowHeight(25);
        tabla.setShowGrid(true);
        tabla.setGridColor(colorBorde);
        tabla.setSelectionBackground(colorPrimario);
        tabla.setSelectionForeground(Color.WHITE);
    }
}