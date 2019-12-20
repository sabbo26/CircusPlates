package eg.edu.alexu.csd.oop.game67;

public interface Observable {
    void add(Observer o);
    void notify(Object obj, String s);
}
