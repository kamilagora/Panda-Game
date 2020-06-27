package panda;

import panda.models.interfaces.OnReturnListener;
import java.awt.Color;
import java.awt.Font;
import panda.components.ImageButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Klasa opisująca zasady gry
 *
 * @author Kamila
 */
public class HowToPlay extends JPanel {

    /**
     * Wczytanie ikony przycisku powrót
     */
    ImageIcon back1 = new ImageIcon("res/images/back1.png");
    /**
     * Wczytanie drugiej ikony przycisku powrót
     */
    ImageIcon back2 = new ImageIcon("res/images/back2.png");
    /**
     * przycisk powrotu
     */
    public ImageButton btnBack = new ImageButton(back1, back2, null);
    /**
     * etykieta z zasadami gry
     */
    public JLabel howToPlayLabel = new JLabel();

    /**
     * obiekt implementujący interfejs OnReturnListener
     */
    private OnReturnListener arg;

    /**
     * Konstruktor klasy odpowiada za dodanie zasad gry do etykiety, ustawienie
     * lokalizacji tekstu oraz przycisku "powrót".
     * Obsługa zdarzenia kliknięcia w przycisk
     */
    HowToPlay() {

        super();
        JPanel rules = new JPanel();
        add(rules);
        rules.add(btnBack);
        rules.setLayout(null);
        btnBack.setLocation(650, 500);
        btnBack.setSize(287, 162);
        /**
         * Obsługa zdarzenia kliknięcia w przycisk, po którym użytkownik powraca
         * do poprzedniego widoku
         */
        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                arg.onReturn();
            }
        });
        rules.add(howToPlayLabel);
        howToPlayLabel.setLocation(100, 50);
        howToPlayLabel.setSize(500, 500);
        howToPlayLabel.setLocation(100, 50);
        howToPlayLabel.setSize(500, 500);
        String text = "Gracz ma do wyboru następujące poziomy: porównania, dodawanie, "
                + "odejmowanie, mnożenie, dzielenie, mix zadaniowy."
                + " W ramach poziomu użytkownikowi zostaną wyświetlone pytania "
                + "z wybranego tematu. Zadania będą wyświetlane na kartce w "
                + "środkowej części pola graficznego gry, natomias odpowiedzi "
                + "zostaną wyświetlone na balonikach. Kliknięcie w balonik "
                + "oznacza wybór odpowiedzi. Jeżeli usłyszymy dźwięk pękającego "
                + "balonika, to odpowiedź była prawidłowa.";
        howToPlayLabel.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        howToPlayLabel.setForeground(Color.GRAY);
        howToPlayLabel.setText("<html><div style=\"text-align: justify\">" + text + "</div></html>");
    }
    
    /**
     * Dodanie obiektu implemenującego interfejs OnReturnListener
     * @param arg obiekt implementujący interfejs OnReturnListener
     */
    public void addOnReturnListener(OnReturnListener arg) {
        this.arg = arg;
    }
}
