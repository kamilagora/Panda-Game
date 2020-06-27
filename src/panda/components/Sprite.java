package panda.components;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Klasa dziedzicząca po klasie JButton
 * Ustawia parametry przycisków, tj. rozmiary, granice, ikonę.
 * Służy do wyświetlania grafiki
 * @author Kamila
 */
public class Sprite extends JButton {
    /**
     * Konstruktor klasy 
     * Ustawienia początkowe obiektu: wymiary, ikona, granice.
     * @param image obraz
     */
    public Sprite(Image image) {
        
        int width = image.getWidth(this);
        int height = image.getHeight(this);
        setIcon(new ImageIcon(image));
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        
        this.setSize(width, height);
    }
}
