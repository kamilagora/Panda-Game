package panda;

import panda.models.interfaces.OnReturnListener;
import panda.models.interfaces.OnCorrectAnswerListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import panda.components.Balloon;
import panda.components.ImagePanel;
import panda.components.Sprite;
import panda.models.Task;
import panda.models.interfaces.OnBadAnswerListener;
import panda.models.interfaces.OnLevelFinishedListener;

/**
 * Główny obszar gry. Klasa dziedzicząca po klasie ImagePanel oraz
 * implementująca zdarzenia wybrania prawidłowej odpowiedzi oraz błędnej
 * odpowiedzi.
 *
 * @author Kamila
 */
public class CurrentLevel extends ImagePanel implements OnCorrectAnswerListener,
        OnBadAnswerListener {

    /**
     * obraz balona nr 1
     */
    private Image balloonImage1;
    /**
     * obraz balona nr 2
     */
    private Image balloonImage2;
    /**
     * obraz balona nr 3
     */
    private Image balloonImage3;
    /**
     * obraz balona nr 4
     */
    private Image balloonImage4;
    /**
     * obraz gwiazdki z punktami
     */
    private Image pointsCard;
    /**
     * obraz pandy
     */
    private Image pandaImage;
    /**
     * karta z pytaniem
     */
    private Image flipCard;
    /**
     * obraz etykiety poziomu
     */
    private Image labelImage;
    /**
     * chumrka z interakcją
     */
    private Image speechBalloon;
    /**
     * obrazek z pandą
     */
    private Sprite pandasPanel;
    /**
     * panel z kartą
     */
    private ImagePanel flipCardPanel;
    /**
     * lista balonów
     */
    private ArrayList<Balloon> balloons = new ArrayList<>(4);
    /**
     * etykieta poziomu
     */
    private Sprite labelSprite;
    /**
     * gwizadka z punktami
     */
    private Sprite pointsPanel;
    /**
     * chumra z interakcjami na zdarzenia
     */
    private Sprite interactionSprite;
    /**
     * czasomierz klatek gry
     */
    private Timer timer1;
    /**
     * odmierza czas do rozpoczęcia gry
     */
    private Timer timer2;
    /**
     * JLabel na etykiecie poziomu
     */
    private JLabel labelLevel;
    /**
     * aktualny numer zadania
     */
    private int currentTask;
    /**
     * lista zadań
     */
    private ArrayList<Task> tasks;
    /**
     * JLabel na etykiecie karty z zadaniami
     */
    private JLabel questionLabel;
    /**
     * obiekt implementujący interfejs OnReturnListener
     */
    private OnReturnListener arg;
    /**
     * przycisk menu - zakoncz
     */
    private JButton btnCloseGame;
    /**
     * przycisk menu
     */
    private JButton btnMenu;
    /**
     * przycisk menu - powrót
     */
    private JButton btnReturn;
    /**
     * przycisk menu - nowa gra
     */
    private JButton btnNewGame;
    /**
     * czas, w którym wystartował timer2
     */
    private long timer2StartTime;
    /**
     * czas, w którym wystartował timer3
     */
    private long timer3StartTime;
    /**
     * czas, w którym użytkownik spauzował grę
     */
    private long pauseTime;
    /**
     * JLabel, który wyświetla zdobyte pkt w grze
     */
    private JLabel pointsLbl;
    /**
     * JLabel wyświetlany na etykiecie interakcji
     */
    private JLabel interactionLbl;
    /**
     * czasomierz startujący balony
     */
    private Timer timer3;
    /**
     * obiekt implementujący interfejs OnLevelFinishedListener
     */
    private OnLevelFinishedListener onLevelFinishedListener;
    /**
     * punkty za zadanie
     */
    private int pointsForTask;
    /**
     * zdobyte punkty w grze
     */
    private int points;
    /**
     * lista pochwał
     */
    private ArrayList<String> consolations;
    /**
     * lista pocieszeń
     */
    private ArrayList<String> commendations;
    /**
     * generator liczb losowych
     */
    private Random random;
    /**
     * czasomierz wyświetlania interakcji
     */
    private Timer timer5;
    /**
     * obiekt odpowiedzialny za wczytywanie obrazów
     */
    private Toolkit toolkit;
    /**
     * obiekt pozwalający czekać na załadowanie obrazów z dysku do pamięci
     * programu
     */
    private MediaTracker tracker;
    /**
     * wymiary okna
     */
    private final Dimension windowDimensions = new Dimension(1024, 768);

    /**
     * Konstruktor obszaru gry. Ładowanie zasobów gry: karta na pytanie, miejsce
     * na nazwę poziomu, balony etykiety, itp. Dodanie obsługi zdarzeń na błedne
     * odpowiedzi, poprawne.
     */
    CurrentLevel() {
        super(null);

        // wczytywanie zasobów graficznych gry
        toolkit = Toolkit.getDefaultToolkit();
        tracker = new MediaTracker(this);

        flipCard = toolkit.getImage("res/images/card.png");
        balloonImage1 = toolkit.getImage("res/images/pink_balloon.png");
        balloonImage2 = toolkit.getImage("res/images/green_balloon.png");
        balloonImage3 = toolkit.getImage("res/images/gold_balloon.png");
        balloonImage4 = toolkit.getImage("res/images/blue_balloon.png");
        pointsCard = toolkit.getImage("res/images/points.png");
        pandaImage = toolkit.getImage("res/images/game1.png");
        labelImage = toolkit.getImage("res/images/etykieta1.png");
        speechBalloon = toolkit.getImage("res/images/lblInfo.png");

        tracker.addImage(pointsCard, 0);
        tracker.addImage(pandaImage, 1);
        tracker.addImage(flipCard, 2);
        tracker.addImage(labelImage, 3);
        tracker.addImage(balloonImage1, 4);
        tracker.addImage(balloonImage2, 5);
        tracker.addImage(balloonImage3, 6);
        tracker.addImage(balloonImage4, 7);
        tracker.addImage(speechBalloon, 8);

        this.setSize(windowDimensions);
        this.setPreferredSize(windowDimensions);
        this.setMinimumSize(windowDimensions);

        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
        }

        setLayout(null);
        random = new Random();
        init();
    }

    /**
     * Start gry - ustawienie początkowego stanu gry
     */
    public void StartGame() {
        currentTask = 0;
        points = 0;
        interactionSprite.setVisible(false);
        pointsLbl.setText("" + points);
        timer2.setInitialDelay(2000);
        timer3.setInitialDelay(3000);
        timer2.restart();
        timer2StartTime = System.nanoTime();
        questionLabel.setText("");
        for (Balloon b : balloons) {
            b.setVisible(false);
        }
    }

    // <editor-fold desc="Initialization">
    /**
     * Funkcja inicjalizująca komponenty
     */
    private void init() {
        initTimers();
        initQuestionLabel();
        initFlipCardPanel();
        initPointsStar();
        initPandaImage();
        initMenuBar();
        initLabelSprite();
        initBalloons();
        initInteractionSprite();
    }

    /**
     * Funkcja inicjalizująca czasomierze
     */
    private void initTimers() {
        timer1 = new Timer(16, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long t1 = System.nanoTime();
                for (Balloon b : balloons) {
                    b.update(t1);
                }
            }
        });
        timer2 = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Balloon b : balloons) {
                    b.resetLocation();
                }
                startTask();
            }
        }
        );
        timer2.setRepeats(false);

        timer3 = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Balloon b : balloons) {
                    b.setVisible(true);
                }
                timer1.restart();
                startBalloons();
            }
        });

        timer3.setRepeats(false);

        timer5 = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interactionSprite.setVisible(false);
            }
        });
        timer5.setRepeats(false);
    }

    /**
     * Funkcja inicjalizująca JLabel do karty pytań
     */
    private void initQuestionLabel() {
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 26));
        questionLabel.setForeground(Color.GRAY);
    }

    /**
     * Funkcja inicjalizującaa panel z kartą zadań
     */
    private void initFlipCardPanel() {
        flipCardPanel = new ImagePanel(flipCard, false);
        flipCardPanel.setLocation(340, 100);
        flipCardPanel.setLayout(new BorderLayout());
        flipCardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        flipCardPanel.add(questionLabel, BorderLayout.CENTER);
        this.add(flipCardPanel);
    }

    /**
     * Funkcja inicjalizująca gwiazdę z punktami za zadania
     */
    private void initPointsStar() {
        pointsLbl = new JLabel();
        pointsLbl.setHorizontalAlignment(JLabel.CENTER);
        pointsLbl.setVerticalAlignment(JLabel.CENTER);
        pointsLbl.setFont(new Font("Segoe UI", Font.BOLD, 48));
        pointsLbl.setBounds(0, 15, pointsCard.getWidth(this), pointsCard.getHeight(this) - 15);
        pointsLbl.setText("<html><div style=\"text-align: center\">" + points + "</div></html>");
        pointsPanel = new Sprite(pointsCard);
        pointsPanel.setLocation(70, 313);
        pointsPanel.setLayout(null);
        pointsPanel.add(pointsLbl);
        this.add(pointsPanel);
    }

    /**
     * Funkcja inicajlizująca etykietę z pandą
     */
    private void initPandaImage() {
        pandasPanel = new Sprite(pandaImage);
        pandasPanel.setLocation(50, 650);
        this.add(pandasPanel);
    }

    /**
     * Funkcja inicjalizująca menu w trakcie gry
     */
    private void initMenuBar() {
        btnReturn = new JButton("Powrót");
        btnReturn.setBackground(Color.GRAY);
        btnReturn.setSize(200, 50);
        btnReturn.setPreferredSize(new Dimension(200, 50));
        btnReturn.setMinimumSize(new Dimension(200, 50));
        btnReturn.setVisible(false);
        btnNewGame = new JButton("Nowa gra");
        btnNewGame.setSize(200, 50);
        btnNewGame.setBackground(Color.GREEN);
        btnNewGame.setPreferredSize(new Dimension(200, 50));
        btnNewGame.setMinimumSize(new Dimension(200, 50));
        btnNewGame.setVisible(false);
        btnCloseGame = new JButton("Zakończ grę");
        btnCloseGame.setBackground(Color.ORANGE);
        btnCloseGame.setSize(200, 50);
        btnCloseGame.setPreferredSize(new Dimension(200, 50));
        btnCloseGame.setMinimumSize(new Dimension(200, 50));
        btnCloseGame.setVisible(false);

        btnMenu = new JButton("MENU");
        btnMenu.setBackground(Color.LIGHT_GRAY);
        btnMenu.setLocation(412, 718);
        btnMenu.setSize(200, 50);
        this.add(btnMenu);
        this.add(btnReturn);
        this.add(btnNewGame);
        this.add(btnCloseGame);

        btnReturn.setLocation(btnMenu.getLocation().x, btnMenu.getLocation().y - 150);
        btnNewGame.setLocation(btnMenu.getLocation().x, btnMenu.getLocation().y - 100);
        btnCloseGame.setLocation(btnMenu.getLocation().x, btnMenu.getLocation().y - 50);

        btnNewGame.addActionListener((ActionEvent arg0) -> {
            newGame();
        });

        btnCloseGame.addActionListener((ActionEvent arg0) -> {
            windowClosing();
        });

        btnMenu.addActionListener((ActionEvent e) -> {
            btnReturn.setVisible(true);
            btnNewGame.setVisible(true);
            btnCloseGame.setVisible(true);
            timer1.stop();
            timer2.stop();
            timer3.stop();
            pauseTime = System.nanoTime();
            for (Balloon b : balloons) {
                b.pause();
            }
        });
        btnReturn.addActionListener((ActionEvent e) -> {
            resume();
        });
    }

    /**
     * Funkcja inicjalizująca etykietę poziomu
     */
    private void initLabelSprite() {
        labelSprite = new Sprite(labelImage);
        labelSprite.setLocation(70, 40);
        labelLevel = new JLabel();
        labelLevel.setHorizontalAlignment(JLabel.CENTER);
        labelLevel.setVerticalAlignment(JLabel.CENTER);
        labelSprite.setLayout(new BorderLayout());
        labelLevel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        labelSprite.add(labelLevel);
        this.add(labelSprite);
    }

    /**
     * Funkcja inicjalizująca balony
     */
    private void initBalloons() {
        Balloon balloon = new Balloon(balloonImage1, new Point(800, windowDimensions.height + 200));
        balloon.xModifier = t -> (int) (25 * Math.sin(1.1 * t));
        balloons.add(balloon);
        balloon = new Balloon(balloonImage2, new Point(890, windowDimensions.height + 100));
        balloon.xModifier = t -> (int) (20 * Math.cos(0.5 * t));
        balloons.add(balloon);
        balloon = new Balloon(balloonImage3, new Point(700, windowDimensions.height + 100));
        balloon.xModifier = t -> (int) (-15 * Math.sin(0.7 * t));
        balloons.add(balloon);
        balloon = new Balloon(balloonImage4, new Point(790, windowDimensions.height));
        balloon.xModifier = t -> (int) (-15 * Math.cos(1.3 * t));
        balloons.add(balloon);

        for (Balloon b : balloons) {
            b.setVisible(false);
            b.addOnCorrectAnswerListener(this);
            b.addOnBadAnswerListener(this);
            this.add(b);
        }
    }

    /**
     * Funkcja inicjalizująca etykietę z interakcjami
     */
    private void initInteractionSprite() {
        interactionLbl = new JLabel();
        interactionLbl.setHorizontalAlignment(JLabel.CENTER);
        interactionLbl.setVerticalAlignment(JLabel.CENTER);
        interactionLbl.setBounds(29, 23, speechBalloon.getWidth(this) - 29 - 29, speechBalloon.getHeight(this) - 23 - 44);
        interactionLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        interactionLbl.setText("<html><div style=\"text-align: center\">" + "" + "</div></html>");

        interactionSprite = new Sprite(speechBalloon);
        interactionSprite.setVisible(false);
        interactionSprite.setLocation(120, 460);
        interactionSprite.setLayout(null);
        interactionSprite.add(interactionLbl);
        this.add(interactionSprite);
        consolations = new ArrayList<>();
        commendations = new ArrayList<>();
        populateList(consolations, "res/data/consolation.txt");
        populateList(commendations, "res/data/commendation.txt");
    }
    // </editor-fold>

    /**
     * Funkcja wczytująca elementy danego poziomu - pytania, odpowiedzi oraz tło
     * w danej rozgrywce
     *
     * @param level określa numer poziomu
     */
    public void loadLevel(int level) {
        switch (level) {
            case 1:
                loadTasks("res/data/task1.txt");
                labelLevel.setText("Porównania");
                Image lvlImage = toolkit.getImage("res/images/level1.png");
                tracker.addImage(lvlImage, level);
                try {
                    tracker.waitForAll();
                } catch (InterruptedException ex) {
                }
                super.setImage(lvlImage);
                break;
            case 2:
                loadTasks("res/data/task2.txt");
                labelLevel.setText("Dodawanie");
                Image lvl2Image = toolkit.getImage("res/images/level2.png");
                tracker.addImage(lvl2Image, level);
                try {
                    tracker.waitForAll();
                } catch (InterruptedException ex) {
                }
                super.setImage(lvl2Image);
                break;
            case 3:
                loadTasks("res/data/task3.txt");
                labelLevel.setText("Odejmowanie");
                Image lvl3Image = toolkit.getImage("res/images/level3.png");
                tracker.addImage(lvl3Image, level);
                try {
                    tracker.waitForAll();
                } catch (InterruptedException ex) {
                }
                super.setImage(lvl3Image);
                break;
            case 4:
                loadTasks("res/data/task4.txt");
                labelLevel.setText("Mnożenie");
                Image lvl4Image = toolkit.getImage("res/images/level4t.png");
                tracker.addImage(lvl4Image, level);
                try {
                    tracker.waitForAll();
                } catch (InterruptedException ex) {
                }
                super.setImage(lvl4Image);
                break;
            case 5:
                loadTasks("res/data/task5.txt");
                labelLevel.setText("Dzielenie");
                Image lvl5Image = toolkit.getImage("res/images/level5.png");
                tracker.addImage(lvl5Image, level);
                try {
                    tracker.waitForAll();
                } catch (InterruptedException ex) {
                }
                super.setImage(lvl5Image);
                break;
            case 6:
                loadTasks("res/data/task6.txt");
                labelLevel.setText("Mix zadaniowy");
                Image lvl6Image = toolkit.getImage("res/images/level6.png");
                tracker.addImage(lvl6Image, level);
                try {
                    tracker.waitForAll();
                } catch (InterruptedException ex) {
                }
                super.setImage(lvl6Image);
                break;
        }
    }

    /**
     * Funkcja wczytująca kolejne linie z pliku do listy Parzyste linie
     * zawierają pytania Nieparzyste linie odpowiedzi do pytania
     *
     * @param tasksFile ścieżka do pliku
     */
    private void loadTasks(String tasksFile) {
        Charset ch = Charset.forName("UTF-8");
        tasks = new ArrayList<>();
        String text;
        int lineCount = 0;
        Task task = null;
        String[] arrOfText;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tasksFile), ch))) {
            while ((text = br.readLine()) != null) {
                if (lineCount % 2 == 0) {
                    task = new Task();
                    task.question = text;
                } else {
                    arrOfText = text.split(" ");
                    task.answers = new ArrayList<>(Arrays.asList(arrOfText));
                    task.correctAnswerIndex = 0;
                    shuffleTaskAnswers(task);
                    tasks.add(task);
                }
                lineCount++;
            }
        } catch (IOException ex) {
        }
    }

    /**
     * Funkcja wczytująca kolejne linie z pliku do listy
     *
     * @param list lista, do której wczytywany jest plik ze ścieżki
     * @param file ścieżka do pliku
     */
    private void populateList(List<String> list, String file) {

        Charset ch = Charset.forName("UTF-8");
        String text;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), ch))) {
            while ((text = br.readLine()) != null) {
                list.add(text);
            }
        } catch (IOException ex) {
        }
    }

    /**
     * Funkcja wybiera losową interakcję.
     *
     * @param list lista z interakcjami
     * @return losowa interakcja
     */
    private String randomInteraction(ArrayList<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Tasuje odpowiedzi na pytanie używając algorytmu Fishera-Yatesa
     *
     * @param task - zadanie, którego odpowiedzi zostaną przemieszane
     */
    private void shuffleTaskAnswers(Task task) {
        for (int i = task.answers.size() - 1; i > 0; i--) {
            int r = random.nextInt(i);

            String temp = task.answers.get(i);
            task.answers.set(i, task.answers.get(r));
            task.answers.set(r, temp);

            if (i == task.correctAnswerIndex) {
                task.correctAnswerIndex = r;
            } else if (r == task.correctAnswerIndex) {
                task.correctAnswerIndex = i;
            }
        }
    }

    /**
     * Funkcja wyświetlająca aktualne zadanie
     */
    private void startTask() {
        questionLabel.setText("<html><div style=\"text-align: justify\">" + tasks.get(currentTask).question + "</div></html>");
        for (int i = 0; i < balloons.size(); i++) {
            Balloon b = balloons.get(i);
            b.resetLocation();
            b.setText(tasks.get(currentTask).answers.get(i));
            b.isCorrect = false;
        }
        balloons.get(tasks.get(currentTask).correctAnswerIndex).isCorrect = true;
        timer3.restart();
        timer3StartTime = System.nanoTime();
        pointsForTask = 4;
    }

    /**
     * Funkcja wznawiająca rozgrywkę
     */
    private void resume() {
        hideMenu();
        long timer2Diff = (pauseTime - timer2StartTime) / 1000000;
        long timer3Diff = (pauseTime - timer3StartTime) / 1000000;
        if (timer2Diff < 5000) {
            timer2StartTime += System.nanoTime() - pauseTime;
            timer2.setInitialDelay(5000 - (int) timer2Diff);
            timer2.restart();
        } else if (timer3Diff < 5000) {
            timer3StartTime += System.nanoTime() - pauseTime;
            timer3.setInitialDelay(5000 - (int) timer3Diff);
            timer3.restart();
        } else {
            timer1.restart();
        }

        for (Balloon b : balloons) {
            b.resume();
        }
    }

    /**
     * Ukrycie menu
     */
    private void hideMenu() {
        btnReturn.setVisible(false);
        btnNewGame.setVisible(false);
        btnCloseGame.setVisible(false);
    }

    /**
     * Rozpoczęcie lotu balonów
     */
    private void startBalloons() {
        long startTime = System.nanoTime();
        for (Balloon b : balloons) {
            b.start(startTime);
        }
    }

    /**
     * Ukrycie balonów
     */
    private void hideBalloons() {
        for (Balloon b : balloons) {
            b.setVisible(false);
        }
    }

    /**
     * potwierdzenie zamknięcia okna
     */
    public void windowClosing() {
        String ObjButtons[] = {"Tak", "Anuluj"};
        int PromptResult = JOptionPane.showOptionDialog(null, " Czy na pewno chcesz zamknąć grę?"
                + " Cały postęp w grze zostanie utracony!",
                "Zanim zamkniesz...", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, ObjButtons, ObjButtons[1]);
        if (PromptResult == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    /**
     * potwierdzenie rozpoczęcia nowej gry
     */
    public void newGame() {
        String ObjButtons[] = {"Tak", "Anuluj"};
        int PromptResult = JOptionPane.showOptionDialog(null, " Czy na pewno chcesz zacząć grę od nowa?"
                + " Cały postęp w grze zostanie utracony!",
                "Zanim zaczniesz od nowa...", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE,
                null, ObjButtons, ObjButtons[1]);
        if (PromptResult == JOptionPane.YES_OPTION) {
            hideMenu();
            timer1.stop();
            this.arg.onReturn();
        }
    }

    /**
     * Dodanie obiektu implemenującego interfejs OnReturnListener
     *
     * @param arg obiekt implementujący interfejs OnReturnListener
     */
    public void addOnReturnListener(OnReturnListener arg) {
        this.arg = arg;
    }

    /**
     * Dodanie obiektu implemenującego interfejs OnLevelFinishedListener
     *
     * @param onLevelFinishedListener obiekt implementujący interfejs
     * onLevelFinishedListener
     */
    public void addOnLevelFinishedListener(OnLevelFinishedListener onLevelFinishedListener) {
        this.onLevelFinishedListener = onLevelFinishedListener;
    }

    /**
     * Funkcja zwracająca punkty
     *
     * @return punkty
     */
    public int getPoints() {
        return points;
    }

    /**
     * Obsługa zdarzenia kliknięcia prawidłowej odpowiedzi
     */
    @Override
    public void onCorrectAnswer() {
        currentTask++;
        points += pointsForTask;
        pointsLbl.setText("<html><div style=\"text-align: center\">" + points + "</div></html>");
        if (currentTask >= tasks.size()) {
            Timer timer4 = new Timer(1000, (ActionEvent e) -> {
                onLevelFinishedListener.OnLevelFinished();
            });
            timer4.setRepeats(false);
            timer4.start();
            return;
        }
        interactionSprite.setVisible(true);
        timer5.start();
        interactionLbl.setText("<html><div style=\"text-align: center\">" + randomInteraction(consolations) + "</div></html>");
        hideBalloons();
        startTask();
    }
    /**
     *  Obsługa zdarzenia kliknięcia błędnej odpowiedzi
     */
    @Override
    public void onBadAnswer() {
        interactionSprite.setVisible(true);
        timer5.start();
        pointsForTask--;
        interactionLbl.setText("<html><div style=\"text-align: center\">" + randomInteraction(commendations) + "</div></html>");
    }
}
