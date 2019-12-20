package eg.edu.alexu.csd.oop.game67;

import eg.edu.alexu.csd.oop.game.GameObject;

import java.util.LinkedList;
import java.util.List;

public class flyweightGObject {
    private int max_size = 20;
    private int count = 0;
    private static flyweightGObject instance;
    private String path[];
    private List<GameObject> moving = new LinkedList<>();
    private MyFactory factory = new MyFactory();

    private flyweightGObject() {

    }

    public static flyweightGObject getInstance(){
        if(instance==null){
            instance=new flyweightGObject();
        }
        return instance;
    }
    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public List<GameObject> check_add(List<GameObject> moving, int screenHeight, int screenWidth) {
        this.moving = moving;
        if(this.moving.size()>max_size)
        {}
        else if (this.moving.size() == max_size) {
            for (int i = 0; i < moving.size(); i++) {
                if (moving.get(i).getY() == screenHeight) {
                    GameObject game_object=  moving.remove(i);
                    moving.add(game_object);

                }
            }

        } else if (moving.size() < max_size) {
            for (int i = 0; i < max_size - moving.size(); i++) {
                moving.add(factory.getGameObject(path,screenWidth,screenHeight));

            }
        }

        return moving;
    }

}

