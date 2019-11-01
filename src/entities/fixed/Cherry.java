package src.entities.fixed;

import src.entities.moving.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Cherry extends Fruit implements TileContent {

    public Cherry() {
        super(100);
    }

    @Override
    public void execute(Pacman pacman) {
        //ajouter this.score au score de Pacman
        //pacman.addScore(super.getScore());
    }
}
