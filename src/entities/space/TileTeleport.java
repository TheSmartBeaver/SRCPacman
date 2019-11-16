package src.entities.space;

import src.entities.moving.Direction;

/**
 * Created by Vincent on 16/11/2019.
 */
public class TileTeleport extends Tile {

    private TileTeleport tileDest;

    public TileTeleport(TileTeleport tileDest) {
        super(null, TileType.TELEPORT);
        this.tileDest = tileDest;
    }

    public TileTeleport getTileDest() {
        return tileDest;
    }

    public void setTileDest(TileTeleport tileDest) {
        this.tileDest = tileDest;
    }
}
