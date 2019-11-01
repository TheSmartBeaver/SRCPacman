package src.entities.fixed;

/**
 * Created by Vincent on 01/11/2019.
 */
public abstract class Fruit {

    private int score;

    public Fruit(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
