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
    public static int minLevelScreenOffsetUp;
    public static int minLevelScreenOffsetDown;
    public static int minLevelScreenOffsetLeft;
    public static int minLevelScreenOffsetRight;

    public static int maxLevelScreenHeight;
    public static int maxLevelScreenWidth;

    public static Texture texture;

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
