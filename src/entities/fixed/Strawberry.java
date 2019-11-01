package src.entities.fixed;

import src.entities.moving.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Strawberry extends Fruit implements TileContent {

    public Strawberry() {
        super(300);
    }

    @Override
    public void execute(Pacman pacman) {
        //ajouter this.score au score de Pacman
        //pacman.addScore(super.getScore());
    }
}
