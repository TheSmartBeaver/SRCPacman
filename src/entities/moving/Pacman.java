package src.entities.moving;

public class Pacman extends MovingEntity{

    int id;

    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.PACMAN;
    }

    public Pacman(int id, float speed) {
        super(speed);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
