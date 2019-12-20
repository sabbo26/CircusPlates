package eg.edu.alexu.csd.oop.game67;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class MyGameWorld implements World, Observable {
    private final List<GameObject> constant = new LinkedList<>();
    private List<Observer> listobservers = new LinkedList<>();
    private List<Memento> UndoList = new LinkedList<>();
    private List<GameObject> moving = new LinkedList<>();
    private List<GameObject> control = new LinkedList<>();
    private int score;
    private final int width;
    private final int height;
    private flyweightGObject fwo;
    private int CounterUndo = -1;
    private int saved;
    private logger l = logger.getInstance();
    private  int speed;


    public List<GameObject> getMoving() {
        return moving;
    }

    public List<GameObject> getControl() {
        return control;
    }

    public MyGameWorld(int screenWidth, int screenHeight, int level, String p, int speed) {
        fwo = flyweightGObject.getInstance();
        File f = new File("shapes");
        fwo.setPath(f.list());
        Vector<String> s = new Vector<>();
        for( int i = 0 ; i < fwo.getPath().length ; i++){
            if ((level == 1 && (fwo.getPath()[i].equals("bomb.png") || fwo.getPath()[i].equals("orange.png")) ) || (level == 2 && fwo.getPath()[i].equals("bomb.png"))  ){
                continue;
            }
            else {
                s.add("shapes\\" + fwo.getPath()[i]);
            }
        }
        String[] tmp = new String[s.size()];
        s.copyInto(tmp);
        fwo.setPath(tmp);
        width = screenWidth;
        this.speed=speed;
        height = screenHeight;
        MyGameObject oo = new MyGameObject(0, 0, true, p);
        constant.add(oo);
        // control objects (hero)
        control.add(new MyGameObject(screenWidth / 3, (int) (screenHeight * .8), true, "res\\mickey.png"));
        List<GameObject> cp;
        cp = copyList(control);
        UndoList.add(new Memento(score, cp));
        CounterUndo++;
        add(l);
    }

    @Override
    public List<GameObject> getConstantObjects() {
        return constant;
    }

    @Override
    public List<GameObject> getMovableObjects() {
        return moving;
    }

    @Override
    public List<GameObject> getControlableObjects() {
        return control;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    private boolean intersect(GameObject o1, GameObject o2) {
        return (Math.abs((o1.getX() + o1.getWidth() / 2) - (o2.getX() + o2.getWidth() / 2)) <= o1.getWidth()) && (Math.abs((o1.getY() + o1.getHeight() / 2) - (o2.getY() + o2.getHeight() / 2)) <= o1.getHeight());
    }

    @Override
    public boolean refresh() {
        if (control.size() != saved) {
            List<GameObject> cp;
            cp = copyList(control);
            UndoList.add(new Memento(score, cp));
            CounterUndo++;
        }
        moving = fwo.check_add(moving, height, width);
        GameObject clown = control.get(0);
        MyGameObject m;
        iterator Controllit = new iterator(control);
        iterator Movinglit = new iterator(moving);
        if (control.get(control.size() - 1).getY() <= 0) {
            while (Controllit.hasNext()) {
                ((MyGameObject) Controllit.next()).setVisible(false);
            }
            notify(null, "lose");
            return false;
        }

        while (Movinglit.hasNext()) {
            m = (MyGameObject) Movinglit.next();
            m.changeLocation(this);
            if (m.isBomb() && m.getY() == control.get(control.size() - 1).getY() - 100 && m.getX() > clown.getX() -20  && m.getX() < clown.getX() + clown.getWidth() - 20) {
                notify(null, "warn");
            }
            if (m.isBomb() && intersect(m, control.get(control.size() - 1))) {
                while (Controllit.hasNext()) {
                    ((MyGameObject) Controllit.next()).setVisible(false);
                }
                notify(null, "lose");
                return false;
            }
            if (intersect(m, control.get(control.size() - 1))) {
                notify("", null);
                if (CounterUndo > 0 && CounterUndo < UndoList.size() - 1) {
                    for (int i = CounterUndo; i < UndoList.size() - 1; i++) {
                        UndoList.remove(i);
                    }
                    CounterUndo = UndoList.size() - 1;
                }
                control.add(m);
                m.setCurrentState(m.getCatched());
                if (m.isBomb() && control.size() == 2) {
                    while (Controllit.hasNext()) {
                        ((MyGameObject) Controllit.next()).setVisible(false);
                    }
                    notify("", "lose");
                    return false;
                }
                m.setY(clown.getY() - (control.size() - 1) * control.get(1).getHeight() + 30);
                m.setX(clown.getX());
                m.setType(1);
                List<GameObject> cp;
                cp = copyList(control);
                UndoList.add(new Memento(score, cp));
                CounterUndo++;
            }
        }
        iterator Controllit1 = new iterator(control);
        while (Controllit1.hasNext()) {
            moving.remove(Controllit1.next());
        }
        int matchedObjects = 1;
        if (control.size() > 3) {
            for (int i = control.size() - 1; i > control.size() - 3; i--) {
                BufferedImage img1 = control.get(i).getSpriteImages()[0];
                BufferedImage img2 = control.get(i - 1).getSpriteImages()[0];
                if (bufferedImagesEqual(img1, img2)) {
                    matchedObjects++;
                }
            }
        }
        if (matchedObjects == 3) {
            UndoList.remove(UndoList.size() - 1);
            GameObject b = control.get(control.size() - 1);
            control.remove(control.size() - 1);
            List<GameObject> cp;
            cp = copyList(control);
            UndoList.add(new Memento(score, cp));
            control.add(b);
            int size = control.size();
            score++;
            notify(score, null);
            for (int i = size - 1; i > size - 4; i--) {
                MyGameObject c = (MyGameObject) control.get(i);
                c.setCurrentState(c.getNotCatched());
                control.remove(i);
                fwo.check_add(moving, height, width);
            }
            List<GameObject> cpp;
            cpp = copyList(control);
            UndoList.add(new Memento(score, cpp));
            CounterUndo++;
        }
        iterator Controllit2 = new iterator(control);
        Controllit2.next();
        MyGameObject b;
        while (Controllit2.hasNext()) {
            b = (MyGameObject) Controllit2.next();
            b.changeLocation(this);
        }
        saved = control.size();
        return true;
    }

    public void undo() {
        if (CounterUndo < 0) {
            return;
        }
        control.clear();
        List<GameObject> cp = UndoList.get(CounterUndo).getControl();
        iterator<GameObject> cpit = new iterator(cp);
        while (cpit.hasNext()) {
            GameObject b = cpit.next();
            b.setX(UndoList.get(CounterUndo).getX());
            control.add(b);
        }
        score = UndoList.get(CounterUndo).getScore();
        CounterUndo--;
    }

    public void redo() {
        if (CounterUndo >= UndoList.size() - 2) {
            return;
        }
        control.clear();
        List<GameObject> cp = UndoList.get(++CounterUndo).getControl();
        iterator<GameObject> cpit = new iterator(cp);
        while (cpit.hasNext()) {
            GameObject b = cpit.next();
            b.setX(UndoList.get(CounterUndo).getX());
            control.add(b);
        }
        score = UndoList.get(CounterUndo).getScore();
    }

    @Override
    public String getStatus() {
        return "Use Arrows to move | Score : " + score;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public int getControlSpeed() {
        return 10;
    }

    private boolean bufferedImagesEqual(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y))
                        return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private List<GameObject> copyList(List<GameObject> list) {
        iterator<GameObject> listit = new iterator<GameObject>(list);
        List<GameObject> cpd = new LinkedList<GameObject>();
        while (listit.hasNext()) {
            cpd.add(listit.next());
        }
        return cpd;
    }

    public void add(Observer o) {
        listobservers.add(o);
    }

    public void notify(Object obj, String s) {
        iterator<Observer> obsit = new iterator<Observer>(listobservers);
        while (obsit.hasNext()){
            obsit.next().update(obj,s);
        }
    }
}
 
