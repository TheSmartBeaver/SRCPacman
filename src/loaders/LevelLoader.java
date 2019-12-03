package src.loaders;

import src.Level;
import src.engine.graphics.generic.Color;
import src.entities.fixed.specific.Berry;
import src.entities.fixed.specific.WallhackPowerUp;
import src.entities.fixed.specific.Nothing;
import src.entities.fixed.specific.TileContentType;
import src.entities.space.generic.Tile;
import src.entities.space.generic.TileMap;
import src.entities.space.specific.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Vincent on 01/11/2019.
 */
public class LevelLoader {

    public static List<Level> levels = new ArrayList<>();
    private final static int MAX_NB_OF_TP = 10;

    public static List<Level> getLevels() {
        return levels;
    }

    public static void loadLevels(String directoryName) {

        List<String> filenames = new ArrayList<>();
        File folder = new File(directoryName);
        for (File file : folder.listFiles()) { /*On récupère toutes les maps de niveaux*/
            if (file.isFile()) {
                if (file.getName().matches(".*\\.txt")) {
                    filenames.add(file.getName());
                } else {
                    System.err.println("Les fichiers doivent �tre des fichiers txt");
                }
            } else {
                System.err.println("Le r�pertoire des niveaux ne doit contenir que des fichiers");
            }
        }


        for (String filename : filenames) { /*On ajoute les niveaux à la liste*/
            Level newLevel = loadLevel(directoryName, filename);
            if (newLevel != null) {
                levels.add(newLevel);
            }
        }
    }

