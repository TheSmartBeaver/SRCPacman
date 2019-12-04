package src.engine.physics.specific;

import src.GameState;
import src.engine.input.Input;
import src.engine.physics.generic.MovementPhysics;
import src.entities.fixed.specific.PowerUp;
import src.entities.fixed.specific.RespawnInvincibility;
import src.entities.moving.generic.Direction;
import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 03/12/2019.
 */
public class GhostCollisionDeath implements GhostCollision {
    @Override
    public void ghostCollision(Pacman pacman, Ghost ghost) {
        System.out.println("Lose"); //TODO: Affichage de d�faite, perte de vie et respawn � la case d�part avec un temps d'invincibilit�.
        (pacman).decLiveCount();
        pacman.setPosX(GameState.currentLevelPlayed.getPacmanXSpawn());
        pacman.setPosY(GameState.currentLevelPlayed.getPacmanYSpawn());
        pacman.setTileX(MovementPhysics.absoluteToRelativePosX(pacman.getPosX(),GameState.currentLevelPlayed.getTileWidth(),GameState.currentLevelPlayed.getLevelScreenOffsetLeft()));
        pacman.setTileY(MovementPhysics.absoluteToRelativePosY(pacman.getPosY(),GameState.currentLevelPlayed.getTileHeight(),GameState.currentLevelPlayed.getLevelScreenOffsetUp()));
        pacman.setInMiddleOfTile(true);
        pacman.setNbPixelsMoved(0);
        pacman.setInput(Input.NONE);
        pacman.setCurrentDirection(Direction.NONE);
        pacman.resetPowerUps();
        PowerUp respawnInvincibility = new RespawnInvincibility();
        pacman.setActivePowerUp(respawnInvincibility);
        System.out.println(pacman.getActivePowerUps().size());
        respawnInvincibility.execute(pacman);
    }
}
