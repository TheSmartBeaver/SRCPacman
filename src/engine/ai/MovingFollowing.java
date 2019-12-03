package src.engine.ai;

import src.engine.input.Input;
import src.entities.moving.specific.Ghost;
import src.entities.space.generic.TileMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class MovingFollowing extends MovingStrategy {

    public MovingFollowing(TileMap tileMap) {
        super(tileMap);
    }

    @Override
    public void computeInputList(Ghost ghost) {
        int pacPosX = ghost.getPacman().getTileX();
        int pacPosY = ghost.getPacman().getTileY();
        TileScore finalTile;
        Boolean skip;

        List<Input> possibleInputs = AIUtil.determinePossibleDirections(ghost, getTileMap());
        int nbPossibilities = possibleInputs.size();

        /*test implémentation de l'algorithme A*/
        List<TileScore> open = new ArrayList<>();
        List<TileScore> close = new ArrayList<>();
        TileScore q = new TileScore(ghost.getTileX(),ghost.getTileY());
        int temp = 0;
        q.setScore(0);
        open.add(q);
        while(!open.isEmpty()){
            q = open.get(0);
            for(int i = 0; i<open.size(); ++i){
                if(q.getScore() > open.get(i).getScore()) {
                    q = open.get(i);
                    temp = i;
                }
            }
            open.remove(temp);
            List<TileScore> possiblesTiles = AIUtil.determinePossibleTiles(ghost, getTileMap());
            for (TileScore possibleTile : possiblesTiles){
                skip = false;
                if(possibleTile.getPosX() == pacPosX && possibleTile.getPosY() == pacPosY){
                    finalTile = close.get(1);
                    for(int i = 1; i<close.size(); ++i){
                        if(finalTile.getScore() > close.get(i).getScore()) {
                            finalTile = close.get(i);
                        }
                    }
                    if(ghost.getTileY() == finalTile.getPosY() - 1)
                        ghost.setInput(Input.DOWN);
                    else if(ghost.getTileY() == finalTile.getPosY() + 1)
                        ghost.setInput(Input.UP);
                    else if(ghost.getTileX() == finalTile.getPosX() - 1)
                        ghost.setInput(Input.RIGHT);
                    else if(ghost.getTileX() == finalTile.getPosX() + 1)
                        ghost.setInput(Input.LEFT);
                    return;
                }
                possibleTile.setScore((int) Math.pow(possibleTile.getPosX() - pacPosX + possibleTile.getPosY() - pacPosY, 2) + q.getScore());
                for(int i = 0; i<open.size(); ++i){
                    if(possibleTile.getPosX().equals(open.get(i).getPosX()) && possibleTile.getPosY().equals(open.get(i).getPosY()) && possibleTile.getScore() > open.get(i).getScore()) {
                        skip = true;
                        break;
                    }
                }
                if(skip){
                    continue;
                }
                for(int i = 0; i<close.size(); ++i){
                    if(possibleTile.getPosX().equals(open.get(i).getPosX()) && possibleTile.getPosY().equals(open.get(i).getPosY()) && possibleTile.getScore() > close.get(i).getScore()) {
                        skip = true;
                        break;
                    }
                }
                if(skip){
                    continue;
                }
                open.add(possibleTile);
            }
            close.add(q);
        }

    }
}

