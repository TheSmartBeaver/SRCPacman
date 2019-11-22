package src.entities.fixed.specific;

import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class InvincibilityPowerUp extends TileContentPacman {

    public InvincibilityPowerUp() {
        super(500);
    }

    @Override
    public void execute(Pacman pacman) {
        //rendre Pacman invincible
    }

    @Override
    public TileContentType getContentType() {
        return TileContentType.INVINCIBILITY;
    }

    @Override
    public String toString() {
        return "inv";
    }
}
