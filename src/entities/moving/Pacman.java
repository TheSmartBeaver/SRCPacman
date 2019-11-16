package src.entities.moving;

public class Pacman extends MovingEntity{

    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.PACMAN;
    }

    public Pacman(float posX, float posY, int length, float speed) {
        super(posX, posY, length, speed);
    }
}
