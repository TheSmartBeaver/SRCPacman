package src.entities.fixed;

import src.entities.moving.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Cherry extends TileContent {

    public Cherry() {
        super(100);
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
