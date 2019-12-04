package src.entities.fixed.specific;

import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 03/12/2019.
 */
public class InvincibilityPowerUp extends PowerUp {

    public InvincibilityPowerUp() {
        super(10000d, 500);
    }

    @Override
    public TileContentType getContentType() {
        return TileContentType.INVINCIBILITY;
    }

    @Override
    public void end(Pacman pacman) {

    }

    @Override
    public void execute(Pacman pacman) {

    }
}
