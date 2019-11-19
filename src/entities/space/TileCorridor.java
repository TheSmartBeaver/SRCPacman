package src.entities.space;

import src.entities.fixed.TileContent;

/**
 * Created by Vincent on 16/11/2019.
 */
public class TileCorridor extends Tile {
    /*Seul corridor peut avoir content*/

    public TileCorridor(TileContent tileContent) {
        super(tileContent, TileType.CORRIDOR);
    }
}
