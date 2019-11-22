package src.entities.moving.specific;

import src.engine.input.Input;
import src.engine.ai.MovingStrategy;
import src.entities.moving.generic.MovingEntity;

import java.util.ArrayDeque;

/**
 * Created by Vincent on 18/11/2019.
 */
public class Ghost extends MovingEntity {

    private Pacman pacman;
    private ArrayDeque<Input> inputs; /*Toujours de taille 1 ??*/

    private MovingStrategy movingStrategy; /*Stratégie du fantôme*/
    private GhostState state;

    public Ghost(float speed, Pacman pacman, MovingStrategy movingStrategy) {
        super(speed); /*VITESSE*/
        this.pacman = pacman;
        this.movingStrategy = movingStrategy;
        //state = new GhostStateWaiting(movingStrategy.getTileMap());
        state = new GhostStateNormal(movingStrategy);
        setMoving(true);
    }

    public Pacman getPacman() { /*Le Pacman qu'il cible ?*/
        return pacman;
    }

    public ArrayDeque<Input> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayDeque<Input> inputs) {
        this.inputs = inputs;
    }

    public MovingStrategy getMovingStrategy() {
        return movingStrategy;
    }

    public GhostState getState() {
        return state;
    }

    public void setState(GhostState state) {
        this.state = state;
    }

    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.GHOST;
    }
}
