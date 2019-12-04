package src.engine.physics.specific;

import src.GameState;
import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.GhostStateReturningHome;
import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 03/12/2019.
 */
public class GhostCollisionInvincible implements GhostCollision {
    @Override
    public void ghostCollision(Pacman pacman, Ghost ghost) {
        ghost.setState(new GhostStateReturningHome(GameState.currentLevelPlayed.getTileMap()));
    }
}
