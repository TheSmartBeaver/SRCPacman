package src.engine.physics;

import src.engine.input.Input;
import src.entities.moving.Ghost;
import src.entities.space.TileMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Vincent on 18/11/2019.
 */
public class MovingRandom extends MovingStrategy {

    public MovingRandom(TileMap tileMap) {
        super(tileMap);
    }

    @Override
    public void computeInputList(Ghost ghost) {
        assert ghost.getInputs().isEmpty();
        List<Input> possibleInputs = MovementPhysics.determinePossibleDirections(ghost, getTileMap());
        int nbPossibilities = possibleInputs.size();
        assert nbPossibilities >=2 && nbPossibilities <= 3;
        ArrayDeque<Input> nextInput = new ArrayDeque<>();
        /*if (nbPossibilities == 1) {
            nextInput.add(possibleInputs.get(0));
            ghost.setInputs(nextInput);
            return;
        }*/
        float probability = 1.0f / nbPossibilities;
        float rand = ThreadLocalRandom.current().nextFloat();
        if (nbPossibilities == 2) {
            if (rand < probability) {
                nextInput.push(possibleInputs.get(0));
            } else {
                nextInput.push(possibleInputs.get(1));
            }
        }
        else {
            if (rand < probability) {
                nextInput.push(possibleInputs.get(0));
            } else if (rand > probability && rand < 2*probability) {
                nextInput.push(possibleInputs.get(1));
            } else {
                nextInput.push(possibleInputs.get(2));
            }
        }
        ghost.setInputs(nextInput);
    }
}
