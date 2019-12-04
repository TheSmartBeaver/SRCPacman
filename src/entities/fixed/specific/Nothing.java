package src.entities.fixed.specific;

import src.entities.moving.specific.Pacman;

public class Nothing extends TileContentPacman {

    public Nothing() {
        super(0);
    }

    @Override
    public TileContentType getContentType() {
        return TileContentType.NOTHING;
    }

    @Override
    public void execute(Pacman pacman) {

    }
}
