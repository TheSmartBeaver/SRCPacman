package src.engine.graphics;

import src.Level;
import src.UserParams;
import src.entities.fixed.TileContentType;
import src.entities.moving.MovingEntity;
import src.entities.space.Tile;
import src.entities.space.TileMap;

import java.util.List;

/**
 * Created by Vincent on 05/11/2019.
 */
public class GameRenderer {

    //TODO : pour debug uniquement
    private static final Color wallColor = new Color(0.5f, 0.5f, 0.5f);
    private static final Color corridorColor = new Color(1.0f, 1.0f, 1.0f);
    private static final Color berryColor = new Color(1.0f, 0.65f, 0.0f);
    private static final Color invincibilityColor = new Color(1.0f, 0.0f, 0.0f);
    private static final Color ghostSpawnColor = new Color(0.0f, 0.2f, 0.7f);
    private static final Color pacmanSpawnColor = new Color(0.75f, 0.75f, 0.0f);


    public static void renderLevel(Level level, List<MovingEntity> entities) {

        /*System.out.println(tileHeight);
        System.out.println(tileWidth);
        System.out.println(levelScreenHeight);
        System.out.println(levelScreenOffsetUp);
        System.out.println(levelScreenOffsetDown);
        System.out.println(levelScreenWidth);
        System.out.println(levelScreenOffsetLeft);
        System.out.println(levelScreenOffsetRight);
        System.out.println();*/

        int levelScreenOffsetLeft = level.getLevelScreenOffsetLeft();
        int x = levelScreenOffsetLeft;
        int y = level.getLevelScreenOffsetUp();
        TileMap levelTileMap = level.getTileMap();
        int rowCount = levelTileMap.getRowCount();
        int columnCount = levelTileMap.getColumnCount();
        int tileWidth = level.getTileWidth();
        int tileHeight = level.getTileHeight();

        for (int rowIndex = 0 ; rowIndex < rowCount ; ++rowIndex) {
            for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnIndex) {
                Tile currentTile = levelTileMap.get(rowIndex, columnIndex);
                //TODO : voir si la technique avec le .ordinal() pose problème
                Sprite currentSprite = UserParams.texture.getSprites().get(currentTile.getSprite().ordinal());
                UserParams.texture.bind();
                Drawer.drawSprite(x, y, tileWidth, tileHeight, currentSprite.getxSprite(), currentSprite.getySprite());
                UserParams.texture.unbind();
                if (currentTile.getContent() != null) {
                    Sprite contentSprite = UserParams.texture.getSprites().get(currentTile.getContent().getSprite().ordinal());
                    UserParams.texture.bind();
                    Drawer.drawSprite(x, y, tileWidth, tileHeight, contentSprite.getxSprite(), contentSprite.getySprite());
                    UserParams.texture.unbind();
                }
                x += tileWidth;
            }
            x = levelScreenOffsetLeft;
            y += tileHeight;
        }


    }
}
