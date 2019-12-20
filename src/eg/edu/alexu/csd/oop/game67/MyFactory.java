package eg.edu.alexu.csd.oop.game67;

import eg.edu.alexu.csd.oop.game.GameObject;

public class MyFactory implements Factory {

    @Override
    public GameObject getGameObject(String[] path , int screenWidth, int screenHeight) {
        return new MyGameObject((int) (Math.random() * screenWidth), -1 * (int) (Math.random() * screenHeight), false, path[(int) (Math.random() * path.length - 1)]);
    }
}
