package src.engine.graphics;

import src.Level;
import src.ScreenParams;
import src.entities.space.TileMap;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

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

    private static void initialize() {
        minLevelScreenOffsetUp = ScreenParams.height / 16;
        minLevelScreenOffsetDown = ScreenParams.height / 64;
        minLevelScreenOffsetLeft = ScreenParams.width / 64;
        minLevelScreenOffsetRight = ScreenParams.width / 64;
        maxLevelScreenHeight = ScreenParams.height - minLevelScreenOffsetUp - minLevelScreenOffsetDown;
        maxLevelScreenWidth = ScreenParams.width - minLevelScreenOffsetLeft - minLevelScreenOffsetRight;

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
                System.out.println(x);
                System.out.println(y);
                System.out.println();
                if (levelTileMap.get(rowIndex, columnIndex).isWall()) {
                    glColor3f(0.5f,0.5f,0.5f);
                } else {
                    glColor3f(1.0f,1.0f,1.0f);
                }
                glBegin(GL_QUADS);
                glVertex2f(x, y);
                glVertex2f(x + tileWidth, y);
                glVertex2f(x + tileWidth, y + tileHeight);
                glVertex2f(x, y + tileHeight);
                glEnd();
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
        result += "Max Screen width : " + maxLevelScreenWidth + '\n';
        result += "Max Screen height : " + maxLevelScreenHeight + '\n';
        return result;
    }
}
