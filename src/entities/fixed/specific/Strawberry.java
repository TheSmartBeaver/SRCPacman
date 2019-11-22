package src.entities.fixed.specific;

import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Strawberry extends TileContentPacman {

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
