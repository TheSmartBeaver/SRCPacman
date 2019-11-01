package src.entities.space;

import src.entities.fixed.TileContent;

import java.awt.*;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Tile {

    private TileContent content = null;
    private Image sprite;
    private boolean isWall;
    private boolean isGhostSpawnTile;

    public Tile(TileContent content, boolean isWall, boolean isGhostSpawnTile) {
        if (isWall) assert !isGhostSpawnTile;
        if (content != null) assert !isWall && !isGhostSpawnTile;
        this.content = content;
        this.isWall = isWall;
        this.isGhostSpawnTile = isGhostSpawnTile;
    }

    @Override
    public String toString() {
        if (isGhostSpawnTile) {
            return "gho";
        }
        else if (content == null) {
            return isWall ? "wal" : "emp";
        } else {
            return content.toString();
        }
    }
}
