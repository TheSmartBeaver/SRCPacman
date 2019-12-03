package src.entities.moving.specific;

import src.engine.physics.specific.MovementRestrictions;
import src.engine.physics.specific.NoWall;
import src.entities.fixed.specific.PowerUp;
import src.entities.moving.generic.MovingEntity;

public class Pacman extends MovingEntity {

    int id;
    private int score = 0;
    private int liveCount;

    private MovementRestrictions movementRestrictions;
    private PowerUp activePowerUp = null;

    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.PACMAN;
    }

    public Pacman(int id, float speed) {
        super(speed);
        this.id = id;
        movementRestrictions = new NoWall();
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

    public void incLiveCount() {
        ++liveCount;
    }

    public void decLiveCount() {
        --liveCount;
    }

    public MovementRestrictions getMovementRestrictions() {
        return movementRestrictions;
    }

    public void setMovementRestrictions(MovementRestrictions movementRestrictions) {
        this.movementRestrictions = movementRestrictions;
    }

    public PowerUp getActivePowerUp() {
        return activePowerUp;
    }

    public void setActivePowerUp(PowerUp activePowerUp) {
        this.activePowerUp = activePowerUp;
    }
}
