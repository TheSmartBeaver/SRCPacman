package src.entities.moving.specific;

import src.entities.fixed.specific.PowerUp;

/**
 * Created by Vincent on 04/12/2019.
 */
public abstract class PacmanWithPowerUp implements SpecialPower {

    private Pacman pacman;
    private PowerUp powerUp;

    public PacmanWithPowerUp(Pacman pacman, PowerUp powerUp) {
        this.pacman = pacman;
        this.powerUp = powerUp;
    }

    public Pacman getPacman() {
        return pacman;
    }

    public void setPacman(Pacman pacman) {
        this.pacman = pacman;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }
}
