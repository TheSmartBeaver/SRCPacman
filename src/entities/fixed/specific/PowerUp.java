package src.entities.fixed.specific;

import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 26/11/2019.
 */
public abstract class PowerUp extends TileContentPacman {

    private double durationMS;
    public PowerUp(double duration, int score) {
        super(score);
        this.durationMS = duration;
    }

    public abstract void end(Pacman pacman);

    public void decDurationMS(double timeElapsed) {
        durationMS -= timeElapsed;
    }

    public boolean isFinished() {
        return durationMS <= 0;
    }

}
