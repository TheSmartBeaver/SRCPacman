package src.entities.moving.specific;

import src.engine.physics.specific.GhostCollision;
import src.engine.physics.specific.GhostCollisionDeath;
import src.engine.physics.specific.MovementRestrictions;
import src.engine.physics.specific.NoWall;
import src.entities.fixed.specific.PowerUp;
import src.entities.moving.generic.MovingEntity;

import java.util.ArrayList;
import java.util.List;

public class Pacman extends MovingEntity implements SpecialPower{

    int id;
    private int score = 0;
    private int liveCount;
    private boolean alive = true;

    private GhostCollision ghostCollision = new GhostCollisionDeath();
    private MovementRestrictions movementRestrictions;

    private List<PowerUp> activePowerUps = new ArrayList<>();

    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.PACMAN;
    }

    public Pacman(int id, float speed) {
        super(speed);
        this.id = id;
        movementRestrictions = new NoWall();
        this.liveCount = 3;
    }

    public boolean isAlive() {
        return alive;
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

    public int getLiveCount() {
        return liveCount;
    }

    public void incLiveCount() {
        ++liveCount;
    }

    public void decLiveCount() {
        --liveCount;
    }

    public GhostCollision getGhostCollision() {
        return ghostCollision;
    }

    public void setGhostCollision(GhostCollision ghostCollision) {
        this.ghostCollision = ghostCollision;
    }

    public MovementRestrictions getMovementRestrictions() {
        return movementRestrictions;
    }

    public void setMovementRestrictions(MovementRestrictions movementRestrictions) {
        this.movementRestrictions = movementRestrictions;
    }

    public List<PowerUp> getActivePowerUps() {
        return activePowerUps;
    }

    public void setActivePowerUp(PowerUp activePowerUp) {
        for (int i=0;i<this.activePowerUps.size();i++)
        {
            if (this.activePowerUps.get(i).getClass()==activePowerUp.getClass())
            {
                this.activePowerUps.set(i,activePowerUp);
                return;
            }
        }
        this.activePowerUps.add(activePowerUp);

    }

    public void removeActivePowerUp(PowerUp activePowerUp) {
        for (int i=0;i<this.activePowerUps.size();i++)
        {
            if (this.activePowerUps.get(i).getClass()==activePowerUp.getClass())
            {
                this.activePowerUps.remove(i);
                break;
            }
        }
    }

    public void resetPowerUps() {
        List<PowerUp> tmpPower = new ArrayList<>(activePowerUps);
        for (PowerUp powerUp : tmpPower) {
            powerUp.end(this);
        }
        activePowerUps = new ArrayList<>();
    }

    @Override
    public void execute() {

    }

    @Override
    public void end() {

    }
}
