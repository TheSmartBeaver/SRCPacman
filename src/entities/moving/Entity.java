package src.entities.moving;

public abstract class Entity {

    private float posX;
    private float posY;

    private int tileX;
    private int tileY;

    private int length;
    private float speed;

    public Entity() {}

    public Entity(float posX, float posY, int length, float speed) {
        this.posX = posX;
        this.posY = posY;
        this.length = length;
        this.speed = speed;
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

    public float getSpeed() {
        return speed;
    }

    public abstract EntityType getEntityType();
}
