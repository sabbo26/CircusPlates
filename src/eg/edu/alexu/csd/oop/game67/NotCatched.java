package eg.edu.alexu.csd.oop.game67;

public class NotCatched implements GameObjectState {

    MyGameObject go ;

    public NotCatched(MyGameObject go) {
        this.go = go;
    }

    @Override
    public void changeLocation(MyGameWorld gw) {
        go.setY((go.getY() + 1));
        if (go.getY() == gw.getHeight()) {
            go.setY(-1 * (int) (Math.random() * gw.getHeight()));
            go.setX((int) (Math.random() * gw.getWidth()));
        }
        go.setX(go.getX() + (Math.random() > 0.5 ? 1 : -1));
    }
}
