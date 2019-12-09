package src.entities.space.generic;

import src.Pair;

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

    public Pair findTilePos(Tile tile) { /*Renvoie pos de la tile en argument*/
        for (int i = 0 ; i < rowCount ; ++i) {
            for (int j = 0 ; j < columnCount ; ++j) {
                if (get(i,j) == tile) {
                    return new Pair(i,j);
                }
            }
        }
        return null;
    }
}
