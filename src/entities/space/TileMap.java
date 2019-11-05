package src.entities.space;

/**
 * Created by Vincent on 01/11/2019.
 */
public class TileMap {

    private Tile[][] tiles;
    private int rowCount;
    private int columnCount;

    public TileMap(Tile[][] tiles, int rowCount, int columnCount) {
        this.tiles = tiles;
        this.rowCount = rowCount;
        this.columnCount = columnCount;
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

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Tile get(int i, int j) {
        return tiles[i][j];
    }
}
