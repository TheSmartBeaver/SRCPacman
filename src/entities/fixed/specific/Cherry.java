package src.entities.fixed.specific;

import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Cherry extends TileContentPacman {

    public Cherry() {
        super(300);
    }

    @Override
    public TileContentType getContentType() {
        return TileContentType.CHERRY;
    }

    @Override
    public void execute(Pacman pacman) {
        pacman.addScore(getScore());
    }
}
