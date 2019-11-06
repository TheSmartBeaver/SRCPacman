package src.engine.graphics;

import src.Level;
import src.UserParams;
import src.entities.fixed.TileContentType;
import src.entities.space.Tile;
import src.entities.space.TileMap;

/**
 * Created by Vincent on 05/11/2019.
 */
public class LevelRenderer {

    private static boolean isInitialized = false;
    //TODO : voir si on garantit pas un certain nombre de pixels minimum pour les offsets si jamais on veut afficher
    //TODO : des infos à gauche, à droite et en bas (vérifier par ex que minLevelScreenOffsetUp >= 50 pour afficher score et vies)
    private static int minLevelScreenOffsetUp;
    private static int minLevelScreenOffsetDown ;
    private static int minLevelScreenOffsetLeft ;
    private static int minLevelScreenOffsetRight;

    private static int maxLevelScreenHeight;
    private static int maxLevelScreenWidth;

    private static Texture texture;

    //TODO : remplacer les couleurs par des sprites ? (pour certains objets)
    private static final Color wallColor = new Color(0.5f, 0.5f, 0.5f);
    private static final Color corridorColor = new Color(1.0f, 1.0f, 1.0f);
    private static final Color berryColor = new Color(1.0f, 0.65f, 0.0f);
    private static final Color invincibilityColor = new Color(1.0f, 0.0f, 0.0f);
    private static final Color ghostSpawnColor = new Color(0.0f, 0.2f, 0.7f);
    private static final Color pacmanSpawnColor = new Color(0.75f, 0.75f, 0.0f);

    private static void initialize() {
        minLevelScreenOffsetUp = UserParams.screenHeight / 16;
        minLevelScreenOffsetDown = UserParams.screenHeight / 64;
        minLevelScreenOffsetLeft = UserParams.screenWidth / 64;
        minLevelScreenOffsetRight = UserParams.screenWidth / 64;
        maxLevelScreenHeight = UserParams.screenHeight - minLevelScreenOffsetUp - minLevelScreenOffsetDown;
        maxLevelScreenWidth = UserParams.screenWidth - minLevelScreenOffsetLeft - minLevelScreenOffsetRight;

        texture = new Texture(UserParams.userDir + "/assets/textures/textures_test.png", 8);
        System.out.println(texture.getWidth());
        System.out.println(texture.getHeight());

        isInitialized = true;
    }

    public static void renderLevel(Level level) {
        if (!isInitialized) {
            initialize();
        }

        int levelScreenOffsetUp = minLevelScreenOffsetUp;
        int levelScreenOffsetDown = minLevelScreenOffsetDown;
        int levelScreenOffsetLeft = minLevelScreenOffsetLeft;
        int levelScreenOffsetRight = minLevelScreenOffsetRight;
        int levelScreenHeight = maxLevelScreenHeight;
        int levelScreenWidth = maxLevelScreenWidth;

        TileMap levelTileMap = level.getTileMap();

        int rowCount = levelTileMap.getRowCount();
        int columnCount = levelTileMap.getColumnCount();

        int tileWidth = maxLevelScreenWidth / columnCount;
        int tileHeight = maxLevelScreenHeight / rowCount;

        if (tileWidth < tileHeight) {
            tileHeight = tileWidth;
            levelScreenHeight = tileHeight * rowCount;
            //TODO: vérif si il ne faut pas quand même que le offsetDown soit plus petit que le offsetUp
            levelScreenOffsetUp = (maxLevelScreenHeight - levelScreenHeight) / 2;
            levelScreenOffsetDown = (maxLevelScreenHeight - levelScreenHeight) / 2;
        } else if (tileHeight < tileWidth) {
            tileWidth = tileHeight;
            levelScreenWidth = tileWidth * columnCount;
            levelScreenOffsetLeft = (maxLevelScreenWidth - levelScreenWidth) / 2;
            levelScreenOffsetRight = (maxLevelScreenWidth - levelScreenWidth) / 2;
        }

        /*System.out.println(tileHeight);
        System.out.println(tileWidth);
        System.out.println(levelScreenHeight);
        System.out.println(levelScreenOffsetUp);
        System.out.println(levelScreenOffsetDown);
        System.out.println(levelScreenWidth);
        System.out.println(levelScreenOffsetLeft);
        System.out.println(levelScreenOffsetRight);
        System.out.println();*/

        int x = levelScreenOffsetLeft;
        int y = levelScreenOffsetUp;

        for (int rowIndex = 0 ; rowIndex < rowCount ; ++rowIndex) {
            for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnIndex) {
                Tile currentTile = levelTileMap.get(rowIndex, columnIndex);
                //TODO : voir si la technique avec le .ordinal() pose problème
                Sprite currentSprite = texture.getSprites().get(currentTile.getSprite().ordinal());
                texture.bind();
                Drawer.drawSprite(x, y, tileWidth, tileHeight, currentSprite.getxSprite(), currentSprite.getySprite());

                //TODO : supprimer le if suivant quand on aura des sprites pour les fruits / powerups
                if (currentTile.getContent() != null) {
                    if (currentTile.getContent().getContentType() == TileContentType.BERRY) {
                        Drawer.drawCircle(x + tileWidth / 2, y + tileHeight / 2, 5, 20, berryColor);
                    }
                    else if (currentTile.getContent().getContentType() == TileContentType.INVINCIBILITY) {
                        Drawer.drawCircle(x + tileWidth / 2, y + tileHeight / 2, 8, 20, invincibilityColor);
                    }
                }
                texture.unbind();
                x += tileWidth;
            }
            x = levelScreenOffsetLeft;
            y += tileHeight;
        }
    }

    public static String dump() {
        String result = "";
        result += "Min Offset up : " + minLevelScreenOffsetUp + '\n';
        result += "Min Offset down : " + minLevelScreenOffsetDown + '\n';
        result += "Min Offset left : " + minLevelScreenOffsetLeft + '\n';
        result += "Min Offset right : " + minLevelScreenOffsetRight + '\n';
        result += "Max Screen screenWidth : " + maxLevelScreenWidth + '\n';
        result += "Max Screen screenHeight : " + maxLevelScreenHeight + '\n';
        return result;
    }
}
