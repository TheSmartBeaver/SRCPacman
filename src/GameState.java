package src;

import src.entities.moving.generic.MovingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 09/11/2019.
 */
public class GameState {

    public static GameView currentView;

    public static Level currentLevelPlayed;
    public static Integer levelStreak;
    //TODO : voir si le score n'est pas plut�t sp�cifique � un objet PacMan si on veut faire un mode comp�titif, multijoueur...
    public static Integer score;
    public static Integer nbJoueurs;
    public static GameDifficulty difficulty;
    public static List<MovingEntity> currentEntities = new ArrayList<>();
}
