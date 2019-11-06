package src.loaders;

import src.Level;
import src.entities.fixed.Berry;
import src.entities.fixed.InvincibilityPowerUp;
import src.entities.space.Tile;
import src.entities.space.TileMap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 01/11/2019.
 */
//TODO :
public class LevelLoader {

    public static List<Level> levels = new ArrayList<>();

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
            while((line = bf.readLine()) != null) {
                for (int charNumber = 0 ; charNumber < levelWidth ; ++charNumber) {
                    switch (line.charAt(charNumber)) {
                        case('X'):
                        {
                            tiles[lineNumber][charNumber] = new Tile(null, true, false, false);
                            break;
                        }
                        case('P'):
                        {
                            tiles[lineNumber][charNumber] = new Tile(null, false, false, true);
                            break;
                        }
                        case('G'):
                        {
                            tiles[lineNumber][charNumber] = new Tile(null, false, true, false);
                            break;
                        }
                        case('.'):
                        {
                            tiles[lineNumber][charNumber] = new Tile(new Berry(), false, false, false);
                            break;
                        }
                        case('*'):
                        {
                            tiles[lineNumber][charNumber] = new Tile(new InvincibilityPowerUp(), false, false, false);
                            break;
                        }
                        default:
                        {
                            tiles[lineNumber][charNumber] = new Tile(null, false, false, false);
                        }
                    }
                }
                ++lineNumber;
            }

            int numLevel = Integer.parseInt(fileName.replaceFirst("[.][^.]+$", ""));
            Level level = new Level(new TileMap(tiles, levelHeight, levelWidth), numLevel);
            return level;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
