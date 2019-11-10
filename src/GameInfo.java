package src;

import src.entities.moving.MovingEntity;

import java.util.List;

/**
 * Created by Vincent on 09/11/2019.
 */
public class GameInfo {

    private Level currentLevelPlayed;
    private Integer levelStreak;
    //TODO : voir si le score n'est pas plutôt spécifique à un objet PacMan si on veut faire un mode compétitif, multijoueur...
    private Integer score;
    private static GameDifficulty difficulty;
    private List<MovingEntity> currentEntities;

}
