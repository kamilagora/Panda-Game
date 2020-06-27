package panda.models;

import java.util.ArrayList;
/**
 * Model zadania
 * @author Kamila
 */
public class Task {
    /** pytanie */
    public String question;
    /** lista odpowiedzi */
    public ArrayList<String> answers;
    /** indeks poprawnej odpowiedzi w liście */
    public int correctAnswerIndex;
}
