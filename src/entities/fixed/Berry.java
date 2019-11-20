package src.entities.fixed;

import src.entities.moving.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Berry extends TileContent {

    public Berry() {
        super(10);
    }

    @Override
    public void execute(Pacman pacman) {
        pacman.addScore(getScore());
    }

    @Override
    public TileContentType getContentType() {
        return TileContentType.BERRY;
    }

    @Override
    public String toString() {
        return "ber";
    }
}
