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
        pacman.removeActivePowerUp(this);
        pacman.setMovementRestrictions(new NoWall());
        //TODO : check si Pacman peut se décoincer s'il est dans un mur, sinon le tp sur sa case départ avec un temps d'invincibilité
    }

    @Override
    public TileContentType getContentType() {
        return TileContentType.WALLHACK;
    }

    @Override
    public String toString() {
        return "WallHack";
    }
}
