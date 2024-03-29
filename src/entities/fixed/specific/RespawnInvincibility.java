package src.entities.fixed.specific;

import src.engine.physics.specific.GhostCollisionDeath;
import src.engine.physics.specific.GhostCollisionNothing;
import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 03/12/2019.
 */
public class RespawnInvincibility extends PowerUp {

    public RespawnInvincibility() {
        super(5000.d, 0);
    }

    @Override
    public TileContentType getContentType() {
        return null;
    }

    @Override
    //TODO: changer �a c'est immonde (faire une file de collisions ?)
    public void end(Pacman pacman) {
        pacman.removeActivePowerUp(this);
        boolean isInvincible = false;
        for(PowerUp powerUp : pacman.getActivePowerUps()) {
            if (powerUp.getClass() == InvincibilityPowerUp.class) {
                isInvincible = true;
            }
        }
        if (!isInvincible) {
            pacman.setGhostCollision(new GhostCollisionDeath());
        }
    }

    @Override
    public void execute(Pacman pacman) {
        pacman.setGhostCollision(new GhostCollisionNothing());
    }
}
