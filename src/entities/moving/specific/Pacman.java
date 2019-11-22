package src.entities.moving.specific;

import src.entities.moving.generic.MovingEntity;

public class Pacman extends MovingEntity {

    int id;
    private int score = 0;

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

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
