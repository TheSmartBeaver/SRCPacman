package src.entities.fixed;

import src.entities.moving.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class InvincibilityPowerUp extends TileContent {

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
