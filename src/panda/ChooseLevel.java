package panda;

import panda.models.interfaces.OnReturnListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import javax.swing.*;
import panda.components.ImageButton;
import panda.components.Sprite;

/**
 * Klasa tworzy i zarządza widokiem wyboru poziomu
 * @author Kamila
 */
public class ChooseLevel extends JPanel {
     /**
     * Ikona przycisku "powrót"
     */
    ImageIcon back1 = new ImageIcon("res/images/back1.png");
     /**
     * Druga ikona przycisku "powrót"
     */
    ImageIcon back2 = new ImageIcon("res/images/back2.png");
    /**
     * ikona do panelu wyboru poziomu
     */
    Image lvlImage;
    /**
     * panel klasy Sprite, zawiera obrazek pandy
     */
    private final Sprite lvlPanel;
    /**
     * obiekt implementujący interfejs OnReturnListener
     */
    private OnReturnListener arg;
    /**
     * przycisk powrotu
     */
    public JButton b1 = new ImageButton(back1, back2, null);
    /**
     * etykieta "wybierz poziom"
     */
    public JLabel lblLevel;

    /**
     * Konstruktor klasy wyboru poziomu.
     * Załadowanie zasobów.
     * Dodanie przycisku powrotu do poprzedniego widoku.
     */
    public ChooseLevel() {
        
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(this);
        lvlImage = toolkit.getImage("res/images/lvlImage.png");
        tracker.addImage(lvlImage, 0);
        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
        }
        this.setBackground(Color.WHITE);
        this.setLayout(null);

        lvlPanel = new Sprite(lvlImage);
        lvlPanel.setLocation(50, 50);
        this.add(lvlPanel);
        lblLevel = new JLabel();
        lblLevel.setLocation(500, 0);
        lblLevel.setSize(500, 500);
        lblLevel.setFont(new Font("Segoe UI", Font.PLAIN, 50));
        lblLevel.setForeground(Color.DARK_GRAY);
        lblLevel.setText("<html>"+ "Wybierz poziom:" + "</html>");
        this.add(lblLevel);
        this.add(b1);

        b1.setLocation(650, 550);
        b1.setSize(287, 162);
       /* Dodaj obsługę zdarzeń - wciśnięcie przycisku*/
        b1.addActionListener((ActionEvent e) -> {
            arg.onReturn();
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
