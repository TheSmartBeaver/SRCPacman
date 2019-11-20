package src.entities.space;

import src.Pair;
import src.entities.fixed.TileContentType;

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
        //algo pour d�terminer quel sprite pour les murs (en fonction de la configuration des murs)
        for (int rowIndex = 0 ; rowIndex < rowCount ; ++rowIndex) {
            for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnIndex) {
                if (tiles[rowIndex][columnIndex].isWall()) {
                    //TODO : faire les if pour les sprites qui ont le plus de murs adjacents en premier (ordre d�croissant de nombre de murs par sprite)

                    if (
                            columnIndex != 0 && columnIndex != columnCount - 1 && rowIndex != 0 && rowIndex != rowCount - 1
                            && tiles[rowIndex][columnIndex-1].isWall()
                            && tiles[rowIndex][columnIndex+1].isWall()
                            && tiles[rowIndex-1][columnIndex].isWall()
                            && tiles[rowIndex+1][columnIndex].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.INT_QUAD_WALL); continue;
                    }

                    if (rowIndex != 0 && columnIndex != 0 && columnIndex != columnCount - 1
                            && tiles[rowIndex][columnIndex-1].isWall()
                            && tiles[rowIndex][columnIndex+1].isWall()
                            && tiles[rowIndex-1][columnIndex].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.INT_RIGHT_TOP_LEFT_WALL); continue;
                    }

                    if (rowIndex != 0 && rowIndex != rowCount - 1 && columnIndex != 0
                            && tiles[rowIndex][columnIndex-1].isWall()
                            && tiles[rowIndex+1][columnIndex].isWall()
                            && tiles[rowIndex-1][columnIndex].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.INT_TOP_LEFT_DOWN_WALL); continue;
                    }

                    if (rowIndex != rowCount - 1 && columnIndex != 0 && columnIndex != columnCount - 1
                            && tiles[rowIndex][columnIndex-1].isWall()
                            && tiles[rowIndex][columnIndex+1].isWall()
                            && tiles[rowIndex+1][columnIndex].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.INT_LEFT_DOWN_RIGHT_WALL); continue;
                    }

                    if (rowIndex != 0 && rowIndex != rowCount - 1 && columnIndex != columnCount - 1
                            && tiles[rowIndex-1][columnIndex].isWall()
                            && tiles[rowIndex+1][columnIndex].isWall()
                            && tiles[rowIndex][columnIndex+1].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.INT_DOWN_RIGHT_TOP_WALL); continue;
                    }

                    if (rowIndex != 0 && columnIndex != columnCount - 1
                            && tiles[rowIndex-1][columnIndex].isWall()
                            && tiles[rowIndex][columnIndex+1].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.BOT_LEFT_CORNER_WALL); continue;
                    }

                    if (rowIndex != 0 && columnIndex != 0
                            && tiles[rowIndex-1][columnIndex].isWall()
                            && tiles[rowIndex][columnIndex-1].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.BOT_RIGHT_CORNER_WALL); continue;
                    }

                    if (columnIndex != 0 && rowIndex != rowCount - 1
                            && tiles[rowIndex+1][columnIndex].isWall()
                            && tiles[rowIndex][columnIndex-1].isWall()) {
                            tiles[rowIndex][columnIndex].setSprite(TileSprite.TOP_RIGHT_CORNER_WALL); continue;
                    }

                    if (rowIndex != rowCount - 1 && columnIndex != columnCount - 1
                            && tiles[rowIndex+1][columnIndex].isWall()
                            && tiles[rowIndex][columnIndex+1].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.TOP_LEFT_CORNER_WALL); continue;
                    }

                    if (rowIndex != 0 && rowIndex != rowCount - 1
                            && tiles[rowIndex-1][columnIndex].isWall()
                            && tiles[rowIndex+1][columnIndex].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.VERTICAL_WALL); continue;
                    }

                    if (columnIndex != 0 && columnIndex != columnCount - 1
                            && tiles[rowIndex][columnIndex-1].isWall()
                            && tiles[rowIndex][columnIndex+1].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.HORIZONTAL_WALL); continue;
                    }

                    if (columnIndex != columnCount - 1
                            && tiles[rowIndex][columnIndex+1].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.EXT_LEFT_WALL); continue;
                    }

                    if (columnIndex != 0
                            && tiles[rowIndex][columnIndex-1].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.EXT_RIGHT_WALL); continue;
                    }

                    if (rowIndex != 0
                            && tiles[rowIndex-1][columnIndex].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.EXT_DOWN_WALL); continue;
                    }

                    if (rowIndex != rowCount - 1
                            && tiles[rowIndex+1][columnIndex].isWall()) {
                        tiles[rowIndex][columnIndex].setSprite(TileSprite.EXT_TOP_WALL); continue;
                    }

                    tiles[rowIndex][columnIndex].setSprite(TileSprite.LONE_WALL); continue;

                } /*Traitement des corridor avec fruits*/
                else {
                    tiles[rowIndex][columnIndex].setSprite(TileSprite.EMPTY);
                    if (tiles[rowIndex][columnIndex].getContent() != null) {
                        if (tiles[rowIndex][columnIndex].getContent().getContentType() == TileContentType.NOTHING) {
                            tiles[rowIndex][columnIndex].getContent().setSprite(TileSprite.EMPTY);
                        } else {
                            if (tiles[rowIndex][columnIndex].getContent().getContentType() == TileContentType.BERRY) {
                                tiles[rowIndex][columnIndex].getContent().setSprite(TileSprite.BERRY);
                            } else if (tiles[rowIndex][columnIndex].getContent().getContentType() == TileContentType.INVINCIBILITY) {
                                tiles[rowIndex][columnIndex].getContent().setSprite(TileSprite.INVINCIBILITY);
                            }
                        }
                    }
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

    public Pair findTilePos(Tile tile) { /*Revoie pos de la tyle en argument*/
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
