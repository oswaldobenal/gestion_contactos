package util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.MissingResourceException;

public class I18nManager {
    private static I18nManager instance;
    private ResourceBundle bundle;
    private Locale currentLocale;
    
    private static final String BUNDLE_NAME = "recursos.i18n.messages";
    public static final Locale LOCALE_ES = new Locale("es", "ES");
    public static final Locale LOCALE_EN = new Locale("en", "US");
    public static final Locale LOCALE_FR = new Locale("fr", "FR");
    
    private I18nManager() {
        // Por defecto, usamos el idioma español
        setLocale(LOCALE_ES);
    }
    
    public static I18nManager getInstance() {
        if (instance == null) {
            instance = new I18nManager();
        }
        return instance;
    }
    
    public void setLocale(Locale locale) {
        try {
            this.currentLocale = locale;
            this.bundle = ResourceBundle.getBundle(BUNDLE_NAME, locale);
        } catch (MissingResourceException e) {
            System.err.println("No se pudo cargar el archivo de idioma: " + e.getMessage());
            // Si falla, intentamos cargar el idioma por defecto
            this.currentLocale = LOCALE_ES;
            this.bundle = ResourceBundle.getBundle(BUNDLE_NAME, LOCALE_ES);
        }
    }
    
    public String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            System.err.println("No se encontró la clave: " + key);
            return "!" + key + "!";
        }
    }
    
    public Locale getCurrentLocale() {
        return currentLocale;
    }
    
    public String getLanguageDisplay() {
        switch (currentLocale.getLanguage()) {
            case "es":
                return "ES";
            case "en":
                return "EN";
            case "fr":
                return "FR";
            default:
                return currentLocale.getLanguage().toUpperCase();
        }
    }
}