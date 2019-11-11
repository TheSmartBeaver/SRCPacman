package src;

import src.entities.moving.MovingEntity;

import java.util.List;

/**
 * Created by Vincent on 09/11/2019.
 */
public class GameState {

    public static GameView currentView;

    public static Level currentLevelPlayed;
    public static Integer levelStreak;
    //TODO : voir si le score n'est pas plutôt spécifique à un objet PacMan si on veut faire un mode compétitif, multijoueur...
    public static Integer score;
    public static GameDifficulty difficulty;
    public static List<MovingEntity> currentEntities;
}
