package panda;

import panda.models.interfaces.OnReturnListener;
import panda.components.ImageButton;
import panda.components.ImagePanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Widok nadrzędny gry Klasa dziedziczy po klasie JFrame Implementuje zdarzenie
 * powrotu do widoku poprzedniego.
 *
 * @author Kamila
 */
public class Menu extends JFrame implements OnReturnListener {

    /**
     * stos przechowujący historię wyświetlanych widoków
     */
    private final Stack<String> cardHistory = new Stack<>();
    /**
     * panel tytułowy
     */
    private JPanel titleScreenPanel;
    /**
     * nadrzędny panel
     */
    private JPanel cardPanel;
    /**
     * panel menu po uruchomieniu gry
     */
    private JPanel menuScreenPanel;
    /**
     * widok o nas
     */
    private AboutAuthor aboutAuthor;
    /**
     * widok z zasadami gry
     */
    private HowToPlay howToPlay;
    /**
     * widok wyboru poziomu
     */
    private ChooseLevel levelCard;
    /**
     * obiekt klasy Game, zarządzający rozgrywką
     */
    private Game game;
    /**
     * obiekt definiujący układ elementów jako kart.
     */
    private CardLayout cl;
    /**
     * klip muzyki w menu
     */
    private static Clip clip;
    /**
     * klip muzyki w rozgrywce
     */
    private static Clip clip2;
    /**
     * tło po uruchomieniu gry
     */
    private Image img;
    /**
     * obraz pandy w wyborze poziomu
     */
    private Image lvlImage;
    /**
     * tło w menu gry
     */
    private Image gradient;
    /**
     * pierwsza ikona przycisku o nas
     */
    private ImageIcon about1 = new ImageIcon("res/images/about.png");
    /**
     * druga ikona przycisku o nas
     */
    private ImageIcon about2 = new ImageIcon("res/images/about1.png");
    /**
     * pierwsza ikona przycisku nowa gra
     */
    private ImageIcon img1 = new ImageIcon("res/images/1.png");
    /**
     * druga ikona przycisku nowa gra
     */
    private ImageIcon img2 = new ImageIcon("res/images/2.png");
    /**
     * pierwsza ikona przycisku koniec
     */
    private ImageIcon a = new ImageIcon("res/images/x.png");
    /**
     * druga ikona przycisku koniec
     */
    private ImageIcon b = new ImageIcon("res/images/y.png");
    /**
     * pierwsza ikona przycisku jak grać
     */
    private ImageIcon im1 = new ImageIcon("res/images/btnHelp.png");
    /**
     * druga ikona przycisku jak grać
     */
    private ImageIcon im2 = new ImageIcon("res/images/btnHelp1.png");
    /**
     * przycisk o nas
     */
    public ImageButton aboutUs = new ImageButton(about1, about2, about1);
    /**
     * przycisk nowa gra
     */
    public ImageButton btnGame = new ImageButton(img1, img2, img1);
    /**
     * przycisk koniec
     */
    public ImageButton btnEnd = new ImageButton(a, b, a);
    /**
     * przycisk jak grać
     */
    public ImageButton btnHelp = new ImageButton(im1, im2, im1);

    /**
     * przycisk poziom 1
     */
    public JButton btnLevel1 = new JButton();
    /**
     * przycisk poziom 2
     */
    public JButton btnLevel2 = new JButton();
    /**
     * przycisk poziom 3
     */
    public JButton btnLevel3 = new JButton();
    /**
     * przycisk poziom 4
     */
    public JButton btnLevel4 = new JButton();
    /**
     * przycisk poziom 5
     */
    public JButton btnLevel5 = new JButton();
    /**
     * przycisk poziom 6
     */
    public JButton btnLevel6 = new JButton();

    /**
     * Konstruktor nadrzędnego widoku gry. Definiuje układ elementów jako karty.
     *
     */
    public Menu() {
        super();
        cl = new CardLayout();
        initImages();
        initTimer();
        initCardPanel();
        setTitle("Panda - nauka matematyki na wesoło");
    }

