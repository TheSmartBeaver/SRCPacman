package src.engine.physics.specific;

import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.Pacman;

/**
 * Created by Vincent on 03/12/2019.
 */
public interface GhostCollision {

    public void ghostCollision(Pacman pacman, Ghost ghost);

}
