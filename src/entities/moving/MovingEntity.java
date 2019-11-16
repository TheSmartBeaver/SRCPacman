package src.entities.moving;

public abstract class MovingEntity {

    private float posX;
    private float posY;

    private int tileX;
    private int tileY;

    private int length;

    private Direction currentDirection = Direction.NONE;
    private Direction nextDirection = Direction.NONE;

    //en ms
    private float speed;
    public float tileTravelTime;

    private float nbPixelsMoved;
    private boolean inMiddleOfTile = true;

    public MovingEntity() {}

    public MovingEntity(float posX, float posY, int length, float speed) {
        this.posX = posX;
        this.posY = posY;
        this.length = length;
        this.speed = speed;
        tileTravelTime = 1000.0f / this.speed;
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

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public int getLength() {
        return length;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getNbPixelsMoved() {
        return nbPixelsMoved;
    }

    public void setNbPixelsMoved(float nbPixelsMoved) {
        this.nbPixelsMoved = nbPixelsMoved;
    }

    public boolean isInMiddleOfTile() {
        return inMiddleOfTile;
    }

    public void setInMiddleOfTile(boolean inMiddleOfTile) {
        this.inMiddleOfTile = inMiddleOfTile;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getNextDirection() {
        return nextDirection;
    }

    public void setNextDirection(Direction nextDirection) {
        this.nextDirection = nextDirection;
    }

    public abstract MovingEntityType getEntityType();
}
