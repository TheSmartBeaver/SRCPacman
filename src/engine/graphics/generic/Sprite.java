package src.engine.graphics.generic;

/**
 * Created by Vincent on 06/11/2019.
 */
public class Sprite {

    private int xSprite;
    private int ySprite;

    /*On donne coordonnées orthonormées du png de la sprite*/
    public Sprite(int xSprite, int ySprite) {
        this.xSprite = xSprite;
        this.ySprite = ySprite;
    }

    public int getxSprite() {
        return xSprite;
    }

    public int getySprite() {
        return ySprite;
    }

    @Override
    public String toString() {
        return "Sprite{" +
                "xSprite=" + xSprite +
                ", ySprite=" + ySprite +
                '}';
    }
}
