package panda.components;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.function.Function;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import panda.models.interfaces.OnBadAnswerListener;
import panda.models.interfaces.OnCorrectAnswerListener;

/**
 * Klasa modelująca przemieszczające się balony z odpowiedziami
 *
 * @author Kamila
 */
public class Balloon extends Sprite {

    /**
     * początkowa lokalizacja
     */
    public Point startLocation;
    /**
     * funkcja opisująca zmianę położenia balonu w osi x, w zależności od czasu
     */
    public Function<Double, Integer> xModifier;
    /**
     * czy prawidłowa odpowiedź
     */
    public boolean isCorrect;
    /**
     * klip muzyki przy dobrej odpowiedzi
     */
    private static Clip clip;
    /**
     * klip muzyki przy złej odpowiedzi
     */
    private static Clip clipBadAnswer;
    /**
     * etykieta z odpowiedziami na balonie
     */
    private JLabel label;
    /**
     * Czas startu balona
     */
    private long startTime;
    /**
     * Czas balona po wciśnięciu menu - pauza
     */
    private long pauseStartTime = 0;
    /**
     * flaga - Czy właczono menu - gra się zatrzymuje
     */
    private boolean isPaused = false;
    /**
     * obiekt implementujący interfejs OnCorrectAnswerListener
     */
    private OnCorrectAnswerListener onCorrectAnswerlistener;
    /**
     * obiekt implementujący interfejs OnBadAnswerListener
     */
    private OnBadAnswerListener onBadAnswerlistener;

    /**
     * Blok wczytujący dźwięki z pliku
     */
    static {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/sounds/2_1.wav").getAbsoluteFile());
            AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(new File("res/sounds/badAnswer.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clipBadAnswer = AudioSystem.getClip();
            clipBadAnswer.open(audioInputStream2);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
        }
    }

    /**
     * Konstruktor - ustawienia parametrów obiektu ustawienie lokalizacji,
     * labeli, obszaru obiektu, w który można kliknąć, ustawienie dzwięków po
     * kliknięciu w balon
     *
     * @param image obraz balona
     * @param startLocation pozycja początkowa balona
     */
    public Balloon(Image image, Point startLocation) {
        super(image);

        this.setLayout(null);
        super.setLocation(startLocation.x, startLocation.y);

        this.startLocation = startLocation;
        this.xModifier = t -> 0;
        this.label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setLayout(null);
        label.setBounds(0, 15, image.getWidth(this), image.getHeight(this) - 154);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        add(label);

        // Ustawienie miejsca (granic) na balonie, w które można kliknąć
        JButton hitBox = new JButton();
        hitBox.setOpaque(false);
        hitBox.setContentAreaFilled(false);
        hitBox.setBorderPainted(false);
        hitBox.setBounds(9, 9, image.getWidth(this) - 9 - 9, 88);
        add(hitBox);

        /**
         * Obsługa zdarzenia kliknięcia w balon - z odpowiednimi dzwiękami
         */
        hitBox.addActionListener((ActionEvent arg0) -> {
            if (isPaused) {
                return;
            }
            clip.stop();
            clipBadAnswer.stop();
            if (isCorrect) {
                clip.setMicrosecondPosition(0);
                clip.start();
                onCorrectAnswerlistener.onCorrectAnswer();
            } else {
                clipBadAnswer.setMicrosecondPosition(0);
                clipBadAnswer.start();
                onBadAnswerlistener.onBadAnswer();
            }
            setVisible(false);
        });
    }

    /**
     * Funkcja resetująca lokalizację obiektu (balona).
     */
    public void resetLocation() {
        this.setLocation(startLocation);
    }

    /**
     * Funkcja ustawiająca podstawowe parametry balona przy starcie
     *
     * @param startTime czas startu balona
     */
    public void start(long startTime) {
        this.setLocation(startLocation);
        this.startTime = startTime;
        isPaused = false;
    }

    /**
     * Funkcja aktualizująca pozycję balona
     *
     * @param time czas przelotu
     */
    public void update(double time) {
        final double v = -60; // [px/s]
        double dt = (double) (time - startTime) / 1000000000;
        int y = (int) Math.round(v * dt + startLocation.y);
        this.setLocation(startLocation.x + xModifier.apply(dt), y);
    }

    /**
     * Funkcja opowiadająca za wpisywanie tekstów do labeli umieszczonych na
     * balonach
     *
     * @param text odpowiedzi wyświetlajace się na etykiecie balona
     */
    @Override
    public void setText(String text) {
        label.setText("<html><div style=\"text-align: center\">" + text + "</div></html>");
    }

    /**
     * Funkcja oblicza zaktualizowaną pozycję y balona tak, aby po przekroczeniu
     * górnej krawędzi okna, balon pojawił się przy dolnej krawędzi
     *
     * @param y współrzędna y
     * @return zaktualizowana współrzędna y
     */
    private int calculateY(int y) {
        int h = this.getHeight();
        int ph = this.getParent().getHeight() + h;
        return y < 0 ? ((((y + h) % ph) + ph) % ph) - h : y;
    }

    /**
     * Nadpisanie metody odpowiedzialnej za ustawienie lokalizacji balona
     *
     * @param p współrzędne obiektu
     */
    @Override
    public void setLocation(Point p) {
        p.y = calculateY(p.y);
        super.setLocation(p);
    }

    /**
     * Nadpisanie metody odpowiedzialnej za ustawienie lokalizacji balona
     *
     * @param x współrzędna x obiektu
     * @param y współrzędna y obiektu
     */
    @Override
    public void setLocation(int x, int y) {
        y = calculateY(y);
        super.setLocation(x, y);
    }

    /**
     * Służy do zaktualizowania stanu balona po zatrzymaniu rozgrywki
     */
    public void pause() {
        pauseStartTime = System.nanoTime();
        isPaused = true;
    }

    /**
     * Służy do zaktualizowania stanu balona po wznowieniu rozgrywki
     */
    public void resume() {
        this.startTime += System.nanoTime() - pauseStartTime;
        isPaused = false;
    }

    /**
     * Dodanie obiektu implementującego interfejs OnCorrectAnswerListener
     *
     * @param listener obiekt implementujący interfejs OnCorrectAnswerListener
     */
    public void addOnCorrectAnswerListener(OnCorrectAnswerListener listener) {
        this.onCorrectAnswerlistener = listener;
    }

    /**
     * Dodanie obiektu implementującego interfejs OnBadAnswerListener
     * @param onBadAnswerlistener obiekt implementujący interfejs 
     * OnBadAnswerListener 
     */
    public void addOnBadAnswerListener(OnBadAnswerListener onBadAnswerlistener) {
        this.onBadAnswerlistener = onBadAnswerlistener;
    }
}
