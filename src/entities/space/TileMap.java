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
        for (int rowIndex = 0 ; rowIndex < rowCount ; ++rowIndex) {
            for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnIndex) {
                if (tiles[rowIndex][columnIndex].isWall()) {
                    //TODO : faire les if pour les sprites qui ont le plus de murs adjacents en premier (ordre décroissant de nombre de murs par sprite)
                    if (columnIndex != 0 && columnIndex != columnCount - 1 && tiles[rowIndex][columnIndex-1].isWall() && tiles[rowIndex][columnIndex+1].isWall() && tiles[rowIndex][columnIndex].getSprite() == null) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.HORIZONTAL_WALL);
                    }
                    if (rowIndex != 0 && rowIndex != rowCount - 1 && tiles[rowIndex-1][columnIndex].isWall() && tiles[rowIndex+1][columnIndex].isWall() && tiles[rowIndex][columnIndex].getSprite() == null){
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.VERTICAL_WALL);
                    }
                    if (rowIndex != 0 && columnIndex != columnCount - 1 && tiles[rowIndex-1][columnIndex].isWall() && tiles[rowIndex][columnIndex+1].isWall() && tiles[rowIndex][columnIndex].getSprite() == null) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.BOT_LEFT_CORNER_WALL);
                    }
                    if (rowIndex != 0 && columnIndex != 0 && tiles[rowIndex-1][columnIndex].isWall() && tiles[rowIndex][columnIndex-1].isWall() && tiles[rowIndex][columnIndex].getSprite() == null) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.BOT_RIGHT_CORNER_WALL);
                    }
                    if (rowIndex != rowCount - 1 && columnIndex != 0 && tiles[rowIndex+1][columnIndex].isWall() && tiles[rowIndex][columnIndex-1].isWall() && tiles[rowIndex][columnIndex].getSprite() == null) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.TOP_RIGHT_CORNER_WALL);
                    }
                    if (rowIndex != rowCount - 1 && columnIndex != columnCount - 1 && tiles[rowIndex+1][columnIndex].isWall() && tiles[rowIndex][columnIndex+1].isWall() && tiles[rowIndex][columnIndex].getSprite() == null) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.TOP_LEFT_CORNER_WALL);
                    }
                    if (tiles[rowIndex][columnIndex].getSprite() == null)
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.ROCK);

                }
                else {
                    tiles[rowIndex][columnIndex].setSprite(TileSprite.GRASS);
                }
            }
        }
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
