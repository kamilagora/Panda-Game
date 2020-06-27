package panda.components;

import java.awt.event.*;
import javax.swing.*;

/**
 * Klasa odpowiedzialna za obsługę niestandardowych przycisków z własną grafiką
 * Klasa dziedziczy po JButton
 * @author Kamila
 */
public class ImageButton extends JButton {

    /**
     * Konstruktor klasy odpowiedzialnej za tworzenie przycisków
     * z niestandardową grafiką. 
     * Ustawienie podstawoych parametrów.
     * Dodanie obsługi zdarzeń: kliknięcie w przycisk, najechanie myszką na 
     * przycisk, zwolnienie przycisku
     * @param normal ikona przycisku bez żadnej aktywności ze strony użytkownika
     * @param onPower ikona przycisk po najechaniu myszką na przycisk 
     * @param onLMBDown ikona przycisk po wciśnięciu lewego przycisku myszki  
     */
    public ImageButton(ImageIcon normal, ImageIcon onPower, ImageIcon onLMBDown) {

        super();
        setIcon(normal);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);

        /**
         * Dodanie obsługi zdarzeń przycisków i ustawienie ich ikon
         */
        addMouseListener(new MouseAdapter() {
           /**
            * Obsługa zdarzenia najechania kursorem myszy na przycisk
            * @param me obiekt zdarzenia
            */
            @Override
            public void mouseEntered(MouseEvent me) {

                setIcon(onPower);
            }
            /**
             * Obsługa zdarzenia zjechania kursorem myszy z przycisku
             * @param e obiekt zdarzenia
             */
            @Override
            public void mouseExited(MouseEvent e) {

                setIcon(normal);
            }
            /**
             * Obsługa zdarzenia wciśnięcia lewego przycisku myszki na przycisku
             * @param e obiekt zdarzenia
             */
            @Override
            public void mousePressed(MouseEvent e) {

                setIcon(onLMBDown);
            }
            /**
             * Obsługa zdarzenia zwolnienia przycisku myszy
             * @param e obiekt zdarzenia
             */
            @Override
            public void mouseReleased(MouseEvent e) {

                setIcon(normal);
            }
        });

    }

}
