package src.entities.fixed;

import src.entities.moving.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class InvincibilityPowerUp implements TileContent {
    @Override
    public void execute(Pacman pacman) {
        //rendre Pacman invincible
    }

    @Override
    public String toString() {
        return "inv";
    }
}
