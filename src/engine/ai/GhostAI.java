package src.engine.ai;

import src.engine.input.Input;
import src.entities.moving.specific.Ghost;
import src.entities.space.generic.TileMap;

import java.util.ArrayDeque;
import java.util.List;

/**
 * Created by Vincent on 19/11/2019.
 */
public class GhostAI {

    private static void determineGhostPath(Ghost ghost, List<Input> possibleInputs) {
        //pas besoin de recalculer l'algo s'il n'y a qu'un seul chemin à prendre
        if (possibleInputs.size() == 1) {
            ArrayDeque<Input> nextMandatoryInput = new ArrayDeque<>();
            nextMandatoryInput.push(possibleInputs.get(0));
            ghost.setInputs(nextMandatoryInput);
        }
        //s'il y a plusieurs chemins à cette intersection, on refait l'algo
        else {
            ghost.getState().executeStrategy(ghost);
        }
    }

    public static void ghostAIStart(Ghost ghost, TileMap tileMap) {
        List<Input> possibleInputs = AIUtil.determinePossibleDirections(ghost, tileMap);
        if (possibleInputs.size() == 0) {
            AIUtil.reverseEntityDirection(ghost);
        }
        //si le fantôme est à une intersection
        else if (AIUtil.isEntityAtIntersection(ghost, tileMap)) {
            //si il a encore des inputs en mémoire, il va dans la direction adéquate
            if (!ghost.getInputs().isEmpty()) {
                ghost.setCurrentDirection(AIUtil.convertInputToDirection(ghost.getInputs().poll()));
            }
            //si sa liste d'inputs est vide, on la recalcule en réexécutant son algo
            else {
                determineGhostPath(ghost, possibleInputs);
                ghost.setCurrentDirection(AIUtil.convertInputToDirection(ghost.getInputs().poll()));
            }
        }
    }
}
