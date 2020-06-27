package panda;

import panda.models.interfaces.OnReturnListener;
import java.awt.*;
import java.awt.CardLayout;
import javax.swing.JPanel;
import panda.models.interfaces.OnLevelFinishedListener;
import panda.models.interfaces.OnPlayAgainListener;

/**
 * Klasa dziedzicząca po klasie JPanel, implementująca zdarzenie zakończenia
 * gry, rozpoczęcia gry na nowo oraz powrotu do wcześniejszego widoku 
 * Zawiera parametry aktualnego poziomu i zakończenia gry
 * @author Kamila
 */
public class Game extends JPanel implements OnReturnListener, OnLevelFinishedListener, OnPlayAgainListener {

    /**
     * panel aktualnie rozgrywanego poziomu
     */
    private final CurrentLevel currentLevel;
    /**
     * panel wyświetlany po zakończeniu poziomu
     */
    private final EndOfLevel endOfLevel;
    /**
     * obiekt implementujący interfejs OnReturnListener
     */
    private OnReturnListener onReturnListener;
    /**
     * obiekt definiujący układ elementów jako kart.
     */
    private final CardLayout cardLayout;
    /**
     * obraz panelu po zakończeniu poziomu
     */
    private Image finishBackgroundImage;

    /**
     * Konstruktor klasy panelu gry: panel aktualnego poziomu, zakończenia
     * wczytanie obrazu panelu zakończenia gry
     * dodanie obsługi zdarzeń: na zakończenie rozgrywki, powrotu do 
     * wcześniejszego widoku oraz ponownego rozpoczęcia rozgrywki
     */
    Game() {
        currentLevel = new CurrentLevel();
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker tracker = new MediaTracker(this);
        finishBackgroundImage = toolkit.getImage("res/images/finishBackgroundImage.jpg");
        tracker.addImage(finishBackgroundImage, 0);

        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
        }

        endOfLevel = new EndOfLevel(finishBackgroundImage);
        cardLayout = new CardLayout();
        currentLevel.addOnLevelFinishedListener(this);
        currentLevel.addOnReturnListener(this);
        endOfLevel.addOnReturnListener(this);
        endOfLevel.addOnPlayAgainListener(this);
        setLayout(cardLayout);
        this.add(currentLevel, "CurrentLevel");
        this.add(endOfLevel, "EndLevel");
        cardLayout.show(this, "CurrentLevel");
    }

    /**
     * Funkcja odpowiadająca za wczytanie poziomu i ustawienie parametrów
     * początkowych gry
     *
     * @param level numer poziomu do wczytania
     */
    public void StartGame(int level) {
        currentLevel.loadLevel(level);
        currentLevel.StartGame();
    }

  /**
 * Dodanie obiektu implementującego interfejs OnReturnListener
 * @param onReturnListener obiekt implementujący interfejs OnReturnListener
 */
    public void addOnReturnListener(OnReturnListener onReturnListener) {
        this.onReturnListener = onReturnListener;
    }

    /**
     * Obsługa zdarzenia zakończenia rozgrywki
     */
    @Override
    public void OnLevelFinished() {
        int points = currentLevel.getPoints();
        endOfLevel.setPoints(points);
        cardLayout.show(this, "EndLevel");
    }

    /**
     * Obsługa zdarzenia powrotu do poprzedniego widoku
     */
    @Override
    public void onReturn() {
        onReturnListener.onReturn();
        cardLayout.show(this, "CurrentLevel");
    }

    /**
     * Obsługa zdarzenia ponownego rozpoczęcia gry.
     */
    @Override
    public void onPlayAgain() {
        cardLayout.show(this, "CurrentLevel");
        currentLevel.StartGame();
    }
}
