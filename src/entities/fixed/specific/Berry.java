package src.entities.fixed.specific;

import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Berry extends TileContentPacman {

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
