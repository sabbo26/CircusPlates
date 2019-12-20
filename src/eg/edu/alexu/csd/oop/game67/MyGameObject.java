package eg.edu.alexu.csd.oop.game67;

import eg.edu.alexu.csd.oop.game.GameObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyGameObject implements GameObject {
    private static final int MAX_MSTATE = 1;
    // an array of sprite images that are drawn sequentially
    private BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
    private int x;
    private int y;
    private boolean visible;
    private int type;
    private boolean horizontalOnly;
    private boolean isBomb = false;
    private GameObjectState catched ;
    private GameObjectState notCatched ;
    private GameObjectState currentState ;


    public void setCurrentState(GameObjectState currentState) {
        this.currentState = currentState;
    }

    public GameObjectState getCatched() {
        return catched;
    }

    public GameObjectState getNotCatched() {
        return notCatched;
    }

    public MyGameObject(int posX, int posY, boolean horizontalOnly, String path) {
        this(posX, posY, horizontalOnly, path, 0);
        catched = new Catched(this);
        notCatched = new NotCatched(this);
        currentState = notCatched ;
    }

    public MyGameObject(int posX, int posY, boolean horizontalOnly, String path, int type) {
        this.x = posX;
        this.y = posY;
        this.type = type;
        this.visible = true;
        this.horizontalOnly = horizontalOnly;
        // create a bunch of buffered images and place into an array, to be displayed sequentially
        try {
            spriteImages[0] = ImageIO.read(new File(path));
            if (path.equals("shapes\\bomb.png")){
                isBomb = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int mX) {
        this.x = mX;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int mY) {
        if (horizontalOnly || type == 1)
            return;
        this.y = mY;
    }

    @Override
    public BufferedImage[] getSpriteImages() {
        return spriteImages;
    }

    @Override
    public int getWidth() {
        return spriteImages[0].getWidth();
    }

    @Override
    public int getHeight() {
        return spriteImages[0].getHeight();
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isBomb() {
        return isBomb;
    }


    public void changeLocation (MyGameWorld gw){
        currentState.changeLocation(gw);
    }
}
