package panda;

import panda.models.interfaces.OnReturnListener;
import java.awt.Color;
import java.awt.Font;
import panda.components.ImageButton;
import java.awt.event.*;
import javax.swing.*;
/**
 * Klasa, która tworzy oraz zarządza widokiem "o nas" - po kliknięciu w 
 * pandę na ekranie startowym
 * Klasa dziedzicząca po klasie JPanel
 * @author Kamila
 */
public class AboutAuthor extends JPanel {

    /**
     * Ikony przycisku "powrót"
     */
    ImageIcon back1 = new ImageIcon("res/images/back1.png");
    /**
     * Druga ikona przycisku "powrót"
     */
    ImageIcon back2 = new ImageIcon("res/images/back2.png");
    
    /**
     * Deklaracja przycisku "powrót" z przydzieleniem mu konkretnego
     * obrazu.
     */
    public ImageButton b1 = new ImageButton(back1, back2, null);
    /**
     * Deklaracja JLabela, w którym wyświetlane są informacje nt. produktu
     */
    public JLabel infoLabel = new JLabel();

    /**
     * obiekt implementujący interfejs OnReturnListener
     */
    private OnReturnListener arg;
  
    /**
     * Konstruktor widok "o nas", po kliknięciu w pandę na ekranie menu 
     * Ustawienie tekstu do wyświetlenia
     * Przycisku powrotu do poprzedniego widoku
     */
    AboutAuthor() {
        super();
        JPanel infoPanel = new JPanel();
        add(infoPanel);
        infoPanel.add(infoLabel);
        infoPanel.add(b1);
        infoPanel.setLayout(null);
        infoLabel.setLocation(100, 50);
        infoLabel.setSize(500, 500);
        String text = "Głównym powodem stworzenia niniejszej gry jest chęć przełamania stereotypu,"
                + " że nauka matematyki jest męcząca. Jest to wsparcie Nauczycieli,"
                + " jak również samych Rodziców w edukacji Dzieci.\n "
                + " Projekt został zrealizowany na potrzeby przedmiotu WJP.";
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setText("<html><div style=\"text-align: justify\">" + text+ "</div></html>");

        b1.setLocation(650, 500);
        b1.setSize(287, 162);
         /* Dodaj obsługę zdarzeń - wciśnięcie przycisku myszki*/
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                arg.onReturn();
            }
        });
    }
 /**
  * Dodanie obiektu implemenującego interfejs OnReturnListener
  * @param arg obiekt implementujący interfejs OnReturnListener
  */
    public void addOnReturnListener(OnReturnListener arg) {
        this.arg = arg;
    }
}
