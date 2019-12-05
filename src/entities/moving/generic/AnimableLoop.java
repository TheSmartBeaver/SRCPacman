package src.entities.moving.generic;

/**
 * Created by Vincent on 04/12/2019.
 */
public class AnimableLoop {

    private int nbAnimations;
    private int msLoop;
    private int msPerAnimation;

    public AnimableLoop(int nbAnimations, int msLoop) {
        this.nbAnimations = nbAnimations;
        this.msLoop = msLoop;
        this.msPerAnimation = msLoop / nbAnimations;
    }
}
