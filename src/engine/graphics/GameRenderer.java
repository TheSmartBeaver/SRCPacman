package src.engine.graphics;

import src.Level;
import src.UserParams;
import src.entities.moving.generic.MovingEntity;
import src.entities.moving.specific.MovingEntityType;
import src.entities.space.generic.Tile;
import src.entities.space.generic.TileMap;
import src.entities.space.specific.TileTeleport;

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
    private static final Color debugRectColor = new Color(1.0f, 1.0f, 1.0f);


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

        int levelScreenOffsetLeft = level.getLevelScreenOffsetLeft(); /*Coin gauche du level?*/
        int x = levelScreenOffsetLeft;
        int y = level.getLevelScreenOffsetUp(); /*y haut du level*/
        TileMap levelTileMap = level.getTileMap();
        int rowCount = levelTileMap.getRowCount(); /*nb lignes level*/
        int columnCount = levelTileMap.getColumnCount();
        int tileWidth = level.getTileWidth(); /*largeur tuile/case*/
        int tileHeight = level.getTileHeight();

        for (int rowIndex = 0 ; rowIndex < rowCount ; ++rowIndex) { /*On parcourt toutes les lignes, colonnes de TileMap*/
            for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnIndex) {
                Tile currentTile = levelTileMap.get(rowIndex, columnIndex); /*case en cours de traitement*/
                //TODO : voir si la technique avec le .ordinal() pose probl�me
                Sprite currentSprite = UserParams.texture.getSprites().get(currentTile.getSprite().ordinal()); /*récup sprite correspondant au contenu de la la Tile*/
                if (currentTile.isTeleportTile()) {
                    Drawer.drawRect(x, y, tileWidth, tileHeight, ((TileTeleport)currentTile).getColor());
                }
                else { /*Si pas case téléportation, bind sprite correspondante*/
                    UserParams.texture.bind();
                    Drawer.drawSprite(x, y, tileWidth, tileHeight, currentSprite.getxSprite(), currentSprite.getySprite());
                    UserParams.texture.unbind();
                }

                /*Test si Tile contient entités(fruits) et dessine*/
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

        /*MOVING entities*/
        int movingEntitySpriteLength = (int)(tileWidth * 0.75); /*Taille du carré PACMAN*/
        for (MovingEntity entity : entities) { /*On dessine toutes les entités*/
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                //Drawer.drawRect((int)entity.getPosX() - movingEntitySpriteLength / 2, (int)entity.getPosY() - movingEntitySpriteLength / 2, movingEntitySpriteLength, movingEntitySpriteLength, pacmanSpawnColor);
                Drawer.drawPacmanSprite(entity, movingEntitySpriteLength, level);
            } else if (entity.getEntityType() == MovingEntityType.GHOST) {
                Drawer.drawRect((int)entity.getPosX() - movingEntitySpriteLength / 2, (int)entity.getPosY() - movingEntitySpriteLength / 2, movingEntitySpriteLength, movingEntitySpriteLength, invincibilityColor);
            }
            /*UserParams.texture.bind();
            Drawer.debugDrawPoint((int)entity.getPosX(), (int)entity.getPosY(), new Color(1.0f, 1.0f, 1.0f));
            UserParams.texture.unbind();
            int xRectDebug = level.getLevelScreenOffsetLeft() + entity.getTileX() * tileWidth;
            int yRectDebug = level.getLevelScreenOffsetUp() + entity.getTileY() * tileHeight;
            UserParams.texture.bind();
            Drawer.drawRect(xRectDebug, yRectDebug, tileWidth, tileHeight, debugRectColor);
            UserParams.texture.unbind();*/
        }
    }
}
