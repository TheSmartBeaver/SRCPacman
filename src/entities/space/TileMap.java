package src.entities.space;

/**
 * Created by Vincent on 01/11/2019.
 */
public class TileMap {

    private Tile[][] tiles;

    public TileMap(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public void loadSprites() {
        //algo pour déterminer quel sprite pour les murs (en fonction de la configuration des murs)
    }

    @Override
    public String toString() {
        String result = "";
        for (Tile[] line : tiles) {
            for (Tile tile : line) {
                result += tile + " ";
            }
            result += '\n';
        }
        return result;
    }
}
