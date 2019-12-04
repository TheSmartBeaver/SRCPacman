package src.engine.ai.util;

import src.GameState;
import src.engine.ai.MovingStrategy;
import src.engine.input.Input;
import src.engine.physics.generic.MovementPhysics;
import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.GhostState;
import src.entities.moving.specific.GhostStateNormal;
import src.entities.space.generic.Tile;
import src.entities.space.generic.TileMap;
import src.entities.space.specific.TilesForA;

import java.util.List;

import static java.lang.System.exit;

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
        System.out.println("pos fantôme RETURN" + xGhost + " " + yGhost);
        AStar ah = new AStar(tilesForA, xGhost, yGhost, false);

        System.out.println("AHHHHHHHH " + tileGhostSpawnX + " " + tileGhostSpawnY);
        List<AStar.Node> path = ah.findPathTo(tileGhostSpawnX, tileGhostSpawnY);
        if (path != null) {
            path.forEach((n) -> {
                System.out.print("RETURN [" + n.x + ", " + n.y + "] ");
                tilesForA[n.y][n.x] = -1; /* -1 représente un bout de chemin*/
            });

        }
        /*DEBUT DEBUG A* */
        AStar.printPath(tilesForA);
        //exit(1);
        /*FIN DEBUG A* */

        //TODO: Décider que faire véritablement quand il croise pacman
        System.out.println("DAMN "+ghost.getTileX()+" "+tileGhostSpawnX+" "+ghost.getTileY()+" "+tileGhostSpawnY);
        if (ghost.getTileX() == tileGhostSpawnX-1 && ghost.getTileY() == tileGhostSpawnY) {
            ghost.setState(new GhostStateNormal(ghost.getMovingStrategy()));
            ghost.setInput(Input.UP);
            System.out.println("sortit avant destination ? spawn fantôme");

            if (path != null) {
                path.forEach((n) -> {
                    System.out.print("[" + n.x + ", " + n.y + "] ");
                    tilesForA[n.y][n.x] = 0;
                });
            }

            return;
        }


        System.out.println(" RETURN Prochaine étape " + path.get(1).x + "--" + path.get(1).y);
        int nextX = path.get(1).x, nextY = path.get(1).y;

        if (nextX + 1 == xGhost) {
            nextInput = Input.LEFT;
            System.out.println("RETURN A* indique GAUCHE");
        }
        if (nextX - 1 == xGhost) {
            nextInput = Input.RIGHT;
            System.out.println("RETURN A* indique DROITE");
        }
        if (nextY == yGhost - 1) {
            nextInput = Input.UP;
            System.out.println("RETURN A* indique HAUT");
        }
        if (nextY == yGhost + 1) {
            nextInput = Input.DOWN;
            System.out.println("RETURN A* indique BAS");
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
