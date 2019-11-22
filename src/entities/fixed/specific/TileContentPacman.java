package src.entities.fixed.specific;

import src.entities.fixed.generic.TileContent;
import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 22/11/2019.
 */
public abstract class TileContentPacman extends TileContent {

    private int score;
    public TileContentPacman(int score) {
        this.score = score;
    }

    public abstract void execute(Pacman pacman);

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
