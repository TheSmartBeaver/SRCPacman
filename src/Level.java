package src;

import src.entities.moving.MovingEntity;
import src.entities.space.TileMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Level {

    private TileMap tileMap;
    private int numeroLevel;

    //ou un HashSet ?
    private List<MovingEntity> entities = new ArrayList<>();

    public Level(TileMap tileMap, int numeroLevel) {
        this.tileMap = tileMap;
        this.numeroLevel = numeroLevel;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public int getNumeroLevel() {
        return numeroLevel;
    }
}
