package eg.edu.alexu.csd.oop.game67;

import eg.edu.alexu.csd.oop.game.GameObject;

public interface Factory {
    GameObject getGameObject (String[] path, int screenWidth, int screenHeight);
}
