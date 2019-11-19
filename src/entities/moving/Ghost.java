package src.entities.moving;

import src.engine.input.Input;
import src.engine.physics.MovingStrategy;
import src.engine.physics.MovingStrategyContext;

import java.util.ArrayDeque;

/**
 * Created by Vincent on 18/11/2019.
 */
public class Ghost extends MovingEntity {

    private Pacman pacman;
    private ArrayDeque<Input> inputs; /*Toujours de taille 1 ??*/

    private MovingStrategyContext context; /*Stratégie du fantôme*/

    public Ghost(float speed, Pacman pacman, MovingStrategy movingStrategy) {
        super(speed); /*VITESSE*/
        this.pacman = pacman;
        this.context = new MovingStrategyContext(movingStrategy, this);
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

    public MovingStrategyContext getContext() {
        return context;
    }

    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.GHOST;
    }
}
