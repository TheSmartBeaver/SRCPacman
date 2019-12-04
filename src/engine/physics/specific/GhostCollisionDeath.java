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
        System.out.println("Lose"); //TODO: Affichage de défaite, perte de vie et respawn à la case départ avec un temps d'invincibilité.
        (pacman).decLiveCount();
        pacman.setPosX(GameState.currentLevelPlayed.getPacmanXSpawn());
        pacman.setPosY(GameState.currentLevelPlayed.getPacmanYSpawn());
        pacman.setTileX(MovementPhysics.absoluteToRelativePosX(pacman.getPosX(),GameState.currentLevelPlayed.getTileWidth(),GameState.currentLevelPlayed.getLevelScreenOffsetLeft()));
        pacman.setTileY(MovementPhysics.absoluteToRelativePosY(pacman.getPosY(),GameState.currentLevelPlayed.getTileHeight(),GameState.currentLevelPlayed.getLevelScreenOffsetUp()));
        pacman.setInMiddleOfTile(true);
        pacman.setInput(Input.NONE);
        pacman.setCurrentDirection(Direction.NONE);
        PowerUp respawnInvincibility = new RespawnInvincibility();
        pacman.setActivePowerUp(respawnInvincibility);
        respawnInvincibility.execute(pacman);
    }
}