    /**
     * Wczytanie klipów muzycznych
     */
    static {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/sounds/opening.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(new File("res/sounds/2.wav").getAbsoluteFile());
            clip2 = AudioSystem.getClip();
            clip2.open(audioInputStream2);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        }
    }

    /**
     * Wczytanie obrazów
     */
    private void initImages() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(this);
        img = toolkit.getImage("res/images/title.png");
        lvlImage = toolkit.getImage("res/images/lvlImage.png");
        gradient = toolkit.getImage("res/images/tlo1.png");

        tracker.addImage(img, 0);
        tracker.addImage(lvlImage, 1);
        tracker.addImage(gradient, 2);
        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Inicjalizacja czasomierza zmieniającego widok na menu
     */
    private void initTimer() {

        ActionListener taskPerformer = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                cardHistory.push("MenuScreen");
                cl.show(cardPanel, cardHistory.peek());
                clip.setMicrosecondPosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }

        };

        Timer timer = new Timer(4000, taskPerformer);
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Inicjalizacja paneli jako kart
     */
    private void initCardPanel() {
        initMenuScreen();
        initTitleScreen();
        initHowToPlay();
        initAboutAuthor();
        initLevelCard();
        initGame();
        cardPanel = new JPanel();
        cardPanel.setLayout(cl);
        cardPanel.add(titleScreenPanel, "titleScreen");
        cardPanel.add(menuScreenPanel, "MenuScreen");
        cardPanel.add(aboutAuthor, "AboutUS");
        cardPanel.add(howToPlay, "HowToPlay");
        cardPanel.add(levelCard, "LevelCard");
        cardPanel.add(game, "Game");
        getContentPane().add(cardPanel, BorderLayout.CENTER);
    }

    /**
     * Inicjalizacja nowej gry
     */
    private void initGame() {
        game = new Game();
        game.addOnReturnListener(this);
    }

    /**
     * Funkcja inicjalizująca panel wyboru poziomu
     */
    private void initLevelCard() {
        levelCard = new ChooseLevel();
        initButton(btnLevel1, 130, 300, "Porównywanie liczb", Color.GREEN, 1);
        initButton(btnLevel2, 386, 300, "Dodawanie", Color.yellow, 2);
        initButton(btnLevel3, 642, 300, "Odejmowanie", Color.GREEN, 3);
        initButton(btnLevel4, 130, 404, "Mnożenie", Color.yellow, 4);
        initButton(btnLevel5, 386, 404, "Dzielenie", Color.GREEN, 5);
        initButton(btnLevel6, 642, 404, "Mix zadaniowy", Color.yellow, 6);
        levelCard.addOnReturnListener(this);
    }

    /**
     * Funkcja inicjalizująca parametry przycisku. Obsługa zdarzenia kliknięcia
     * w przycisk.
     *
     * @param button przycisk
     * @param x szerokość
     * @param y wysokość
     * @param text napis
     * @param color kolor przycisku
     * @param level numer poziomu
     */
    private void initButton(JButton button, int x, int y, String text, Color color, int level) {
        button.setLocation(x, y);
        button.setBackground(color);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 28));
        button.setText("<html><div style=\"text-align: center\">" + text + "</div></html>");
        button.setSize(252, 100);

        button.addActionListener((ActionEvent arg0) -> {
            cardHistory.push("Game");
            cl.show(cardPanel, cardHistory.peek());
            clip.stop();
            clip2.setMicrosecondPosition(0);
            clip2.loop(Clip.LOOP_CONTINUOUSLY);
            clip2.start();
            game.StartGame(level);
        });

        levelCard.add(button);
    }

    /**
     * Funkcja inicjalizująca panel "Jak grać"
     */
    private void initHowToPlay() {
        howToPlay = new HowToPlay();
        howToPlay.addOnReturnListener(this);
        howToPlay.setLayout(cl);
    }

    /**
     * Funkcja inicjalizująca panel "O nas"
     */
    private void initAboutAuthor() {
        aboutAuthor = new AboutAuthor();
        aboutAuthor.setLayout(cl);
        aboutAuthor.addOnReturnListener(this);
    }

    /**
     * Funkcja inicjalizująca widok tytułowy
     */
    private void initTitleScreen() {
        titleScreenPanel = new ImagePanel(img);
        titleScreenPanel.setLayout(new BorderLayout());
    }

    /**
     * Funkcja inicjalizująca widok menu gry
     */
    private void initMenuScreen() {
        initAboutUsBtn();
        initGameBtn();
        initHelpBtn();
        initEndBtn();
        menuScreenPanel = new ImagePanel(gradient, false);
        menuScreenPanel.setLayout(null);
        menuScreenPanel.setOpaque(false);
        menuScreenPanel.setOpaque(false);
        menuScreenPanel.add(aboutUs);
        menuScreenPanel.add(btnEnd);
        menuScreenPanel.add(btnGame);
        menuScreenPanel.add(btnHelp);
    }

    /**
     * Funkcja inicjalizująca przycisk, po wciśnięciu którego przechodzimy do
     * panelu widoku "O nas".
     */
    private void initAboutUsBtn() {
        aboutUs.setLocation(30, 100);
        aboutUs.setSize(358, 610);

        aboutUs.addActionListener((ActionEvent arg0) -> {
            cardHistory.push("AboutUS");
            cl.show(cardPanel, cardHistory.peek());
        });
    }

    /**
     * Funkcja inicjalizująca przycisk, po wciśnięciu którego przechodzimy do
     * panelu wyboru poziomu
     */
    private void initGameBtn() {
        btnGame.setLocation(400, 280);
        btnGame.setSize(239, 189);

        btnGame.addActionListener((ActionEvent arg0) -> {
            cardHistory.push("LevelCard");
            cl.show(cardPanel, cardHistory.peek());
        });
    }

    /**
     * Funkcja inicjalizująca przycisk, po wciśnięciu którego przechodzimy do
     * panelu widoku "Jak grać".
     */
    private void initHelpBtn() {
        btnHelp.setLocation(600, 50);
        btnHelp.setSize(227, 180);

        btnHelp.addActionListener((ActionEvent arg0) -> {
            cardHistory.push("HowToPlay");
            cl.show(cardPanel, cardHistory.peek());
        });
    }

    /**
     * Funkcja inicjalizująca przycisk wyjścia z gry
     */
    private void initEndBtn() {
        btnEnd.setLocation(650, 500);
        btnEnd.setSize(313, 186);

        btnEnd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                System.exit(0);
            }
        });
    }

    /**
     * Obsługa zdarzenia powrotu do poprzedniego widoku
     */
    @Override
    public void onReturn() {
        cardHistory.pop();
        cl.show(cardPanel, cardHistory.peek());

        if (clip2.isRunning()) {
            clip.setMicrosecondPosition(0);
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip2.stop();
        clip.start();
    }
}
