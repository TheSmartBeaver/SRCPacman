package src;

/**
 * Created by Vincent on 02/11/2019.
 */
public class SquareTest {

    private float posX;
    private float posY;
    private int length;

    private int speed;

    public SquareTest() {
    }

    public SquareTest(float posX, float posY, int length) {
        this.posX = posX;
        this.posY = posY;
        this.length = length;
        speed = 100;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public int getLength() {
        return length;
    }

    public int getSpeed() {
        return speed;
    }
}
