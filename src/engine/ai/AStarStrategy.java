package src.engine.ai;

import src.engine.ai.util.AStar;
import src.engine.input.Input;
import src.entities.moving.specific.Ghost;

import java.util.List;

import static java.lang.System.exit;

public class AStarStrategy extends MovingStrategy{


    public AStarStrategy(int[][] tilesForA) {
        super(tilesForA);
    }

    @Override
    public void computeInputList(Ghost ghost) {
        Input nextInput = Input.NONE;
        int xGhost = ghost.getTileX(), yGhost = ghost.getTileY();
        int xPacman= ghost.getPacman().getTileX(), yPacman = ghost.getPacman().getTileY();
        AStar ah = new AStar(tilesForA, xGhost, yGhost, false);
        List<AStar.Node> path = ah.findPathTo(xPacman,yPacman);
        if (path != null) {
            path.forEach((n) -> {
                System.out.print("[" + n.x + ", " + n.y + "] ");
                tilesForA[n.y][n.x] = -1; /* -1 représente un bout de chemin*/
            });

        }
        /*DEBUT DEBUG A* */
        //AStar.printPath(tilesForA);
        /*FIN DEBUG A* */

        //TODO: Décider que faire véritablement quand il croise pacman
        if(path == null || path.size()==1) {
            return;
        }

        int nextX = path.get(1).x, nextY = path.get(1).y;

        if(nextX+1 == xGhost){
            nextInput = Input.LEFT;
            //System.out.println("A* indique GAUCHE");
        }
        if(nextX-1 == xGhost){
            nextInput = Input.RIGHT;
            //System.out.println("A* indique DROITE");
        }
        if(nextY == yGhost-1){
            nextInput = Input.UP;
            //System.out.println("A* indique HAUT");
        }
        if(nextY == yGhost+1){
            nextInput = Input.DOWN;
            //System.out.println("A* indique BAS");
        }



        /*print debug*/
        //System.out.println(xGhost+"-"+yGhost);
        //System.out.println("pos fantome = toujours -1 :"+tilesForA[xGhost][yGhost]+" gauche = "+tilesForA[xGhost][yGhost+1]+" "+"droite = "+tilesForA[xGhost][yGhost-1]+" "+"bas = "+tilesForA[xGhost+1][yGhost]+" "+"haut = "+tilesForA[xGhost-1][yGhost]+" ");

        if (nextInput == Input.NONE){
            //System.out.println("AHHH INPUT = NONE");
            exit(1);
        }

        ghost.setInput(nextInput); /*On met la prochaine direction retenu dans imput fantomes*/


        /* On nettoie le chemin*/
        if (path != null) {
            path.forEach((n) -> {
                System.out.print("[" + n.x + ", " + n.y + "] ");
                tilesForA[n.y][n.x] = 0;
            });

            /*On affiche chemin nettoyé*/
            //AStar.printPath(tilesForA);

        }
    }
}
