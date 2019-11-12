package src;

import src.engine.graphics.Texture;

/**
 * Created by Vincent on 05/11/2019.
 */
public class UserParams {

    public static int resolutionScale; /*Car on aura pas besoin d'aussi grosse résolution ?? */
    public static int screenWidth;
    public static int screenHeight;

    public static String userDir;

    //TODO : voir si on garantit pas un certain nombre de pixels minimum pour les offsets si jamais on veut afficher
    //TODO : des infos à gauche, à droite et en bas (vérifier par ex que minLevelScreenOffsetUp >= 50 pour afficher score et vies)
    public static int minLevelScreenOffsetUp = UserParams.screenHeight / 16;
    public static int minLevelScreenOffsetDown = UserParams.screenHeight / 64;
    public static int minLevelScreenOffsetLeft = UserParams.screenWidth / 64;
    public static int minLevelScreenOffsetRight = UserParams.screenWidth / 64;

    public static int maxLevelScreenHeight  = UserParams.screenHeight - minLevelScreenOffsetUp - minLevelScreenOffsetDown;
    public static int maxLevelScreenWidth  = UserParams.screenWidth - minLevelScreenOffsetLeft - minLevelScreenOffsetRight;

    public static Texture texture = new Texture(UserParams.userDir + "/assets/textures/textures_test.png", 8);

    public static String dump() {
        //TODO : enrichir pour tous les autres paramètres
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
