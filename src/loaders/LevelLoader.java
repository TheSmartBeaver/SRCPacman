package src.loaders;

import src.Level;
import src.engine.graphics.Color;
import src.entities.fixed.Berry;
import src.entities.fixed.InvincibilityPowerUp;
import src.entities.space.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Vincent on 01/11/2019.
 */
//TODO :
public class LevelLoader {

    public static List<Level> levels = new ArrayList<>();
    private final static int MAX_NB_OF_TP = 10;

    public static void loadLevels(String directoryName) {

        List<String> filenames = new ArrayList<>();
        File folder = new File(directoryName);
        for (File file : folder.listFiles()) {
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


        for (String filename : filenames) {
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

            if((line = bf.readLine()) != null) {
                levelWidth = Integer.parseInt(line);
            }

            if((line = bf.readLine()) != null) {
                levelHeight = Integer.parseInt(line);
            }

            Tile[][] tiles = new Tile[levelHeight][levelWidth];
            int lineNumber = 0;
            TileTeleport[] tilesTeleportFound = new TileTeleport[MAX_NB_OF_TP];
            while((line = bf.readLine()) != null) {
                for (int charNumber = 0 ; charNumber < levelWidth ; ++charNumber) {
                    Character currentChar = line.charAt(charNumber);
                    switch (currentChar) {
                        case('X'):
                        {
                            tiles[lineNumber][charNumber] = new TileWall();
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
                            break;
                        }
                        case('*'):
                        {
                            tiles[lineNumber][charNumber] = new TileCorridor(new InvincibilityPowerUp());
                            break;
                        }
                        default:
                        {
                            if ((int)currentChar >= 48 && (int)currentChar <= 57) {
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
                            } else {
                                tiles[lineNumber][charNumber] = new TileCorridor(null);
                            }
                        }
                    }
                }
                ++lineNumber;
            }

            int numLevel = Integer.parseInt(fileName.replaceFirst("[.][^.]+$", ""));
            TileMap newTileMap = new TileMap(tiles, levelHeight, levelWidth);
            newTileMap.loadSprites();
            Level level = new Level(newTileMap, numLevel);
            return level;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
