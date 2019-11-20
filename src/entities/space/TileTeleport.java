package src.entities.space;

import src.engine.graphics.Color;
import src.entities.fixed.TileContent;
import src.entities.fixed.TileContentType;

/**
 * Created by Vincent on 16/11/2019.
 */
public class TileTeleport extends Tile {

    private TileTeleport tileDest;
    private Color color;

    public TileTeleport(TileTeleport tileDest, Color color) {
        super(null, TileType.TELEPORT);
        this.tileDest = tileDest;
        this.color = color;
    }

    public TileTeleport getTileDest() {
        return tileDest;
    }

    public void setTileDest(TileTeleport tileDest) {
        this.tileDest = tileDest;
    }

    public Color getColor() {
        return color;
    }
}