    public static Level loadLevel(String directoryName, String fileName) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(directoryName + "/" + fileName));

            String line;
            int levelWidth = 0;
            int levelHeight = 0;
            int nbBerryForWin = 0;

            if((line = bf.readLine()) != null) {
                levelWidth = Integer.parseInt(line);
            }

            if((line = bf.readLine()) != null) {
                levelHeight = Integer.parseInt(line);
            }

            Tile[][] tiles = new Tile[levelHeight][levelWidth];
            int[][] tilesForA = new int[levelHeight][levelWidth];
            int lineNumber = 0;
            TileTeleport[] tilesTeleportFound = new TileTeleport[MAX_NB_OF_TP];
            while((line = bf.readLine()) != null) { /*On associe caractères à objets du jeu*/
                for (int charNumber = 0 ; charNumber < levelWidth ; ++charNumber) {
                    Character currentChar = line.charAt(charNumber);
                    switch (currentChar) {
                        case('X'):
                        {
                            tiles[lineNumber][charNumber] = new TileWall();
                            tilesForA[lineNumber][charNumber] = 100; /*Il s'agit de la tileMap pour l'algo A*   */
                            break;
                        }
                        case('P'):
                        {
                            tiles[lineNumber][charNumber] = new TilePacmanSpawn();
                            break;
                        }
                        case('G'):
                        {
                            tiles[lineNumber][charNumber] = new TileGhostSpawn();
                            break;
                        }
                        case('.'):
                        {
                            tiles[lineNumber][charNumber] = new TileCorridor(new Berry());
                            nbBerryForWin++; /*On incrémente le nombre de berry à manger pour gagner*/
                            break;
                        }
                        case('*'):
                        {
                            tiles[lineNumber][charNumber] = new TileCorridor(new WallhackPowerUp());
                            break;
                        }
                        default:
                        {
                            if ((int)currentChar >= 48 && (int)currentChar <= 57) { /*Si caractère téléportation*/
                                int idTileTeleport = Integer.parseInt(String.valueOf(currentChar));
                                if (tilesTeleportFound[idTileTeleport] == null) {
                                    tiles[lineNumber][charNumber] = new TileTeleport(null, new Color(
                                            ThreadLocalRandom.current().nextFloat(),
                                            ThreadLocalRandom.current().nextFloat(),
                                            ThreadLocalRandom.current().nextFloat()));
                                    tilesTeleportFound[idTileTeleport] = (TileTeleport)tiles[lineNumber][charNumber];
                                } else {
                                    TileTeleport tileTeleport = tilesTeleportFound[idTileTeleport];
                                    tiles[lineNumber][charNumber] = new TileTeleport(tileTeleport, tileTeleport.getColor());
                                    tileTeleport.setTileDest((TileTeleport)tiles[lineNumber][charNumber]);
                                }
                            } else { /*Si Corridor sans content*/
                                tiles[lineNumber][charNumber] = new TileCorridor(new Nothing());
                                break;
                            }
                        }
                    }
                }
                ++lineNumber;
            }

            int numLevel = Integer.parseInt(fileName.replaceFirst("[.][^.]+$", ""));
            TileMap newTileMap = new TileMap(tiles, levelHeight, levelWidth);
            loadSprites(newTileMap);
            Level level = new Level(newTileMap, numLevel, nbBerryForWin);
            level.setTilesForA(tilesForA); /*ON SET map pour A* */
            return level;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void loadSprites(TileMap tileMap) {
        int rowCount = tileMap.getRowCount();
                int columnCount = tileMap.getColumnCount();
                Tile currentTile = null;
                Tile upperTile = null;
                Tile downTile = null;
                Tile leftTile = null;
                Tile rightTile = null;
                //algo pour d�terminer quel sprite pour les murs (en fonction de la configuration des murs)
                for (int rowIndex = 0 ; rowIndex < rowCount ; ++rowIndex) {
                    for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnIndex) {
                        currentTile = tileMap.get(rowIndex, columnIndex);
                        upperTile = rowIndex > 0 ?                  tileMap.get(rowIndex-1, columnIndex) : null;
                        downTile  = rowIndex < rowCount - 1 ?       tileMap.get(rowIndex+1, columnIndex) : null;
                        leftTile  = columnIndex > 0 ?               tileMap.get(rowIndex, columnIndex-1) : null;
                        rightTile = columnIndex < columnCount - 1 ? tileMap.get(rowIndex, columnIndex+1) : null;
                if (currentTile.isWall()) {
                    //TODO : faire les if pour les sprites qui ont le plus de murs adjacents en premier (ordre d�croissant de nombre de murs par sprite)

                    if (
                            columnIndex != 0 && columnIndex != columnCount - 1 && rowIndex != 0 && rowIndex != rowCount - 1
                                    && leftTile.isWall()
                                    && rightTile.isWall()
                                    && upperTile.isWall()
                                    && downTile.isWall()) {
                        currentTile.setSprite(TileSprite.INT_QUAD_WALL); continue;
                    }

                    if (rowIndex != 0 && columnIndex != 0 && columnIndex != columnCount - 1
                            && leftTile.isWall()
                            && rightTile.isWall()
                            && upperTile.isWall()) {
                        currentTile.setSprite(TileSprite.INT_RIGHT_TOP_LEFT_WALL); continue;
                    }

                    if (rowIndex != 0 && rowIndex != rowCount - 1 && columnIndex != 0
                            && leftTile.isWall()
                            && downTile.isWall()
                            && upperTile.isWall()) {
                        currentTile.setSprite(TileSprite.INT_TOP_LEFT_DOWN_WALL); continue;
                    }

                    if (rowIndex != rowCount - 1 && columnIndex != 0 && columnIndex != columnCount - 1
                            && leftTile.isWall()
                            && rightTile.isWall()
                            && downTile.isWall()) {
                        currentTile.setSprite(TileSprite.INT_LEFT_DOWN_RIGHT_WALL); continue;
                    }

                    if (rowIndex != 0 && rowIndex != rowCount - 1 && columnIndex != columnCount - 1
                            && upperTile.isWall()
                            && downTile.isWall()
                            && rightTile.isWall()) {
                        currentTile.setSprite(TileSprite.INT_DOWN_RIGHT_TOP_WALL); continue;
                    }

                    if (rowIndex != 0 && columnIndex != columnCount - 1
                            && upperTile.isWall()
                            && rightTile.isWall()) {
                        currentTile.setSprite(TileSprite.BOT_LEFT_CORNER_WALL); continue;
                    }

                    if (rowIndex != 0 && columnIndex != 0
                            && upperTile.isWall()
                            && leftTile.isWall()) {
                        currentTile.setSprite(TileSprite.BOT_RIGHT_CORNER_WALL); continue;
                    }

                    if (columnIndex != 0 && rowIndex != rowCount - 1
                            && downTile.isWall()
                            && leftTile.isWall()) {
                        currentTile.setSprite(TileSprite.TOP_RIGHT_CORNER_WALL); continue;
                    }

                    if (rowIndex != rowCount - 1 && columnIndex != columnCount - 1
                            && downTile.isWall()
                            && rightTile.isWall()) {
                        currentTile.setSprite(TileSprite.TOP_LEFT_CORNER_WALL); continue;
                    }

                    if (rowIndex != 0 && rowIndex != rowCount - 1
                            && upperTile.isWall()
                            && downTile.isWall()) {
                        currentTile.setSprite(TileSprite.VERTICAL_WALL); continue;
                    }

                    if (columnIndex != 0 && columnIndex != columnCount - 1
                            && leftTile.isWall()
                            && rightTile.isWall()) {
                        currentTile.setSprite(TileSprite.HORIZONTAL_WALL); continue;
                    }

                    if (columnIndex != columnCount - 1
                            && rightTile.isWall()) {
                        currentTile.setSprite(TileSprite.EXT_LEFT_WALL); continue;
                    }

                    if (columnIndex != 0
                            && leftTile.isWall()) {
                        currentTile.setSprite(TileSprite.EXT_RIGHT_WALL); continue;
                    }

                    if (rowIndex != 0
                            && upperTile.isWall()) {
                        currentTile.setSprite(TileSprite.EXT_DOWN_WALL); continue;
                    }

                    if (rowIndex != rowCount - 1
                            && downTile.isWall()) {
                        currentTile.setSprite(TileSprite.EXT_TOP_WALL); continue;
                    }

                    currentTile.setSprite(TileSprite.LONE_WALL); continue;

                } /*Traitement des corridor avec fruits*/
                else {
                    currentTile.setSprite(TileSprite.EMPTY);
                    if (currentTile.getContent() != null) {
                        if (currentTile.getContent().getContentType() == TileContentType.NOTHING) {
                            currentTile.getContent().setSprite(TileSprite.EMPTY);
                        } else {
                            if (currentTile.getContent().getContentType() == TileContentType.BERRY) {
                                currentTile.getContent().setSprite(TileSprite.BERRY);
                            } else if (currentTile.getContent().getContentType() == TileContentType.INVINCIBILITY) {
                                currentTile.getContent().setSprite(TileSprite.INVINCIBILITY);
                            }
                        }
                    }
                }
            }
        }
    }
}
