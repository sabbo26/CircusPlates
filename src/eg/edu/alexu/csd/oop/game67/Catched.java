package eg.edu.alexu.csd.oop.game67;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.List;

public class Catched implements GameObjectState {

    MyGameObject go ;

    public Catched(MyGameObject go) {
        this.go = go;
    }

    @Override
    public void changeLocation(MyGameWorld gw) {
        List<GameObject> temp = gw.getControl();
        if (temp.size() > 1) {
            go.setY(temp.get(0).getY() - ((gw.getControl().size() - 1) * gw.getControl().get(1).getHeight() + 40));
            go.setX(temp.get(0).getX());
        }
    }
}
