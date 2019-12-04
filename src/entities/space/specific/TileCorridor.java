package src.entities.space.specific;

import src.entities.fixed.generic.TileContent;
import src.entities.space.generic.Tile;

/**
 * Created by Vincent on 16/11/2019.
 */
public class TileCorridor extends Tile {
    /*Seul corridor peut avoir content*/

    public TileCorridor(TileContent tileContent) {
        super(tileContent, TileType.CORRIDOR);
    }
}
