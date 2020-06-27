package panda;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import javax.swing.*;

/**
 * Klasa zarządzająca oknem aplikacji
 * Dziedziczy po klasie JFrame
 * @author Kamila
 */
public class Panda extends JFrame {
    //* różnica między szerokością okna a wyświetlanym oknem */
    private static final int additionalWindowWidth = 10;
    //* różnica między wysokością okna a wyświetlanym oknem */
    private static final int additionalWindowHeight = 10;
    /** wysokość paska tytułowego */
    private static final int titleBarHeight = 26;
    /** przesunięcie tła okna w osi x */ 
    private static final int windowBackgroundXOffset = 3;
    /** obraz tła*/
    private Image img;
   
/**
 * Główna funkcja aplikacji
 * Ustawienie parametrów okna: rozmiary, usunięcie możliwości zmiany rozmiarów
 * okna, domyślne działanie przycisku zamknięcia okna.
 * @param args parametry przekazane do aplikacji
 * @throws InterruptedException wyjątek przerwania
 * @throws IOException wyjątek wejścia/wyjścia
 */
    public static void main(String[] args) throws InterruptedException,
            IOException {

        Menu cl = new Menu();

        cl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cl.setVisible(true);
        cl.getContentPane().setPreferredSize(new Dimension(1024
                - additionalWindowWidth, 768 - additionalWindowHeight));
        cl.setResizable(false);
        cl.pack();
        cl.setLocationRelativeTo(null);
    }

    /**
     * Nadpisanie metody odpowiedzialnej za odrysowanie panelu - własne
     * wypełnienie pola graficznego gry
     * @param g obiekt zawierający informację potrzebne do renderowania
     */
    @Override
    public void paint(Graphics g) {
        // Draw the previously loaded image to Component.
        super.paint(g);
        g.drawImage(img, windowBackgroundXOffset, titleBarHeight, null);
    }

}
