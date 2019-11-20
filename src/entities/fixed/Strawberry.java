package src.entities.fixed;

import src.entities.moving.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Strawberry extends TileContent {

    public Strawberry() {
        super(300);
    }

    @Override
    public TileContentType getContentType() {
        return TileContentType.STRAWBERRY;
    }

    @Override
    public void execute(Pacman pacman) {
        pacman.addScore(getScore());
    }
}
