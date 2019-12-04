package src.engine.graphics;

import src.GameMain;
import src.GameTextures;
import src.Level;
import src.engine.graphics.generic.Color;
import src.engine.graphics.generic.Sprite;
import src.engine.graphics.generic.TextSlick2D;
import src.entities.moving.generic.MovingEntity;
import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.MovingEntityType;
import src.entities.moving.specific.Pacman;
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
                Sprite currentSprite = GameTextures.fixedEntitiesTexture.getSprites().get(currentTile.getSprite().ordinal()); /*récup sprite correspondant au contenu de la la Tile*/
                if (currentTile.isTeleportTile()) {
                    Drawer.drawRect(x, y, tileWidth, tileHeight, ((TileTeleport)currentTile).getColor());
                }
                else { /*Si pas case téléportation, bind sprite correspondante*/
                    GameTextures.fixedEntitiesTexture.bind();
                    Drawer.drawSprite(x, y, tileWidth, tileHeight, currentSprite.getxSprite(), currentSprite.getySprite());
                    GameTextures.fixedEntitiesTexture.unbind();
                }

                /*Test si Tile contient entités(fruits) et dessine*/
                if (currentTile.getContent() != null) {
                    //System.out.println("Type CONTENT : "+currentTile.getContent().getContentType());
                    Sprite contentSprite = GameTextures.fixedEntitiesTexture.getSprites().get(currentTile.getContent().getSprite().ordinal());
                    GameTextures.fixedEntitiesTexture.bind();
                    Drawer.drawSprite(x, y, tileWidth, tileHeight, contentSprite.getxSprite(), contentSprite.getySprite());
                    GameTextures.fixedEntitiesTexture.unbind();
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
                Drawer.drawPacmanSprite(entity, level);
            } else if (entity.getEntityType() == MovingEntityType.GHOST) {
                Drawer.drawRect((int)entity.getPosX() - movingEntitySpriteLength / 2, (int)entity.getPosY() - movingEntitySpriteLength / 2, movingEntitySpriteLength, movingEntitySpriteLength, invincibilityColor);
                Ghost aGhost = (Ghost) entity;
                if(aGhost.isAChaser)
                    Drawer.drawRect((int)entity.getPosX() - movingEntitySpriteLength / 2, (int)entity.getPosY() - movingEntitySpriteLength / 2, movingEntitySpriteLength, movingEntitySpriteLength, debugRectColor);
            }
            /*UserParams.fixedEntitiesTexture.bind();
            Drawer.debugDrawPoint((int)entity.getPosX(), (int)entity.getPosY(), new Color(1.0f, 1.0f, 1.0f));
            UserParams.fixedEntitiesTexture.unbind();
            int xRectDebug = level.getLevelScreenOffsetLeft() + entity.getTileX() * tileWidth;
            int yRectDebug = level.getLevelScreenOffsetUp() + entity.getTileY() * tileHeight;
            UserParams.fixedEntitiesTexture.bind();
            Drawer.drawRect(xRectDebug, yRectDebug, tileWidth, tileHeight, debugRectColor);
            UserParams.fixedEntitiesTexture.unbind();*/
        }
        /*On dessine les scores*/
        for (MovingEntity entity : entities) {
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                Pacman pac = (Pacman) entity;
                Drawer.drawPacmanScore(pac.getId(),pac.getScore());
            }
        }
        TextSlick2D.drawText(0,0,"nb berry restant : "+ level.getNbBerryForWin(), org.newdawn.slick.Color.green);
    }
}
