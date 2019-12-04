package src.entities.moving.specific;

import src.entities.fixed.specific.InvincibilityPowerUp;
import src.entities.fixed.specific.PowerUp;

/**
 * Created by Vincent on 04/12/2019.
 */
public class PacmanInvincible extends PacmanWithPowerUp {

    public PacmanInvincible(Pacman pacman) {
        super(pacman, new InvincibilityPowerUp());
    }

    @Override
    public void execute() {
        getPowerUp().execute(getPacman());
    }

    @Override
    public void end() {
        getPacman().end();
        if (getPowerUp().isFinished()) {
            getPowerUp().end(getPacman());
        }
    }
}
