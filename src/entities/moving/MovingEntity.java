package src.entities.moving;

import src.engine.input.Input;

import java.util.ArrayList;
import java.util.List;

public abstract class MovingEntity {

    private float posX = 0;
    private float posY = 0;

    private int tileX;
    private int tileY;

    //TODO : attribut à supprimer, il sert juste à débug
    private int length = 20;

    private Direction currentDirection = Direction.NONE;

    //en ms
    private float speed;
    private boolean isMoving = false;
    public float tileTravelTime;

    private float nbPixelsMoved;
    private boolean inMiddleOfTile = true;

    private Input input;

    public MovingEntity() {}

    //CONSTRUCTEUR DE DEBUG
    public MovingEntity(float posX, float posY, int length, float speed) {
        this.posX = posX;
        this.posY = posY;
        this.length = length;
        this.speed = speed;
    }

    //CONSTRUCTEUR OFFICIEL
    public MovingEntity(float speed) {
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

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public static List<Pacman> findPacmanEntities(List<MovingEntity> entities) {
        List<Pacman> pacmans = new ArrayList<>();
        for (MovingEntity entity : entities) {
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                pacmans.add((Pacman)entity);
            }
        }
        return pacmans;
    }

    public abstract MovingEntityType getEntityType();
}
