package src.engine.ai;

import src.engine.input.Input;
import src.entities.moving.specific.Ghost;
import src.entities.space.generic.TileMap;

import java.util.ArrayDeque;
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
        List<Input> possibleInputs = AIUtil.determinePossibleDirections(ghost, getTileMap());
        int nbPossibilities = possibleInputs.size();
        assert nbPossibilities >=2 && nbPossibilities <= 3; /*assert si = 2 ou 3*/
        Input nextInput = Input.NONE;

        /*Si nbPossibilité == 2 ou 3 on choisit aléatoirement l'une d'entre elle*/
        float probability = 1.0f / nbPossibilities;
        float rand = ThreadLocalRandom.current().nextFloat();
        if (nbPossibilities == 2) {
            if (rand < probability) {
                nextInput = possibleInputs.get(0);
            } else {
                nextInput = possibleInputs.get(1);
            }
        }
        else {
            if (rand < probability) {
                nextInput = possibleInputs.get(0);
            } else if (rand > probability && rand < 2*probability) {
                nextInput = possibleInputs.get(1);
            } else {
                nextInput = possibleInputs.get(2);
            }
        }
        ghost.setInput(nextInput); /*On met la prochaine direction retenu dans imput fantomes*/
    }
}
