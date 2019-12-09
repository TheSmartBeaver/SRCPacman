package src.engine.ai;

import src.GameState;
import src.engine.ai.util.AStar;
import src.engine.input.Input;
import src.engine.physics.generic.MovementPhysics;
import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.GhostStateNormal;

import java.util.List;

public class MovingReturningHome extends MovingStrategy {

    private int tileGhostSpawnX;
    private int tileGhostSpawnY;


    public MovingReturningHome(int[][] tilesForA) {
        super(tilesForA);

        tileGhostSpawnX = MovementPhysics.absoluteToRelativePosX(GameState.currentLevelPlayed.getGhostXSpawn(), GameState.currentLevelPlayed.getTileWidth(), GameState.currentLevelPlayed.getLevelScreenOffsetLeft());
        tileGhostSpawnY = MovementPhysics.absoluteToRelativePosY(GameState.currentLevelPlayed.getGhostYSpawn(), GameState.currentLevelPlayed.getTileHeight(), GameState.currentLevelPlayed.getLevelScreenOffsetUp());

    }

    @Override
    public void computeInputList(Ghost ghost) {
        Input nextInput = Input.NONE;
        int xGhost = ghost.getTileX(), yGhost = ghost.getTileY();
        AStar ah = new AStar(tilesForA, xGhost, yGhost, false);

        List<AStar.Node> path = ah.findPathTo(tileGhostSpawnX, tileGhostSpawnY);
        if (path != null) {
            path.forEach((n) -> {
                //System.out.print("RETURN [" + n.x + ", " + n.y + "] ");
                tilesForA[n.y][n.x] = -1; /* -1 représente un bout de chemin*/
            });

        }
        /*DEBUT DEBUG A* */
        AStar.printPath(tilesForA);
        //exit(1);
        /*FIN DEBUG A* */

        //TODO: Ceci est immonde, voir si on change
        if (ghost.getTileX() == tileGhostSpawnX-1 && ghost.getTileY() == tileGhostSpawnY) {
            ghost.setState(new GhostStateNormal(ghost.getMovingStrategy()));
            ghost.setInput(Input.UP);

            if (path != null) {
                path.forEach((n) -> {
                    System.out.print("[" + n.x + ", " + n.y + "] ");
                    tilesForA[n.y][n.x] = 0;
                });
            }

            return;
        }

        int nextX = path.get(1).x, nextY = path.get(1).y;

        if (nextX + 1 == xGhost) {
            nextInput = Input.LEFT;
            //System.out.println("RETURN A* indique GAUCHE");
        }
        if (nextX - 1 == xGhost) {
            nextInput = Input.RIGHT;
            //System.out.println("RETURN A* indique DROITE");
        }
        if (nextY == yGhost - 1) {
            nextInput = Input.UP;
            //System.out.println("RETURN A* indique HAUT");
        }
        if (nextY == yGhost + 1) {
            nextInput = Input.DOWN;
            //System.out.println("RETURN A* indique BAS");
        }

        ghost.setInput(nextInput);

        /*nettoie chemin*/
        if (path != null) {
            path.forEach((n) -> {
                System.out.print("[" + n.x + ", " + n.y + "] ");
                tilesForA[n.y][n.x] = 0;
            });
        }


    }
}
