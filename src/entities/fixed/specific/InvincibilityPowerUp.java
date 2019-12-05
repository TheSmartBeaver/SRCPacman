package src.entities.fixed.specific;

import src.GameState;
import src.engine.physics.specific.GhostCollisionDeath;
import src.engine.physics.specific.GhostCollisionInvincible;
import src.entities.moving.generic.MovingEntity;
import src.entities.moving.specific.*;

import java.util.ArrayList;
import java.util.List;

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
        pacman.removeActivePowerUp(this);
        pacman.setGhostCollision(new GhostCollisionDeath());
        for (MovingEntity entity : GameState.currentEntities) {
            if(entity.getEntityType() == MovingEntityType.GHOST) {
                //TODO : mettre un libellé dans la classe State
                if (((Ghost)entity).getState().getClass() == GhostStateFleeing.class) {
                    ((Ghost)entity).setState(new GhostStateNormal(((Ghost)entity).getMovingStrategy()));
                }
            }
        }
    }

    @Override
    public void execute(Pacman pacman) {
        pacman.setActivePowerUp(this);
        pacman.setGhostCollision(new GhostCollisionInvincible());
        for (MovingEntity entity : GameState.currentEntities) {
            if(entity.getEntityType() == MovingEntityType.GHOST) {
                ((Ghost)entity).setState(new GhostStateFleeing(GameState.currentLevelPlayed.getTileMap()));
            }
        }
    }
}
