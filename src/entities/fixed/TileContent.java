package src.entities.fixed;

import src.entities.moving.Pacman;
import src.entities.space.TileSprite;

/**
 * Created by Vincent on 01/11/2019.
 */
public abstract class TileContent {

    private int score;
    private TileSprite sprite;

    public TileContent(int score) {
        this.score = score;
    }

    public abstract TileContentType getContentType();
    public abstract void execute(Pacman pacman);

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public TileSprite getSprite() {
        return sprite;
    }

    public void setSprite(TileSprite sprite) {
        this.sprite = sprite;
    }
}
