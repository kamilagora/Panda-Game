package panda;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import panda.components.ImagePanel;
import panda.components.Sprite;
import panda.models.interfaces.OnPlayAgainListener;
import panda.models.interfaces.OnReturnListener;

/**
 * Klasa odpiwadająca za widok panelu po zakończeniu aktualnie rozgrywanego
 * poziomu. Obsługuje zdarzenia przemieszczania między widokami, rozegrania 
 * aktualnego poziomu od początku.
 * Dziedziczy po klasie ImagePanel
 * @author Kamila
 */
public class EndOfLevel extends ImagePanel {
    
    /** przycisk zagraj ponownie */
    public JButton btnPlayAgain;
    /** przycisk kolejny poziom */
    public JButton btnNextLevel;
    /** etykieta z pandą */
    private Sprite imagePanda;
     /** obrazek pandy */
    private Image panda;
    /** obiekt implementujący interfejs OnReturnListener */
    private OnReturnListener onReturnListener;
    /** label z informacjami odnośnie zakończonej gry */
    private JLabel infoLabel;
     /** obiekt implementujący interfejs OnPlayAgainListener */
    private OnPlayAgainListener onPlayAgainListener;

    /**
     * Konstruktor klasy widoku po zakończeniu gry.
     * Wczytanie zasobów graficznych.
     * Dodanie przycisków i zdarzeń obsługujących przyciski
     * @param image obraz
     */
    public EndOfLevel(Image image) {
        super(image);
        setLayout(null);
        /** wczytanie obrazu pandy */
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(this);
        panda = toolkit.getImage("res/images/endOfLevel.png");
        tracker.addImage(panda, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
        }
        initPandaImage();
        initPlayAgainButton();
        initNextLevelButton();
        initInfoLabel();
    }

    /**
     * Funkcja inicjalizująca obrazek pandy
     */
    private void initPandaImage() {
        imagePanda = new Sprite(panda);
        imagePanda.setLocation(600, 177);
        add(imagePanda);
    }
/**
 * Funkcja inicjalizująca przycisk "Graj ponownie"
 * Ustawienie parametrów: kolor, wielkość tekstu, napis, lokalizacja, wielkość
 * przycisku
 * Obsługa zdarzenia kliknięcia w przycisk
 */
    private void initPlayAgainButton() {
        btnPlayAgain = new JButton();
        btnPlayAgain.setBackground(Color.cyan);
        btnPlayAgain.setFont(new Font("Segoe UI", Font.BOLD, 28));
        btnPlayAgain.setText("<html><div style=\"text-align: center\">" + "Jeszcze raz" + "</div></html>");
        btnPlayAgain.setLocation(690, 650);
        btnPlayAgain.setSize(252, 100);
        add(btnPlayAgain);
        /**
         * Dodanie obsługi zdarzenia rozegrania gry ponownie po
         * kliknięciu w przycisk
         */
        btnPlayAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                onPlayAgainListener.onPlayAgain();
            }
        });
    }
/**
 * Funkcja inicjalizuje podstawowe parametry przycisku "graj dalej"
 * Obsługa kliknięcia w przycisk i przejście do panelu wyboru poziomów
 */
    private void initNextLevelButton() {
        btnNextLevel = new JButton();
        btnNextLevel.setBackground(Color.yellow);
        btnNextLevel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        btnNextLevel.setText("<html><div style=\"text-align: center\">" + "Następny poziom" + "</div></html>");
        btnNextLevel.setLocation(420, 650);
        btnNextLevel.setSize(252, 100);
        add(btnNextLevel);
        btnNextLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onReturnListener.onReturn();
            }
        });
    }
/**
 * Funkcja ustawiające parametry labela 
 */
    private void initInfoLabel() {
        infoLabel = new JLabel();
        infoLabel.setLocation(80, 50);
        infoLabel.setSize(500, 500);
        infoLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        add(infoLabel);
    }
/**
 * Funkcja wstawiająca liczbę zdobytych punktów w danej rozgrywce do 
 * etykiety
 * @param points punkty
 */
    public void setPoints(int points) {
        String text = "Brawo, udało Ci się! Jestem z Ciebie dumna! W trakcie gry uzykałeś " + points + " " + polishString(points) + "!";
        infoLabel.setText("<html><div style=\"text-align: justify\">" + text + "</div></html>");
    }
/**
 * Funkcja, która zwraca odpowiednią odmianę słowa punkt w zależności
 * od zdobytej ilości punktów, w uwzględniu zasad gramatyki
 * @param points punkty
 * @return prawidłowa odmiana słowa punkt
 */
    public String polishString(int points) {
        if (points == 1) {
            return "punkt";
        }
        if (points % 10 >= 2 && points % 10 <= 4
                && (points % 100 < 10 || points % 100 > 20)) {
            return "punkty";
        }
        return "punktów";
    }
/**
 * Dodanie obiektu implementującego interfejs OnReturnListener
 * @param onReturnListener obiekt implementujący interfejs OnReturnListener
 */
    public void addOnReturnListener(OnReturnListener onReturnListener) {
        this.onReturnListener = onReturnListener;
    }
/**
 * Dodanie obiektu implementującego interfejs OnPlayAgainListener
 * @param onPlayAgainListener obiekt implementujący interfejs 
 * OnPlayAgainListener
 */
    public void addOnPlayAgainListener(OnPlayAgainListener onPlayAgainListener) {
        this.onPlayAgainListener = onPlayAgainListener;
    }
}
