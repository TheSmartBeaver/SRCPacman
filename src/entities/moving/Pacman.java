package src.entities.moving;

public class Pacman extends MovingEntity{

    private boolean hasChangedDirection = false;

    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.PACMAN;
    }

    public Pacman(float posX, float posY, int length, float speed) {
        super(posX, posY, length, speed);
    }

    public boolean isHasChangedDirection() {
        return hasChangedDirection;
    }

    public void setHasChangedDirection(boolean hasChangedDirection) {
        this.hasChangedDirection = hasChangedDirection;
    }
}
