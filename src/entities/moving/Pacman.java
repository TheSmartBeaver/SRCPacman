package src.entities.moving;

public class Pacman extends MovingEntity{
    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.PACMAN;
    }
}
