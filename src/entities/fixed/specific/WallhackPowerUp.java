package src.entities.fixed.specific;

import src.engine.physics.specific.NoRestrictions;
import src.engine.physics.specific.NoWall;
import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 01/11/2019.
 */
public class WallhackPowerUp extends PowerUp {

    public WallhackPowerUp() {
        super(10000.0d, 500);
    }

    @Override
    public void execute(Pacman pacman) {
        pacman.setActivePowerUp(this);
        pacman.setMovementRestrictions(new NoRestrictions());
    }

    @Override
    public void end(Pacman pacman) {
        pacman.setActivePowerUp(null);
        pacman.setMovementRestrictions(new NoWall());
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
