package src.entities.space;

import src.entities.fixed.TileContent;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Tile {

    private TileContent content = null;
    private TileSprite sprite;
    private boolean isWall;
    private boolean isGhostSpawnTile;
    private boolean isPacmanSpawnTile;

    public Tile(TileContent content, boolean isWall, boolean isGhostSpawnTile, boolean isPacmanSpawnTile) {
        if (isWall) assert !isGhostSpawnTile && !isPacmanSpawnTile;
        if (content != null) assert !isWall && !isGhostSpawnTile;
        this.content = content;
        this.isWall = isWall;
        this.isGhostSpawnTile = isGhostSpawnTile;
        this.isPacmanSpawnTile = isPacmanSpawnTile;
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

    public TileContent getContent() {
        return content;
    }

    public boolean isWall() {
        return isWall;
    }

    public boolean isGhostSpawnTile() {
        return isGhostSpawnTile;
    }

    public boolean isPacmanSpawnTile() {
        return isPacmanSpawnTile;
    }

    public TileSprite getSprite() {
        return sprite;
    }

    public void setSprite(TileSprite sprite) {
        this.sprite = sprite;
    }
}
