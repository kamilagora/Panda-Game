package panda.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * Klasa dziedzicząca po klasie JPanel Ustawia parametry wczytywanych obrazów w
 * panelach
 *
 * @author Kamila
 */
public class ImagePanel extends JPanel {

    /**
     * obraz panelu
     */
    private Image image = null;
    /**
     * szerokość panelu z obrazem
     */
    private int iWidth2;
    /**
     * wysokość panelu z obrazem
     */
    private int iHeight2;
    /**
     * flaga - czy jest wyśrodkowany obraz
     */
    private final boolean center;
/**
 * Konstruktor panelu graficznego
 * Ustwienie pozycji obrazu jako wyśrodkowany.
 * @param image obraz tła
 */
    public ImagePanel(Image image) {
        this(image, true);
    }

    /**
     * Konstruktor klasy panelu graficznego. Ustawienie pozycji obrazów
     * wczytywanych do gry
     *
     * @param image obraz
     * @param center pozycja obrazu
     */
    public ImagePanel(Image image, boolean center) {
        super();
        this.center = center;
        this.image = image;
        if (image != null) {
            this.iWidth2 = image.getWidth(this);
            this.iHeight2 = image.getHeight(this);
            this.setSize(this.iWidth2, this.iHeight2);
            this.setPreferredSize(new Dimension(this.iWidth2, this.iHeight2));
            this.setMinimumSize(new Dimension(this.iWidth2, this.iHeight2));
        }
    }

    /**
     * Funkcja ustawiająca obraz
     *
     * @param image obraz
     */
    public void setImage(Image image) {
        this.image = image;
        this.iWidth2 = image.getWidth(this);
        this.iHeight2 = image.getHeight(this);
        this.setSize(this.iWidth2, this.iHeight2);
        this.setPreferredSize(new Dimension(this.iWidth2, this.iHeight2));
        this.setMinimumSize(new Dimension(this.iWidth2, this.iHeight2));
    }

    /**
     * Nadpisanie metody odpowiedzialnej za odrysowanie panelu - własne 
     * wypełnienie pola graficznego gry
     *
     * @param g obiekt zawierający informację potrzebne do renderowania
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            int x = 0;
            int y = 0;
            if (center) {
                x = (this.getParent().getWidth() - iWidth2) / 2;
                y = (this.getParent().getHeight() - iHeight2) / 2;
            }

            g.drawImage(image, x, y, this);
        }
    }
}
