package src.entities.fixed;

import src.entities.moving.Pacman;

public class Nothing extends TileContent {

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
