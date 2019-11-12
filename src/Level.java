package src;

import src.entities.moving.MovingEntity;
import src.entities.space.TileMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Level {

    private TileMap tileMap;
    private int numeroLevel;

    private int levelScreenOffsetUp;
    private int levelScreenOffsetDown;
    private int levelScreenOffsetLeft;
    private int levelScreenOffsetRight;
    private int levelScreenHeight;
    private int levelScreenWidth;

    private int tileWidth;
    private int tileHeight;

    public Level(TileMap tileMap, int numeroLevel) {
        this.tileMap = tileMap;
        this.numeroLevel = numeroLevel;

        levelScreenOffsetUp = UserParams.minLevelScreenOffsetUp;
        levelScreenOffsetDown = UserParams.minLevelScreenOffsetDown;
        levelScreenOffsetLeft = UserParams.minLevelScreenOffsetLeft;
        levelScreenOffsetRight = UserParams.minLevelScreenOffsetRight;
        levelScreenHeight = UserParams.maxLevelScreenHeight;
        levelScreenWidth = UserParams.maxLevelScreenWidth;

        tileWidth = UserParams.maxLevelScreenWidth / tileMap.getColumnCount();
        tileHeight = UserParams.maxLevelScreenHeight / tileMap.getRowCount();

        if (tileWidth < tileHeight) {
            tileHeight = tileWidth;
            levelScreenHeight = tileHeight * tileMap.getRowCount();
            //TODO: vérif si il ne faut pas quand même que le offsetDown soit plus petit que le offsetUp
            levelScreenOffsetUp = (UserParams.maxLevelScreenHeight - levelScreenHeight) / 2;
            levelScreenOffsetDown = (UserParams.maxLevelScreenHeight - levelScreenHeight) / 2;
        } else if (tileHeight < tileWidth) {
            tileWidth = tileHeight;
            levelScreenWidth = tileWidth * tileMap.getColumnCount();
            levelScreenOffsetLeft = (UserParams.maxLevelScreenWidth - levelScreenWidth) / 2;
            levelScreenOffsetRight = (UserParams.maxLevelScreenWidth - levelScreenWidth) / 2;
        }
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public int getNumeroLevel() {
        return numeroLevel;
    }

    public int getLevelScreenOffsetUp() {
        return levelScreenOffsetUp;
    }

    public int getLevelScreenOffsetLeft() {
        return levelScreenOffsetLeft;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
}
