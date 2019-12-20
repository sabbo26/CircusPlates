package eg.edu.alexu.csd.oop.game67;
import eg.edu.alexu.csd.oop.game.GameObject;
import java.util.List;

public class Memento {
    private final int score;
    private final List<GameObject> control;
    private final int x;

    public Memento(int score, List<GameObject> control) {
        this.score = score;
        this.control = control;
        x = control.get(0).getX();
    }

    public int getX() {
        return x;
    }

    public List<GameObject> getControl() {
        return control;
    }

    public int getScore() {
        return score;
    }
}
