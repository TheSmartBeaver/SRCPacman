package src.entities.space;

import src.entities.fixed.TileContent;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Tile {

    private TileContent content = null;
    private TileSprite sprite;
    private TileType tileType;

    public Tile(TileContent content, TileType tileType) {
        this.content = content;
        this.tileType = tileType;
    }

    @Override
    public String toString() {
        if (tileType == TileType.GHOST_SPAWN) {
            return "gho";
        }
        else if (content == null) {
            return tileType == TileType.WALL ? "wal" : "emp";
        } else {
            return content.toString();
        }
    }

    public TileContent getContent() {
        return content;
    }

    public TileSprite getSprite() {
        return sprite;
    }

    public void setSprite(TileSprite sprite) {
        this.sprite = sprite;
    }

    public boolean isWall() {
        return tileType == TileType.WALL;
    }

    public boolean isPacmanSpawnTile() {
        return tileType == TileType.PACMAN_SPAWN;
    }

    public boolean isGhostSpawnTile() {
        return tileType == TileType.GHOST_SPAWN;
    }
}
